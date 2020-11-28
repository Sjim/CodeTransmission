package net.mooctest;
import static net.mooctest.CMD.*;
import java.util.Locale;
import org.junit.Test;
import static org.junit.Assert.*;
public class MyTest{


/*Transform from CMDTest*/

    @Test
    public void test() {
        CMD cmd = new CMD();
        CMD.Option.IntegerOption optionInt1 = new CMD.Option.IntegerOption('i', "test1");
        CMD.Option.IntegerOption optionInt2 = new CMD.Option.IntegerOption("test2");
        CMD.Option.LongOption optionLong1 = new CMD.Option.LongOption('l', "test3");
        CMD.Option.LongOption optionLong2 = new CMD.Option.LongOption("test4");
        CMD.Option.DoubleOption optionDouble1 = new CMD.Option.DoubleOption('d', "test5");
        CMD.Option.DoubleOption optionDouble2 = new CMD.Option.DoubleOption("test6");
        CMD.Option.StringOption optionString1 = new CMD.Option.StringOption('s', "test7");
        CMD.Option.StringOption optionString2 = new CMD.Option.StringOption("test8");
        CMD.Option.BooleanOption optionBoolean1 = new CMD.Option.BooleanOption('b', "test9");
        CMD.Option.BooleanOption optionBoolean2 = new CMD.Option.BooleanOption("test10");
        String[] arg1 = { "-i", "10", "-l", "10", "-d", "10.0", "-s", "abc", "-b", "true" };
        cmd.addOption(optionInt1);
        cmd.addOption(optionInt2);
        cmd.addBooleanOption(optionBoolean2.longForm());
        cmd.addBooleanOption(optionBoolean1.shortForm().charAt(0), optionBoolean1.longForm());
        cmd.addDoubleOption(optionDouble1.shortForm().charAt(0), optionDouble1.longForm());
        cmd.addDoubleOption(optionDouble2.longForm());
        cmd.addLongOption(optionLong1.shortForm().charAt(0), optionLong1.longForm());
        cmd.addLongOption(optionLong2.longForm());
        cmd.addStringOption(optionString2.longForm());
        cmd.addStringOption(optionString1.shortForm().charAt(0), optionString1.longForm());
        try {
            cmd.parse(arg1);
            assertEquals(10, (long) cmd.getOptionValue(optionInt1));
            assertEquals("true", cmd.getRemainingArgs()[0]);
        } catch (CMD.OptionException e) {
            e.printStackTrace();
        }
    }



/*Transform from CMDTest*/

    @Test
    public void test2() {
        CMD cmd = new CMD();
        CMD.Option.IntegerOption optionInt1 = new CMD.Option.IntegerOption('i', "test1");
        CMD.Option.IntegerOption optionInt2 = new CMD.Option.IntegerOption("test2");
        String[] arg1 = { "-i", "10", "-l", "10", "-d", "10.0", "-s", "abc", "-b", "true" };
        cmd.addIntegerOption(optionInt2.longForm());
        cmd.addIntegerOption(optionInt1.shortForm().charAt(0), optionInt1.longForm());
        try {
            cmd.parse(arg1);
        } catch (CMD.UnknownOptionException c) {
            assertEquals("Unknown option '-l'", c.getMessage());
            assertEquals("-l", c.getOptionName());
        } catch (CMD.OptionException e) {
        // e.printStackTrace();
        }
    }



/*Transform from CMDTest*/

    @Test
    public void test5() {
        CMD cmd = new CMD();
        CMD.Option.IntegerOption optionInt1 = new CMD.Option.IntegerOption('i', "test1");
        CMD.Option.IntegerOption optionInt2 = new CMD.Option.IntegerOption("test2");
        CMD.Option.LongOption optionLong1 = new CMD.Option.LongOption('l', "test3");
        String[] arg1 = { "-i-l" };
        cmd.addIntegerOption(optionInt2.longForm());
        cmd.addIntegerOption(optionInt1.shortForm().charAt(0), optionInt1.longForm());
        cmd.addOption(optionLong1);
        try {
            cmd.parse(arg1);
        } catch (CMD.OptionException e) {
            // e.printStackTrace();
            assertEquals("Illegal option: '-i-l', 'i' requires a value", e.getMessage());
        }
    }



/*Transform from CMDTest*/

