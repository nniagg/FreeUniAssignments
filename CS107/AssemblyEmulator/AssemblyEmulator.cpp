#include <bits/stdc++.h>
#include <unistd.h>
using namespace std;

#define INF 2309943409 
int memory[100001];

void DisplayInstructions(){
    cout<<"In Assembly, there are 3 types of operations:\n";
    cout<<"The first one is loading data into the register, where data is transferred from computer memory to the register.\n";
    cout<<"For instance, the instruction 'R1=M[100]' copies four bytes from memory location 100 to the first register.\n";
    cout<<endl;
    cout<<"The next operation is the opposite, called storing data, where data is transferred from the register to memory.\n";
    cout<<"This can be done either by copying data from the register or manually inputting the value.\n";
    cout<<"Examples include 'M[100]=R1' which copies data from the first register to memory location 100, or \n";
    cout<<"'M[100]=5' which stores the integer value of 5 at memory location 100.\n";
    cout<<"Finally, the third type of operation is Arithmetic and Logical Unit (ALU) operation, which covers all arithmetic operations.\n";
    cout<<"However, there is one rule to keep in mind. You cannot combine two operations into one line.\n";
    cout<<"This emulator only supports code with one operation per line, and ALU operations (+,-,*,/) can only be performed on data stored in registers.\n";
    cout<<"Additionally, the emulator only works with integers. Other variable types, such as short and char, are not supported.\n";
    cout<<"Uninitialized values in both memory and registers are assumed to be 0.\n";
    cout<<"That concludes the instructions. Let's begin!\n";
}

long StringToInt(string num) {
    long result = 0;
    for (int i = 0; i < num.size(); i++) {
        result = result * 10 + (num[i] - '0');
    }
    return result;
}

int Operand(string str,int SP, int PC, map<string,int>& registers) {
    if (str=="PC") {
        return PC;
    }
    if (str=="SP") {
        return SP;
    }
    if (str[0]=='R') {
        return registers[str];
    }
    return StringToInt(str);
}

bool StackPointer(int& SP, int PC, string line, map<string, int>& registers) {
    string subLine = line.substr(3);
    int newSp;
    if (line[3] != 'S') {
        cout << "PC: " << PC << ". Calculating new Stack Pointer using ALU operation." << endl;
        newSp = Operand(subLine, SP, PC, registers);
        if (newSp < 0 || newSp >= 100000) {
            cout << "Stack Pointer index must be between 0 and 99999." << endl;
            return false;
        }
    } else {
        cout << "PC: " << PC << ". Calculating new Stack Pointer using ALU operation with an offset of " << subLine.substr(3) << "." << endl;
        int offset = StringToInt(subLine.substr(3));
        if (offset % 4 != 0) {
            cout << "Array elements have a size of 4 bytes. The offset value must be a multiple of 4." << endl;
            return false;
        }
        offset /= 4;
        if (line[5] == '-') {
            if (offset > SP) {
                cout << "Current Stack Pointer index is " << SP << ". It cannot be decreased by such a large value." << endl;
                return false;
            }
            newSp = SP - offset;
        } else {
            if (SP + offset >= 100000) {
                cout << "Memory is an array of 4-byte integers with a size of 100000. Current Stack Pointer index is " << SP << "." << endl;
                cout << "It cannot be increased by such a large value." << endl;
                return false;
            }
            newSp = SP + offset;
        }
    }
    SP = newSp;
    cout << "Stack Pointer updated successfully to element " << SP << "." << endl;
    return true;
}
 

