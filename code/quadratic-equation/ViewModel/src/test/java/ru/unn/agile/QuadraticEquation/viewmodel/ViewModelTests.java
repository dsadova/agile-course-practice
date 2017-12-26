package ru.unn.agile.QuadraticEquation.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.aProperty().get());
        assertEquals("", viewModel.bProperty().get());
        assertEquals("", viewModel.cProperty().get());
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenSolutionWithEmptyFields() {
        viewModel.solve();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        setInputData();
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canReportBadFormat() {
        viewModel.aProperty().set("a");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingIfNotEnoughCorrectData() {
        viewModel.aProperty().set("");
        viewModel.bProperty().set("");
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingEvenIfThirdCoefIsHere() {
        viewModel.aProperty().set("");
        viewModel.bProperty().set("");
        viewModel.cProperty().set("12345");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsBadIfTwoFirstCoefsAreZero() {
        viewModel.aProperty().set("0");
        viewModel.bProperty().set("0");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsOkIfEvenFirstCoefIsZeroAndSecondIsNot() {
        viewModel.aProperty().set("0");
        viewModel.bProperty().set("1");
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsOkIfEvenFirstCoefIsEmptyAndSecondIsNot() {
        viewModel.aProperty().set("");
        viewModel.bProperty().set("1");
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsOkIfEvenSecondCoefIsZeroAndFirstIsNot() {
        viewModel.aProperty().set("123");
        viewModel.bProperty().set("0");
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsOkIfEvenSecondCoefIsEmptyAndFirstIsNot() {
        viewModel.aProperty().set("123");
        viewModel.bProperty().set("");
        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void solveButtonIsDisabledInitially() {
        assertTrue(viewModel.solutionDisabledProperty().get());
    }

    @Test
    public void solveButtonIsDisabledWhenFormatIsBad() {
        setInputData();
        viewModel.aProperty().set("someLetters");
        assertTrue(viewModel.solutionDisabledProperty().get());
    }

    @Test
    public void solveButtonIsDisabledWithIncompleteInput() {
        viewModel.cProperty().set("1");
        assertTrue(viewModel.solutionDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInput() {
        setInputData();
        assertFalse(viewModel.solutionDisabledProperty().get());
    }

    @Test
    public void solutionHasCorrectResultWithOneRoot() {
        viewModel.aProperty().set("1");
        viewModel.bProperty().set("2");
        viewModel.cProperty().set("1");
        viewModel.solve();
        assertTrue(viewModel.getResult().contains("-1.0 + 0.0i"));
    }

    @Test
    public void solutionHasCorrectResultWithOnlyFirstCoef() {
        viewModel.aProperty().set("1");
        viewModel.bProperty().set("");
        viewModel.cProperty().set("");
        viewModel.solve();
        assertTrue(viewModel.getResult().contains("0.0 + 0.0i"));
    }

    @Test
    public void firstEmptyCellWillBeZeroIfYouInputNothingToIt() {
        viewModel.aProperty().set("");
        viewModel.bProperty().set("1");
        viewModel.cProperty().set("1");
        viewModel.solve();
        assertEquals("0", viewModel.aProperty().get());
    }

    @Test
    public void secondtEmptyCellWillBeZeroIfYouInputNothingToIt() {
        viewModel.aProperty().set("1");
        viewModel.bProperty().set("");
        viewModel.cProperty().set("1");
        viewModel.solve();
        assertEquals("0", viewModel.bProperty().get());
    }

    @Test
    public void thirdEmptyCellWillBeZeroIfYouInputNothingToIt() {
        viewModel.aProperty().set("1");
        viewModel.bProperty().set("1");
        viewModel.cProperty().set("");
        viewModel.solve();
        assertEquals("0", viewModel.cProperty().get());
    }

    @Test
    public void firstAndThirdEmptyCellWillBeZeroIfYouInputNothingToIt() {
        viewModel.aProperty().set("");
        viewModel.bProperty().set("1");
        viewModel.cProperty().set("");
        viewModel.solve();
        assertEquals("0", viewModel.aProperty().get());
        assertEquals("0", viewModel.cProperty().get());
    }

    @Test
    public void secondAndThirdEmptyCellWillBeZeroIfYouInputNothingToIt() {
        viewModel.aProperty().set("1");
        viewModel.bProperty().set("");
        viewModel.cProperty().set("");
        viewModel.solve();
        assertEquals("0", viewModel.bProperty().get());
        assertEquals("0", viewModel.cProperty().get());
    }

    @Test
    public void solutionHasCorrectResultWithOnlySecondCoef() {
        viewModel.aProperty().set("");
        viewModel.bProperty().set("5");
        viewModel.cProperty().set("");
        viewModel.solve();
        assertTrue(viewModel.getResult().contains("0.0 + 0.0i"));
    }

    @Test
    public void solutionHasCorrectResultWithSecondAndThirdCoef() {
        viewModel.aProperty().set("");
        viewModel.bProperty().set("5");
        viewModel.cProperty().set("-10");
        viewModel.solve();
        assertTrue(viewModel.getResult().contains("2.0 + 0.0i"));
    }

    @Test
    public void solutionHasCorrectResultWithTwoInputCoefs() {
        viewModel.aProperty().set("1");
        viewModel.bProperty().set("-1");
        viewModel.cProperty().set("");
        viewModel.solve();
        boolean flag = false;
        if (viewModel.getResult().contains("0.0 + 0.0i")
                && viewModel.getResult().contains("1.0 + 0.0i")) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void solutionHasCorrectResultWithTwoIntRoots() {
        viewModel.aProperty().set("1");
        viewModel.bProperty().set("5");
        viewModel.cProperty().set("6");
        viewModel.solve();
        boolean flag = false;
        if (viewModel.getResult().contains("-2.0 + 0.0i")
                && viewModel.getResult().contains("-3.0 + 0.0i")) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void solutionHasCorrectResultWithTwoComplexRoots() {
        viewModel.aProperty().set("1");
        viewModel.bProperty().set("6");
        viewModel.cProperty().set("10");
        viewModel.solve();
        boolean flag = false;
        if (viewModel.getResult().contains("-3.0 - 1.0i")
                && viewModel.getResult().contains("-3.0 + 1.0i")) {
            flag = true;
        }
        assertTrue(flag);
    }

    @Test
    public void canSetSuccessMessage() {
        setInputData();
        viewModel.solve();
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetSuccessMessageForLinearEquation() {
        viewModel.bProperty().set("2");
        viewModel.cProperty().set("3");
        viewModel.solve();
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetSuccessMessageWithoutSecondCoef() {
        viewModel.aProperty().set("1");
        viewModel.cProperty().set("3");
        viewModel.solve();
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetSuccessMessageOnlyWithFirstCoef() {
        viewModel.aProperty().set("1");
        viewModel.solve();
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetSuccessMessageOnlyWithSecondCoef() {
        viewModel.bProperty().set("1");
        viewModel.solve();
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetSuccessMessageWithoutThirdCoef() {
        viewModel.aProperty().set("1");
        viewModel.bProperty().set("3");
        viewModel.solve();
        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void canSetBadFormatMessage() {
        viewModel.aProperty().set("#=-badMess");
        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    private void setInputData() {
        viewModel.aProperty().set("1");
        viewModel.bProperty().set("2");
        viewModel.cProperty().set("3");
    }
}
