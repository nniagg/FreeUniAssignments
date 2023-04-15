#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <assert.h>
#include <inttypes.h>

#include "teller.h"
#include "account.h"
#include "error.h"
#include "debug.h"
#include "branch.h"

BranchID
AccountNum_GetBranchID(AccountNumber accountNum);
/*
 * deposit money into an account
 */
int
Teller_DoDeposit(Bank *bank, AccountNumber accountNum, AccountAmount amount)
{
  assert(amount >= 0);

  DPRINTF('t', ("Teller_DoDeposit(account 0x%"PRIx64" amount %"PRId64")\n",
                accountNum, amount));

  Account *account = Account_LookupByNumber(bank, accountNum);

  if (account == NULL) {
    return ERROR_ACCOUNT_NOT_FOUND;
  }

  sem_wait(&(account->LockAccount));
  sem_wait(&(bank->branches[AccountNum_GetBranchID(accountNum)].lockBranch));

  Account_Adjust(bank,account, amount, 1);
  sem_post(&(account->LockAccount));
  sem_post(&(bank->branches[AccountNum_GetBranchID(accountNum)].lockBranch));
  return ERROR_SUCCESS;
}

/*
 * withdraw money from an account
 */
int
Teller_DoWithdraw(Bank *bank, AccountNumber accountNum, AccountAmount amount)
{
  assert(amount >= 0);

  DPRINTF('t', ("Teller_DoWithdraw(account 0x%"PRIx64" amount %"PRId64")\n",
                accountNum, amount));

  Account *account = Account_LookupByNumber(bank, accountNum);
  sem_wait(&(account->LockAccount));
  sem_wait(&(bank->branches[AccountNum_GetBranchID(accountNum)].lockBranch));
  if (account == NULL) {
    return ERROR_ACCOUNT_NOT_FOUND;
  }

  if (amount > Account_Balance(account)) {
    sem_post(&(account->LockAccount));
    sem_post(&(bank->branches[AccountNum_GetBranchID(accountNum)].lockBranch));
    return ERROR_INSUFFICIENT_FUNDS;
  }

  Account_Adjust(bank,account, -amount, 1);
  sem_post(&(account->LockAccount));
  sem_post(&(bank->branches[AccountNum_GetBranchID(accountNum)].lockBranch));
  return ERROR_SUCCESS;
}

/*
 * do a tranfer from one account to another account
 */
int
Teller_DoTransfer(Bank *bank, AccountNumber srcAccountNum,
                  AccountNumber dstAccountNum,
                  AccountAmount amount)
{
  assert(amount >= 0);

  DPRINTF('t', ("Teller_DoTransfer(src 0x%"PRIx64", dst 0x%"PRIx64
                ", amount %"PRId64")\n",
                srcAccountNum, dstAccountNum, amount));

  Account *srcAccount = Account_LookupByNumber(bank, srcAccountNum);
  if (srcAccount == NULL) {
    return ERROR_ACCOUNT_NOT_FOUND;
  }

  Account *dstAccount = Account_LookupByNumber(bank, dstAccountNum);
  if (dstAccount == NULL) {
    return ERROR_ACCOUNT_NOT_FOUND;
  }

  if(srcAccount==dstAccount)return ERROR_SUCCESS;
  int sameBranches = !Account_IsSameBranch(srcAccountNum, dstAccountNum);
  if(sameBranches==0){
     if(srcAccount->accountNumber < dstAccount->accountNumber){
      sem_wait(&(srcAccount->LockAccount));
      sem_wait(&(dstAccount->LockAccount));
    } else{
      sem_wait(&(dstAccount->LockAccount));
      sem_wait(&(srcAccount->LockAccount));
    }
    if (amount > Account_Balance(srcAccount)) {
      sem_post(&(srcAccount->LockAccount));
      sem_post(&(dstAccount->LockAccount));
      
      return ERROR_INSUFFICIENT_FUNDS;
  }

  // if (amount > Account_Balance(srcAccount)) {
  //   return ERROR_INSUFFICIENT_FUNDS;
  // }

  /*
   * If we are doing a transfer within the branch, we tell the Account module to
   * not bother updating the branch balance since the net change for the
   * branch is 0.
   */
  Account_Adjust(bank, srcAccount, -amount, sameBranches);
  Account_Adjust(bank, dstAccount, amount, sameBranches);
  
  sem_post(&(srcAccount->LockAccount));
  sem_post(&(dstAccount->LockAccount));
  
  return ERROR_SUCCESS;

  } else {
    int sourceBranch=AccountNum_GetBranchID(srcAccountNum);
    int destBranch=AccountNum_GetBranchID(dstAccountNum);
    
    if(sourceBranch < destBranch){
      sem_wait(&(srcAccount->LockAccount));
      sem_wait(&(dstAccount->LockAccount));
      sem_wait(&(bank->branches[sourceBranch].lockBranch));
      sem_wait(&(bank->branches[destBranch].lockBranch));
    } else {
      sem_wait(&(dstAccount->LockAccount));
      sem_wait(&(srcAccount->LockAccount));
      sem_wait(&(bank->branches[destBranch].lockBranch));
      sem_wait(&(bank->branches[sourceBranch].lockBranch));
    }

    if (amount > Account_Balance(srcAccount)) {
      sem_post(&(srcAccount->LockAccount));
      sem_post(&(dstAccount->LockAccount));
      sem_post(&(bank->branches[destBranch].lockBranch));
      sem_post(&(bank->branches[sourceBranch].lockBranch));
      return ERROR_INSUFFICIENT_FUNDS;
    } 

    Account_Adjust(bank, srcAccount, -amount, sameBranches);
    Account_Adjust(bank, dstAccount, amount, sameBranches);
    
    sem_post(&(srcAccount->LockAccount));
    sem_post(&(dstAccount->LockAccount));
    sem_post(&(bank->branches[destBranch].lockBranch));
    sem_post(&(bank->branches[sourceBranch].lockBranch));
    
    return ERROR_SUCCESS;
  }
}
