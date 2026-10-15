package ua.edu.lab2;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Objects;

/** Стек зі зберіганням елементів у списку та порядком LIFO. */
public final class Stack<T> {
    private final ArrayList<T> elements = new ArrayList<>();

    /** Додає елемент на вершину. Значення null не дозволене. */
    public void push(T value) {
        elements.add(Objects.requireNonNull(value, "Елемент стеку не може бути null"));
    }

    /** Видаляє та повертає вершину; для порожнього стеку кидає виняток. */
    public T pop() {
        requireNotEmpty();
        return elements.remove(elements.size() - 1);
    }

    /** Повертає вершину без видалення; для порожнього стеку кидає виняток. */
    public T peek() {
        requireNotEmpty();
        return elements.get(elements.size() - 1);
    }

    public int size() {
        return elements.size();
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    private void requireNotEmpty() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
    }
}