bool StoreOperation(int SP, int PC, string line, map<string, int>& registers) {
    int startIndex = line.find("[");
    int endIndex = line.find("]");
    string memoryPointer = line.substr(startIndex + 1, endIndex - startIndex - 1);
    int valueToStore;
    if (line[endIndex + 2] == 'R') {
        string registerName = line.substr(endIndex + 2);
        valueToStore = registers[registerName];
    } else if (line[endIndex + 2] == 'S') {
        valueToStore = SP;
    } else if (line[endIndex + 2] == 'P') {
        valueToStore = PC;
    } else {
        valueToStore = StringToInt(line.substr(endIndex + 2));
    }
    cout << "Current PC: " << PC << ". Executing store operation, storing value " << valueToStore;
    cout << " in memory." << endl;

    if (memoryPointer == "SP") {
        memory[SP] = valueToStore;
        cout << "Store operation completed successfully. Value " << valueToStore << " stored at memory index " << SP << "." << endl;
        return true;
    }

    int memoryIndex;
    if (memoryPointer[0] == 'R') {
        memoryIndex = registers[memoryPointer];
    } else {
        memoryIndex = StringToInt(memoryPointer);
    }

    if (memoryIndex < 0 || memoryIndex >= 100000) {
        
        cout << "Invalid memory index. The memory is represented as an array of 4-byte integers with a size of 100000.";
        cout << " The index must be between 0 and 99999." << endl;
        return false;
    }

    memory[memoryIndex] = valueToStore;
    cout << "Store operation completed successfully. Value " << valueToStore << " stored at memory index " << memoryIndex << "." << endl;
    return true;
}

bool ALUOperation(string line, int operatorIndex, int ind, int SP, int PC, string reg, map<string,int>& registers){
    cout<<"Current PC - "<<PC<<".\nThis line performs ALU operation."<<endl;    

    string first=line.substr(ind+1,operatorIndex-ind-1);
    string second=line.substr(operatorIndex+1);
    int firstOp=Operand(first,SP,PC,registers);
    int secondOp=Operand(second,SP,PC,registers);
    if (first == "SP") secondOp/=4; 
    if (second == "SP") firstOp/=4;
    if (line[operatorIndex] == '+')
        registers[reg] = firstOp + secondOp;
    else if (line[operatorIndex] == '-')
        registers[reg] = firstOp - secondOp;
    else if (line[operatorIndex] == '*')
        registers[reg] = firstOp * secondOp;
    else if (line[operatorIndex] == '/')
    {
        if (secondOp == 0)
        {
            cout << "Division by zero is forbidden!.\n" << endl;
            return false;
        }
        registers[reg] = firstOp / secondOp;
    }
    cout<<"ALU operation was performed. "<<registers[reg]<<" was loaded into "<<reg<<".\n"<<endl;
    return true;
}


bool RegisterOperations(int SP,int PC,string line,map<string,int>& registers) {
    int ind = line.find("=");
    int operatorIndex = line.find_first_of("+-*/", ind + 1);
    string reg = line.substr(0, ind);

    if (operatorIndex == -1) { 
    cout << "Current PC: " << PC << ". This line performs a load operation.\n" << endl;
    char loadType = line[ind + 1];
    if (loadType == 'S') {
        registers[reg] = SP;
        cout << "Operation done. Stack pointer value has been loaded to " << reg << "." << endl;
        return true;
    }
    else if (loadType == 'P') {
        registers[reg] = PC;
        cout << "Operation done. Program counter value has been loaded to " << reg << "." << endl;
        return true;
    }
    else if (loadType == 'M') {
        int memLoc;
        if (line[ind + 3] == 'S') {
            memLoc = SP;
        }
        else if (line[ind + 3] == 'R') {
            string regBuff = line.substr(ind + 3, line.size() - ind - 4);
            int regBuffInd = stoi(regBuff.substr(1));
            memLoc = registers[regBuff];
        }
        else {
            memLoc = stoi(line.substr(ind + 3));
        }
        if (memLoc < 0 || memLoc >= 100000) {
            cout << "Illegal memory index. The index must be between 0 and 100000." << endl;
            return false;
        }
        registers[reg] = memory[memLoc];
        cout << "Operation done. Data from memory location " << memLoc << " has been loaded to " << reg << "." << endl;
        return true;
    }
    else if (loadType == 'R') {
        string regBuff = line.substr(ind + 1);
        registers[reg] = registers[regBuff];
        cout << "Operation done. Data from " << regBuff << " has been loaded to " << reg << "." << endl;
        return true;
    }
    else {
        int numToLoad = stoi(line.substr(ind + 1));
        registers[reg] = numToLoad;
        cout << "Operation done. " << numToLoad << " has been loaded to " << reg << "." << endl;
        return true;
    }
}
    return ALUOperation(line, operatorIndex, ind, SP, PC, reg, registers);
}

