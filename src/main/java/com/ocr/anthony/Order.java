package com.ocr.anthony;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;

import static java.nio.file.StandardOpenOption.APPEND;

public class Order {
    Scanner sc = new Scanner(System.in);
    String orderSummary = "";


    /**
     * Display all available menus in the restaurant.
     */
    public void displayAvailableMenu() {
        System.out.println("Choix menu");
        System.out.println("1 - poulet");
        System.out.println("2 - boeuf");
        System.out.println("3 - végétarien");
        System.out.println("Que shouaitez-vous comme menu ?");

    }
    /**
     * Display a selected menu.
     * @param nbMenu The selected menu.
     */
    public void displaySelectedMenu(int nbMenu) {
        switch(nbMenu){
            case 1:
                System.out.println("Vous avez choisi comme menu : poulet");
                break;
            case 2:
                System.out.println("Vous avez choisi comme menu : boeuf");
                break;
            case 3:
                System.out.println("Vous avez choisi comme menu : végétarien");
                break;
            default:
                System.out.println("Vous n'avez pas choisi de menu parmi les choix proposés");
                break;

        }
    }

    /**
     * Run the menu display
     */
    public String  runMenu() {
        int nbMenu = askMenu();
        int sideChoice = -1;
        int drinkChoice = -1;

        switch (nbMenu) {
            case 1:
                sideChoice =  askSide(true);
                drinkChoice =  askDrink();
                break;
            case 2:
                sideChoice =  askSide(true);
                break;
            case 3:
                sideChoice =  askSide(false);
                drinkChoice =  askDrink();
                break;
        }
        return nbMenu+","+sideChoice+","+drinkChoice+"%n";

    }

    /**
     * Display a selected side depending on all sides enable or not.
     * All sides = vegetables, frites and rice
     * No all sides = rice or not
     * @param nbSide The selected Side
     * @param allSidesEnable  enable display for all side or not
     */
    public void displaySelectedSide(int nbSide, boolean allSidesEnable) {

        if(allSidesEnable){
            switch(nbSide){
                case 1:
                    System.out.println("Vous avez choisi comme accompagnement : légumes frais");
                    break;
                case 2:
                    System.out.println("Vous avez choisi comme accompagnement : frites");
                    break;
                case 3:
                    System.out.println("Vous avez choisi comme accompagnement : riz");
                    break;
                default:
                    System.out.println("Vous n'avez pas choisi d'accompagnement parmi les choix proposés");
                    break;
            }

        }else{
            switch(nbSide){
                case 1:
                    System.out.println("Vous avez choisi comme accompagnement : riz");
                    break;
                case 2:
                    System.out.println("Vous avez choisi comme accompagnement : pas de riz");
                    break;
                default:
                    System.out.println("Vous n'avez pas choisi d'accompagnement parmi les choix proposés");
                    break;
            }
        }


    }

    public void displaySelectedDrink(int nbBoisson) {


        switch (nbBoisson) {
            case 1:
                System.out.println("Vous avez choisi comme boisson : eau plate");
                break;
            case 2:
                System.out.println("Vous avez choisi comme boisson : eau gazeuse");
                break;
            case 3:
                System.out.println("Vous avez choisi comme boisson : soda");
                break;
            default:
                System.out.println("Vous n'avez pas choisi de boisson parmi les choix proposés");
                break;
        }


    }

    /**
     * Display all available sides depending on all sides enable or not.
     * All sides = vegetables, frites and rice
     * No all sides = rice or not
     * @param allSideEnable enable display for all side or not
     */
    public void displayAvailableSide(boolean allSideEnable) {
        System.out.println("Choix accompagnement");
        if (allSideEnable) {
            System.out.println("1 - légumes frais");
            System.out.println("2 - frites");
            System.out.println("3 - riz");
        } else {
            System.out.println("1 - riz");
            System.out.println("2 - pas de riz");
        }
        System.out.println("Que souhaitez-vous comme accompagnement ?");
    }

    /**
     * Display all available drinks in the restaurant
     */
    public void displayAvailableDrink() {
        System.out.println("Choix boisson");
        System.out.println("1 - eau plate");
        System.out.println("2 - eau gazeuse");
        System.out.println("3 - soda");
        System.out.println("Que souhaitez-vous comme boisson ?");
    }


    public void runMenus() {
        Path orderPath = Paths.get("commands.csv");
        System.out.println("Combien de menus desirez vous commander ?");
        orderSummary = "Résumé de votre commande :%n";
        int menus = -1;
        boolean isvalidAnswer;
        do {
            try {
                menus = sc.nextInt();
                isvalidAnswer = true;
            } catch (InputMismatchException e) {
                sc.next();
                isvalidAnswer = false;
                System.out.println("Vous devez saisir un nombre, correspondant au nombre de menus souhaités");
            }
        }while(!isvalidAnswer);
        for (int i = 0; i < menus; i++) {
            orderSummary += "Menu "+(i+1)+":%n";
            String command = runMenu();
            try {
                Files.write(orderPath, String.format(command).getBytes(), APPEND);
            } catch (IOException e) {
                System.out.println("Ooops une erreur est survenue. Merci de réessayer plus tard");
                return;
            }
        }
        System.out.println("");
        System.out.println(String.format(orderSummary));
    }

    public int askSomething(String sujet, String[] responses) {

        System.out.println("Choix "+sujet);
        for (int i=0;i<responses.length;i++){
            System.out.println((i+1)+" "+responses[i]);
        }

        System.out.println("Que souhaitez-vous comme " + sujet + "?");
        int answer = -1;
        boolean isValidAnswer;

        do{
            try {
                answer = sc.nextInt();
                isValidAnswer = !(answer < 1 || (answer > responses.length));
            } catch (InputMismatchException e){
                sc.next();
                isValidAnswer = false;
            }
            if (!isValidAnswer){

                boolean isVowel = "aeiouy".contains(Character.toString(sujet.charAt(0)));
                if (isVowel) {
                    System.out.println("Vous n'avez pas choisi d'" + sujet + " parmi les choix proposés");
                } else {
                    System.out.println("Vous n'avez pas choisi de " + sujet + " parmi les choix proposés");
                }
            }else{
                System.out.println("Vous avez choisi comme "+sujet+" : "+responses[answer-1]);
                String choice = "Vous avez choisi comme " + sujet + " : " + responses[answer - 1];
                orderSummary += choice + "%n";
            }
        }while(!isValidAnswer);

        return answer;
    }

    public int askMenu() {
        String menus[] = {"poulet","boeuf","végétarien"};
        int answer = askSomething("menu",menus);
        return answer;
    }



    public int askSide(boolean allSidesEnable) {
        int answer;
        if(allSidesEnable){
            String sideDishes[] = {"légumes frais","frites","riz"};
            answer = askSomething("accompagnement",sideDishes);
        }else{
            String sideDishes[] = {"riz","pas de riz"};
            answer = askSomething("accompagnement",sideDishes);
        }
        return answer;

    }

    public int askDrink() {
        int answer;
        String drinks[] = {"eau plate","eau gazeuse","soda"};
        answer = askSomething("boisson",drinks);
        return answer;
    }
}
