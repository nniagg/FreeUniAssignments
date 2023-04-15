#include "hashset.h"
#include <assert.h>
#include <stdlib.h>
#include <string.h>
//#include <stdio.h>

void HashSetNew(hashset *h, int elemSize, int numBuckets,
		HashSetHashFunction hashfn, HashSetCompareFunction comparefn, HashSetFreeFunction freefn)
{
	assert(h!=NULL);
	assert(elemSize>=0 && numBuckets>0 && hashfn!=NULL && comparefn!=NULL);
	h->elemSize=elemSize;
	h->comparefn=comparefn;
	h->freefn=freefn;
	h->hashfn=hashfn;
	h->numBuckets=numBuckets;
	h->data=malloc(numBuckets*sizeof(vector));
	h->loglen=0;
	assert(h->data!=NULL);
	for(int i = 0; i < numBuckets; i++){
		VectorNew(&h->data[i], elemSize, freefn, 0);
	}
}

void HashSetDispose(hashset *h)
{
	assert(h!=NULL);
	for(int i = 0; i < h->numBuckets; i++)VectorDispose(&h->data[i]);
	free(h->data);
}

int HashSetCount(const hashset *h)
{ 
	assert(h!=NULL);
	return h->loglen; 
}

void HashSetMap(hashset *h, HashSetMapFunction mapfn, void *auxData)
{
	assert(h!=NULL);
	assert(mapfn!=NULL);
	for(int i=0; i < h->numBuckets; i++){
		// mapfn(&h->data[i], auxData);
		VectorMap(&(h->data[i]), mapfn, auxData);
	}
}
/**
 * Function: HashSetEnter
 * ----------------------
 * Inserts the specified element into the specified
 * hashset.  If the specified element matches an
 * element previously inserted (as far as the hash
 * and compare functions are concerned), the the
 * old element is replaced by this new element.
 *
 * An assert is raised if the specified address is NULL, or
 * if the embedded hash function somehow computes a hash code
 * for the element that is out of the [0, numBuckets) range.
 */
void HashSetEnter(hashset *h, const void *elemAddr)
{
	assert(h!=NULL);
	assert(elemAddr!=NULL);
	int code = h->hashfn(elemAddr, h->numBuckets);
	assert(code>=0 && code<h->numBuckets);
	int found = VectorSearch(&h->data[code], elemAddr, h->comparefn, 0, false);
	if(found==-1)VectorAppend(&h->data[code], elemAddr);
	else VectorReplace(&h->data[code], elemAddr, found);
	if(found==-1)h->loglen++;
}
/**
 * Function: HashSetLookup
 * -----------------------
 * Examines the specified hashset to see if anything matches
 * the item residing at the specified elemAddr.  If a match
 * is found, then the address of the stored item is returned.
 * If no match is found, then NULL is returned as a sentinel.
 * Understand that the key (residing at elemAddr) only needs
 * to match a stored element as far as the hash and compare
 * functions are concerned.
 *
 * An assert is raised if the specified address is NULL, or
 * if the embedded hash function somehow computes a hash code
 * for the element that is out of the [0, numBuckets) range.
 */
void *HashSetLookup(const hashset *h, const void *elemAddr)
{ 
	assert(h!=NULL);
	assert(elemAddr!=NULL);
	int code = h->hashfn(elemAddr, h->numBuckets);
	assert(code>=0 && code<h->numBuckets);
	int found = VectorSearch(&h->data[code], elemAddr, h->comparefn, 0, false);
	//fprintf(stdout, "\n%d\n", found);
	if(found!=-1)return VectorNth(&h->data[code], found);
	return NULL; 
}
