// $ANTLR 3.2 Sep 23, 2009 12:02:23 C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g 2010-03-17 17:00:29

package net.moraleboost.flux.lang;


import org.antlr.runtime.*;

public class SolrqlLexer extends Lexer {
    public static final int T_FROM=10;
    public static final int EXPONENT=135;
    public static final int T_OPERATOR_AND=27;
    public static final int STRING_CORE=128;
    public static final int T_OPERATOR_PLUS=37;
    public static final int NOT=72;
    public static final int T_OPTIMIZE=25;
    public static final int EOF=-1;
    public static final int RPAREN=60;
    public static final int T_INTO=6;
    public static final int INSERT=56;
    public static final int T_AS=22;
    public static final int T_OPERATOR_DIVIDE=42;
    public static final int SELECT=67;
    public static final int STRING_ESCAPE_CORE=129;
    public static final int INTO=57;
    public static final int ID_ESCAPE_CORE=134;
    public static final int D=101;
    public static final int LESS_THAN_OR_EQUAL_TO=78;
    public static final int E=102;
    public static final int F=103;
    public static final int G=104;
    public static final int A=98;
    public static final int B=99;
    public static final int ASC=91;
    public static final int T_OPERATOR_GREATER_THAN_OR_EQUAL_TO=36;
    public static final int C=100;
    public static final int L=109;
    public static final int T_ORDER=14;
    public static final int M=110;
    public static final int N=111;
    public static final int O=112;
    public static final int T_ORDERING=15;
    public static final int H=105;
    public static final int NULL=86;
    public static final int I=106;
    public static final int J=107;
    public static final int QUOTE_DOUBLE=94;
    public static final int K=108;
    public static final int U=118;
    public static final int T=117;
    public static final int W=120;
    public static final int T_OPERATOR_LESS_THAN=33;
    public static final int V=119;
    public static final int UNDERSCORE=93;
    public static final int Q=114;
    public static final int SEMICOLON=46;
    public static final int P=113;
    public static final int DELETE=54;
    public static final int S=116;
    public static final int R=115;
    public static final int ROLLBACK=49;
    public static final int Y=122;
    public static final int ID_ESCAPE=131;
    public static final int X=121;
    public static final int Z=123;
    public static final int BACKTICK=97;
    public static final int LSBRACKET=81;
    public static final int T_NWHERE=12;
    public static final int WS=136;
    public static final int NOT_EQUAL_TO_SINGLE=75;
    public static final int T_WHERE=11;
    public static final int OR=70;
    public static final int LESS_THAN=77;
    public static final int T_FIELD_ALIAS=13;
    public static final int QUOTE_SINGLE=95;
    public static final int USE=51;
    public static final int T_SELECT=9;
    public static final int T_OPERATOR_EQUAL_TO_DOUBLE=30;
    public static final int FROM=55;
    public static final int T_COMMIT=23;
    public static final int FALSE=85;
    public static final int GREATER_THAN_OR_EQUAL_TO=80;
    public static final int BACKSLASH=96;
    public static final int EQUAL_TO_DOUBLE=74;
    public static final int WHERE=68;
    public static final int NOT_EQUAL_TO_DOUBLE=76;
    public static final int ID_PLAIN=130;
    public static final int STRING_CORE_DOUBLE=126;
    public static final int ORDER=62;
    public static final int T_LIMIT=16;
    public static final int LIMIT=64;
    public static final int T_INSERT=5;
    public static final int T_USE=20;
    public static final int FLOAT=83;
    public static final int STRING_CORE_SINGLE=127;
    public static final int ID=48;
    public static final int AND=71;
    public static final int ASTERISK=87;
    public static final int LPAREN=58;
    public static final int T_OPERATOR_NOT_EQUAL_TO_SINGLE=31;
    public static final int T_OPERATOR_LESS_THAN_OR_EQUAL_TO=34;
    public static final int AS=53;
    public static final int T_LIST=45;
    public static final int SLASH=90;
    public static final int T_OPERATOR_SUBTRACT=40;
    public static final int RSBRACKET=82;
    public static final int COMMA=59;
    public static final int OFFSET=66;
    public static final int T_OPERATOR_CALL=43;
    public static final int T_OPERATOR_OR=26;
    public static final int PLUS=88;
    public static final int T_OPERATOR_MINUS=38;
    public static final int T_OPERATOR_INDEX=44;
    public static final int T_ASC=18;
    public static final int INTEGER=65;
    public static final int T_OPERATOR_ADD=39;
    public static final int NWHERE=69;
    public static final int BY=63;
    public static final int T_OPERATOR_NOT=28;
    public static final int T_OPERATOR_EQUAL_TO_SINGLE=29;
    public static final int GREATER_THAN=79;
    public static final int T_OPERATOR_NOT_EQUAL_TO_DOUBLE=32;
    public static final int T_OFFSET=17;
    public static final int T_VALUES=7;
    public static final int VALUES=61;
    public static final int ID_CORE=133;
    public static final int T_DESC=19;
    public static final int T_ROLLBACK=24;
    public static final int MINUS=89;
    public static final int T_FIELDS=8;
    public static final int TRUE=84;
    public static final int EQUAL_TO_SINGLE=73;
    public static final int OPTIMIZE=47;
    public static final int COMMIT=50;
    public static final int T_OPERATOR_GREATER_THAN=35;
    public static final int T_SERVER=21;
    public static final int T_DELETE=4;
    public static final int STRING_DOUBLE=125;
    public static final int STRING_SINGLE=124;
    public static final int DESC=92;
    public static final int T_OPERATOR_MULTIPLY=41;
    public static final int ID_START=132;
    public static final int STRING=52;

    // delegates
    // delegators

    public SolrqlLexer() {;} 
    public SolrqlLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public SolrqlLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g"; }

