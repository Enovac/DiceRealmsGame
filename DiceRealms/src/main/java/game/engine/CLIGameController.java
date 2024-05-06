package main.java.game.engine;
import main.java.game.dice.*;

import java.util.*;
import main.java.game.collectibles.*;
import main.java.game.realms.*;
import main.java.game.creatures.*;
public class CLIGameController extends GameController{
    private GameBoard gameBoard;
    private Scanner sc;
    
    public CLIGameController(){
        gameBoard=new GameBoard();
        sc=new Scanner(System.in);
    }
//============================GameFlow=================================================
    public  void startGame(){
        createDelay();
        createDelay();
    
        while(!getGameStatus().isGameFinished()){
            Player currentPlayer=getActivePlayer();
            System.out.println("Round: "+getGameStatus().getGameRound());
            System.out.println(currentPlayer.getPlayerType()+"'s Turn!!!");
            createDelay();
            System.out.println("Printing your Grimore......");
            createDelay();
            System.out.println(getScoreSheet(currentPlayer));
            while(getGameStatus().getTurn()<=3){
                System.out.println("Turn: "+getGameStatus().getTurn());
                createDelay();
                
                System.out.println("Rolling dice......");
                createDelay();
                if(getAvailableDice()==null){
                    System.out.println("No more dice to roll :( ");
                    break;
                }
                rollDice();
                displayAvailableDice();
                displayTimeWarp(currentPlayer);
                attackSequence(currentPlayer);
                System.out.println(getScoreSheet(currentPlayer));
                getGameStatus().incrementTurn();
            } 
            
            getGameStatus().incrementRound();
            switchPlayer();
            System.out.println("Passive player choose dice from forgotten realm later..");
            resetDiceStatus();
        }
        if(getGameScore(getActivePlayer()).getTotalGameScore()>getGameScore(getPassivePlayer()).getTotalGameScore())
            System.out.println(getActivePlayer().getPlayerType()+" Wins!!!");
        else
            System.out.println(getPassivePlayer().getPlayerType()+" Wins!!!");     
    }
    public void resetDiceStatus(){
       Dice[] allDice= getAllDice();
       for(Dice x:allDice)
            x.setDiceStatus(DiceStatus.AVAILABLE);
    }
    public void checkAndGetPossibleRewards(Player player,RealmColor diceColor){
        Realms checkInRealm=null;
        switch(diceColor){
            case RED:checkInRealm=player.getRedRealm();break;
            case BLUE:checkInRealm=player.getBlueRealm();break;
            case GREEN:checkInRealm=player.getGreenRealm();break;
            case YELLOW:checkInRealm=player.getYellowRealm();break;
            case MAGENTA:checkInRealm=player.getMagentaRealm();break;
            case WHITE:System.out.println("Error checking reward in white realm");//change later
        }
        if(!checkInRealm.isRewardAvailable())
            return;
        Reward storeReward=checkInRealm.getReward();// TODO: FORGET TO FIX IMPLEMENTATION TO ARRAYSSSSSSSSSS
        switch (storeReward.getRewardType()) {
            case CREST:player.addElementalCrest((ElementalCrest)storeReward);break; 
            case POWER:
            if(storeReward instanceof TimeWarp){
                System.out.println("You got a TimeWarp power");
                player.addTimeWarp((TimeWarp)storeReward);
            }
            else{
                System.out.println("You got an ArcaneBoost power");  
                player.addArcaneBoost((ArcaneBoost)storeReward);
            }break;
            case BONUS:
            if(storeReward instanceof EssenceBonus){
                System.out.println("You got an Essence Bonus");  
                //Calls the Method that interrupts and uses Bonus!!!!
            }
            else{
                System.out.println("You got a insertColor  Bonus");  
                //Calls the Method That interrupts and uses Bonuss!!!
            }
            System.out.println("Bonuses are not yet implemented");
        } 
        createDelay(); 
    }
    public Creature chooseDragon(Player player){
        System.out.println();
        System.out.print("Chosen Dragon:");
        System.out.println();
        String chosen=sc.next();
        int dragonNumber=Integer.parseInt(""+chosen.charAt(chosen.length()-1));
        switch (dragonNumber) {
            case 1:return player.getRedRealm().getDragon1();
            case 2:return player.getRedRealm().getDragon2();
            case 3:return player.getRedRealm().getDragon3();
            case 4:return player.getRedRealm().getDragon4();
        }
        return null;//error occured
    }
    public Creature chooseCreatureToAttack(RealmColor color,Player player){
        switch (color) {
            case RED:System.out.println(player.getRedRealm());
            System.out.println("Please Choose a dragon");
            Dragon[] aliveDragons=player.getRedRealm().getAliveDragons();
            for(Dragon x:aliveDragons)
                System.out.print(x+" ");
            return chooseDragon(player);

            case BLUE:System.out.println(player.getBlueRealm());
            System.out.println("Attacking Hydra....");
            createDelay();
            return player.getBlueRealm().getHydra();

            case GREEN:System.out.println(player.getGreenRealm());
            System.out.println("Attacking Gaia Gurdian....");
            createDelay();
            return player.getGreenRealm().getGaia();

            case YELLOW:System.out.println(player.getYellowRealm());
            System.out.println("Attacking Lion....");
            createDelay();
            return player.getYellowRealm().getLion();

            case MAGENTA:System.out.println(player.getMagentaRealm());
            System.out.println("Attacking Phoenix....");
            createDelay();
            return player.getMagentaRealm().getPhoenix();
            case WHITE:System.out.println("Error Arcane not set");
        }
        return null;//Invalid Color Entered
    }
    public void chooseArcaneDiceColor(){
        System.out.println("Please chose a color for the Arcane Prism:");
        System.out.println("Red Blue Green Yellow Magenta");
        System.out.print("Chosen color: ");
        getGameBoard().getArcanePrism().setChosenColor(sc.next());
    }
    
