package main.java.game.engine;
import main.java.game.dice.*;

import java.util.*;
import main.java.game.collectibles.*;
import main.java.game.realms.*;
import main.java.game.creatures.*;
public class CLIGameController extends GameController{
    private GameBoard gameBoard;
    private Scanner sc;
    private static final String ANSI_RED="\u001B[31m";
    private static final String ANSI_GREEN="\u001B[32m";
    private static final String ANSI_BLUE="\u001B[34m";
    private static final String ANSI_YELLOW="\u001B[33m";
    private static final String ANSI_MAGENTA="\u001B[35m";
    private static final String ANSI_CROSS="\u001B[9m";
    private static final String ANSI_RESET="\u001B[0m";
//=======================================Constructor===================================    
    public CLIGameController(){
        gameBoard=new GameBoard();
        sc=new Scanner(System.in);
    }
//=======================================GameFlow======================================
    public void startGame(){
        //Delay to Show Game Name
        createDelay();
        createDelay();
        //Enter Player Name
        selectPlayerName(getActivePlayer(), 1);
        selectPlayerName(getPassivePlayer(), 2);
        while(!getGameStatus().isGameFinished()){
            //Displaying ActivePlayer Name and Round Number,Grimore
            Player activePlayer=getActivePlayer();
            System.out.println("Round: "+getGameStatus().getGameRound());
            System.out.println(activePlayer.getPlayerName()+"'s Turn!!!");
            createDelay();
            displayPlayerGrimore(activePlayer);
            checkRoundReward(activePlayer);
            //Turn Loop
            while(getGameStatus().getTurn()<=3){
                System.out.println("Turn: "+getGameStatus().getTurn());
                createDelay();
                //Rolling Dice Ending if no more dice Avaiable
                System.out.println("Rolling dice......");
                rollDice();
                createDelay();
                if(getAvailableDice().length==0){
                    System.out.println("No available  dice found ending turn");
                    createDelay();
                    break;
                }
                //Display Dice and promting for TimeWarp
                displayAvailableDice();
                displayTimeWarpPrompt(activePlayer);
                //Selecting dice
                Dice selectedDice=selectDiceSequence(activePlayer);
                //Send Remaning Dice to forgotten Realm
                selectDice(selectedDice, activePlayer);
                System.out.println("Sending remaining Dice to forgoten realm.....");
                createDelay();
                displayForgottenRealmDice();
                //attacking
                attackSequence(activePlayer,selectedDice);
                //Display Score Sheet after each turn
                displayPlayerGrimore(activePlayer);
                getGameStatus().incrementTurn();
            }
            passivePlayerSequence(getPassivePlayer());
            switchPlayer();
            resetDiceStatus(); 
            getGameStatus().incrementRound();
        }
        if(getGameScore(getActivePlayer()).getTotalGameScore()>getGameScore(getPassivePlayer()).getTotalGameScore())
        System.out.println(getActivePlayer().getPlayerName()+" Wins!!!");
        else
        System.out.println(getPassivePlayer().getPlayerName()+" Wins!!!");     
    }
    public void printWithColor(RealmColor color,String text,boolean cross){
        if(cross){
            System.out.print(ANSI_CROSS+text+ANSI_RESET);return;}
        switch (color) {
            case RED: System.out.print(ANSI_RED+text+ANSI_RESET);break;
            case GREEN:System.out.print(ANSI_GREEN+text+ANSI_RESET);break;
            case BLUE:System.out.print(ANSI_BLUE+text+ANSI_RESET);break;
            case MAGENTA:System.out.print(ANSI_MAGENTA+text+ANSI_RESET);break;
            case YELLOW:System.out.print(ANSI_YELLOW+text+ANSI_RESET);break;
            case WHITE:System.out.print(text);break;
        }
    }
    public void createDelay(){
        try{
            Thread.sleep(1500);
        }
        catch(Exception ex){
            System.out.println("Error in Thread Sleep");
            ex.printStackTrace();}
    }
    public void checkRoundReward(Player player){
        switch(getGameStatus().getGameRound()){
            case 1:
            System.out.println("You earned a TimeWarp power");
            player.addArcaneBoost(new ArcaneBoost());break;
            case 2:
            System.out.println("You earned an ArcaneBoost power");  
            player.addArcaneBoost(new ArcaneBoost());break;
            case 3:
            System.out.println("You earned a TimeWarp power");
                player.addTimeWarp(new TimeWarp());break;
            case 4:
            displaySelectEssenceBonusColorPromt(player,new EssenceBonus());break;
        }
    }
    public void selectPlayerName(Player player,int playerNumber){
        String playerName="";
        while(playerName.isEmpty()){//While loop till user enters valid name
        System.out.println("Player"+playerNumber+" Enter Your Name:");    
        System.out.print("Name:");
        playerName=sc.next().trim();
        if(playerName.isEmpty())
            System.out.println("Player name cannot be blank ");
        if(playerName.toUpperCase().charAt(0)<'A'||playerName.toUpperCase().charAt(0)>'Z'){
            System.out.println("Player name cannot start with a special character!");
            playerName="";
        }
        }
        player.setPlayerName(playerName);
    }
    public void displayPlayerGrimore(Player player){
        System.out.println("Printing "+player.getPlayerName()+"'s Grimore");
        createDelay();
        System.out.println(getScoreSheet(player));
        System.out.println("Type anything to continue:");
        sc.next();
    }
    public void displayAvailableDice(){
        Dice[]availableDice=getAvailableDice();
        for(int i=1;i<=availableDice.length;i++){
            String text="("+i+") "+availableDice[i-1]+"  ";
            printWithColor(availableDice[i-1].getDiceColor(), text,
            getPossibleMovesForADie(getActivePlayer(),availableDice[i-1]).length==0);
        }
        System.out.println();
    }
    public void displayTimeWarpPrompt(Player player){
        //Not Prompting if no TimeWarps Available
        if(player.getTimeWarps().size()==0)
            return;
        while(player.getTimeWarps().size()>0){  
        System.out.println("Would you like to use a TimeWarp?"); 
        System.out.println("You have x"+player.getTimeWarps().size()+" TimeWarps remaining");
        if(getPossibleMovesForAvailableDice(player).length==0)
            System.out.println("Please note that all dice have no possible moves");
        System.out.println("Enter YES or NO");
        System.out.print("Choice: ");
        String choice=""+sc.next().trim().toUpperCase().charAt(0);
        if(choice.equals("Y"))
            useTimeWarp(player);
        else if(choice.equals("N"))
            return;
        else
             System.out.println("Invalid input please enter YES or No!!");        
        }
        //If player used all TimeWarps
        if(player.getTimeWarps().size()==0)
            System.out.println("No more TimeWarps Avaialble");
    }
    public void useTimeWarp(Player player){
        player.removeTimeWarp();
        System.out.println("TimeWarp power activated!");
        System.out.println("Rerolling dice......");
        rollDice();
        createDelay();
        displayAvailableDice();
    }
    public Dice selectDiceSequence(Player player){
         //First select dice
         Dice selectedDice;
         int selectedDiceIndex=displaySelectDicePromt(player)-1;//-1 Because Prompt starts from 1
         if(player.getPlayerStatus()==PlayerStatus.ACTIVE){//Active
             selectedDice=getAvailableDice()[selectedDiceIndex];
         }
         else{//Passive
            selectedDice=getForgottenRealmDice()[selectedDiceIndex];
         }
         //Prompt Player to choose Arcane Dice color if chosen
         RealmColor selectedDiceColor=selectedDice.getDiceColor();    
         if(selectedDiceColor==RealmColor.WHITE){
             displayChooseArcaneDiceColorPrompt(selectedDice);
             selectedDiceColor=((ArcanePrism)selectedDice).getChosenColor(); 
         }
         return selectedDice;
    }
    public void attackSequence(Player player,Dice selectedDice){
        //White
        RealmColor selectedDiceColor=selectedDice.getDiceColor();    
         if(selectedDiceColor==RealmColor.WHITE)
             selectedDiceColor=((ArcanePrism)selectedDice).getChosenColor(); 
         
        //Select Creature  //TODO HEERE ERORR
        Creature selectedCreature=null;//to initialize
        if(selectedDice.getDiceStatus()==DiceStatus.POWER_SELECTED
        &&selectedDiceColor==RealmColor.RED
        &&(((selectedDice instanceof RedDice)&&(((RedDice)selectedDice).getselectsDragon()!=-1))))
        {//-1 To prevent ArcaneBoost from entering the bonus as dragon selection is different user chooses in bonus
            RedRealm redRealm=player.getRedRealm();
            RedDice bonusDice=(RedDice)selectedDice;
            int dragonNumber=bonusDice.getselectsDragon();
            System.out.print("Attacking ");
            printWithColor(RealmColor.RED, "Dragon"+dragonNumber, false);
            System.out.print(".....");
            System.out.println();
            createDelay();
            switch(dragonNumber){
                case 1:selectedCreature= redRealm.getDragon1();break;
                case 2:selectedCreature= redRealm.getDragon2();break;
                case 3:selectedCreature= redRealm.getDragon3();break;
                case 4:selectedCreature= redRealm.getDragon4();break;
                default:System.out.println("An error occured in dragon selection");
            }
        }
        else
        selectedCreature=selectCreatureToAttack(player, selectedDiceColor,selectedDice.getValue());
        //Make a move
        boolean isMoveSuccessful=makeMove(player,new Move(selectedDice, selectedCreature));
        if(isMoveSuccessful)
            System.out.println("Attack was successfull!!");
        else
            System.out.println("Attack failed!!");
        //Check and get Possible Rewards
        checkForPossibleRewards(player,selectedDiceColor);    
    }
    public int displaySelectDicePromt(Player player){
        Dice[]availableDice;
        if(player.getPlayerStatus()==PlayerStatus.ACTIVE)
            availableDice=getAvailableDice();
        else
            availableDice=getForgottenRealmDice();

        //Check if user can bypass as no moves work
        boolean bypass=false;
        for(int i=0;i<availableDice.length;i++)
            if(getPossibleMovesForADie(player, availableDice[i]).length>0)break;
            else if(i==availableDice.length-1)bypass=true;
        if(bypass)
            System.out.println("All dice have no possible moves choose any");    
        


        while(true){
            System.out.println("Choose a number to select a dice");
            System.out.print("Choice: ");
            try{
                int choice=Integer.parseInt(sc.next().trim());
                if(1<=choice&&choice<=availableDice.length){
                    Dice selectedDice;
                    if(player.getPlayerStatus()==PlayerStatus.ACTIVE){//Active
                        selectedDice=getAvailableDice()[choice-1];
                    }
                    else{//Passive
                       selectedDice=getForgottenRealmDice()[choice-1];
                    }
                    if(getPossibleMovesForADie(player, selectedDice).length==0&&!bypass)
                        System.out.println("No Possible attacks for this die please choose another");
                    else    
                    return choice;
                }else
                    System.out.println("Please choose a number between 1 and "+(availableDice.length)+"!!");    

            }catch(Exception ex){
                System.out.println("Please enter a valid number");
            }
        }
    }


