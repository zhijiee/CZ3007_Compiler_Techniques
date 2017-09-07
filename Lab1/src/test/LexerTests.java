package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import frontend.Token;
import frontend.Token.Type;
import static frontend.Token.Type.*;

/**
 * This class contains unit tests for your lexer. Currently, there is only one test, but you
 * are strongly encouraged to write your own tests.
 */
public class LexerTests {
	// helper method to run tests; no need to change this
	private final void runtest(String input, Token... output) {
		Lexer lexer = new Lexer(new StringReader(input));
		int i=0;
		Token actual, expected;
		try {
			do {
				assertTrue(i < output.length);
				expected = output[i++];
				try {
					actual = lexer.nextToken();
					assertEquals(expected, actual);
				} catch(Error e) {
					if(expected != null)
						fail(e.getMessage());
					return;
				}
			} while(!actual.isEOF());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/** Example unit test. */
	@Test
	public void testKWs() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("module false return while",
				new Token(MODULE, 0, 0, "module"),
				new Token(FALSE, 0, 7, "false"),
				new Token(RETURN, 0, 13, "return"),
				new Token(WHILE, 0, 20, "while"),
				new Token(EOF, 0, 25, ""));
	}

	@Test
	public void testStringLiteralWithDoubleQuote() {
		runtest("\"\"\"",
				new Token(STRING_LITERAL, 0, 0, ""),
				(Token)null);
	}

	@Test
	public void testStringLiteral() {
		runtest("\"\\n\"", 
				new Token(STRING_LITERAL, 0, 0, "\\n"),
				new Token(EOF, 0, 4, ""));

	}
	
	@Test
	public void testWhileLoop(){
		runtest("while(true) break;", 
				new Token(WHILE, 0,0, "while"),
				new Token(LPAREN, 0, 5, "("),
				new Token(TRUE, 0, 6, "true"),
				new Token(RPAREN, 0, 10, ")"),
				new Token(BREAK, 0, 12, "break"),
				new Token(SEMICOLON, 0, 17, ";"),
				new Token(EOF, 0, 18, "")
				);
	}

	@Test
	public void testIfElse(){
		runtest("if(x==10) break; else break;",
				new Token(IF, 0,0,"if"),
				new Token(LPAREN, 0, 2, "("),
				new Token(ID, 0,3,"x"),
				new Token(EQEQ,0,4,"=="),
				new Token(INT_LITERAL,0,6,"10"),
				new Token(RPAREN, 0, 8, ")"),
				new Token(BREAK, 0, 10, "break"),
				new Token(SEMICOLON, 0, 15, ";"),
				new Token(ELSE, 0, 17, "else"),
				new Token(BREAK, 0, 22, "break"),
				new Token(SEMICOLON, 0, 27, ";"),
				new Token(EOF, 0, 28, "")
				);
	}
	

}
