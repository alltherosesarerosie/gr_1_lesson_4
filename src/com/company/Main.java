package com.company;

import java.util.Random;

public class Main {
    public static int bossHealth = 500;
    public static int bossDamage =50;
    public static String bossDefenceType;

    public static int [] heroesHealth = {260,270,280,990,150,220,220,1500};
    public static int [] heroesDamage = {20,30,10,0,10,15,10,5};
    public static String [] heroesAttackType = {"Physical","Magical","Kinetic","Medic","Lucky","Berserk","Thor","Golem"};
    public static int round = 0;
    public static int medicSaving = 100;
    public static int hitBerserk;


    public static void main(String[] args) {
	printStat();
    while (!isGameFinished()){
        playRound();
    }
    }

    public static void printStat(){
        System.out.println("round is "+round);

        System.out.println("Boss's health  is "+bossHealth+"; damage is "+
                bossDamage+"; defence is "+bossDefenceType);

        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i]+"'s health is  "+heroesHealth[i]+"; damage is "+
                    heroesDamage[i]);
        }
    }

    public static boolean isGameFinished(){
        if(bossHealth<=0){
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if(heroesHealth[i] > 0){
                allHeroesDead=false;
                break;
            }
        }
        if(allHeroesDead){
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void bossHits(){
        Random rd = new Random();
        boolean isLuckyLucky = rd.nextBoolean();
        boolean isChance= rd.nextBoolean();
        int golemsSaving = 0;
        int saving = 0;

        for (int i = 0; i < heroesAttackType.length; i++) {
            int bHit = bossDamage;
            if(heroesHealth[i]>0){
                if(!heroesAttackType[i].equals("Golem")){
                    int count = bossDamage/5;
                    golemsSaving = golemsSaving+ (count);
                    bHit = bossDamage - bossDamage/5;
                }


                if(heroesAttackType[i].equals("Thor") && isChance){
                    System.out.println("The boss is deafening");
                    bHit=0;
                }


                if(heroesAttackType[i].equals("Lucky") && isLuckyLucky){
                    System.out.println("lucky is lucky");
                    bHit=0;
                }
                if(heroesAttackType[i].equals("Berserk")){
                    int hitToBerserk = (int) (bHit*0.7);
                    hitBerserk = bHit-hitToBerserk;
                    System.out.println("Hit of Boss to Berserk is "+hitToBerserk);
                    heroesHealth[i]=heroesHealth[i]-hitToBerserk;

                }


                if(heroesHealth[i]-bHit<0){
                    heroesHealth[i]=0;
                }else
                heroesHealth[i]=heroesHealth[i]-bHit;

                if(heroesHealth[i]<100 && heroesHealth[i]>0 && saving < 1 && heroesHealth[3] >0 && (!heroesAttackType[i].equals("medical"))){
                    heroesHealth[i]=heroesHealth[i]+medicSaving;
                    System.out.println("medical help to "+heroesAttackType[i]+" +"+medicSaving);
                    saving++;
                }

                if(heroesAttackType[i].equals("Golem")){
                    heroesHealth[i] = heroesHealth[i]-golemsSaving;
                    System.out.println("golem took "+golemsSaving);
                    System.out.println("-----------------------                             -------------------------");
                    System.out.println();
                }
            }
        }
    }

    public static void heroesHits(){
        for (int i = 0; i < heroesAttackType.length; i++) {
            if(heroesHealth[i]>0 && bossHealth>0) {
                int hit = heroesDamage[i];
                if(heroesAttackType[i].equals(bossDefenceType)){
                    Random rd = new Random();
                    int coef = rd.nextInt(6)+2;
                    hit = hit*coef;
                    System.out.println("Critical damage is "+hit );
                }
                if(heroesAttackType[i].equals("Berserk")){
                    hit = hit+hitBerserk;
                }

                bossHealth=bossHealth-hit;
                if(bossHealth<0){
                    bossHealth=0;
                }

            }
        }
    }

    public static void chooseBossDefence(){
        Random rd = new Random();
        int randomIndex = rd.nextInt(heroesHealth.length);
        bossDefenceType = heroesAttackType[randomIndex];
    }


    public static void playRound(){
        round++;
        chooseBossDefence();
        bossHits();
        heroesHits();
        printStat();
    }






}