    public void displayForgottenRealmDice(){
        System.out.println("Forgotten Realm Dice:");
        Dice[]forgottenDice=getForgottenRealmDice();
        if(forgottenDice.length==0){
            System.out.println("No dice in Forgotten Realm");
            return;
        }
        for(int i=1;i<=forgottenDice.length;i++){
            String text="("+i+") "+forgottenDice[i-1]+"  ";
            printWithColor(forgottenDice[i-1].getDiceColor(), text,
            getPossibleMovesForADie(getPassivePlayer(),forgottenDice[i-1]).length==0);
        }
        System.out.println();
    }
    public void displayChooseArcaneDiceColorPrompt(Dice dice){
        ArcanePrism whiteDice=gameBoard.getArcanePrism();
        //To check realm is accessible before attacking
        boolean red=getActivePlayer().getRedRealm().getPossibleMovesForADie(dice.getValue(),RealmColor.RED).length>0;
        boolean blue=getActivePlayer().getBlueRealm().getPossibleMovesForADie(dice.getValue(),RealmColor.BLUE).length>0;
        boolean green=getActivePlayer().getGreenRealm().getPossibleMovesForADie(dice.getValue(),RealmColor.GREEN).length>0;
        boolean magenta=getActivePlayer().getMagentaRealm().getPossibleMovesForADie(dice.getValue(),RealmColor.MAGENTA).length>0;
        boolean yellow=getActivePlayer().getYellowRealm().getPossibleMovesForADie(dice.getValue(),RealmColor.YELLOW).length>0;
        boolean bypass=false;
       
        System.out.println("Choose ArcanePrism color: ");
        if(!red&&!green&&!blue&&!magenta&&!yellow){
            bypass=true;
            System.out.println("Choose any color all colors have no moves");
        }
        //Printing dice with color and crossed if not avaialble
        printWithColor(RealmColor.RED, "Red ", !red);
        printWithColor(RealmColor.GREEN,"Green ", !green);
        printWithColor(RealmColor.BLUE,"Blue ", !blue);
        printWithColor(RealmColor.MAGENTA,"Magenta ", !magenta);
        printWithColor(RealmColor.YELLOW,"Yellow ",!yellow);
        System.out.println();

        while(true){
            System.out.print("Choice: ");
            String choice=sc.next().trim().toUpperCase();
            if(!choice.isEmpty()){
                switch (choice.charAt(0)) {
                    case 'R':
                    if(bypass||red){
                    whiteDice.setChosenColor(RealmColor.RED);return;}
                    else
                    System.out.println("The Dragons have been defeated");
                    break;
                    case 'G':
                    if(bypass||green){
                    whiteDice.setChosenColor(RealmColor.GREEN);return;}
                    else
                    System.out.println("The Gurdians have been defeated");
                    break;
                    case 'B':
                    if(bypass||blue){
                    whiteDice.setChosenColor(RealmColor.BLUE);return;}
                    else
                    System.out.println("The Hydra has been defeated");
                    break;
                    case 'M':
                    if(bypass||magenta){
                    whiteDice.setChosenColor(RealmColor.MAGENTA);return;}
                    else
                    System.out.println("The Phoenix has been defeated");
                    break;
                    case 'Y':
                    if(bypass||yellow){
                    whiteDice.setChosenColor(RealmColor.YELLOW);return;}
                    else
                    System.out.println("The Lion has been defeated");
                    break;
                    default:System.out.println("Please Enter A valid Color");
                }
            }
            else System.out.println("Choice cannot be blank");   
        }
    }
    public Creature selectCreatureToAttack(Player player,RealmColor color,int diceValue){
        switch (color) {
            case RED:return displaySelectDragonPrompt(player,diceValue);
            case GREEN:
            System.out.println("Attacking Gaia Gurdian....");
            createDelay();
            return player.getGreenRealm().getGaia();
            case BLUE:
            System.out.println("Attacking Hydra....");
            createDelay();
            return player.getBlueRealm().getHydra();
            case MAGENTA:
            System.out.println("Attacking Phoenix....");
            createDelay();
            return player.getMagentaRealm().getPhoenix();
            case YELLOW:
            System.out.println("Attacking Lion....");
            createDelay();
            return player.getYellowRealm().getLion();
            case WHITE:System.out.println("Error occured White Selection wasnt successful");    
        }
        return null;//error occured
    }
    public Creature displaySelectDragonPrompt(Player player,int diceValue){
        RedRealm redRealm=player.getRedRealm();
        Dragon dragon1=redRealm.getDragon1();
        Dragon dragon2=redRealm.getDragon2();
        Dragon dragon3=redRealm.getDragon3();
        Dragon dragon4=redRealm.getDragon4();
        
        int choice=0;
        int possibleSelections=1;
        //Only Printing dragons that can be attacked
        boolean bypass=false;
        if(dragon1.checkPossibleAttack(diceValue))
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon1 ", false);
        if(dragon2.checkPossibleAttack(diceValue))
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon2 ", false);
        if(dragon3.checkPossibleAttack(diceValue))
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon3 ", false);    
        if(dragon4.checkPossibleAttack(diceValue))
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon4 ", false);
        //No dragon can be attacked
        if(!dragon1.checkPossibleAttack(diceValue)&&!dragon2.checkPossibleAttack(diceValue)
        &&!dragon3.checkPossibleAttack(diceValue)&&!dragon4.checkPossibleAttack(diceValue)) {
            bypass=true;
            System.out.println("all dragons cant be attacked by dice selecting random dragon");
            return dragon1;
        }      
        //User choosing
        while(true){
            try{
            System.out.print("Choice: ");
            choice=Integer.parseInt(sc.next().trim());
            if(choice>=1&&choice<=possibleSelections-1)
                break;
            System.out.println("Please choose a number within the range");    
            }catch(Exception ex){
                System.out.println("Please enter a valid number");
            }
        }
        //finding to get the dragon selected
        int indexSoFar=1;
        boolean found=false;
        if(dragon1.checkPossibleAttack(diceValue)){
            if(indexSoFar++==choice&&!found){
                choice=1;  
                found=true;}
        }
        if(dragon2.checkPossibleAttack(diceValue)){
            if(indexSoFar++==choice&&!found){
                choice=2;  
                found=true;}
        }
        if(dragon3.checkPossibleAttack(diceValue)){
            if(indexSoFar++==choice&&!found){
                choice=3;  
                found=true;}
        }
        if(dragon4.checkPossibleAttack(diceValue)){
            if(indexSoFar++==choice&&!found){
                found=true;
                choice=4;  }
        }

        gameBoard.getRedDice().selectsDragon(choice);
        System.out.println("Attacking Dragon"+choice+".....");
        createDelay();
        switch(choice){
            case 1:return redRealm.getDragon1();
            case 2:return redRealm.getDragon2();
            case 3:return redRealm.getDragon3();
            case 4:return redRealm.getDragon4();
            default:System.out.println("An error occured in dragon selection");return null;
        }
    }
    public void checkForPossibleRewards(Player player,RealmColor diceColor){
        //Checking which realm was attacked to receive rewards from
        Realms checkInRealm=null;
        switch(diceColor){
            case RED:checkInRealm=player.getRedRealm();break;
            case BLUE:checkInRealm=player.getBlueRealm();break;
            case GREEN:checkInRealm=player.getGreenRealm();break;
            case YELLOW:checkInRealm=player.getYellowRealm();break;
            case MAGENTA:checkInRealm=player.getMagentaRealm();break;
            case WHITE:System.out.println("Error checking reward in white realm");
        }
        if(!checkInRealm.isRewardAvailable())
            return;
        Reward[]allRewards=checkInRealm.getReward();
        for(int i=0;i<allRewards.length;i++){//Reward array for multiple cases of rewards
            Reward earnedReward=allRewards[i];
            switch(earnedReward.getRewardType()){
                case CREST:
                System.out.println("You earned an ElementalCrest!!");
                player.addElementalCrest((ElementalCrest)earnedReward);
                break;
                case POWER:
                if(earnedReward instanceof TimeWarp){
                    System.out.println("You earned a TimeWarp power");
                    player.addTimeWarp((TimeWarp)earnedReward);
                }
                else if(earnedReward instanceof ArcaneBoost){
                    System.out.println("You earned an ArcaneBoost power");  
                    player.addArcaneBoost((ArcaneBoost)earnedReward);
                }
                else
                    System.out.println("An error has occured in Power Rewards?");
                
                break;
                case BONUS:
                if(earnedReward instanceof EssenceBonus)
                    displaySelectEssenceBonusColorPromt(player,(EssenceBonus)earnedReward);
                else if(earnedReward instanceof Bonus)
                    useColorBonusPrompt(player,(Bonus)earnedReward);    
                else
                    System.out.println("An error has occured in Bonus Rewards?");
            }
        }  
    }
    public void displaySelectEssenceBonusColorPromt(Player player,EssenceBonus bonus){
        System.out.println("You earned an EssenceBonus Choose which realm to attack ");
        boolean red=player.getRedRealm().isRealmAccessible();
        boolean blue=player.getBlueRealm().isRealmAccessible();
        boolean green=player.getGreenRealm().isRealmAccessible();
        boolean magenta=player.getMagentaRealm().isRealmAccessible();
        boolean yellow=player.getYellowRealm().isRealmAccessible();
        
        if(!red&&!green&&!blue&&!magenta&&!yellow){
            System.out.println("All Realms have no moves bonus lost");
            return;
        }
        //Printing the realms with color and crossed if not avaiable 
        printWithColor(RealmColor.RED, "Red ", !red);
        printWithColor(RealmColor.GREEN,"Green ", !green);
        printWithColor(RealmColor.BLUE,"Blue ", !blue);
        printWithColor(RealmColor.MAGENTA,"Magenta ", !magenta);
        printWithColor(RealmColor.YELLOW,"Yellow ", !yellow);
        System.out.println();
        //Selecting Color that only is avaialbe
         while(true){
            System.out.print("Choice: ");
            String choice=sc.next().trim().toUpperCase();
            if(!choice.isEmpty()){
                switch (choice.charAt(0)) {
                    case 'R':
                    if(red){
                    bonus.setEssenceBonusColor(RealmColor.RED);break;}
                    else
                    System.out.println("The Dragons have been defeated");
                    break;
                    case 'G':
                    if(green){
                    bonus.setEssenceBonusColor(RealmColor.GREEN);break;}
                    else
                    System.out.println("The Gurdians have been defeated");
                    break;
                    case 'B':
                    if(blue){
                    bonus.setEssenceBonusColor(RealmColor.BLUE);break;}
                    else
                    System.out.println("The Hydra has been defeated");
                    break;
                    case 'M':
                    if(magenta){
                    bonus.setEssenceBonusColor(RealmColor.MAGENTA);break;}
                    else
                    System.out.println("The Phoenix has been defeated");
                    break;
                    case 'Y':
                    if(yellow){
                    bonus.setEssenceBonusColor(RealmColor.YELLOW);break;}
                    else
                    System.out.println("The Lion have has defeated");
                    break;
                    default:System.out.println("Please Enter A valid Color");
                }
                if(bonus.getBonusColor()!=RealmColor.WHITE){
                useColorBonusPrompt(player, bonus);
                break;
                }
            }
            else System.out.println("Choice cannot be blank");   
        }
    }
    public void useColorBonusPrompt(Player player,Bonus bonus){
        if(!(bonus instanceof EssenceBonus))
            System.out.println("You earned a "+bonus.getBonusColor()+" Color Bonus!!");
        //Making sure not all realms closed
        boolean red=player.getRedRealm().isRealmAccessible();
        boolean blue=player.getRedRealm().isRealmAccessible();
        boolean green=player.getRedRealm().isRealmAccessible();
        boolean magenta=player.getRedRealm().isRealmAccessible();
        boolean yellow=player.getRedRealm().isRealmAccessible();
        
        if(!red&&!green&&!blue&&!magenta&&!yellow){
            System.out.println("All Realms have no moves bonus lost");
            return;
        }    

        Dice diceToBeUsed=null;
        switch(bonus.getBonusColor()){
            case RED:diceToBeUsed=selectRedColorBonusDragon(player);break;
            case GREEN:diceToBeUsed=selectGreenColorBonusGaia(player);break;
            case BLUE:
            BlueDice blueDice=new BlueDice(6);
            blueDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            diceToBeUsed=blueDice;break;
            case MAGENTA:
            MagentaDice magentaDice=new MagentaDice(6);
            magentaDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            diceToBeUsed=magentaDice;break;
            case YELLOW:
            YellowDice yellowDice=new YellowDice(6);
            yellowDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            diceToBeUsed=yellowDice;break;
            case WHITE:diceToBeUsed=null;System.out.println("An error has occured in Essence color selection");
        }
        attackSequence(player, diceToBeUsed);
    }
    public Dice selectRedColorBonusDragon(Player player){
        //Choosing which dragon
        RedRealm redRealm=player.getRedRealm();
        Dragon dragon1=redRealm.getDragon1();
        Dragon dragon2=redRealm.getDragon2();
        Dragon dragon3=redRealm.getDragon3();
        Dragon dragon4=redRealm.getDragon4();

        int choice=0;
        int possibleSelections=1;
        //Making sure only dragons that can be attack can be chosen
        if(!dragon1.isDeadDragon())
        printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon1 ", false);
        if(!dragon2.isDeadDragon())
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon2 ", false);
        if(!dragon3.isDeadDragon())
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon3 ", false);    
        if(!dragon4.isDeadDragon())
            printWithColor(RealmColor.RED,"("+(possibleSelections++)+") Dragon4 ", false);
        System.out.println();
        if(dragon1.isDeadDragon()&&dragon2.isDeadDragon()&&dragon3.isDeadDragon()&&dragon4.isDeadDragon()){
            System.out.println("All dragons are dead attacking a random dragon");
            RedDice bonusDice=new RedDice(1);    
            bonusDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            bonusDice.selectsDragon(1);
            return bonusDice; 
        }

        while(true){
            try{
            System.out.print("Choice: ");
            choice=Integer.parseInt(sc.next().trim());
            if(choice>=1&&choice<=4)
                break;
            System.out.println("Please choose a number between 1 and 4");    
            }catch(Exception ex){
                System.out.println("Please enter a valid number");
            }
        }
        Dragon dragon=null;
        //Shifting to see which dragon
        int indexSoFar=1;
        boolean found=false;
        if(!dragon1.isDeadDragon()){
            if(indexSoFar++==choice&&!found){
                choice=1;  
                found=true; }
        }
        if(!dragon2.isDeadDragon()){
            if(indexSoFar++==choice&&!found){
                choice=2;  
                found=true;}
        }
        if(!dragon3.isDeadDragon()){
            if(indexSoFar++==choice&&!found){
                choice=3;  
                found=true;}
        }
        if(!dragon4.isDeadDragon()){
            if(indexSoFar++==choice&&!found){
                choice=4;  
                found=true;}
        }
        switch (choice) {
            case 1:dragon=dragon1;break;
            case 2:dragon=dragon2;break;
            case 3:dragon=dragon3;break;
            case 4:dragon=dragon4;break;
            default:System.out.println("An error occured in dragon selection");
        }
        //choosing which part
        System.out.println("Please Choose which part to attack");
        if(!dragon.isFaceKilled())
            System.out.print("Face  ");
        if(!dragon.isWingsKilled())
            System.out.println("Wings  ");
        if(!dragon.isTailKilled())
            System.out.println("Tail  ");
        if(!dragon.isHeartKilled())
            System.out.println("Heart  ");
        System.out.println();    
        int diceValue=-1;
        while(true){
            String selection=sc.next().toUpperCase().trim();
            if(!selection.isEmpty()){
                switch (selection.charAt(0)) {
                   case 'F':
                        if(!dragon.isFaceKilled())
                            diceValue=dragon.getFace();
                        else
                            System.out.println("Not a valid Selection");break;  
                   case 'W':
                        if(!dragon.isWingsKilled())
                            diceValue=dragon.getWings();
                        else
                            System.out.println("Not a valid Selection");break;
                   case 'T':
                         if(!dragon.isTailKilled())
                            diceValue=dragon.getTail();
                         else
                            System.out.println("Not a valid Selection");break;
                   case 'H':
                        if(!dragon.isHeartKilled())
                            diceValue=dragon.getHeart();
                        else
                            System.out.println("Not a valid Selection");break;
                }
                if(diceValue!=-1)
                break;
            }else System.out.println("Choice cant be blank");
        }     
        RedDice bonusDice=new RedDice(diceValue);    
        bonusDice.setDiceStatus(DiceStatus.POWER_SELECTED);
        bonusDice.selectsDragon(choice);
        return bonusDice; 
    }
    public Dice selectGreenColorBonusGaia(Player player){
        System.out.println("Please Choose Which Gaia Gurdian To Attack");
        Gaia gaia=player.getGreenRealm().getGaia();
        boolean[] gaiaHealth=gaia.getGurdiansHealth();
        //Making sure green realm hasnt been defeated

        if(!player.getGreenRealm().isRealmAccessible()){
            System.out.println("Gaia realm has been defeated choosing randomly");
            GreenDice bonusDice=new GreenDice(2);
            bonusDice.setDiceStatus(DiceStatus.POWER_SELECTED);
            return bonusDice;
        }

        for(int i=2;i<gaiaHealth.length;i++)
            if(gaiaHealth[i])
                System.out.print(i+" ");
        System.out.println(); 
        int choiceNum;    
        while(true){
            System.out.print("Choice: ");
            String choice=sc.next().trim();
            try{
                choiceNum=Integer.parseInt(choice);
                if(choiceNum>=2&&choiceNum<=12){
                    if(gaiaHealth[choiceNum])
                        break;
                    else
                        System.out.println("This gaia is already dead choose another one");    
                }
                else
                    System.out.println("Please choose from the previous numbers");     
            }catch(Exception ex){System.out.println("Please Enter a  number");}
        }    
        GreenDice bonusDice=new GreenDice(choiceNum);
        bonusDice.setDiceStatus(DiceStatus.POWER_SELECTED);
        return bonusDice;
    }
    public void displayArcaneBoostPrompt(Player player){
        //No available dice or arcaneboosts
        if(player.getArcaneBoosts().size()==0
        ||getAvailableDice().length==0)
            return;
        resetDiceStatus();//To reset all the dice so player can reuse
        while(player.getArcaneBoosts().size()>0&&getAvailableDice().length>0){  
        System.out.println(player.getPlayerName()+" would you like to use an ArcaneBoost?"); 
        System.out.println("You have x"+player.getArcaneBoosts().size()+" ArcaneBoosts remaining");
        System.out.println("Enter YES or NO");
        System.out.print("Choice: ");
        String choice=""+sc.next().trim().toUpperCase().charAt(0);
        if(choice.equals("Y"))
            useArcaneBoost(player);
        else if(choice.equals("N"))
            return;
        else
             System.out.println("Invalid input please enter YES or No!!");        
        }
        //If player used all ArcaneBoost or All Dice
        if(player.getArcaneBoosts().size()==0){
            System.out.println("No more ArcaneBoosts Avaialble"); 
            createDelay();
        }
        else if(getAvailableDice().length==0){
            System.out.println("No more Dice Available"); 
            createDelay();
        }

    }
    public void useArcaneBoost(Player player){
        player.removeArcaneBoost();
        Dice boostDice=selectDiceSequenceArcane(player);
        boostDice.setDiceStatus(DiceStatus.POWER_SELECTED);
        if(boostDice.getDiceColor()==RealmColor.RED)
            ((RedDice)boostDice).selectsDragon(-1);
        attackSequence(player, boostDice);    
    }
    public Dice selectDiceSequenceArcane(Player player){
        //First select dice
        displayAvailableDice();
        int selectedDiceIndex=displaySelectArcaneDicePromt(player)-1;//-1 Because Prompt starts from 1
        Dice selectedDice=getAvailableDice()[selectedDiceIndex];
        //Prompt Player to choose Arcane Dice color if chosen
        RealmColor selectedDiceColor=selectedDice.getDiceColor();    
        if(selectedDiceColor==RealmColor.WHITE){
            displayChooseArcaneDiceColorPrompt(selectedDice);
            selectedDiceColor=((ArcanePrism)selectedDice).getChosenColor(); 
        }
        return selectedDice;
    }
    
