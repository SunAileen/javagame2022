/*
Names: Aileen, Arman, Akshayan, Alisha
Date: July 22, 2022
Program Name: HangmanCulminating
Description: Creating a word guessing game, while drawing out a part of a man's figure each time you guess a wrong word
 */


import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.Arrays;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author AE
 */
public class HangmanCulminating2 extends javax.swing.JDialog {

    int minute = 0;
    int second = 0;
    int lives = 0;
    int paintCounter = 0;
    boolean winner = false;
    boolean loser = false;
    Timer timer;
    private String word; // word the user has to guess
    private String displayWord = ""; // word shown on screen with underscores

    public static String wrongCharacter;
    public static String guessedCharacter;

    /**
     * Creates new form HangmanCulminating2
     */
    public HangmanCulminating2() {
        initComponents();

    }

    public void timer() {
        timer = new Timer(1000, new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                second++;
                if (second == 60) {
                    minute++;
                    second = 0;
                }

                if (second < 10) {
                    lblSecond.setText("0" + second);
                } else {
                    lblSecond.setText("" + second);
                }
                if (minute < 10) {
                    lblMinute.setText("0" + minute);
                } else {
                    lblMinute.setText("0" + minute);
                }
                // throw new UnsupportedOperationException("Not supported yet."); // Generated
                // from
                // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        });

        timer.start();
    }

    //
    public void paint() {
        String fileName = "";
        Graphics g = null;
        //super.paint(g);
        // Change color to cream
        g.setColor(Color.BLACK);
        // Draw Hang
        if (paintCounter == 0) {
            g.drawLine(80, 300, 80, 500);
            g.drawLine(80, 300, 250, 300);
            g.drawLine(240, 300, 240, 350);
            g.drawLine(50, 500, 230, 500);
            g.drawLine(120, 300, 80, 350);
            paintCounter++;
            // } else if (paintCounter == 1) {
            // Draw and fill the face
            g.setColor(Color.BLACK);
            g.drawOval(220, 350, 40, 40);
            g.setColor(Color.MAGENTA);
            g.fillOval(220, 350, 40, 40);
            // } else if (paintCounter == 2) {
            // Draw the body
            g.setColor(Color.BLACK);
            g.drawLine(240, 390, 240, 440);
            // } else if (paintCounter == 3) {
            // Draw the right hand
            g.drawLine(240, 410, 260, 390);
            // } else if (paintCounter == 4) {
            // Draw the left hand
            g.drawLine(240, 410, 220, 390);
            // } else if (paintCounter == 5) {
            // Draw the right foot
            g.drawLine(240, 440, 260, 460);
            // } else if (paintCounter == 6) {
            // Draw the left foot
            g.drawLine(240, 440, 220, 460);
            // Draw the left eye
            //       } else if (paintCounter == 8) {
            g.drawOval(225, 365, 5, 5);
            g.fillOval(225, 365, 5, 5);
            // Draw the right eye
            g.drawOval(245, 365, 5, 5);
            g.fillOval(245, 365, 5, 5);
            if (winner) {
                // Draw the smile
                g.drawArc(235, 370, 10, 10, 0, -180);
                fileName = "victory.wav";
            }
            //    if (loser) {
            // Draw the cry
            g.drawArc(235, 380, 10, 5, 0, 180);
            fileName = "loser.wav";
            // }
            try {
                // g.drawLine(150,100,155,105);
                playMusic(fileName);

                // draw circle outline
                // gh.drawOval(80,80,150,150);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(HangmanCulminating2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HangmanCulminating2.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void playMusic(String fileName) throws LineUnavailableException, IOException {
        File music = new File(fileName);
        AudioInputStream audio = null;
        try {
            audio = AudioSystem.getAudioInputStream(music);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(HangmanCulminating2.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(HangmanCulminating2.class.getName()).log(Level.SEVERE, null, ex);
        }
        Clip clip = AudioSystem.getClip();
        clip.open(audio);
        clip.start();
    }

    public void play(int diff){
        //Calls randomWord function to get and use a random word
        word = randomWord(); //calls function to get a word
        int length = word.length(); //creates a variable for the word's length

        //Creates the display word given hints
        for(int i = 0; i < length; i++){ //loops through every letter of the chosen word
            if(diff != 3 && length > 1 && i == 0){ //if easy or medium mode and word is longer than 1 letter, reveal first letter as hint
                displayWord += word.charAt(i);
            }else if(diff == 1 && length > 2 && i == 1){ //if easy mode and word is longer than 2 letters, reveal second letter as hint
                displayWord += word.charAt(i);
            }else{ //otherwise, add an underscore for each letter
                displayWord += "_ "; //add an underscore to represent each letter
            }
        }

        //Sets unknown word in the text field to show the user
        txtCorrectLetterOutput.setText(displayWord);
    }

    /*
     * Pre-condition: no input
     * Post-condition: returns a random word as a String
     */
    private String randomWord() {
        String output = ""; // creates the output string
        try {
            File file = new File("words.txt"); // initiates the file
            Scanner fileScanner = new Scanner(file); // initiates the scanner
            for (int i = 0; i < (int) (Math.random() * 920); i++) { // creates a random integer between 0-920 and skips
                // every line before that number
                fileScanner.nextLine();
            }
            output = fileScanner.nextLine(); // output takes in random word at the line stopped at
        } catch (Exception e) { // catch with error message to user in case of any error
            txtCorrectLetterOutput.setText("Sorry, something went wrong. Please try again");
        }
        return output;
    }

    public void mainInput() throws Exception {
        lifeCheck();// Call the life check method
        txtResult.setText("Please enter your guess.");// Prompt user for input
        String guess = txtLetterGuessInput.getText();// Store the input into a string variable.
        characterGuess(word, guess);// Call the method to check.
    }

    public void lifeCheck() throws Exception {// Life check
        if (lives > 0) {// If the number of lives is greater than 0
            txtLivesOutput.setText("You have " + lives + " lives remaining.");
        } else {// else, that means they have no more lives
            loser = true;
            paint();
            txtLivesOutput.setText("You ran out of lives! You have lost the game.");
        }
    }

    public void characterGuess(String word, String guess) throws Exception {
        if (wrongCharacter.contains(guess)) {// If the input equals a wrong character already guessed
            txtResult.setText("This character has already been guessed as a wrong answer.");
            check(word);// call the method that checks the "givenWord"
        } else if (guessedCharacter.contains(guess)) {// else if the
            txtResult.setText("This character is already in the word.");
            check(word);
        } else if (word.contains(guess)) {
            guessedCharacter += guess;
            txtResult.setText("You have guessed the character " + guess + " correctly.");
            check(word);
        } else {
            txtResult.setText("The word does not contain the character " + guess + ".");
            paint();
            wrongCharacter += guess + "\n";
            lives--;
            check(word);
        }
    }

    public void check(String word) throws Exception {
        int endGame = 0;
        for (int i = 0; i < word.length(); i++) {
            if (guessedCharacter.contains(Character.toString((word.charAt(i))))) {
                txtResult.append(word.charAt(i) + " ");
            } else if (!guessedCharacter.equals(Character.toString(word.charAt(i)))) {
                // if the the input from the user doesn't equal to the word's letter, it will
                // perform this
                String[] letterInput; // creating a new array called letterInput

                letterInput = new String[Integer.parseInt(guessedCharacter)]; // adding the guessedCharacter to the list

                Arrays.sort(letterInput); // sorting method to have the letters sorted alphabetically

                txtWrongGuessOuput.setText(Arrays.toString(letterInput));
                // outputting these wrongly guessed letters in txtWrongGuessOuput
            } else {
                txtResult.setText("_ ");
                endGame = 1;
            }
        }

        if (endGame == 0) {
            winner = true;
            paint();
            txtResult.setText("You have guessed the word " + word + " with " + lives + "lives remaining. ");
        } else {
            mainInput();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWrongGuesses = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtWrongGuessOuput = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblUnknownWord = new javax.swing.JLabel();
        txtCorrectLetterOutput = new javax.swing.JTextField();
        lblLetterGuess = new javax.swing.JLabel();
        lblLives = new javax.swing.JLabel();
        txtLetterGuessInput = new javax.swing.JTextField();
        txtLivesOutput = new javax.swing.JTextField();
        btnEnd = new javax.swing.JButton();
        lblMinute = new javax.swing.JLabel();
        lblColon = new javax.swing.JLabel();
        lblSecond = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtResult = new javax.swing.JTextArea();
        btnAddLetter = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblWrongGuesses.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblWrongGuesses.setForeground(new java.awt.Color(0, 204, 204));
        lblWrongGuesses.setText("Wrong guesses:");

        txtWrongGuessOuput.setEditable(false);
        txtWrongGuessOuput.setColumns(20);
        txtWrongGuessOuput.setRows(5);
        jScrollPane2.setViewportView(txtWrongGuessOuput);

        jLabel8.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 204, 204));
        jLabel8.setText("Mr. Hangman!");

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 255));
        jLabel1.setText("Program result:");

        lblTitle.setFont(new java.awt.Font("Stencil", 0, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 0, 255));
        lblTitle.setText("Hangman");

        lblUnknownWord.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        lblUnknownWord.setForeground(new java.awt.Color(0, 204, 204));
        lblUnknownWord.setText("Unknown word:");

        txtCorrectLetterOutput.setEditable(false);
        txtCorrectLetterOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCorrectLetterOutputActionPerformed(evt);
            }
        });

        lblLetterGuess.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        lblLetterGuess.setForeground(new java.awt.Color(255, 0, 255));
        lblLetterGuess.setText("Guess a letter:");

        lblLives.setFont(new java.awt.Font("Segoe UI Black", 0, 14)); // NOI18N
        lblLives.setForeground(new java.awt.Color(255, 0, 0));
        lblLives.setText("Lives ");

        txtLetterGuessInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLetterGuessInputActionPerformed(evt);
            }
        });

        txtLivesOutput.setEditable(false);
        txtLivesOutput.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        txtLivesOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLivesOutputActionPerformed(evt);
            }
        });

        btnEnd.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnEnd.setForeground(new java.awt.Color(255, 0, 0));
        btnEnd.setText("End Game");
        btnEnd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEndActionPerformed(evt);
            }
        });

        lblMinute.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblMinute.setForeground(new java.awt.Color(0, 204, 204));
        lblMinute.setText("00");

        lblColon.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblColon.setForeground(new java.awt.Color(0, 204, 204));
        lblColon.setText(":");

        lblSecond.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSecond.setForeground(new java.awt.Color(0, 204, 204));
        lblSecond.setText("00");

        txtResult.setEditable(false);
        txtResult.setColumns(20);
        txtResult.setRows(5);
        jScrollPane3.setViewportView(txtResult);

        btnAddLetter.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        btnAddLetter.setForeground(new java.awt.Color(255, 0, 0));
        btnAddLetter.setText("Add the letter");
        btnAddLetter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblMinute)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblColon)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblSecond)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                        javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lblTitle)
                                                .addGap(128, 128, 128)
                                                .addComponent(btnEnd)
                                                .addGap(29, 29, 29))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(18, 18, 18)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 411,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                        Short.MAX_VALUE))))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(lblLives)
                                                .addGap(18, 18, 18)
                                                .addComponent(txtLivesOutput, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(129, 129, 129)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 112,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(94, 94, 94)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout
                                                                .createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(17, 17, 17)
                                                                        .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(lblUnknownWord)
                                                                                .addComponent(txtCorrectLetterOutput,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                        301,
                                                                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                        layout.createSequentialGroup()
                                                                                .addGap(29, 29, 29)
                                                                                .addGroup(layout.createParallelGroup(
                                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                                        .addComponent(lblWrongGuesses)
                                                                                        .addComponent(jScrollPane2,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                289,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(97, 97, 97)
                                                                .addComponent(btnAddLetter,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE, 145,
                                                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(18, 18, 18)
                                                                .addGroup(layout.createParallelGroup(
                                                                        javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(txtLetterGuessInput,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                301,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addComponent(lblLetterGuess))))))
                                .addContainerGap(40, Short.MAX_VALUE)));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(1, 1, 1)
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(btnEnd)
                                                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(lblMinute)
                                                .addComponent(lblColon)
                                                .addComponent(lblSecond)))
                                .addGap(14, 14, 14)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout
                                                        .createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(lblLives, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(txtLivesOutput,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE, 41,
                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel1))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(39, 39, 39)
                                                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 39,
                                                        Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lblUnknownWord, javax.swing.GroupLayout.PREFERRED_SIZE, 19,
                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtCorrectLetterOutput, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblLetterGuess, javax.swing.GroupLayout.PREFERRED_SIZE, 19,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtLetterGuessInput, javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(22, 22, 22)
                                .addComponent(btnAddLetter)
                                .addGap(18, 18, 18)
                                .addComponent(lblWrongGuesses, javax.swing.GroupLayout.PREFERRED_SIZE, 27,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 144,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(18, Short.MAX_VALUE)));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCorrectLetterOutputActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtCorrectLetterOutputActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtCorrectLetterOutputActionPerformed

    private void txtLetterGuessInputActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtLetterGuessInputActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtLetterGuessInputActionPerformed

    private void txtLivesOutputActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtLivesOutputActionPerformed
        // TODO add your handling code here:
    }// GEN-LAST:event_txtLivesOutputActionPerformed

    private void btnEndActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnEndActionPerformed
        this.dispose(); //exit from hangman game screen
        ExitScreen2 obj = new ExitScreen2();
        obj.setVisible(true);
    }// GEN-LAST:event_btnEndActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddActionPerformed
        try{
            mainInput();
            check(word);
        }catch (Exception e){
            System.out.println("something wrong with add button");
        }
    }// GEN-LAST:event_btnAddActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
        // (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default
         * look and feel.
         * For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HangmanCulminating2.class

                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HangmanCulminating2.class

                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HangmanCulminating2.class

                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HangmanCulminating2.class

                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HangmanCulminating2().setVisible(true);

            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddLetter;
    private javax.swing.JButton btnEnd;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblColon;
    private javax.swing.JLabel lblLetterGuess;
    private javax.swing.JLabel lblLives;
    private javax.swing.JLabel lblMinute;
    private javax.swing.JLabel lblSecond;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblUnknownWord;
    private javax.swing.JLabel lblWrongGuesses;
    private javax.swing.JTextField txtCorrectLetterOutput;
    private javax.swing.JTextField txtLetterGuessInput;
    private javax.swing.JTextField txtLivesOutput;
    private javax.swing.JTextArea txtResult;
    private javax.swing.JTextArea txtWrongGuessOuput;
    // End of variables declaration//GEN-END:variables
}
