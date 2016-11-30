package controllers;

import database.DatabaseHandler;
import database.dao.*;
import models.Categories;
import models.Etiquettes;
import models.Textes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;


public class consolControl {

    int requete;
    Connection db = null;
    TextesDAO textesDAO = DAOFactory.getTextesDAO();
    CategoriesDAO categoriesDAO = DAOFactory.getCategoriesDAO();
    EtiquettesDAO etiquettesDAO = DAOFactory.getEtiquettesDAO();


    public void run() throws IOException {
        Scanner sc = new Scanner(System.in);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Bienvenue sur Docomat !");
        db = DatabaseHandler.getInstance();
        System.out.println("Voici la liste des actions disponible : " +
                "\n- 1 : Entrer une nouvelle portion de texte," +
                "\n- 2 : Gérer les catégories," +
                "\n- 3 : voir les textes écrits," +
                "\n- 4 : Gérer les étiquettes," +
                "\n- 8 : Quitter");
        try {
            requete = sc.nextInt();
            if (requete < 1 && requete> 9) {
                throw new NumberFormatException();
            }
        }catch (NumberFormatException ne) {
            System.out.printf("Saisie invalide");
            requete = sc.nextInt();
        }
        while (requete != 8) {
            switch (requete) {
                case 1 :
                    System.out.println("Entrer votre texte : ");
                    String contenu = br.readLine();
                    System.out.println("Avez-vous terminez l'édition du texte : (o/n)");
                    String rep = br.readLine();
                    while (rep.charAt(0) == 'n') {
                        System.out.println(contenu);
                        contenu += " "+br.readLine();
                        System.out.println("Avez-vous terminez l'édition du texte : (o/n)");
                        rep = br.readLine();
                    }
                    System.out.println("Entrer le nom de ce texte : ");
                    String nomTexte = br.readLine();
                    List<Categories> categories = categoriesDAO.readAll();
                    if (categories.isEmpty()) {
                        System.out.println("Aucune catégories existantes, voulez-vous en créer une ? (o/n)");
                        rep = br.readLine();
                        if (rep.charAt(0) == 'n') break;
                        else {
                            System.out.println("Entrer le nom de la catégorie : ");
                            String nomCat = br.readLine();
                            Categories categorie = new Categories(nomCat,0);
                            categories.add(categorie);
                            categoriesDAO.insert(categories.get(0));
                            Textes texte = new Textes(categorie.getId_categorie(),nomTexte,contenu);
                            textesDAO.insert(texte);
                            System.out.println("Voulez-vous ajouter des étiquettes ?");
                            if (br.readLine().charAt(0) == 'o') {
                                System.out.println("Entrer le mot-clé");
                                String mc = br.readLine();
                                Etiquettes etiquette = new Etiquettes(mc);
                                etiquettesDAO.insert(etiquette);
                                etiquettesDAO.insert_Etiquetes_Textes(etiquette,texte);
                            }
                        }
                    }
                    else {
                        List<Categories> categoriesList = categoriesDAO.readAll();
                        for (Categories cat : categoriesList) {
                            System.out.println(cat.toString());
                        }
                        System.out.println("Voulez vous ajouter à une de ces catégories (o/n)");
                        rep = br.readLine();
                        if (rep.charAt(0) == 'n') {
                            System.out.println("Entrer le nom de la catégorie : ");
                            String nomCat = br.readLine();
                            Categories categorie = new Categories(nomCat,0);
                            categories.add(categorie);
                            categoriesDAO.insert(categorie);
                            Textes texte = new Textes(categorie.getId_categorie(),nomTexte,contenu);
                            textesDAO.insert(texte);
                            System.out.println("Voulez-vous ajouter des étiquettes ?");
                            if (br.readLine().charAt(0) == 'o') {
                                System.out.println("Entrer le mot-clé");
                                String mc = br.readLine();
                                Etiquettes etiquette = new Etiquettes(mc);
                                etiquettesDAO.insert(etiquette);
                                etiquettesDAO.insert_Etiquetes_Textes(etiquette,texte);
                            }
                        }
                        else {
                            System.out.println("entrez l'id de la cat");
                            int idCat = sc.nextInt();
                            Textes texte = new Textes(idCat,nomTexte,contenu);
                            textesDAO.insert(texte);
                        }
                    }
                    break;
                case 2 :
                    List<Categories> categoriesList = categoriesDAO.readAll();
                    for (Categories cat : categoriesList) {
                        System.out.println(cat.toString());
                    }
                    break;
                case 3 :
                    List<Textes> textesList = textesDAO.readAll();
                    for (Textes textes : textesList) {
                        System.out.println(textes.toString());
                    }
                    break;
                case 4 :
                    List<Etiquettes> etiquettesList = etiquettesDAO.readAll();
                    for (Etiquettes etiq : etiquettesList) {
                        System.out.println(etiq.toString());
                    }
                    break;


            }
            System.out.println();
            System.out.println("Voici la liste des actions disponible : " +
                    "\n- 1 : Entrer une nouvelle portion de texte," +
                    "\n- 2 : Gérer les catégories," +
                    "\n- 3 : voir les textes écrits," +
                    "\n- 4 : Gérer les étiquettes," +
                    "\n- 8 : Quitter");
            try {
                requete = sc.nextInt();
                if (requete != 1 && requete != 2 && requete != 3) {
                    throw new NumberFormatException();
                }
            }catch (NumberFormatException ne) {
                System.out.printf("Saisie invalide");
                requete = sc.nextInt();
            }
        }




    }
}