    public void createDelay(){
        try{
            Thread.sleep(1500);
        }
        catch(Exception ex){
            System.out.println("Error in Thread Sleep");
            ex.printStackTrace();}
    }
//============================Display====================================================
    public void displayAvailableDice(){
        Dice[]availableDice=getAvailableDice();
        for(int i=0;i<availableDice.length;i++)
            System.out.print(i+"-"+availableDice[i]);
        System.out.println();

    }
    public int chooseAvailableDice(){
        Dice[]availableDice=getAvailableDice();
        System.out.println("Choose a number between 0 and "+(availableDice.length-1)+" to pick a die");
        System.out.print("Chosen Number: ");
        String chosenDie=sc.next().trim();
        //Error here
        return Integer.parseInt(chosenDie);
    }
    public void displayForgottenDice(){
        System.out.println("Forgotten Realm Dice: ");
        Dice[]forgottenDice=getForgottenRealmDice();
        for(Dice x:forgottenDice)
            System.out.println(x+"  ");
    }
    public void displayTimeWarp(Player player){
        while(true){
        System.out.println("Would you like to use Time Warp?");
        int numberOfTimeWarps=player.getTimeWarps().size();
        System.out.println("You have x"+numberOfTimeWarps+" TimeWarps Type Yes or No");
        System.out.print("Choice: ");
        char choice=sc.next().trim().toUpperCase().charAt(0);
        if(choice=='Y'){
            if(numberOfTimeWarps>0){
                player.removeTimeWarp();
                System.out.println("TimeWarp Used!! Rerolling dice.....");
                createDelay();
                rollDice();
                displayAvailableDice();
            }
            else{
                System.out.println("Not enough TimeWarps :( ");
                break;
            }
        }
        else break;
        }
    }
    public void attackSequence(Player currentPlayer){
        int chosenDie=chooseAvailableDice();
                Dice[] availableDice=getAvailableDice();
                Dice selectedDie=availableDice[chosenDie];
                selectDice(selectedDie, currentPlayer);
                System.out.println("Sending remaining Dice to forgoten realm.....");
                createDelay();
                displayForgottenDice();
                createDelay();
                RealmColor theDiceColor=selectedDie.getDiceColor();
                if(selectedDie.getDiceColor()==RealmColor.WHITE){
                    chooseArcaneDiceColor();
                    theDiceColor=((ArcanePrism)selectedDie).getChosenColor();
                }
                Creature selectedCreature=chooseCreatureToAttack(theDiceColor, currentPlayer);
                boolean suc=makeMove(currentPlayer,new Move(selectedDie,selectedCreature));
                System.out.println(suc?"Attack was successful!!":"Attack failed :(");
                checkAndGetPossibleRewards(currentPlayer, theDiceColor);
                displayArcaneBoost(currentPlayer);
    }
    public void displayArcaneBoost(Player player){
        while(true){
            System.out.println("Would you like to use ArcaneBoost?");
            displayAvailableDice();
            int numberOfArcaneBoosts=player.getArcaneBoosts().size();
            System.out.println("You have x"+numberOfArcaneBoosts+" ArcaneBoosts Type Yes or No");
            System.out.print("Choice: ");
            char choice=sc.next().trim().toUpperCase().charAt(0);
            if(choice=='Y'){
                if(numberOfArcaneBoosts>0){
                    player.removeArcaneBoost();
                    System.out.println("ArcaneBoost used!!");
                    createDelay();
                    
                }
                else{
                    System.out.println("Not enough ArcaneBoosts :( ");
                    break;
                }
            }
            else break;
            }

    }
//============================Methods====================================================    
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
            return null;
       Dice[] availableDice=new Dice[numberOfAvailableDice];

        for(int i=0,j=0;i<numberOfAvailableDice;j++)
            if(allDice[j].getDiceStatus()==DiceStatus.AVAILABLE)
                availableDice[i++]=allDice[j];

        return availableDice;        
    }
    
    public  Dice[] getAllDice(){
        return gameBoard.getAllDice();//or Do I do seperate Dice?
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

    public  boolean selectDice(Dice dice, Player player){

        //Do i make mara from passive mara from Active??????????
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
        //getGameScore(player).calculateGameScore();
        return false;
    }
}