    public int displaySelectArcaneDicePromt(Player player){
        Dice[]totalDice=getAvailableDice();

        //Check if user can bypass as no moves work
        boolean bypass=false;
        for(int i=0;i<totalDice.length;i++)
            if(getPossibleMovesForADie(player, totalDice[i]).length>0)break;
            else if(i==totalDice.length-1)bypass=true;
        if(bypass)
            System.out.println("All dice have no possible moves choose any");    

        while(true){
            System.out.println("Choose a number to select a dice");
            System.out.print("Choice: ");
            try{
                int choice=Integer.parseInt(sc.next().trim());
                if(1<=choice&&choice<=totalDice.length){
                    if(getPossibleMovesForADie(player, totalDice[choice-1]).length>0)
                        return choice;
                    else
                        System.out.println("No possible moves for this dice");
                }
                else
                    System.out.println("Please choose a number between 1 and "+(totalDice.length)+"!!");    

            }catch(Exception ex){
                System.out.println("Please enter a valid number");
            }
        }
    }

    // public Dice[] getForgottenAndAvailableDice(){
    //     int size=getAvailableDice().length+getForgottenRealmDice().length;
    //     Dice[]totalDice=new Dice[size];
    //     int index=0;
    //     Dice[] availableDice=getAvailableDice();
    //     Dice[] forgottenDice=getForgottenRealmDice();
    //     for(Dice x:availableDice)
    //         totalDice[index++]=x;
    //     for(Dice x:forgottenDice)
    //         totalDice[index++]=x;
    //     return totalDice;    
    // }
    // public void displayForgottenAndAvailableDice(Player player){
    //     int index=1;
    //     //displaying available dice
    //     Dice[]availableDice=getAvailableDice();
    //     System.out.println("Available dice:");    
    //     if(availableDice.length==0){
    //         System.out.println("No available dice");
    //     }else{
    //         for(int i=0;i<availableDice.length;i++){
    //             String text="("+(index++)+") "+availableDice[i]+"  ";
    //             printWithColor(availableDice[i].getDiceColor(), text,
    //             getPossibleMovesForADie(getPassivePlayer(),availableDice[i]).length==0);
    //         }
    //         System.out.println();
    //     }    
    //     //displaying forgotten realm dice
    //     System.out.println("Forgotten Realm Dice:");
    //     Dice[]forgottenDice=getForgottenRealmDice();
    //     if(forgottenDice.length==0){
    //         System.out.println("No dice in Forgotten Realm");
    //         return;
    //     }
    //     for(int i=0;i<forgottenDice.length;i++){
    //         String text="("+(index++)+") "+forgottenDice[i]+"  ";
    //         printWithColor(forgottenDice[i].getDiceColor(), text,
    //         getPossibleMovesForADie(getPassivePlayer(),forgottenDice[i]).length==0);
    //     }
    //     System.out.println();
    // }

