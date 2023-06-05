import java.util.List;
import java.util.ArrayList;

public class Stack <T> {
	private final List<T> stack;
	private final int size;
	private int index = -1;

	public Stack(int size) {
		this.size = size;
		this.stack = new ArrayList<>(size);
		for (int i = 0; i < this.size; i++) this.stack.add(null);
	}

	public boolean empty() {
		return this.index < 0;
	}

	public boolean full() {
		return this.index >= this.size - 1;
	}

	public boolean push(T obj) {
		if (full()) return false;
		this.stack.set(++this.index, obj);
		return true;
	}

	public T pop() {
		return empty() ? null : this.stack.get(this.index--);
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = this.stack.size() - 1; i >= 0; i--)
			str += String.format("%s [%4d]: %s\n", this.index == i ? ">" : " ", i, this.stack.get(i));
		return str;
	}
}
