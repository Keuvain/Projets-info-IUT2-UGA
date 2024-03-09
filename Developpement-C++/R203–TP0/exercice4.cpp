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
void swap(vector<T>& v, const int first, const int second) {
    // {v.size() >0} => {permutation des valeurs de v aux indices first et second}
    int tmp = v[first];
    v[first] = v[second];
    v[second] = tmp;
}

template<class T>
T partage(vector <T> &v, const int inf, const int sup) {
// {v.size()≥ 1} => {résultat = valeur du médian tel que défini dans l’étape 1)
//                   et mise en place du plus petit et du plus grand dans v telles que définies dans l’étape 2)}
    T center = (inf + sup) / 2;  // indice de l'élémenent milieu
    // placer le plus grand et le plus petit parmi V[inf], v[sup], v[center] à leur place
    if (v[center] < v[inf]) {
        swap(v, inf, center);
    }
    if (v[sup] < v[inf]) {
        swap(v, inf, sup);
    }
    if (v[sup] < v[center]) {
        swap(v, center, sup);
    }
    // Placer le median en position sup-1
    swap(v, center, sup - 1);
    return v[sup - 1];
}

template<class T>
void insertionSort(vector<T> &a) {
    // un tri par insertion
    int j = 0, key = 0;
    for (int i = 1; i < (int) a.size(); ++i) {
        key = a[i];  // élément à placer
        j = i - 1;
        while ((j >= 0) && (a[j] > key)) { // remonter de l'élément à placer à sa place effective
            a[j + 1] = a[j];
            j -= 1;
        }
        a[j + 1] = key;
    }
}

#define CUTOFF 6

template<class T>
void triR302(vector<T> &v, const int inf, const int sup) {
    if (inf + CUTOFF > sup) {
        insertionSort(v);
    } else {
        int valPartage = partage(v, inf, sup);
        int i = inf + 1;
        int j = sup - 2;
        while (i <= j) {
            while (v[i] < valPartage) {
                i++;
            }
            while (v[j] > valPartage) {
                j--;
            }
            if (i <= j) {
                swap(v, i, j);
                i++;
                j--;
            }
        }
        swap(v, i, sup - 1);

        triR302(v, inf, i - 1);
        triR302(v, i + 1, sup);
    }
}

void testTriR302() {
    cout << "***************************" << endl;
    cout << "*          triR302        *" << endl;
    cout << "***************************" << endl;
    vector<int> v = {10, 3, 6, 8, 67, 2, 9, 1};
    cout << "vecteur initial : ";
    afficheVecteur(v);
    triR302(v, 0, (int) v.size()-1);
    cout << "vecteur trié : ";
    afficheVecteur(v);

    v.clear();
    srand((unsigned)time(NULL));
    for (int i =0; i < 128; i++){
        int b = rand() % 128 + 1;
        v.push_back(b);
    }
    cout << "vecteur initial : ";
    afficheVecteur(v);
    triR302(v, 0, (int) v.size()-1);
    cout << "vecteur trié : ";
    afficheVecteur(v);
}

int main(int argc, char **argv) {
    testTriR302();
    return 0;
}