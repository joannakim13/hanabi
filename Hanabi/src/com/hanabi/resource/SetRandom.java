package com.hanabi.resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SetRandom<T> {

	private List<T> list = new ArrayList<T>();
	public Set<T> set = new HashSet<T>();

	public boolean add(T element) {
		if (set.contains(element)) {
			return false;
		} else {
			set.add(element);
			list.add(element);
			return true;
		}
	}

	public T removeRandom() {
		int index = new Random().nextInt(list.size());
		T element = list.get(index);
		T last = list.get(list.size() - 1);
		set.remove(element);
		list.set(index, last);
		list.remove(list.size() - 1);
		return element;
	}

	public int size() {
	  return list.size();
	}
}
