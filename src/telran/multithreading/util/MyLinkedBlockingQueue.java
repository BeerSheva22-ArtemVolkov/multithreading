package telran.multithreading.util;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.*;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class MyLinkedBlockingQueue<E> implements BlockingQueue<E> {

	LinkedList<E> list = new LinkedList<>();
	int limit;
	ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	ReadLock readLock = rwLock.readLock();
	WriteLock writeLock = rwLock.writeLock();

	Condition waitingConsumer = writeLock.newCondition();
	Condition waitingProducer = writeLock.newCondition();

	public MyLinkedBlockingQueue(int limit) {
		this.limit = limit;
	}

	public MyLinkedBlockingQueue() {
		this(Integer.MAX_VALUE);
	}

	@Override
	public E remove() {
		writeLock.lock();
		try {
			E res = list.removeFirst();
			waitingProducer.signalAll();
			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E poll() {
		writeLock.lock();
		try {
			return list.poll();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E element() {
		writeLock.lock();
		try {
			return list.element();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E peek() {
		writeLock.lock();
		try {
			return list.isEmpty() ? null : list.getFirst();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		writeLock.lock();
		try {
			return list.isEmpty();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public Iterator<E> iterator() {
		writeLock.lock();
		try {
			return list.iterator();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public Object[] toArray() {
		writeLock.lock();
		try {
			return list.toArray();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		writeLock.lock();
		readLock.lock();
		try {
			return list.toArray(a);
		} finally {
			writeLock.unlock();
			readLock.unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		writeLock.lock();
		readLock.lock();
		try {
			return list.containsAll(c);
		} finally {
			writeLock.unlock();
			readLock.unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean res = false;
		for (E el : c) {
			try {
				put(el);
				res = true;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return res;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		writeLock.lock();
		try {
			return list.removeAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		writeLock.lock();
		boolean[] res = new boolean[1];
		res[0] = false;
		try {
			c.forEach(el -> {
				if (list.contains(el)) {
					c.remove(el);
					res[0] = true;
				}
			});
			return res[0];
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void clear() {
		readLock.lock();
		try {
			list.clear();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean add(E e) {
		writeLock.lock();
		try {
			if (list.size() + 1 > limit) {
				throw new IllegalStateException();
			} else if (e == null) {
				throw new NullPointerException();
			} else {
				return list.add(e);
			}
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		writeLock.lock();
		try {
			if (size() < limit) {
				boolean res = list.offer(e);
				waitingConsumer.signal();
				return res;
			} else {
				return false;
			}
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		writeLock.lock();
		try {
			if (e == null) {
				throw new NullPointerException();
			} else if (!e.getClass().equals(this.getClass())) {
				throw new ClassCastException();
			}
			while (list.size() + 1 > limit) {
				waitingProducer.await();
			}
			list.add(e);
			waitingConsumer.signal();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		writeLock.lock();
		try {
			boolean res = false;
			while (list.size() + 1 > limit) {
				waitingProducer.await(timeout, unit);
			}
			if (list.size() + 1 < limit) {
				list.add(e);
				res = true;
				waitingConsumer.signal();
			}
			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		writeLock.lock();
		try {
			while (list.isEmpty()) {
				waitingConsumer.await();
			}
			E res = list.pop();
			waitingProducer.signal();
			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		writeLock.lock();
		try {
			while (list.isEmpty()) {
				waitingConsumer.await(timeout, unit);
			}
			E res = null;
			if (!list.isEmpty()) {
				res = list.remove();
				waitingProducer.signal();
			}
			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public int remainingCapacity() {
		readLock.lock();
		try {
			return limit - size();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		writeLock.lock();
		try {
			if (o == null) {
				throw new NullPointerException();
			} else if (!o.getClass().equals(this.getClass())) {
				throw new ClassCastException();
			} else {
				return list.remove(o);
			}
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean contains(Object o) {
		writeLock.lock();
		try {
			return list.contains(o);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		throw new UnsupportedOperationException();
	}

}