    // $ANTLR start "SEMICOLON"
    public final void mSEMICOLON() throws RecognitionException {
        try {
            int _type = SEMICOLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:306:10: ( ';' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:306:17: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMICOLON"

    // $ANTLR start "ASTERISK"
    public final void mASTERISK() throws RecognitionException {
        try {
            int _type = ASTERISK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:307:9: ( '*' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:307:17: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASTERISK"

    // $ANTLR start "COMMA"
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:308:6: ( ',' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:308:17: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMA"

    // $ANTLR start "UNDERSCORE"
    public final void mUNDERSCORE() throws RecognitionException {
        try {
            int _type = UNDERSCORE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:309:11: ( '_' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:309:17: '_'
            {
            match('_'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "UNDERSCORE"

    // $ANTLR start "QUOTE_DOUBLE"
    public final void mQUOTE_DOUBLE() throws RecognitionException {
        try {
            int _type = QUOTE_DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:310:13: ( '\"' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:310:17: '\"'
            {
            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QUOTE_DOUBLE"

    // $ANTLR start "QUOTE_SINGLE"
    public final void mQUOTE_SINGLE() throws RecognitionException {
        try {
            int _type = QUOTE_SINGLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:311:13: ( '\\'' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:311:17: '\\''
            {
            match('\''); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "QUOTE_SINGLE"

    // $ANTLR start "BACKSLASH"
    public final void mBACKSLASH() throws RecognitionException {
        try {
            int _type = BACKSLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:312:10: ( '\\\\' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:312:17: '\\\\'
            {
            match('\\'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BACKSLASH"

    // $ANTLR start "BACKTICK"
    public final void mBACKTICK() throws RecognitionException {
        try {
            int _type = BACKTICK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:313:9: ( '`' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:313:17: '`'
            {
            match('`'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BACKTICK"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:314:7: ( '(' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:314:17: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:315:7: ( ')' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:315:17: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "LSBRACKET"
    public final void mLSBRACKET() throws RecognitionException {
        try {
            int _type = LSBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:316:10: ( '[' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:316:17: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LSBRACKET"

    // $ANTLR start "RSBRACKET"
    public final void mRSBRACKET() throws RecognitionException {
        try {
            int _type = RSBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:317:10: ( ']' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:317:17: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RSBRACKET"

    // $ANTLR start "LESS_THAN"
    public final void mLESS_THAN() throws RecognitionException {
        try {
            int _type = LESS_THAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:318:10: ( '<' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:318:17: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS_THAN"

    // $ANTLR start "LESS_THAN_OR_EQUAL_TO"
    public final void mLESS_THAN_OR_EQUAL_TO() throws RecognitionException {
        try {
            int _type = LESS_THAN_OR_EQUAL_TO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:319:22: ( '<=' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:319:24: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS_THAN_OR_EQUAL_TO"

    // $ANTLR start "GREATER_THAN"
    public final void mGREATER_THAN() throws RecognitionException {
        try {
            int _type = GREATER_THAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:320:13: ( '>' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:320:17: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER_THAN"

    // $ANTLR start "GREATER_THAN_OR_EQUAL_TO"
    public final void mGREATER_THAN_OR_EQUAL_TO() throws RecognitionException {
        try {
            int _type = GREATER_THAN_OR_EQUAL_TO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:321:25: ( '>=' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:321:27: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER_THAN_OR_EQUAL_TO"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:322:5: ( '+' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:322:17: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:323:6: ( '-' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:323:17: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "SLASH"
    public final void mSLASH() throws RecognitionException {
        try {
            int _type = SLASH;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:324:6: ( '/' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:324:17: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SLASH"

    // $ANTLR start "EQUAL_TO_SINGLE"
    public final void mEQUAL_TO_SINGLE() throws RecognitionException {
        try {
            int _type = EQUAL_TO_SINGLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:325:16: ( '=' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:325:18: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUAL_TO_SINGLE"

    // $ANTLR start "EQUAL_TO_DOUBLE"
    public final void mEQUAL_TO_DOUBLE() throws RecognitionException {
        try {
            int _type = EQUAL_TO_DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:326:16: ( '==' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:326:18: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQUAL_TO_DOUBLE"

    // $ANTLR start "NOT_EQUAL_TO_SINGLE"
    public final void mNOT_EQUAL_TO_SINGLE() throws RecognitionException {
        try {
            int _type = NOT_EQUAL_TO_SINGLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:327:20: ( '!=' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:327:22: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT_EQUAL_TO_SINGLE"

    // $ANTLR start "NOT_EQUAL_TO_DOUBLE"
    public final void mNOT_EQUAL_TO_DOUBLE() throws RecognitionException {
        try {
            int _type = NOT_EQUAL_TO_DOUBLE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:328:20: ( '!==' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:328:22: '!=='
            {
            match("!=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT_EQUAL_TO_DOUBLE"

    // $ANTLR start "A"
    public final void mA() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:330:11: ( ( 'a' | 'A' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:330:13: ( 'a' | 'A' )
            {
            if ( input.LA(1)=='A'||input.LA(1)=='a' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "A"

    // $ANTLR start "B"
    public final void mB() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:331:11: ( ( 'b' | 'B' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:331:13: ( 'b' | 'B' )
            {
            if ( input.LA(1)=='B'||input.LA(1)=='b' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "B"

    // $ANTLR start "C"
    public final void mC() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:332:11: ( ( 'c' | 'C' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:332:13: ( 'c' | 'C' )
            {
            if ( input.LA(1)=='C'||input.LA(1)=='c' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "C"

    // $ANTLR start "D"
    public final void mD() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:333:11: ( ( 'd' | 'D' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:333:13: ( 'd' | 'D' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='d' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "D"

    // $ANTLR start "E"
    public final void mE() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:334:11: ( ( 'e' | 'E' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:334:13: ( 'e' | 'E' )
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "E"

    // $ANTLR start "F"
    public final void mF() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:335:11: ( ( 'f' | 'F' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:335:13: ( 'f' | 'F' )
            {
            if ( input.LA(1)=='F'||input.LA(1)=='f' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "F"

    // $ANTLR start "G"
    public final void mG() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:336:11: ( ( 'g' | 'G' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:336:13: ( 'g' | 'G' )
            {
            if ( input.LA(1)=='G'||input.LA(1)=='g' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "G"

    // $ANTLR start "H"
    public final void mH() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:337:11: ( ( 'h' | 'H' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:337:13: ( 'h' | 'H' )
            {
            if ( input.LA(1)=='H'||input.LA(1)=='h' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "H"

    // $ANTLR start "I"
    public final void mI() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:338:11: ( ( 'i' | 'I' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:338:13: ( 'i' | 'I' )
            {
            if ( input.LA(1)=='I'||input.LA(1)=='i' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "I"

    // $ANTLR start "J"
    public final void mJ() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:339:11: ( ( 'j' | 'J' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:339:13: ( 'j' | 'J' )
            {
            if ( input.LA(1)=='J'||input.LA(1)=='j' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "J"

    // $ANTLR start "K"
    public final void mK() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:340:11: ( ( 'k' | 'K' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:340:13: ( 'k' | 'K' )
            {
            if ( input.LA(1)=='K'||input.LA(1)=='k' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "K"

    // $ANTLR start "L"
    public final void mL() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:341:11: ( ( 'l' | 'L' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:341:13: ( 'l' | 'L' )
            {
            if ( input.LA(1)=='L'||input.LA(1)=='l' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "L"

    // $ANTLR start "M"
    public final void mM() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:342:11: ( ( 'm' | 'M' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:342:13: ( 'm' | 'M' )
            {
            if ( input.LA(1)=='M'||input.LA(1)=='m' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "M"

    // $ANTLR start "N"
    public final void mN() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:343:11: ( ( 'n' | 'N' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:343:13: ( 'n' | 'N' )
            {
            if ( input.LA(1)=='N'||input.LA(1)=='n' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "N"

    // $ANTLR start "O"
    public final void mO() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:344:11: ( ( 'o' | 'O' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:344:13: ( 'o' | 'O' )
            {
            if ( input.LA(1)=='O'||input.LA(1)=='o' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "O"

    // $ANTLR start "P"
    public final void mP() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:345:11: ( ( 'p' | 'P' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:345:13: ( 'p' | 'P' )
            {
            if ( input.LA(1)=='P'||input.LA(1)=='p' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "P"

    // $ANTLR start "Q"
    public final void mQ() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:346:11: ( ( 'q' | 'Q' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:346:13: ( 'q' | 'Q' )
            {
            if ( input.LA(1)=='Q'||input.LA(1)=='q' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Q"

    // $ANTLR start "R"
    public final void mR() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:347:11: ( ( 'r' | 'R' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:347:13: ( 'r' | 'R' )
            {
            if ( input.LA(1)=='R'||input.LA(1)=='r' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "R"

    // $ANTLR start "S"
    public final void mS() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:348:11: ( ( 's' | 'S' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:348:13: ( 's' | 'S' )
            {
            if ( input.LA(1)=='S'||input.LA(1)=='s' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "S"

    // $ANTLR start "T"
    public final void mT() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:349:11: ( ( 't' | 'T' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:349:13: ( 't' | 'T' )
            {
            if ( input.LA(1)=='T'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "T"

    // $ANTLR start "U"
    public final void mU() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:350:11: ( ( 'u' | 'U' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:350:13: ( 'u' | 'U' )
            {
            if ( input.LA(1)=='U'||input.LA(1)=='u' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "U"

    // $ANTLR start "V"
    public final void mV() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:351:11: ( ( 'v' | 'V' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:351:13: ( 'v' | 'V' )
            {
            if ( input.LA(1)=='V'||input.LA(1)=='v' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "V"

    // $ANTLR start "W"
    public final void mW() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:352:11: ( ( 'w' | 'W' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:352:13: ( 'w' | 'W' )
            {
            if ( input.LA(1)=='W'||input.LA(1)=='w' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "W"

    // $ANTLR start "X"
    public final void mX() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:353:11: ( ( 'x' | 'X' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:353:13: ( 'x' | 'X' )
            {
            if ( input.LA(1)=='X'||input.LA(1)=='x' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "X"

    // $ANTLR start "Y"
    public final void mY() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:354:11: ( ( 'y' | 'Y' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:354:13: ( 'y' | 'Y' )
            {
            if ( input.LA(1)=='Y'||input.LA(1)=='y' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Y"

    // $ANTLR start "Z"
    public final void mZ() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:355:11: ( ( 'z' | 'Z' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:355:13: ( 'z' | 'Z' )
            {
            if ( input.LA(1)=='Z'||input.LA(1)=='z' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "Z"

    // $ANTLR start "DELETE"
    public final void mDELETE() throws RecognitionException {
        try {
            int _type = DELETE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:358:7: ( D E L E T E )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:358:13: D E L E T E
            {
            mD(); 
            mE(); 
            mL(); 
            mE(); 
            mT(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DELETE"

    // $ANTLR start "INSERT"
    public final void mINSERT() throws RecognitionException {
        try {
            int _type = INSERT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:359:7: ( I N S E R T )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:359:13: I N S E R T
            {
            mI(); 
            mN(); 
            mS(); 
            mE(); 
            mR(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INSERT"

    // $ANTLR start "INTO"
    public final void mINTO() throws RecognitionException {
        try {
            int _type = INTO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:360:5: ( I N T O )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:360:13: I N T O
            {
            mI(); 
            mN(); 
            mT(); 
            mO(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTO"

    // $ANTLR start "VALUES"
    public final void mVALUES() throws RecognitionException {
        try {
            int _type = VALUES;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:361:7: ( V A L U E S )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:361:13: V A L U E S
            {
            mV(); 
            mA(); 
            mL(); 
            mU(); 
            mE(); 
            mS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "VALUES"

    // $ANTLR start "SELECT"
    public final void mSELECT() throws RecognitionException {
        try {
            int _type = SELECT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:362:7: ( S E L E C T )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:362:13: S E L E C T
            {
            mS(); 
            mE(); 
            mL(); 
            mE(); 
            mC(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SELECT"

    // $ANTLR start "ORDER"
    public final void mORDER() throws RecognitionException {
        try {
            int _type = ORDER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:363:6: ( O R D E R )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:363:13: O R D E R
            {
            mO(); 
            mR(); 
            mD(); 
            mE(); 
            mR(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ORDER"

    // $ANTLR start "BY"
    public final void mBY() throws RecognitionException {
        try {
            int _type = BY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:364:3: ( B Y )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:364:13: B Y
            {
            mB(); 
            mY(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "BY"

    // $ANTLR start "LIMIT"
    public final void mLIMIT() throws RecognitionException {
        try {
            int _type = LIMIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:365:6: ( L I M I T )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:365:13: L I M I T
            {
            mL(); 
            mI(); 
            mM(); 
            mI(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LIMIT"

    // $ANTLR start "OFFSET"
    public final void mOFFSET() throws RecognitionException {
        try {
            int _type = OFFSET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:366:7: ( O F F S E T )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:366:13: O F F S E T
            {
            mO(); 
            mF(); 
            mF(); 
            mS(); 
            mE(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OFFSET"

    // $ANTLR start "FROM"
    public final void mFROM() throws RecognitionException {
        try {
            int _type = FROM;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:367:5: ( F R O M )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:367:13: F R O M
            {
            mF(); 
            mR(); 
            mO(); 
            mM(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FROM"

    // $ANTLR start "WHERE"
    public final void mWHERE() throws RecognitionException {
        try {
            int _type = WHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:368:6: ( W H E R E )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:368:13: W H E R E
            {
            mW(); 
            mH(); 
            mE(); 
            mR(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WHERE"

    // $ANTLR start "NWHERE"
    public final void mNWHERE() throws RecognitionException {
        try {
            int _type = NWHERE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:369:7: ( N W H E R E )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:369:13: N W H E R E
            {
            mN(); 
            mW(); 
            mH(); 
            mE(); 
            mR(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NWHERE"

    // $ANTLR start "ASC"
    public final void mASC() throws RecognitionException {
        try {
            int _type = ASC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:370:4: ( A S C )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:370:13: A S C
            {
            mA(); 
            mS(); 
            mC(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASC"

    // $ANTLR start "DESC"
    public final void mDESC() throws RecognitionException {
        try {
            int _type = DESC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:371:5: ( D E S C )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:371:13: D E S C
            {
            mD(); 
            mE(); 
            mS(); 
            mC(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DESC"

    // $ANTLR start "USE"
    public final void mUSE() throws RecognitionException {
        try {
            int _type = USE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:372:4: ( U S E )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:372:13: U S E
            {
            mU(); 
            mS(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "USE"

    // $ANTLR start "AS"
    public final void mAS() throws RecognitionException {
        try {
            int _type = AS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:373:3: ( A S )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:373:13: A S
            {
            mA(); 
            mS(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AS"

    // $ANTLR start "COMMIT"
    public final void mCOMMIT() throws RecognitionException {
        try {
            int _type = COMMIT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:374:7: ( C O M M I T )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:374:13: C O M M I T
            {
            mC(); 
            mO(); 
            mM(); 
            mM(); 
            mI(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COMMIT"

    // $ANTLR start "ROLLBACK"
    public final void mROLLBACK() throws RecognitionException {
        try {
            int _type = ROLLBACK;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:375:9: ( R O L L B A C K )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:375:13: R O L L B A C K
            {
            mR(); 
            mO(); 
            mL(); 
            mL(); 
            mB(); 
            mA(); 
            mC(); 
            mK(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ROLLBACK"

    // $ANTLR start "OPTIMIZE"
    public final void mOPTIMIZE() throws RecognitionException {
        try {
            int _type = OPTIMIZE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:376:9: ( O P T I M I Z E )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:376:13: O P T I M I Z E
            {
            mO(); 
            mP(); 
            mT(); 
            mI(); 
            mM(); 
            mI(); 
            mZ(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OPTIMIZE"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:377:3: ( O R )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:377:13: O R
            {
            mO(); 
            mR(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:378:4: ( A N D )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:378:13: A N D
            {
            mA(); 
            mN(); 
            mD(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:379:4: ( N O T )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:379:13: N O T
            {
            mN(); 
            mO(); 
            mT(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "NULL"
    public final void mNULL() throws RecognitionException {
        try {
            int _type = NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:380:5: ( N U L L )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:380:13: N U L L
            {
            mN(); 
            mU(); 
            mL(); 
            mL(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NULL"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:381:5: ( T R U E )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:381:13: T R U E
            {
            mT(); 
            mR(); 
            mU(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:382:6: ( F A L S E )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:382:13: F A L S E
            {
            mF(); 
            mA(); 
            mL(); 
            mS(); 
            mE(); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FALSE"

    // $ANTLR start "STRING"
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:385:7: ( STRING_SINGLE | STRING_DOUBLE )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='\'') ) {
                alt1=1;
            }
            else if ( (LA1_0=='\"') ) {
                alt1=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:385:33: STRING_SINGLE
                    {
                    mSTRING_SINGLE(); 

                    }
                    break;
                case 2 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:385:49: STRING_DOUBLE
                    {
                    mSTRING_DOUBLE(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "STRING"

    // $ANTLR start "STRING_DOUBLE"
    public final void mSTRING_DOUBLE() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:386:23: ( QUOTE_DOUBLE ( STRING_CORE_DOUBLE )* QUOTE_DOUBLE )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:386:33: QUOTE_DOUBLE ( STRING_CORE_DOUBLE )* QUOTE_DOUBLE
            {
            mQUOTE_DOUBLE(); 
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:386:46: ( STRING_CORE_DOUBLE )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='\u0000' && LA2_0<='!')||(LA2_0>='#' && LA2_0<='\uFFFF')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:386:46: STRING_CORE_DOUBLE
            	    {
            	    mSTRING_CORE_DOUBLE(); 

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            mQUOTE_DOUBLE(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "STRING_DOUBLE"

    // $ANTLR start "STRING_SINGLE"
    public final void mSTRING_SINGLE() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:387:23: ( QUOTE_SINGLE ( STRING_CORE_SINGLE )* QUOTE_SINGLE )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:387:33: QUOTE_SINGLE ( STRING_CORE_SINGLE )* QUOTE_SINGLE
            {
            mQUOTE_SINGLE(); 
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:387:46: ( STRING_CORE_SINGLE )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='\u0000' && LA3_0<='&')||(LA3_0>='(' && LA3_0<='\uFFFF')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:387:46: STRING_CORE_SINGLE
            	    {
            	    mSTRING_CORE_SINGLE(); 

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            mQUOTE_SINGLE(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "STRING_SINGLE"

    // $ANTLR start "STRING_CORE_DOUBLE"
    public final void mSTRING_CORE_DOUBLE() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:388:28: ( STRING_CORE | QUOTE_SINGLE | STRING_ESCAPE_CORE )
            int alt4=3;
            int LA4_0 = input.LA(1);

            if ( (LA4_0=='\\') ) {
                int LA4_1 = input.LA(2);

                if ( (LA4_1=='\"'||LA4_1=='\''||LA4_1=='\\'||LA4_1=='n'||LA4_1=='r'||LA4_1=='t') ) {
                    alt4=3;
                }
                else {
                    alt4=1;}
            }
            else if ( (LA4_0=='\'') ) {
                alt4=2;
            }
            else if ( ((LA4_0>='\u0000' && LA4_0<='!')||(LA4_0>='#' && LA4_0<='&')||(LA4_0>='(' && LA4_0<='[')||(LA4_0>=']' && LA4_0<='\uFFFF')) ) {
                alt4=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:388:33: STRING_CORE
                    {
                    mSTRING_CORE(); 

                    }
                    break;
                case 2 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:388:47: QUOTE_SINGLE
                    {
                    mQUOTE_SINGLE(); 

                    }
                    break;
                case 3 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:388:62: STRING_ESCAPE_CORE
                    {
                    mSTRING_ESCAPE_CORE(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "STRING_CORE_DOUBLE"

    // $ANTLR start "STRING_CORE_SINGLE"
    public final void mSTRING_CORE_SINGLE() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:389:28: ( STRING_CORE | QUOTE_DOUBLE | STRING_ESCAPE_CORE )
            int alt5=3;
            int LA5_0 = input.LA(1);

            if ( (LA5_0=='\\') ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1=='\"'||LA5_1=='\''||LA5_1=='\\'||LA5_1=='n'||LA5_1=='r'||LA5_1=='t') ) {
                    alt5=3;
                }
                else {
                    alt5=1;}
            }
            else if ( (LA5_0=='\"') ) {
                alt5=2;
            }
            else if ( ((LA5_0>='\u0000' && LA5_0<='!')||(LA5_0>='#' && LA5_0<='&')||(LA5_0>='(' && LA5_0<='[')||(LA5_0>=']' && LA5_0<='\uFFFF')) ) {
                alt5=1;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:389:33: STRING_CORE
                    {
                    mSTRING_CORE(); 

                    }
                    break;
                case 2 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:389:47: QUOTE_DOUBLE
                    {
                    mQUOTE_DOUBLE(); 

                    }
                    break;
                case 3 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:389:62: STRING_ESCAPE_CORE
                    {
                    mSTRING_ESCAPE_CORE(); 

                    }
                    break;

            }
        }
        finally {
        }
    }
    // $ANTLR end "STRING_CORE_SINGLE"

    // $ANTLR start "STRING_CORE"
    public final void mSTRING_CORE() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:390:21: (~ ( QUOTE_SINGLE | QUOTE_DOUBLE ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:390:33: ~ ( QUOTE_SINGLE | QUOTE_DOUBLE )
            {
            if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "STRING_CORE"

    // $ANTLR start "STRING_ESCAPE_CORE"
    public final void mSTRING_ESCAPE_CORE() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:391:28: ( BACKSLASH ( BACKSLASH | QUOTE_DOUBLE | QUOTE_SINGLE | 'r' | 'n' | 't' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:391:33: BACKSLASH ( BACKSLASH | QUOTE_DOUBLE | QUOTE_SINGLE | 'r' | 'n' | 't' )
            {
            mBACKSLASH(); 
            if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "STRING_ESCAPE_CORE"

    // $ANTLR start "ID"
    public final void mID() throws RecognitionException {
        try {
            int _type = ID;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:393:3: ( ID_PLAIN | ID_ESCAPE )
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( ((LA6_0>='A' && LA6_0<='Z')||LA6_0=='_'||(LA6_0>='a' && LA6_0<='z')) ) {
                alt6=1;
            }
            else if ( (LA6_0=='`') ) {
                alt6=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }
            switch (alt6) {
                case 1 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:393:33: ID_PLAIN
                    {
                    mID_PLAIN(); 

                    }
                    break;
                case 2 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:393:44: ID_ESCAPE
                    {
                    mID_ESCAPE(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ID"

    // $ANTLR start "ID_PLAIN"
    public final void mID_PLAIN() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:394:18: ( ID_START ( ID_CORE )* )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:394:33: ID_START ( ID_CORE )*
            {
            mID_START(); 
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:394:42: ( ID_CORE )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( ((LA7_0>='0' && LA7_0<='9')||(LA7_0>='A' && LA7_0<='Z')||LA7_0=='_'||(LA7_0>='a' && LA7_0<='z')) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:394:42: ID_CORE
            	    {
            	    mID_CORE(); 

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "ID_PLAIN"

    // $ANTLR start "ID_ESCAPE"
    public final void mID_ESCAPE() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:395:19: ( BACKTICK ( ID_ESCAPE_CORE )+ BACKTICK )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:395:33: BACKTICK ( ID_ESCAPE_CORE )+ BACKTICK
            {
            mBACKTICK(); 
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:395:42: ( ID_ESCAPE_CORE )+
            int cnt8=0;
            loop8:
            do {
                int alt8=2;
                int LA8_0 = input.LA(1);

                if ( ((LA8_0>='\u0000' && LA8_0<='_')||(LA8_0>='a' && LA8_0<='\uFFFF')) ) {
                    alt8=1;
                }


                switch (alt8) {
            	case 1 :
            	    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:395:42: ID_ESCAPE_CORE
            	    {
            	    mID_ESCAPE_CORE(); 

            	    }
            	    break;

            	default :
            	    if ( cnt8 >= 1 ) break loop8;
                        EarlyExitException eee =
                            new EarlyExitException(8, input);
                        throw eee;
                }
                cnt8++;
            } while (true);

            mBACKTICK(); 

            }

        }
        finally {
        }
    }
    // $ANTLR end "ID_ESCAPE"

    // $ANTLR start "ID_START"
    public final void mID_START() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:396:18: ( 'a' .. 'z' | 'A' .. 'Z' | UNDERSCORE )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "ID_START"

    // $ANTLR start "ID_CORE"
    public final void mID_CORE() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:397:17: ( ID_START | '0' .. '9' )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "ID_CORE"

    // $ANTLR start "ID_ESCAPE_CORE"
    public final void mID_ESCAPE_CORE() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:398:24: (~ BACKTICK )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:398:33: ~ BACKTICK
            {
            if ( (input.LA(1)>='\u0000' && input.LA(1)<='`')||(input.LA(1)>='b' && input.LA(1)<='\uFFFF') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "ID_ESCAPE_CORE"

    // $ANTLR start "INTEGER"
    public final void mINTEGER() throws RecognitionException {
        try {
            int _type = INTEGER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:400:8: ( ( '0' .. '9' )+ )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:400:33: ( '0' .. '9' )+
            {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:400:33: ( '0' .. '9' )+
            int cnt9=0;
            loop9:
            do {
                int alt9=2;
                int LA9_0 = input.LA(1);

                if ( ((LA9_0>='0' && LA9_0<='9')) ) {
                    alt9=1;
                }


                switch (alt9) {
            	case 1 :
            	    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:400:34: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt9 >= 1 ) break loop9;
                        EarlyExitException eee =
                            new EarlyExitException(9, input);
                        throw eee;
                }
                cnt9++;
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTEGER"

    // $ANTLR start "FLOAT"
    public final void mFLOAT() throws RecognitionException {
        try {
            int _type = FLOAT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:403:5: ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT )
            int alt16=3;
            alt16 = dfa16.predict(input);
            switch (alt16) {
                case 1 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:403:7: ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )?
                    {
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:403:7: ( '0' .. '9' )+
                    int cnt10=0;
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0>='0' && LA10_0<='9')) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:403:8: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt10 >= 1 ) break loop10;
                                EarlyExitException eee =
                                    new EarlyExitException(10, input);
                                throw eee;
                        }
                        cnt10++;
                    } while (true);

                    match('.'); 
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:403:23: ( '0' .. '9' )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>='0' && LA11_0<='9')) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:403:24: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);

                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:403:35: ( EXPONENT )?
                    int alt12=2;
                    int LA12_0 = input.LA(1);

                    if ( (LA12_0=='E'||LA12_0=='e') ) {
                        alt12=1;
                    }
                    switch (alt12) {
                        case 1 :
                            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:403:35: EXPONENT
                            {
                            mEXPONENT(); 

                            }
                            break;

                    }


                    }
                    break;
                case 2 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:404:7: '.' ( '0' .. '9' )+ ( EXPONENT )?
                    {
                    match('.'); 
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:404:11: ( '0' .. '9' )+
                    int cnt13=0;
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( ((LA13_0>='0' && LA13_0<='9')) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:404:12: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt13 >= 1 ) break loop13;
                                EarlyExitException eee =
                                    new EarlyExitException(13, input);
                                throw eee;
                        }
                        cnt13++;
                    } while (true);

                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:404:23: ( EXPONENT )?
                    int alt14=2;
                    int LA14_0 = input.LA(1);

                    if ( (LA14_0=='E'||LA14_0=='e') ) {
                        alt14=1;
                    }
                    switch (alt14) {
                        case 1 :
                            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:404:23: EXPONENT
                            {
                            mEXPONENT(); 

                            }
                            break;

                    }


                    }
                    break;
                case 3 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:405:7: ( '0' .. '9' )+ EXPONENT
                    {
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:405:7: ( '0' .. '9' )+
                    int cnt15=0;
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( ((LA15_0>='0' && LA15_0<='9')) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:405:8: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt15 >= 1 ) break loop15;
                                EarlyExitException eee =
                                    new EarlyExitException(15, input);
                                throw eee;
                        }
                        cnt15++;
                    } while (true);

                    mEXPONENT(); 

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FLOAT"

    // $ANTLR start "EXPONENT"
    public final void mEXPONENT() throws RecognitionException {
        try {
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:407:18: ( ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+ )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:407:33: ( 'e' | 'E' ) ( '+' | '-' )? ( '0' .. '9' )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:407:45: ( '+' | '-' )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0=='+'||LA17_0=='-') ) {
                alt17=1;
            }
            switch (alt17) {
                case 1 :
                    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:407:58: ( '0' .. '9' )+
            int cnt18=0;
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( ((LA18_0>='0' && LA18_0<='9')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:407:59: '0' .. '9'
            	    {
            	    matchRange('0','9'); 

            	    }
            	    break;

            	default :
            	    if ( cnt18 >= 1 ) break loop18;
                        EarlyExitException eee =
                            new EarlyExitException(18, input);
                        throw eee;
                }
                cnt18++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end "EXPONENT"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:409:3: ( ( ' ' | '\\r' | '\\t' | '\\n' ) )
            // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:409:5: ( ' ' | '\\r' | '\\t' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||input.LA(1)=='\r'||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

             _channel=HIDDEN; 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:8: ( SEMICOLON | ASTERISK | COMMA | UNDERSCORE | QUOTE_DOUBLE | QUOTE_SINGLE | BACKSLASH | BACKTICK | LPAREN | RPAREN | LSBRACKET | RSBRACKET | LESS_THAN | LESS_THAN_OR_EQUAL_TO | GREATER_THAN | GREATER_THAN_OR_EQUAL_TO | PLUS | MINUS | SLASH | EQUAL_TO_SINGLE | EQUAL_TO_DOUBLE | NOT_EQUAL_TO_SINGLE | NOT_EQUAL_TO_DOUBLE | DELETE | INSERT | INTO | VALUES | SELECT | ORDER | BY | LIMIT | OFFSET | FROM | WHERE | NWHERE | ASC | DESC | USE | AS | COMMIT | ROLLBACK | OPTIMIZE | OR | AND | NOT | NULL | TRUE | FALSE | STRING | ID | INTEGER | FLOAT | WS )
        int alt19=53;
        alt19 = dfa19.predict(input);
        switch (alt19) {
            case 1 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:10: SEMICOLON
                {
                mSEMICOLON(); 

                }
                break;
            case 2 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:20: ASTERISK
                {
                mASTERISK(); 

                }
                break;
            case 3 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:29: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 4 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:35: UNDERSCORE
                {
                mUNDERSCORE(); 

                }
                break;
            case 5 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:46: QUOTE_DOUBLE
                {
                mQUOTE_DOUBLE(); 

                }
                break;
            case 6 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:59: QUOTE_SINGLE
                {
                mQUOTE_SINGLE(); 

                }
                break;
            case 7 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:72: BACKSLASH
                {
                mBACKSLASH(); 

                }
                break;
            case 8 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:82: BACKTICK
                {
                mBACKTICK(); 

                }
                break;
            case 9 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:91: LPAREN
                {
                mLPAREN(); 

                }
                break;
            case 10 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:98: RPAREN
                {
                mRPAREN(); 

                }
                break;
            case 11 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:105: LSBRACKET
                {
                mLSBRACKET(); 

                }
                break;
            case 12 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:115: RSBRACKET
                {
                mRSBRACKET(); 

                }
                break;
            case 13 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:125: LESS_THAN
                {
                mLESS_THAN(); 

                }
                break;
            case 14 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:135: LESS_THAN_OR_EQUAL_TO
                {
                mLESS_THAN_OR_EQUAL_TO(); 

                }
                break;
            case 15 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:157: GREATER_THAN
                {
                mGREATER_THAN(); 

                }
                break;
            case 16 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:170: GREATER_THAN_OR_EQUAL_TO
                {
                mGREATER_THAN_OR_EQUAL_TO(); 

                }
                break;
            case 17 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:195: PLUS
                {
                mPLUS(); 

                }
                break;
            case 18 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:200: MINUS
                {
                mMINUS(); 

                }
                break;
            case 19 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:206: SLASH
                {
                mSLASH(); 

                }
                break;
            case 20 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:212: EQUAL_TO_SINGLE
                {
                mEQUAL_TO_SINGLE(); 

                }
                break;
            case 21 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:228: EQUAL_TO_DOUBLE
                {
                mEQUAL_TO_DOUBLE(); 

                }
                break;
            case 22 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:244: NOT_EQUAL_TO_SINGLE
                {
                mNOT_EQUAL_TO_SINGLE(); 

                }
                break;
            case 23 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:264: NOT_EQUAL_TO_DOUBLE
                {
                mNOT_EQUAL_TO_DOUBLE(); 

                }
                break;
            case 24 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:284: DELETE
                {
                mDELETE(); 

                }
                break;
            case 25 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:291: INSERT
                {
                mINSERT(); 

                }
                break;
            case 26 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:298: INTO
                {
                mINTO(); 

                }
                break;
            case 27 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:303: VALUES
                {
                mVALUES(); 

                }
                break;
            case 28 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:310: SELECT
                {
                mSELECT(); 

                }
                break;
            case 29 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:317: ORDER
                {
                mORDER(); 

                }
                break;
            case 30 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:323: BY
                {
                mBY(); 

                }
                break;
            case 31 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:326: LIMIT
                {
                mLIMIT(); 

                }
                break;
            case 32 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:332: OFFSET
                {
                mOFFSET(); 

                }
                break;
            case 33 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:339: FROM
                {
                mFROM(); 

                }
                break;
            case 34 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:344: WHERE
                {
                mWHERE(); 

                }
                break;
            case 35 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:350: NWHERE
                {
                mNWHERE(); 

                }
                break;
            case 36 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:357: ASC
                {
                mASC(); 

                }
                break;
            case 37 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:361: DESC
                {
                mDESC(); 

                }
                break;
            case 38 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:366: USE
                {
                mUSE(); 

                }
                break;
            case 39 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:370: AS
                {
                mAS(); 

                }
                break;
            case 40 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:373: COMMIT
                {
                mCOMMIT(); 

                }
                break;
            case 41 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:380: ROLLBACK
                {
                mROLLBACK(); 

                }
                break;
            case 42 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:389: OPTIMIZE
                {
                mOPTIMIZE(); 

                }
                break;
            case 43 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:398: OR
                {
                mOR(); 

                }
                break;
            case 44 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:401: AND
                {
                mAND(); 

                }
                break;
            case 45 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:405: NOT
                {
                mNOT(); 

                }
                break;
            case 46 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:409: NULL
                {
                mNULL(); 

                }
                break;
            case 47 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:414: TRUE
                {
                mTRUE(); 

                }
                break;
            case 48 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:419: FALSE
                {
                mFALSE(); 

                }
                break;
            case 49 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:425: STRING
                {
                mSTRING(); 

                }
                break;
            case 50 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:432: ID
                {
                mID(); 

                }
                break;
            case 51 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:435: INTEGER
                {
                mINTEGER(); 

                }
                break;
            case 52 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:443: FLOAT
                {
                mFLOAT(); 

                }
                break;
            case 53 :
                // C:\\Home\\src\\workspace\\solrql\\grammar\\Solrql.g:1:449: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA16 dfa16 = new DFA16(this);
    protected DFA19 dfa19 = new DFA19(this);
    static final String DFA16_eotS =
        "\5\uffff";
    static final String DFA16_eofS =
        "\5\uffff";
    static final String DFA16_minS =
        "\2\56\3\uffff";
    static final String DFA16_maxS =
        "\1\71\1\145\3\uffff";
    static final String DFA16_acceptS =
        "\2\uffff\1\2\1\3\1\1";
    static final String DFA16_specialS =
        "\5\uffff}>";
    static final String[] DFA16_transitionS = {
            "\1\2\1\uffff\12\1",
            "\1\4\1\uffff\12\1\13\uffff\1\3\37\uffff\1\3",
            "",
            "",
            ""
    };

    static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
    static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
    static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
    static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
    static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
    static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
    static final short[][] DFA16_transition;

    static {
        int numStates = DFA16_transitionS.length;
        DFA16_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
        }
    }

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = DFA16_eot;
            this.eof = DFA16_eof;
            this.min = DFA16_min;
            this.max = DFA16_max;
            this.accept = DFA16_accept;
            this.special = DFA16_special;
            this.transition = DFA16_transition;
        }
        public String getDescription() {
            return "402:1: FLOAT : ( ( '0' .. '9' )+ '.' ( '0' .. '9' )* ( EXPONENT )? | '.' ( '0' .. '9' )+ ( EXPONENT )? | ( '0' .. '9' )+ EXPONENT );";
        }
    }
    static final String DFA19_eotS =
        "\4\uffff\1\47\1\50\1\52\1\uffff\1\53\4\uffff\1\55\1\57\3\uffff"+
        "\1\61\1\uffff\17\43\1\uffff\1\110\15\uffff\1\112\4\43\1\121\2\43"+
        "\1\125\7\43\1\135\5\43\3\uffff\6\43\1\uffff\3\43\1\uffff\6\43\1"+
        "\163\1\uffff\1\164\1\165\1\166\3\43\1\172\1\43\1\174\7\43\1\u0084"+
        "\3\43\1\u0088\4\uffff\2\43\1\u008b\1\uffff\1\43\1\uffff\3\43\1\u0090"+
        "\2\43\1\u0093\1\uffff\1\u0094\1\u0095\1\43\1\uffff\2\43\1\uffff"+
        "\1\u0099\1\u009a\1\u009b\1\u009c\1\uffff\1\u009d\1\43\3\uffff\1"+
        "\u009f\1\u00a0\1\43\5\uffff\1\43\2\uffff\1\43\1\u00a4\1\u00a5\2"+
        "\uffff";
    static final String DFA19_eofS =
        "\u00a6\uffff";
    static final String DFA19_minS =
        "\1\11\3\uffff\1\60\2\0\1\uffff\1\0\4\uffff\2\75\3\uffff\2\75\1"+
        "\105\1\116\1\101\1\105\1\106\1\131\1\111\1\101\1\110\1\117\1\116"+
        "\1\123\2\117\1\122\1\uffff\1\56\15\uffff\1\75\1\114\1\123\2\114"+
        "\1\60\1\106\1\124\1\60\1\115\1\117\1\114\1\105\1\110\1\114\1\124"+
        "\1\60\1\104\1\105\1\115\1\114\1\125\3\uffff\1\103\1\105\1\117\1"+
        "\105\1\125\1\105\1\uffff\1\105\1\123\1\111\1\uffff\1\111\1\115\1"+
        "\123\1\122\1\105\1\114\1\60\1\uffff\3\60\1\115\1\114\1\105\1\60"+
        "\1\124\1\60\1\122\1\105\1\103\1\122\1\105\1\115\1\124\1\60\2\105"+
        "\1\122\1\60\4\uffff\1\111\1\102\1\60\1\uffff\1\105\1\uffff\1\124"+
        "\1\123\1\124\1\60\1\124\1\111\1\60\1\uffff\2\60\1\105\1\uffff\1"+
        "\124\1\101\1\uffff\4\60\1\uffff\1\60\1\132\3\uffff\2\60\1\103\5"+
        "\uffff\1\105\2\uffff\1\113\2\60\2\uffff";
    static final String DFA19_maxS =
        "\1\172\3\uffff\1\172\2\uffff\1\uffff\1\uffff\4\uffff\2\75\3\uffff"+
        "\2\75\1\145\1\156\1\141\1\145\1\162\1\171\1\151\1\162\1\150\1\167"+
        "\2\163\2\157\1\162\1\uffff\1\145\15\uffff\1\75\1\163\1\164\2\154"+
        "\1\172\1\146\1\164\1\172\1\155\1\157\1\154\1\145\1\150\1\154\1\164"+
        "\1\172\1\144\1\145\1\155\1\154\1\165\3\uffff\1\143\1\145\1\157\1"+
        "\145\1\165\1\145\1\uffff\1\145\1\163\1\151\1\uffff\1\151\1\155\1"+
        "\163\1\162\1\145\1\154\1\172\1\uffff\3\172\1\155\1\154\1\145\1\172"+
        "\1\164\1\172\1\162\1\145\1\143\1\162\1\145\1\155\1\164\1\172\2\145"+
        "\1\162\1\172\4\uffff\1\151\1\142\1\172\1\uffff\1\145\1\uffff\1\164"+
        "\1\163\1\164\1\172\1\164\1\151\1\172\1\uffff\2\172\1\145\1\uffff"+
        "\1\164\1\141\1\uffff\4\172\1\uffff\2\172\3\uffff\2\172\1\143\5\uffff"+
        "\1\145\2\uffff\1\153\2\172\2\uffff";
    static final String DFA19_acceptS =
        "\1\uffff\1\1\1\2\1\3\3\uffff\1\7\1\uffff\1\11\1\12\1\13\1\14\2"+
        "\uffff\1\21\1\22\1\23\21\uffff\1\62\1\uffff\1\64\1\65\1\4\1\5\1"+
        "\61\1\6\1\10\1\16\1\15\1\20\1\17\1\25\1\24\26\uffff\1\63\1\27\1"+
        "\26\6\uffff\1\53\3\uffff\1\36\7\uffff\1\47\25\uffff\1\55\1\44\1"+
        "\54\1\46\3\uffff\1\45\1\uffff\1\32\7\uffff\1\41\3\uffff\1\56\2\uffff"+
        "\1\57\4\uffff\1\35\2\uffff\1\37\1\60\1\42\3\uffff\1\30\1\31\1\33"+
        "\1\34\1\40\1\uffff\1\43\1\50\3\uffff\1\52\1\51";
    static final String DFA19_specialS =
        "\5\uffff\1\1\1\0\1\uffff\1\2\u009d\uffff}>";
    static final String[] DFA19_transitionS = {
            "\2\46\2\uffff\1\46\22\uffff\1\46\1\23\1\5\4\uffff\1\6\1\11"+
            "\1\12\1\2\1\17\1\3\1\20\1\45\1\21\12\44\1\uffff\1\1\1\15\1\22"+
            "\1\16\2\uffff\1\36\1\31\1\40\1\24\1\43\1\33\2\43\1\25\2\43\1"+
            "\32\1\43\1\35\1\30\2\43\1\41\1\27\1\42\1\37\1\26\1\34\3\43\1"+
            "\13\1\7\1\14\1\uffff\1\4\1\10\1\36\1\31\1\40\1\24\1\43\1\33"+
            "\2\43\1\25\2\43\1\32\1\43\1\35\1\30\2\43\1\41\1\27\1\42\1\37"+
            "\1\26\1\34\3\43",
            "",
            "",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\0\51",
            "\0\51",
            "",
            "\140\43\1\uffff\uff9f\43",
            "",
            "",
            "",
            "",
            "\1\54",
            "\1\56",
            "",
            "",
            "",
            "\1\60",
            "\1\62",
            "\1\63\37\uffff\1\63",
            "\1\64\37\uffff\1\64",
            "\1\65\37\uffff\1\65",
            "\1\66\37\uffff\1\66",
            "\1\70\11\uffff\1\71\1\uffff\1\67\23\uffff\1\70\11\uffff\1"+
            "\71\1\uffff\1\67",
            "\1\72\37\uffff\1\72",
            "\1\73\37\uffff\1\73",
            "\1\75\20\uffff\1\74\16\uffff\1\75\20\uffff\1\74",
            "\1\76\37\uffff\1\76",
            "\1\101\5\uffff\1\100\1\uffff\1\77\27\uffff\1\101\5\uffff\1"+
            "\100\1\uffff\1\77",
            "\1\103\4\uffff\1\102\32\uffff\1\103\4\uffff\1\102",
            "\1\104\37\uffff\1\104",
            "\1\105\37\uffff\1\105",
            "\1\106\37\uffff\1\106",
            "\1\107\37\uffff\1\107",
            "",
            "\1\45\1\uffff\12\44\13\uffff\1\45\37\uffff\1\45",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\111",
            "\1\114\6\uffff\1\113\30\uffff\1\114\6\uffff\1\113",
            "\1\116\1\115\36\uffff\1\116\1\115",
            "\1\117\37\uffff\1\117",
            "\1\120\37\uffff\1\120",
            "\12\43\7\uffff\3\43\1\122\26\43\4\uffff\1\43\1\uffff\3\43"+
            "\1\122\26\43",
            "\1\123\37\uffff\1\123",
            "\1\124\37\uffff\1\124",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\126\37\uffff\1\126",
            "\1\127\37\uffff\1\127",
            "\1\130\37\uffff\1\130",
            "\1\131\37\uffff\1\131",
            "\1\132\37\uffff\1\132",
            "\1\133\37\uffff\1\133",
            "\1\134\37\uffff\1\134",
            "\12\43\7\uffff\2\43\1\136\27\43\4\uffff\1\43\1\uffff\2\43"+
            "\1\136\27\43",
            "\1\137\37\uffff\1\137",
            "\1\140\37\uffff\1\140",
            "\1\141\37\uffff\1\141",
            "\1\142\37\uffff\1\142",
            "\1\143\37\uffff\1\143",
            "",
            "",
            "",
            "\1\144\37\uffff\1\144",
            "\1\145\37\uffff\1\145",
            "\1\146\37\uffff\1\146",
            "\1\147\37\uffff\1\147",
            "\1\150\37\uffff\1\150",
            "\1\151\37\uffff\1\151",
            "",
            "\1\152\37\uffff\1\152",
            "\1\153\37\uffff\1\153",
            "\1\154\37\uffff\1\154",
            "",
            "\1\155\37\uffff\1\155",
            "\1\156\37\uffff\1\156",
            "\1\157\37\uffff\1\157",
            "\1\160\37\uffff\1\160",
            "\1\161\37\uffff\1\161",
            "\1\162\37\uffff\1\162",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\167\37\uffff\1\167",
            "\1\170\37\uffff\1\170",
            "\1\171\37\uffff\1\171",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\173\37\uffff\1\173",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\175\37\uffff\1\175",
            "\1\176\37\uffff\1\176",
            "\1\177\37\uffff\1\177",
            "\1\u0080\37\uffff\1\u0080",
            "\1\u0081\37\uffff\1\u0081",
            "\1\u0082\37\uffff\1\u0082",
            "\1\u0083\37\uffff\1\u0083",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\u0085\37\uffff\1\u0085",
            "\1\u0086\37\uffff\1\u0086",
            "\1\u0087\37\uffff\1\u0087",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            "",
            "",
            "",
            "\1\u0089\37\uffff\1\u0089",
            "\1\u008a\37\uffff\1\u008a",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            "\1\u008c\37\uffff\1\u008c",
            "",
            "\1\u008d\37\uffff\1\u008d",
            "\1\u008e\37\uffff\1\u008e",
            "\1\u008f\37\uffff\1\u008f",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\u0091\37\uffff\1\u0091",
            "\1\u0092\37\uffff\1\u0092",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\u0096\37\uffff\1\u0096",
            "",
            "\1\u0097\37\uffff\1\u0097",
            "\1\u0098\37\uffff\1\u0098",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\u009e\37\uffff\1\u009e",
            "",
            "",
            "",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\1\u00a1\37\uffff\1\u00a1",
            "",
            "",
            "",
            "",
            "",
            "\1\u00a2\37\uffff\1\u00a2",
            "",
            "",
            "\1\u00a3\37\uffff\1\u00a3",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "\12\43\7\uffff\32\43\4\uffff\1\43\1\uffff\32\43",
            "",
            ""
    };

    static final short[] DFA19_eot = DFA.unpackEncodedString(DFA19_eotS);
    static final short[] DFA19_eof = DFA.unpackEncodedString(DFA19_eofS);
    static final char[] DFA19_min = DFA.unpackEncodedStringToUnsignedChars(DFA19_minS);
    static final char[] DFA19_max = DFA.unpackEncodedStringToUnsignedChars(DFA19_maxS);
    static final short[] DFA19_accept = DFA.unpackEncodedString(DFA19_acceptS);
    static final short[] DFA19_special = DFA.unpackEncodedString(DFA19_specialS);
    static final short[][] DFA19_transition;

    static {
        int numStates = DFA19_transitionS.length;
        DFA19_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA19_transition[i] = DFA.unpackEncodedString(DFA19_transitionS[i]);
        }
    }

    class DFA19 extends DFA {

        public DFA19(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 19;
            this.eot = DFA19_eot;
            this.eof = DFA19_eof;
            this.min = DFA19_min;
            this.max = DFA19_max;
            this.accept = DFA19_accept;
            this.special = DFA19_special;
            this.transition = DFA19_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( SEMICOLON | ASTERISK | COMMA | UNDERSCORE | QUOTE_DOUBLE | QUOTE_SINGLE | BACKSLASH | BACKTICK | LPAREN | RPAREN | LSBRACKET | RSBRACKET | LESS_THAN | LESS_THAN_OR_EQUAL_TO | GREATER_THAN | GREATER_THAN_OR_EQUAL_TO | PLUS | MINUS | SLASH | EQUAL_TO_SINGLE | EQUAL_TO_DOUBLE | NOT_EQUAL_TO_SINGLE | NOT_EQUAL_TO_DOUBLE | DELETE | INSERT | INTO | VALUES | SELECT | ORDER | BY | LIMIT | OFFSET | FROM | WHERE | NWHERE | ASC | DESC | USE | AS | COMMIT | ROLLBACK | OPTIMIZE | OR | AND | NOT | NULL | TRUE | FALSE | STRING | ID | INTEGER | FLOAT | WS );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA19_6 = input.LA(1);

                        s = -1;
                        if ( ((LA19_6>='\u0000' && LA19_6<='\uFFFF')) ) {s = 41;}

                        else s = 42;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA19_5 = input.LA(1);

                        s = -1;
                        if ( ((LA19_5>='\u0000' && LA19_5<='\uFFFF')) ) {s = 41;}

                        else s = 40;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA19_8 = input.LA(1);

                        s = -1;
                        if ( ((LA19_8>='\u0000' && LA19_8<='_')||(LA19_8>='a' && LA19_8<='\uFFFF')) ) {s = 35;}

                        else s = 43;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 19, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}