#include "vector.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <search.h>
#include <assert.h>

static const int VECTOR_DEFAULT_SIZE=4;
void VectorNew(vector *v, int elemSize, VectorFreeFunction freeFn, int initialAllocation)
{
    assert(v!=NULL);
    assert(elemSize>0 && initialAllocation>=0 && elemSize>0);
    v->aloclen=(initialAllocation==0) ? VECTOR_DEFAULT_SIZE : initialAllocation;
    v->loglen=0;
    v->elemSize=elemSize;
    v->freeFn=freeFn;
    v->data=malloc(v->aloclen*v->elemSize);
    assert(v->data!=NULL);
}

void VectorDispose(vector *v)
{
    assert(v!=NULL);
    if(v->freeFn!=NULL){
        for(int i = 0; i < v->loglen; i++)v->freeFn((char*)v->data+i*v->elemSize);
    }
    free(v->data);
}

int VectorLength(const vector *v)
{ 
    assert(v!=NULL);
    return v->loglen; 
}

void *VectorNth(const vector *v, int position)
{ 
    // assert(v!=NULL);
    assert(position>=0);
    //  <= sheilea
    assert(position<v->loglen);
    // char* res = (char*)v->data+position*v->elemSize;
    return ((char*)v->data+(position*v->elemSize));
}

void VectorReplace(vector *v, const void *elemAddr, int position)
{
    assert(v!=NULL);
    assert(position>=0);
    assert(position<=v->loglen);
    // char* currElem=VectorNth(v, position);
    //aqac sheilbea schirdebaaadsd
    if(v->freeFn!=NULL)v->freeFn((char*)(v->data)+position*v->elemSize);

    memcpy((char*)(v->data)+position*v->elemSize, elemAddr, v->elemSize);
}

void VectorInsert(vector *v, const void *elemAddr, int position)
{
    assert(v!=NULL);
    assert(position>=0 && position<=v->loglen && elemAddr!=NULL);
    if(v->loglen==v->aloclen){
        v->aloclen*=2;
        v->data=realloc(v->data, v->aloclen*(v->elemSize));
        assert(v->data!=NULL);
    }
    
    if(position<v->loglen){
        // char* memtocopy=VectorNth(v, position);
        // char* destmem = VectorNth(v, position+1);
        // int memsize = v->elemSize*(v->loglen-position-1);
        // memmove(destmem, memtocopy, memsize);
        memmove((char*)(v->data)+(position+1)*v->elemSize, (char*)(v->data)+position*v->elemSize, v->elemSize*(v->loglen-position));
    }
    memcpy((char*)(v->data)+v->elemSize*position, elemAddr, v->elemSize);
    v->loglen++;
}

void VectorAppend(vector *v, const void *elemAddr)
{
    assert(v!=NULL);
    assert(elemAddr!=NULL);
    // if(v->loglen==v->aloclen){
    //     v->aloclen*=2;
    //     v->data=realloc(v->data, v->aloclen*v->elemSize);
    //     assert(v!=NULL);
    // }
    // char* currElem = (char*)VectorNth(v, v->loglen-1);
    // memcpy(currElem, elemAddr, v->elemSize);
    // v->loglen++;
    VectorInsert(v, elemAddr, v->loglen);
}

void VectorDelete(vector *v, int position)
{
    assert(v!=NULL);
    assert(position>=0 && position<v->loglen);
    // char* currElem = VectorNth(v, position);
    if(v->freeFn!=NULL)v->freeFn((char*)(v->data)+position*v->elemSize);
    if(position!=v->loglen-1){
        char* addr = (char*)(VectorNth(v, position));
        char* memtocopy = (char*)(VectorNth(v, position+1));
        // char* addr = (char*)((char*)(v->data)+position*v->elemSize);
        // char* memtocopy = (char*)((char*)(v->data)+(position+1)*v->elemSize);
        memmove(addr, memtocopy, v->elemSize*(v->loglen-position+1));
    }
    v->loglen--;
}

void VectorSort(vector *v, VectorCompareFunction compare)
{
    // assert(v!=NULL);
    assert(compare!=NULL);
    qsort(v->data, v->loglen, v->elemSize, compare);
}

void VectorMap(vector *v, VectorMapFunction mapFn, void *auxData)
{
    assert(v!=NULL);
    assert(mapFn!=NULL);
    for(int i = 0; i < v->loglen; i++){
       
        mapFn((char*)(v->data)+i*v->elemSize, auxData);    
    }
}


static const int kNotFound = -1;
int VectorSearch(const vector *v, const void *key, VectorCompareFunction searchFn, int startIndex, bool isSorted)
{
    assert(v!=NULL);
    assert(key != NULL);
    assert(searchFn!=NULL);
    assert(startIndex>=0);
    assert(startIndex<=v->loglen);
    // && searchFn!=NULL && startIndex>=0 && startIndex<v->loglen);
    if(startIndex==v->loglen)return -1;
    if(isSorted){
        void* found = bsearch(key, (char*)v->data+startIndex*v->elemSize, (size_t)(v->loglen-startIndex), v->elemSize, searchFn);
        if((char*)found!=NULL)return ((char*)found-(char*)v->data)/v->elemSize;
        else return kNotFound;
    }
    size_t size = v->loglen-startIndex
;    void* found = lfind(key, (char*)v->data+startIndex*v->elemSize, &size, v->elemSize, searchFn);
    if((char*)found!=NULL)return ((char*)found-(char*)v->data)/v->elemSize;
    return kNotFound;  
} 