bool PrintfOperation(string line,int SP, int PC, map<string,int>& registers) {
    string askedInfo = line.substr(7,line.size()-8);
    cout<<"Current PC - "<<PC<<".\n";
    if (askedInfo[0]=='S') {
        cout<<"Stack pointer currently pointers to the element with index "<<SP<<" of the memory.\n"<<endl;
        return true;
    }
    if (askedInfo[0]=='P') {
        cout<<"Current Program Counter is "<<PC<<".\n"<<endl;
        return true;
    }
    if (askedInfo=="RV") {
        cout<<"Data loaded in RV register is "<<registers[askedInfo]<<".\n"<<endl;
        return true;
    }
    if (askedInfo[0]=='R') {
        cout<<"Data loaded in "<<askedInfo<<" is: "<<registers[askedInfo]<<".\n"<<endl;
        return true;
    }
    if (askedInfo[0]=='M')  {
        if (askedInfo[2]=='S') {
            cout<<"Data loaded in memory location "<<SP<<" is "<<memory[SP]<<".\n"<<endl;
            return true;
        }
        if (askedInfo[2] == 'R') {
            string reg = askedInfo.substr(2,askedInfo.size()-3);
            cout<<"Data loaded in memory location "<<registers[reg]<<" is "<<memory[registers[reg]]<<".\n"<<endl;
            return true;
        }
        int memLoc = StringToInt(askedInfo.substr(2,askedInfo.size()-3));
        if (memLoc<0 || memLoc >= 100000) {
            cout<<"Illegal index. Computer memory is represented as an array of 4-byte ";
            cout<<"integers with the size of 100000. Therefore, index cannot be negative or more than 100000.\n"<<endl;
            return false;
        }
        cout<<"Data loaded in memory location "<<memLoc<<" is "<<memory[memLoc]<<".\n"<<endl;
        return true;
    }
    cout<<askedInfo<<"\n"<<endl;
    return true;
}