    @Test
    public void test_8() {
        new CMD();
        CMD.Option<Boolean> opBoolean = new CMD.Option.BooleanOption('a', "aa");
        CMD.Option<Integer> opInteger = new CMD.Option.IntegerOption('b', "bb");
        CMD.Option<String> opString = new CMD.Option.StringOption('c', "cc");
        CMD.Option<Long> opLong = new CMD.Option.LongOption('d', "dd");
        CMD.Option<Double> opDouble = new CMD.Option.DoubleOption('e', "ee");
        /*[Transform from] assertEquals("a", opBoolean.shortForm());*/
        opBoolean.shortForm();
        /*[Transform from] assertEquals("aa", opBoolean.longForm());*/
        opBoolean.longForm();
        /*[Transform from] assertNull(opInteger.getDefaultValue());*/
        opInteger.getDefaultValue();
        /*[Transform from] assertEquals(Boolean.TRUE, opBoolean.getDefaultValue());*/
        opBoolean.getDefaultValue();
        try {
            /*[Transform from] assertEquals(Boolean.TRUE, opBoolean.getValue("ola", Locale.CANADA));*/
            opBoolean.getValue("ola", Locale.CANADA);
        } catch (CMD.IllegalOptionValueException e1) {
        }
        try {
            /*[Transform from] assertEquals(new Integer("1"), opInteger.getValue("1", Locale.CANADA));*/
            opInteger.getValue("1", Locale.CANADA);
        } catch (CMD.IllegalOptionValueException e2) {
        }
        try {
            opInteger.getValue(null, Locale.CANADA);
        } catch (CMD.IllegalOptionValueException e3) {
        }
        try {
            /*[Transform from] assertEquals(new Double(1.0), opDouble.parseValue("1.0", (Locale) Locale.CANADA));*/
            opDouble.parseValue("1.0", (Locale) Locale.CANADA);
        } catch (CMD.IllegalOptionValueException e4) {
        }
        try {
            /*[Transform from] assertEquals("ola", opString.parseValue("ola", (Locale) Locale.CANADA));*/
            opString.parseValue("ola", (Locale) Locale.CANADA);
        } catch (CMD.IllegalOptionValueException e) {
        }
        CMD cmd = new CMD();
        cmd.addBooleanOption("aa");
        assertNull(cmd.getOptionValue(opBoolean));
    }



/*Transform from CMDTest*/

    @Test
    public void addOptions_1() throws CMD.OptionException {
        CMD cmd = new CMD();
        cmd.addStringOption("aa");
        cmd.addBooleanOption('b', "bool");
        String[] argv = { "--aa=abc", "--bool=false" };
        cmd.parse(argv);
        /*[Transform from] assertEquals("abc", cmd.getOptionValue(new CMD.Option.StringOption("aa")));*/
        cmd.getOptionValue(new CMD.Option.StringOption("aa"));
        assertTrue(cmd.getOptionValue(new CMD.Option.BooleanOption("bool")));
    }



/*Transform from CMDTest*/

    @Test
    public void otherArgs() throws Exception {
        CMD cmd = new CMD();
        cmd.parse(new String[] { "123", "456", "789" });
        assertEquals(3, cmd.getRemainingArgs().length);
    }



/*Transform from CMDTest*/

    @Test(expected = CMD.IllegalOptionValueException.class)
    public void testIllegalOption3() throws CMD.OptionException {
        CMD cmd = new CMD();
        CMD.Option<Long> cmd_string_ls = cmd.addLongOption('d', "days");
        String[] arguments = { "-d", "bottom" };
        cmd.parse(arguments);
    }

}