    public void passivePlayerSequence(Player player){
        displayPlayerGrimore(player);
        System.out.println("Passive Player Choose from forgotten Realm");
        createDelay();
        createDelay();
        if(getForgottenRealmDice().length==0){
            System.out.println("No Dice Found in forgotten Realm");
            createDelay();
            return;
        }
        displayForgottenRealmDice();
        Dice selectedDice=selectDiceSequence(player);
        selectedDice.setDiceStatus(DiceStatus.AVAILABLE);
        attackSequence(player, selectedDice);
        //ArcaneBoost For active player
        displayArcaneBoostPrompt(getActivePlayer());
        //ArcaneBoost For passive player
        displayArcaneBoostPrompt(player);
    }
    public void resetDiceStatus(){
        Dice[] allDice= getAllDice();
        for(Dice x:allDice)
             x.setDiceStatus(DiceStatus.AVAILABLE);
     }
//=======================================Methods=======================================
    public  boolean switchPlayer(){
        Player activePlayer=getActivePlayer();
        Player passivePlayer=getPassivePlayer();
        passivePlayer.setPlayerStatus(PlayerStatus.ACTIVE);
        activePlayer.setPlayerStatus(PlayerStatus.PASSIVE);
        System.out.println("Switching");
        return true;
    }

    public  Dice[] rollDice(){
        Dice[] rolledDice=getAvailableDice();
        for(Dice currentDice:rolledDice)
            currentDice.rollDice();
        return rolledDice;
    }