bool Branch(string line, int SP, int& PC,int size, map<string,int>& registers) {
    int firstComma = -1;
    int secondComma = -1;
    cout << "Current PC - " << PC << ".\nThis line is for branch instructions. Please note that the operands in the test ";
    cout << "of a branch statement must be in registers or constant values.\n";
    for (int i = 3; i < line.size(); i++) {
        if (line[i] == ',') {
            if (firstComma == -1) {
                firstComma = i;
            } else {
                secondComma = i;
                break;
            }
        }
    }
    if (firstComma != -1 && secondComma == -1) {
        secondComma = line.size();
    }

    int newPC;
    if (line[secondComma+1]=='P') {
        int delta=StringToInt(line.substr(secondComma+4));
        if (delta % 4 != 0)  {
            cout<<"The increment of Program Counter must be multiple of 4.\n"<<endl;
            return false;
        }
        if (line[secondComma+3]=='+')   {
            if ((PC+delta)/4>=size) {
                cout<<"Can't change program counter by such a big value. Count lines of your code once again!\n"<<endl;
                return false;
            }
            newPC=PC+delta;
        }
        if (line[secondComma+3]=='-') {
            if (PC-delta+4<0) {
                cout<<"Can't change program counter by such a big value. Count lines of your code once again!\n"<<endl;
                return false;
            }
            newPC=PC-delta;
        }
    }
    else {
        newPC=StringToInt(line.substr(secondComma+1));
        if (newPC % 4 != 0)  {
            cout<<"The value of Program Counter must always be multiple of 4.\n"<<endl;
            return false;
        }
        if (newPC/4>=size) {
            cout<<"Program Counter can't be equal to such a big value. Count lines of your code once again!\n"<<endl;
            return false;
        }
    }
    int firstOperand=Operand(line.substr(3,firstComma-3),SP,PC,registers);
    int secondOperand=Operand(line.substr(firstComma+1,secondComma-firstComma-1),SP,PC,registers);
    string op=line.substr(1,2);
    if (op == "LT") {
        if (firstOperand<secondOperand) {
            cout<<"Branch condition is true. Program Counter will be changed to "<<newPC<<".\n"<<endl;
            PC=newPC;
        }   else {
            cout<<"Branch condition is false. Emulator will just continue executing instructions from the next line.\n"<<endl;
            PC+=4;
        }
        return true;    
    }
    if (op == "LE") {
        if (firstOperand<=secondOperand) {
            cout<<"Branch condition is true. Program Counter will be changed to "<<newPC<<".\n"<<endl;
            PC=newPC;
        }   else {
            cout<<"Branch condition is false. Emulator will just continue executing instructions from the next line.\n"<<endl;
            PC+=4;
        }
        return true;    
    }
    if (op == "GT") {
        if (firstOperand>secondOperand) {
            cout<<"Branch condition is true. Program Counter will be changed to "<<newPC<<".\n"<<endl;
            PC=newPC;
        }   else {
            cout<<"Branch condition is false. Emulator will just continue executing instructions from the next line.\n"<<endl;
            PC+=4;
        }
        return true;    
    }
    if (op == "GE") {
        if (firstOperand>=secondOperand) {
            cout<<"Branch condition is true. Program Counter will be changed to "<<newPC<<".\n"<<endl;
            PC=newPC;
        }   else {
            cout<<"Branch condition is false. Emulator will just continue executing instructions from the next line.\n"<<endl;
            PC+=4;
        }
        return true;    
    }
    if (op == "EQ") {
        if (firstOperand==secondOperand) {
            cout<<"Branch condition is true. Program Counter will be changed to "<<newPC<<".\n"<<endl;
            PC=newPC;
        }   else {
            cout<<"Branch condition is false. Emulator will just continue executing instructions from the next line.\n"<<endl;
            PC+=4;
        }
        return true;    
    }
    if (op == "NE") {
        if (firstOperand!=secondOperand) {
            cout<<"Branch condition is true. Program Counter will be changed to "<<newPC<<".\n"<<endl;
            PC+=PC;
        }   else {
            cout<<"Branch condition is false. Emulator will just continue executing instructions from the next line.\n"<<endl;
            PC+=4;
        }
        return true;    
    }
    return false;
}
bool JumpInstruction(string line, int& PC, int size) {
    cout << "Current PC: " << PC << ". This line performs jump operation." << endl;
    int newPC;

    if (line.substr(3, 3) == "PC+") {
        int delta = StringToInt(line.substr(6));
        if (delta % 4 != 0) {
     
            cout << "The increment of Program Counter should be a multiple of 4." << endl;
            return false;
        }
        newPC = PC + delta;
    } else {
        newPC = StringToInt(line.substr(3));
        if (newPC % 4 != 0) {
            
            cout << "The value of Program Counter should always be a multiple of 4." << endl;
            return false;
        }
    }

    if (newPC / 4 >= size) {
        
        cout << "Invalid value for Program Counter." << endl;
        return false;
    }
    PC = newPC;
    cout << "Jump operation executed. PC: " << PC << "." << endl;
    return true;
}


bool ValidExpression(string s) {
    bool hasOperand = false;
    bool hasOperator = false;
    for (char c : s) {
        if (c == 'M') {
        hasOperand = true;
        } else if (c == '+' || c == '-' || c == '*' || c == '/') {
            if (!hasOperand) return false;
            hasOperator = true;
            hasOperand = false;
        } else if (!isdigit(c)) {
            return false;
        } else {
            hasOperand = true;
        }
    }
    return hasOperand && hasOperator;
}

