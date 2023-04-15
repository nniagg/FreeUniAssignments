using namespace std;
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/mman.h>
#include <fcntl.h>
#include <unistd.h>
#include <string.h>
#include "imdb.h"
#include <cstring>
#include <iostream>

const char *const imdb::kActorFileName = "actordata";
const char *const imdb::kMovieFileName = "moviedata";

imdb::imdb(const string& directory)
{
  const string actorFileName = directory + "/" + kActorFileName;
  const string movieFileName = directory + "/" + kMovieFileName;
  
  actorFile = acquireFileMap(actorFileName, actorInfo);
  movieFile = acquireFileMap(movieFileName, movieInfo);
}

bool imdb::good() const
{
  return !( (actorInfo.fd == -1) || 
	    (movieInfo.fd == -1) ); 
}



// void* binarySearch(const void* currActor, int* fileStart, int lo, int hi){
//   int* lo = fileStart+1;
//   int* hi = *fileStart+fileStart;
//   int mid;
  
//   char* loActorptr = (char*)fileStart+lo;
//   char* hiActorptr = (char*)fileStart+hi;

//   char* loActor = "";
//   char* hiActor = "";
//   strcpy(loActor, loActorptr);
//   strcpy(hiActor, hiActorptr);

//   char* currAct = "";
//   strcpy(currAct, (const char *)currActor);
//   if((string)hiActor >= (string)loActor){
//     mid = lo-hi;
//     char* midActorptr = (char*)fileStart+mid;
//     char* midActor = "";
//     strcpy(midActor, midActorptr);
//     if((string)midActor == (string)currAct)return &midActorptr;
//     if((string)midActor > (string)currAct)return binarySearch(currActor, fileStart, lo, mid-1);
//     return binarySearch(currActor, fileStart, mid+1, hi);
//   }
//   return NULL;
// }

int imdb::comparator1(const void* a, const void* b){
  char* file = (char*)(((key*)a)->fileptr);
  char* firstptr = (char*)(((key*)a)->key_tosearch);
  // char* firstptr1 = (char*)(file+*firstptr);

  char first[100];
  strcpy(first, firstptr);
  //  cout << (string)first << endl;
  // cout << "aqmovida" << endl;
  char* secondptr = (char*)(file+*(int*)b);
  char second[100];
  // cout << string(second) << endl;
  strcpy(second, secondptr);
  return strcmp(first, second);
}

int imdb::comparator2(const void* a, const void* b){
  char* moviefile = (char*)(((movieKey*)a)->fileptr);
  film firstMovie = (((movieKey*)a)->movie);

  char* secondptr = (char*)(moviefile+*(int*)b);
  film secondMovie;
  secondMovie.title=(string)secondptr;
  secondMovie.year=1900+(int)(*(secondptr+secondMovie.title.length()+1));
  
  if(firstMovie==secondMovie)return 0;
  else if(firstMovie < secondMovie)return -1;
  else return 1;
}
// you should be implementing these two methods right here... 
bool imdb::getCredits(const string& player, vector<film>& films) const {
  const char* currActor = &(player[0]);
  
  key k;
  k.fileptr=actorFile;
  k.key_tosearch= currActor;

  int* actor = (int*)bsearch(&k, (int*)actorFile+1, *(int*)actorFile, sizeof(int), comparator1);


  if(actor==NULL)return false;
  char *found = (char*)(actorFile + *(actor));
  int reminder = (player.length() + 1)%2;
  short* moviesQuantity = (short*)((char*)actorFile+*actor+player.length()+reminder+1);
  int afterQuantRemainder = (player.length() + 1+ reminder + 2) % 4;
  int* currMovieOffset = (int*)((char*)moviesQuantity+2+afterQuantRemainder);
 
   
  for(int i = 0; i < *moviesQuantity; i++){
    film movie;
    char* currMovie = (char*)((char*)movieFile+(*currMovieOffset));
    movie.title = currMovie; 
    char* yearptr = (char*)(currMovie+movie.title.length()+1);
    int yr = 1900+(int)(*yearptr);
    movie.year=yr;
    films.push_back(movie);
    currMovieOffset++;
  }
  return true;
}

bool imdb::getCast(const film& movie, vector<string>& players) const {
  movieKey k;
  k.fileptr=movieFile;
  k.movie=movie;
  int* found = (int*)bsearch(&k, (int*)movieFile+1, *((int*)movieFile), sizeof(int), comparator2);
  if(found==NULL)return false;

  char* movieY = (char*)((char*)movieFile+*found+movie.title.length()+1);
  int remainder = (movie.title.length()+2) % 2;
  short* numOfActorsptr = (short*)(movieY+remainder+1);

  
  int secondRemainder = (movie.title.length()+2 + remainder + 2) % 4;
  int* currActorptr = (int*)((char*)numOfActorsptr+2 + secondRemainder);
  for(int i = 0; i < *numOfActorsptr; i++){
    char* act =(char*)((char*)actorFile+*currActorptr);
    players.push_back(act);
    currActorptr++;
  }
  return true; 
}

imdb::~imdb()
{
  releaseFileMap(actorInfo);
  releaseFileMap(movieInfo);
}

// ignore everything below... it's all UNIXy stuff in place to make a file look like
// an array of bytes in RAM.. 
const void *imdb::acquireFileMap(const string& fileName, struct fileInfo& info)
{
  struct stat stats;
  stat(fileName.c_str(), &stats);
  info.fileSize = stats.st_size;
  info.fd = open(fileName.c_str(), O_RDONLY);
  return info.fileMap = mmap(0, info.fileSize, PROT_READ, MAP_SHARED, info.fd, 0);
}

void imdb::releaseFileMap(struct fileInfo& info)
{
  if (info.fileMap != NULL) munmap((char *) info.fileMap, info.fileSize);
  if (info.fd != -1) close(info.fd);
}
