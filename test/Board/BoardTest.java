/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Board;

import Pieces.King;
import Pieces.Pawn;
import Pieces.Piece;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author stamp
 */
public class BoardTest {
    
    public BoardTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of move method, of class Board.
     */
    @Test
    public void testMove() {
        System.out.println("move");
        Move move = null;
        Board instance = null;
        instance.move(move);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copyAndMove method, of class Board.
     */
    @Test
    public void testCopyAndMove() {
        System.out.println("copyAndMove");
        Move move = null;
        Board instance = null;
        Board expResult = null;
        Board result = instance.copyAndMove(move);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of copyBoard method, of class Board.
     */
    @Test
    public void testCopyBoard() {
        System.out.println("copyBoard");
        Board original = null;
        Board expResult = null;
        Board result = Board.copyBoard(original);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fillMoves method, of class Board.
     */
    @Test
    public void testFillMoves() {
        System.out.println("fillMoves");
        Board instance = null;
        instance.fillMoves();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of printBoard method, of class Board.
     */
    @Test
    public void testPrintBoard() {
        System.out.println("printBoard");
        Board instance = null;
        instance.printBoard();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEmpty method, of class Board.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("isEmpty");
        Position pos = null;
        Board instance = null;
        boolean expResult = false;
        boolean result = instance.isEmpty(pos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOnPosition method, of class Board.
     */
    @Test
    public void testGetOnPosition_Position() {
        System.out.println("getOnPosition");
        Position pos = null;
        Board instance = null;
        Piece expResult = null;
        Piece result = instance.getOnPosition(pos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOnPosition method, of class Board.
     */
    @Test
    public void testGetOnPosition_int_int() {
        System.out.println("getOnPosition");
        int col = 0;
        int row = 0;
        Board instance = null;
        Piece expResult = null;
        Piece result = instance.getOnPosition(col, row);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getState method, of class Board.
     */
    @Test
    public void testGetState() {
        System.out.println("getState");
        Board instance = null;
        Board.State expResult = null;
        Board.State result = instance.getState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPieces method, of class Board.
     */
    @Test
    public void testGetPieces() {
        System.out.println("getPieces");
        Board instance = null;
        ArrayList<Piece> expResult = null;
        ArrayList<Piece> result = instance.getPieces();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBoxes method, of class Board.
     */
    @Test
    public void testGetBoxes() {
        System.out.println("getBoxes");
        Board instance = null;
        Box[][] expResult = null;
        Box[][] result = instance.getBoxes();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTurn method, of class Board.
     */
    @Test
    public void testSetTurn() {
        System.out.println("setTurn");
        Piece.Color turn = null;
        Board instance = null;
        instance.setTurn(turn);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTurn method, of class Board.
     */
    @Test
    public void testGetTurn() {
        System.out.println("getTurn");
        Board instance = null;
        Piece.Color expResult = null;
        Piece.Color result = instance.getTurn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLastMove method, of class Board.
     */
    @Test
    public void testGetLastMove() {
        System.out.println("getLastMove");
        Board instance = null;
        Move expResult = null;
        Move result = instance.getLastMove();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWhiteSight method, of class Board.
     */
    @Test
    public void testGetWhiteSight() {
        System.out.println("getWhiteSight");
        Board instance = null;
        ArrayList<Position> expResult = null;
        ArrayList<Position> result = instance.getWhiteSight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlackSight method, of class Board.
     */
    @Test
    public void testGetBlackSight() {
        System.out.println("getBlackSight");
        Board instance = null;
        ArrayList<Position> expResult = null;
        ArrayList<Position> result = instance.getBlackSight();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWhiteMoves method, of class Board.
     */
    @Test
    public void testGetWhiteMoves() {
        System.out.println("getWhiteMoves");
        Board instance = null;
        ArrayList<Position> expResult = null;
        ArrayList<Position> result = instance.getWhiteMoves();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlackMoves method, of class Board.
     */
    @Test
    public void testGetBlackMoves() {
        System.out.println("getBlackMoves");
        Board instance = null;
        ArrayList<Position> expResult = null;
        ArrayList<Position> result = instance.getBlackMoves();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWhiteKing method, of class Board.
     */
    @Test
    public void testGetWhiteKing() {
        System.out.println("getWhiteKing");
        Board instance = null;
        King expResult = null;
        King result = instance.getWhiteKing();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBlackKing method, of class Board.
     */
    @Test
    public void testGetBlackKing() {
        System.out.println("getBlackKing");
        Board instance = null;
        King expResult = null;
        King result = instance.getBlackKing();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPositionFen method, of class Board.
     */
    @Test
    public void testGetPositionFen() {
        System.out.println("getPositionFen");
        Board instance = null;
        String expResult = "";
        String result = instance.getPositionFen();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMoves method, of class Board.
     */
    @Test
    public void testGetMoves() {
        System.out.println("getMoves");
        Piece.Color color = null;
        Board instance = null;
        ArrayList<Position> expResult = null;
        ArrayList<Position> result = instance.getMoves(color);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnPassantPos method, of class Board.
     */
    @Test
    public void testGetEnPassantPos() {
        System.out.println("getEnPassantPos");
        Board instance = null;
        Position expResult = null;
        Position result = instance.getEnPassantPos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnPassantPawn method, of class Board.
     */
    @Test
    public void testGetEnPassantPawn() {
        System.out.println("getEnPassantPawn");
        Board instance = null;
        Pawn expResult = null;
        Pawn result = instance.getEnPassantPawn();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of resetEnPassant method, of class Board.
     */
    @Test
    public void testResetEnPassant() {
        System.out.println("resetEnPassant");
        Board instance = null;
        instance.resetEnPassant();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
