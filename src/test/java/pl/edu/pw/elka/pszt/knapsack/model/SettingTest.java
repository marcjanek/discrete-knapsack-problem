package pl.edu.pw.elka.pszt.knapsack.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class SettingTest {
    Setting setting;
    final String key1 = "key1";
    final Object value1 = "object";
    final Object value2 = new Setting(key1,2);
    @BeforeEach
    void init(){
        setting = new Setting(key1, value1);
    }

    @Test
    void getName() {
        assertEquals(key1,setting.getName());
    }

    @Test
    void getValue() {
        assertEquals(value1,setting.getValue());
    }

    @Test
    void setValue() {
        assert setting.getValue().equals(value1);
        setting.setValue(value2);
        assertSame(value2,setting.getValue());
    }
}