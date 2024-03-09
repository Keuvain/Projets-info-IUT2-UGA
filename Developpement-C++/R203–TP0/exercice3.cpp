#include <cstdlib>
#include <iostream>
#include <iomanip>
#include <vector>

using namespace std;

template<class T>
void afficheVecteur(const vector<T> v) {
    // {v.size() > 0} => {pretty print de v}
    cout << "[" << v[0];
    int i = 1;
    while (i < v.size()) {
        cout << ", " << v[i];
        i++;
    }
    cout << "]" << endl;
}

template<class T>
void minMaxIter(const vector<T> v, T &min, T &max, int &nbComp) {
    // {v.size()≥ 1} => {min = plus petite valeur de v,
    //                   max = plus grande valeur de v,
    //                   nbcomp = nombre de comparaisons impliquant au moins un élément de v}

    nbComp = 0;
    max = min = v[0];
    int i = 1;
    while ( i < (int) v.size()) {
        T item = v[i];
        if (item < min) {
            min = item;
        }
        if (item > max) {
            max = item;
        }
        nbComp += 2;
        i++;
    }
}

template<class T>
void minMaxRec(const vector<T>& v, int inf, int sup, T &min, T &max, int &nbComp) {
    if (inf == sup) {
        min = max = v[inf];
    } else if (sup == inf + 1) {
        if (v[inf] < v[sup]) {
            min = v[inf];
            max = v[sup];
        } else {
            min = v[sup];
            max = v[inf];
        }
        nbComp += 1;
    } else {
        int pivot = (int) (inf + sup) / 2;
        int minL, maxL, minR, maxR;
        minMaxRec(v, inf, pivot, minL, maxL, nbComp);
        minMaxRec(v, pivot + 1, sup, minR, maxR, nbComp);
        min = (minL < minR) ? minL : minR;
        max = (maxL > maxR) ? maxL : maxR;
        nbComp += 2;

    }
}


void testMinMaxIter() {
    cout << "***************************" << endl;
    cout << "*        min max iter     *" << endl;
    cout << "***************************" << endl;

    int min;
    int max;
    int nbComp = 0;

    vector<int> v = {10};
    minMaxIter(v, min, max, nbComp);
    cout << "v : ";
    afficheVecteur(v);
    cout << "Taille du vecteur = " << v.size() << " -> " << " min = " << min << ", max = " << max << ", nbComp = " << nbComp << endl << endl;

    nbComp = 0;
    v = {10, 3, 6, 8, 67, 2, 9, 1};
    minMaxIter(v, min, max, nbComp);
    cout << "v : ";
    afficheVecteur(v);
    cout << "Taille du vecteur = " << v.size() << " -> " << " min = " << min << ", max = " << max << ", nbComp = " << nbComp << endl << endl;

    nbComp = 0;
    v.clear();
    srand((unsigned)time(NULL));
    for (int i =0; i < 128; i++){
        int b = rand() % 128 + 1;
        v.push_back(b);
    }
    minMaxIter(v, min, max, nbComp);
    cout << "v : ";
    afficheVecteur(v);
    cout << "Taille du vecteur = " << v.size() << " -> " << " min = " << min << ", max = " << max << ", nbComp = " << nbComp << endl << endl;
}

void testMinMaxDR() {
    cout << "***************************" << endl;
    cout << "*         min max DR      *" << endl;
    cout << "***************************" << endl;

    int min;
    int max;
    int inf;
    int sup;
    int nbComp = 0;

    vector<int> v = {10};
    inf = 0;
    sup = v.size()-1;
    minMaxRec(v, inf, sup, min, max, nbComp);
    cout << "v : ";
    afficheVecteur(v);
    cout << "Taille du vecteur = " << v.size() <<  " -> min = " << min << ", max = " << max << ", nbComp = " << nbComp << endl << endl;

    nbComp = 0;
    v = {10, 3, 6, 8, 67, 2, 9, 1};
    inf = 0;
    sup = v.size()-1;
    minMaxRec(v, inf, sup, min, max, nbComp);
    cout << "v : ";
    afficheVecteur(v);
    cout << "Taille du vecteur = " << v.size() <<  " -> min = " << min << ", max = " << max << ", nbComp = " << nbComp << endl << endl;

    nbComp = 0;
    v.clear();
    srand((unsigned)time(NULL));
    for (int i =0; i < 128; i++){
        int b = rand() % 128 + 1;
        v.push_back(b);
    }
    inf = 0;
    sup = v.size()-1;
    minMaxRec(v, inf, sup, min, max, nbComp);
    cout << "v : ";
    afficheVecteur(v);
    cout << "Taille du vecteur = " << v.size() << " -> min = " << min << ", max = " << max << ", nbComp = " << nbComp << endl << endl;
}

int main(int argc, char **argv) {

    testMinMaxIter();
    testMinMaxDR();

    return 0;
}
