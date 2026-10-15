package ua.edu.lab2;

import java.util.EmptyStackException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {
    private Stack<Integer> stack;

    @BeforeEach
    void setUp() {
        stack = new Stack<>();
    }

    @Test
    @DisplayName("Елементи дістаються у зворотному порядку додавання (LIFO)")
    void popsElementsInLifoOrder() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("Перегляд вершини не видаляє елемент і не змінює розмір")
    void peekDoesNotRemoveTop() {
        stack.push(10);
        stack.push(20);

        int top = stack.peek();

        assertAll(
                () -> assertEquals(20, top),
                () -> assertEquals(20, stack.peek()),
                () -> assertEquals(2, stack.size()),
                () -> assertFalse(stack.isEmpty()));
    }

    @Test
    @DisplayName("Новий стек порожній і має нульовий розмір")
    void newStackIsEmpty() {
        assertAll(
                () -> assertTrue(stack.isEmpty()),
                () -> assertEquals(0, stack.size()));
    }

    @Test
    @DisplayName("Додавання та видалення єдиного елемента знову робить стек порожнім")
    void singleElementCanBeRemoved() {
        stack.push(42);
        assertEquals(1, stack.size());
        assertEquals(42, stack.peek());

        int removed = stack.pop();

        assertAll(
                () -> assertEquals(42, removed),
                () -> assertEquals(0, stack.size()),
                () -> assertTrue(stack.isEmpty()));
    }

    @Test
    @DisplayName("Видалення з порожнього стеку кидає EmptyStackException")
    void popOnEmptyStackThrows() {
        assertThrows(EmptyStackException.class, () -> stack.pop());
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("Перегляд порожнього стеку кидає EmptyStackException")
    void peekOnEmptyStackThrows() {
        assertThrows(EmptyStackException.class, () -> stack.peek());
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("Значення null відхиляється без зміни наявних елементів")
    void rejectsNullWithoutChangingStack() {
        stack.push(7);

        assertThrows(NullPointerException.class, () -> stack.push(null));

        assertAll(
                () -> assertEquals(1, stack.size()),
                () -> assertEquals(7, stack.peek()));
    }

    @Test
    @DisplayName("Стек підтримує рядки завдяки узагальненому типу")
    void supportsStrings() {
        Stack<String> words = new Stack<>();
        words.push("перший");
        words.push("другий");

        assertEquals("другий", words.pop());
        assertEquals("перший", words.pop());
    }

    @Test
    @DisplayName("Стек можна повторно використовувати після повного спорожнення")
    void canBeReusedAfterEmptying() {
        stack.push(1);
        stack.pop();
        assertThrows(EmptyStackException.class, () -> stack.pop());

        stack.push(2);

        assertEquals(2, stack.pop());
        assertTrue(stack.isEmpty());
    }
}