    public  Dice[] getAvailableDice(){
        Dice[] allDice=getAllDice();
        int numberOfAvailableDice=0;
        for(Dice x:allDice)
            if(x.getDiceStatus()==DiceStatus.AVAILABLE)
                numberOfAvailableDice++;
       if(numberOfAvailableDice==0)
            return new Dice[0];
       Dice[] availableDice=new Dice[numberOfAvailableDice];

        for(int i=0,j=0;i<numberOfAvailableDice;j++)
            if(allDice[j].getDiceStatus()==DiceStatus.AVAILABLE)
                availableDice[i++]=allDice[j];

        return availableDice;        
    }
    
    public  Dice[] getAllDice(){
        return gameBoard.getAllDice();
    }

    public  Dice[] getForgottenRealmDice(){
        Dice[] allDice=getAllDice();
        int numberOfForgottenDice=0;
        for(Dice x:allDice)
            if(x.getDiceStatus()==DiceStatus.FORGOTTEN_REALM)
                numberOfForgottenDice++;

       Dice[] forgottenDice=new Dice[numberOfForgottenDice];

        for(int i=0,j=0;i<numberOfForgottenDice;j++)
            if(allDice[j].getDiceStatus()==DiceStatus.FORGOTTEN_REALM)
                forgottenDice[i++]=allDice[j];
                
        return forgottenDice;     
    }

