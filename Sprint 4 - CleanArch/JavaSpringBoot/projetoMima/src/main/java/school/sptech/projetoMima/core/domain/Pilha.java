package school.sptech.projetoMima.core.domain;

import java.util.EmptyStackException;

public class Pilha<E> {

    private E[] elementos;
    private int topo;

    public Pilha(int capacidade) {
        this.elementos = (E[]) new Object[capacidade];
        this.topo = -1;
    }

    public E push(E elemento) {
        if (isFull()) {
            throw new StackOverflowError("Pilha cheia! Capacidade: " + elementos.length);
        }

        elementos[++topo] = elemento;
        return elemento;
    }

    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }

        return elementos[topo--];
    }

    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return elementos[topo];
    }

    public boolean isEmpty() {
        return topo == -1;
    }

    public boolean isFull() {
        return topo == elementos.length - 1;
    }

    public int size() {
        return topo + 1;
    }

    public int capacity() {
        return elementos.length;
    }

    public void clear() {
        this.elementos = (E[]) new Object[elementos.length];
        topo = -1;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Pilha vazia []";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Pilha [").append(size()).append("/").append(capacity()).append("]:\n");

        for (int i = topo; i >= 0; i--) {
            sb.append("  ");
            if (i == topo) {
                sb.append("TOPO ");
            } else {
                sb.append("     ");
            }
            sb.append("| ").append(elementos[i]).append(" |\n");
        }
        sb.append("  └─────┘");

        return sb.toString();
    }
}