void Process(vector <string>& lines,map<string,int>& registers,map<string,pair<int,int> >& functions) {
    int RV=INF,SP=1000,PC=0;
    stack<string> funcs;
    bool retVal = false;
    int st = 0;
    cout<<"\nStack Pointer points to "<<SP<<" .\n\n";
    while (PC/4 < lines.size()) {
        string line = lines[PC/4];
        if (line[0]!='J' && line[0]!='P')
        if (ValidExpression(line)) {
            cout<<"Instruction on line "<<PC/4+1<<" could not be executed.\n"<<endl;
            cout<<"More than one operation per line cannot be performed\n"<<endl;
            return;
        }   
        if (line.substr(0,2) == "SP") {
            if (StackPointer(SP,PC,line,registers)) {;
                PC+=4;
                continue;
            }   else {
                cout<<"Instruction on line "<<PC/4+1<<" could not be executed.\n"<<endl;
                return;
            }
        }
        if (line[0] == 'M') {
            if (StoreOperation(SP, PC, line, registers)) {
                PC+=4;
                continue;
            }   else {
                cout<<"Instruction on line "<<PC/4+1<<" could not be executed.\n"<<endl;
                return;
            }
        }
        if (line=="RET") {
            cout<<"Execution of "<<funcs.top()<<" has finished.\n";
            if (retVal) {
                cout<<"This function returned "<<registers["RV"]<<".\n"<<endl;
            }   else {
                cout<<"This was void function.\n"<<endl;
            }
            PC=memory[SP];
            SP=SP+1;
            funcs.pop();
            continue;
        }
        if (line[0] == 'R') {
            if (line[1]=='V') retVal=true;
            if (RegisterOperations(SP,PC,line,registers)) {
                PC+=4;
                continue;
            }   else {
                cout<<"Instruction on line "<<PC/4+1<<" could not be executed.\n"<<endl;
                return;
            }
        }
        if (line.substr(0,6) == "PRINTF") {
           if (PrintfOperation(line,SP,PC,registers)) {
               PC+=4;
               continue;
            }   else {
                cout<<"Instruction on line "<<PC/4+1<<" could not be executed.\n"<<endl;
                return;
            }
        }
        if (line[0]=='B') {
            if (Branch(line,SP,PC,lines.size(),registers)) {
                continue;
            }   else {
                cout<<"Instruction on line "<<PC/4+1<<" could not be executed.\n"<<endl;
                return;
            }
        }
        if (line[0]=='J') {
            if (JumpInstruction(line,PC,lines.size())) {
                continue;
            }   else {
                cout<<"Instruction on line "<<PC/4+1<<" could not be executed.\n"<<endl;
                return;
            }
        }
        if (line[0]=='D') {
            string functionDef=line.substr(7,line.size()-8);
            PC+=4;
            functions[functionDef].first=PC;
            functions[functionDef].second=-1;
            while (lines[PC/4]!="END_DEF") PC+=4;
            PC+=4;
        }
        if (line[0]=='C') {
            string functionCall=line.substr(5,line.size()-6);
            if (functions.find(functionCall)==functions.end()) {
                cout<<"The called function was not defined.\n";
                cout<<"Instruction on line "<<PC/4+1<<" could not be executed.\n"<<endl;
                return;
            }
            cout<<"Calling function "<<functionCall<<".\n";
            cout<<"Program Counter: "<<functions[functionCall].first<<endl;
            SP=SP-1;
            memory[SP]=PC+4;
            functions[functionCall].second=PC+4;
            PC=functions[functionCall].first;
            funcs.push(functionCall);
            continue;
        }
    }
}

int main() { 
    DisplayInstructions();
    vector <string> lines;
    map<string,int> registers;
    map<string,pair<int, int> > functions;
    
    cout<<"Enter file name: ";
    string testFile;
    cin>>testFile;
    ifstream myfile(testFile);
    if(myfile.is_open()){
        string line;
        while(getline(myfile, line)){
            if(line=="")break;
            lines.push_back(line);
        }
    }
    Process(lines,registers,functions);
    return 0;
}