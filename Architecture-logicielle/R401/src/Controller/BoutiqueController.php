<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use App\Service\BoutiqueService;
use Symfony\Component\HttpFoundation\Request;


class BoutiqueController extends AbstractController
{
    #[Route('/{_locale}/boutique', name: 'app_boutique')]
    public function index(BoutiqueService $boutique): Response
    {
        $categories = $boutique->findAllCategories();

        return $this->render('boutique/index.html.twig', [
            'categories' => $categories,
        ]);
    }

    #[Route('/{_locale}/rayon/{idCategorie}', name:'app_boutique_rayon')]
    public function rayon(BoutiqueService $boutique, int $idCategorie): Response
    {
        $produit = $boutique->findProduitsByCategorie($idCategorie);
        return $this->render('boutique/rayon.html.twig', 
        ['produits' => $produit]);
    }

    #[Route('/{_locale}/chercher', name: 'app_boutique_chercher')] 
    public function chercher(Request $request, BoutiqueService $boutique): Response 
    {
        $recherche = $request->query->get('recherche', '');
        $produits = $boutique->findProduitsByLibelleOrTexte($recherche);

        return $this->render('boutique/chercher.html.twig', [
            'produits' => $produits,
            'recherche' => $recherche,
        ]);
    }


}