    public  Move[] getAllPossibleMoves(Player player){
        BlueRealm playerBlueRealm=player.getBlueRealm();
        RedRealm  playerRedRealm=player.getRedRealm();
        GreenRealm playerGreenRealm=player.getGreenRealm();
        YellowRealm playerYellowRealm=player.getYellowRealm();
        MagentaRealm playerMagentaRealm=player.getMagentaRealm();

        Move[] blueRealmMoves=playerBlueRealm.getAllPossibleMoves();
        Move[] redRealmMoves=playerRedRealm.getAllPossibleMoves();
        Move[] greenRealmMoves=playerGreenRealm.getAllPossibleMoves();
        Move[] yellowRealmMoves=playerYellowRealm.getAllPossibleMoves();
        Move[] magentaRealmMoves=playerMagentaRealm.getAllPossibleMoves();

        return mergeMoves(redRealmMoves,greenRealmMoves,blueRealmMoves,magentaRealmMoves, yellowRealmMoves,new Move[0] );
    }

    public  Move[] getPossibleMovesForAvailableDice(Player player){
        ArcanePrism arcanePrism=gameBoard.getArcanePrism();
        BlueDice blueDice=gameBoard.getBlueDice();
        RedDice redDice=gameBoard.getRedDice();
        GreenDice greenDice=gameBoard.getGreenDice();
        YellowDice yellowDice=gameBoard.getYellowDice();
        MagentaDice magentaDice=gameBoard.getMagentaDice();

        
        Move[] redMoves=getPossibleMovesForADie(player, redDice);
        if(redDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            redMoves=new Move[0];
        Move[] greenMoves=getPossibleMovesForADie(player, greenDice);
        if(greenDice.getDiceStatus()!=DiceStatus.AVAILABLE||arcanePrism.getDiceStatus()==DiceStatus.AVAILABLE)
            greenMoves=new Move[0];
        Move[] blueMoves=getPossibleMovesForADie(player, blueDice);
        if(blueDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            blueMoves=new Move[0];    
        Move[] magentaMoves=getPossibleMovesForADie(player, magentaDice);
        if(magentaDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            magentaMoves=new Move[0];
        Move[] yellowMoves=getPossibleMovesForADie(player, yellowDice);
        if(yellowDice.getDiceStatus()!=DiceStatus.AVAILABLE)
            yellowMoves=new Move[0];
        Move[] arcaneMoves=getPossibleMovesForADie(player, arcanePrism);
        if(arcanePrism.getDiceStatus()!=DiceStatus.AVAILABLE)
            arcaneMoves=new Move[0];

        return mergeMoves(redMoves, greenMoves,blueMoves,magentaMoves,yellowMoves,arcaneMoves);
    }

    public  Move[] getPossibleMovesForADie(Player player, Dice dice){
     
        RealmColor diceColor=dice.getDiceColor();
        int diceValue=dice.getValue();

        Move[] blueRealmMoves=new Move[0];
        Move[] redRealmMoves=new Move[0];
        Move[] greenRealmMoves=new Move[0];
        Move[] yellowRealmMoves=new Move[0];
        Move[] magentaRealmMoves=new Move[0];
        
        if(diceColor==RealmColor.BLUE||diceColor==RealmColor.WHITE){
            BlueRealm playerBlueRealm=player.getBlueRealm();
            blueRealmMoves=playerBlueRealm.getPossibleMovesForADie(diceValue, diceColor);
        }
        if(diceColor==RealmColor.RED||diceColor==RealmColor.WHITE){
            RedRealm  playerRedRealm=player.getRedRealm();
            redRealmMoves=playerRedRealm.getPossibleMovesForADie(diceValue, diceColor);
        }
        if(diceColor==RealmColor.GREEN||diceColor==RealmColor.WHITE){
            GreenRealm playerGreenRealm=player.getGreenRealm();
            int sumDiceValue=diceValue;
            
            if(diceColor==RealmColor.WHITE){
                GreenDice greenDice=gameBoard.getGreenDice();
                sumDiceValue+=greenDice.getValue();
            }
            else{
                ArcanePrism arcanePrism=gameBoard.getArcanePrism();
                sumDiceValue+=arcanePrism.getValue();
            }

            greenRealmMoves=playerGreenRealm.getPossibleMovesForADie(sumDiceValue, diceColor);
        }
        if(diceColor==RealmColor.YELLOW||diceColor==RealmColor.WHITE){
            YellowRealm playerYellowRealm=player.getYellowRealm();
            yellowRealmMoves=playerYellowRealm.getPossibleMovesForADie(diceValue, diceColor);
        }
        if(diceColor==RealmColor.MAGENTA||diceColor==RealmColor.WHITE){
            MagentaRealm playerMagentaRealm=player.getMagentaRealm();
            magentaRealmMoves=playerMagentaRealm.getPossibleMovesForADie(diceValue, diceColor);
        }

        return mergeMoves( redRealmMoves, greenRealmMoves,blueRealmMoves,magentaRealmMoves,yellowRealmMoves,new Move[0] );
    }

    public Move[] mergeMoves(Move[] red,Move[] green,Move[] blue,Move[]magenta,Move[] yellow,Move[]white){
        int moveArraySize=0;
        moveArraySize+=red.length;
        moveArraySize+=green.length;
        moveArraySize+=blue.length;
        moveArraySize+=magenta.length;
        moveArraySize+=yellow.length;
        moveArraySize+=white.length;

        
        Move[]moves=new Move[moveArraySize];
        int index=0;
        //red
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.RED)
                moves[index++]=white[i];
        for(Move x:red)
            moves[index++]=x;
        //Green
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.GREEN)
                moves[index++]=white[i];
        for(Move x:green)
            moves[index++]=x;    
        //blue
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.BLUE)
                moves[index++]=white[i];
        for(Move x:blue)
            moves[index++]=x;    
        //magenta
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.MAGENTA)
                moves[index++]=white[i];
        for(Move x:magenta)
            moves[index++]=x;    
        //yellow
        for(int i=0;i<white.length;i++)
            if(white[i].getDice().getDiceColor()==RealmColor.YELLOW)
                moves[index++]=white[i];
        for(Move x:yellow)
            moves[index++]=x;    

