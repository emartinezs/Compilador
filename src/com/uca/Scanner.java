package com.uca;

public class Scanner {

    private Lexicon.Token token;
    private String lexeme;
    private char c = ' ';

    private FileManager inputFile;
    private FileManager outputFile;

    public void scan(String fileName){
        inputFile = new FileManager(fileName);
        inputFile.openFile();

        outputFile = new FileManager("output.txt");
        outputFile.createFile();
        outputFile.clearFile();

        System.out.println("Tokens:");
        while (!inputFile.isEndOfFile()){
            getToken();
            if (token != null) {
                System.out.println(lexeme+" -> "+token.toString());
                outputFile.writeLine(lexeme+" -> "+token.toString());
            }
        }

        outputFile.closeFile();
        inputFile.closeFile();
        System.out.println("Analisis lexicografico finalizado.");
    }

    public void getToken(){
        lexeme = "";
        token = null;
        while (c == ' ' || c == '\n' || c == '\t'){
            c = getChar();
        }
        if (Character.isLetter(c)){
            lexeme = lexeme.concat(Character.toString(c));
            c = getChar();
            while (Character.isLetter(c)){
                lexeme = lexeme.concat(Character.toString(c));
                c = getChar();
            }
            int i;
            boolean isReservedWord = false;
            for (i=0; i<Parameters.MAX_RESERVED_WORDS; i++){
                if (Lexicon.getReservedWordsLexemes()[i].equals(lexeme)){
                    isReservedWord = true;
                    break;
                }
            }
            if (isReservedWord) {
                token = Lexicon.getReservedWordsTokens()[i];
            }else{
                token = Lexicon.Token.IDENTIFIER;
            }
        }else if (Character.isDigit(c)){

        }else {
            lexeme = Character.toString(c);
            token = Lexicon.getSpecialSymbolsTokens()[c];
            c = getChar();
        }
    }

    public char getChar(){
        return inputFile.getNextChar();
    }
}