        return moves;
    }

    public  GameBoard getGameBoard(){
        return gameBoard;
    }

    public  Player getActivePlayer(){
        return gameBoard.getActivePlayer();
    }

    public  Player getPassivePlayer(){
        return gameBoard.getPassivePlayer();
    }

    public  ScoreSheet getScoreSheet(Player player){
        return player.getScoreSheet();
    }


    public  GameStatus getGameStatus(){
        return getGameBoard().getGameStatus();
    }

    public  GameScore getGameScore(Player player){
        return player.getGameScore();
    }

    public  TimeWarp[] getTimeWarpPowers(Player player){
        ArrayList<TimeWarp> timeWarp=player.getTimeWarps();

        if(timeWarp==null||timeWarp.isEmpty())//May change implementation based on tests
            return new TimeWarp[0];

        TimeWarp[] timeWarpOutput=new TimeWarp[timeWarp.size()];
        for(int i=0;i<timeWarpOutput.length;i++)
            timeWarpOutput[i]=timeWarp.get(i);

        return timeWarpOutput;    
    }

    public  ArcaneBoost[] getArcaneBoostPowers(Player player){
        ArrayList<ArcaneBoost> arcaneBoosts=player.getArcaneBoosts();

        if(arcaneBoosts==null||arcaneBoosts.isEmpty())//May change implementation based on tests
            return new ArcaneBoost[0];

        ArcaneBoost[] arcaneBoostOutput=new ArcaneBoost[arcaneBoosts.size()];
        for(int i=0;i<arcaneBoostOutput.length;i++)
            arcaneBoostOutput[i]=arcaneBoosts.get(i);
            
        return arcaneBoostOutput;    
    }

    public  boolean selectDice(Dice dice, Player player){//TODO  dont make set forgtten until after dice found
       Dice[] availableDice=getAvailableDice();
       boolean foundDice=false;
       for(Dice x:availableDice){
        if(x==dice){
            foundDice=true;
            x.setDiceStatus(DiceStatus.TURN_SELECTED);
        }
        else if(x.getValue()<dice.getValue())    
            x.setDiceStatus(DiceStatus.FORGOTTEN_REALM);
       }
        return foundDice;    
    }
    public  boolean makeMove(Player player, Move move){ 
        Dice moveDice=move.getDice();
        Creature moveCreature=move.getMoveCreature();
        RealmColor color=moveDice.getDiceColor();
        if(color==RealmColor.WHITE)
            color=((ArcanePrism)moveDice).getChosenColor();
        switch(color){
            case BLUE:
            return player.getBlueRealm().attack(moveDice.getValue(), moveCreature);
            case RED:
            return player.getRedRealm().attack(moveDice.getValue(), moveCreature);
            case GREEN:
            int sumValue=0;
            sumValue+=gameBoard.getGreenDice().getValue();
            sumValue+=gameBoard.getArcanePrism().getValue();  
            return player.getGreenRealm().attack(sumValue, moveCreature);
            case YELLOW:
            return player.getYellowRealm().attack(moveDice.getValue(), moveCreature);
            case MAGENTA:
            return player.getMagentaRealm().attack(moveDice.getValue(), moveCreature);
            case WHITE:System.out.println("Error Attacking WHite");
        }
        return false;
    }
}
