package com.example.multithread;

/*
 * 测试案例：子线程内有一个类，将这个类传递给该子线程的子线程，然后在子线程中看这个类的数据是否会被更改，结论是会的。
 */

class valuetest {
	private String value;

	public valuetest(String v) {
		this.setValue(v);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}


public class Thread2InThread1 {

	public class thread1 extends Thread {
		private valuetest thread1value;
		public int count;

		public thread1(valuetest v, int c) {
			thread1value = v;
			this.count = c;
		}

		public void run() {
			while (true) {
				System.out.println("i am thread1 and begin value is: " + thread1value.getValue());
				new thread2(thread1value).start();
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {}
				System.out.println("i am thread1 and end value is: " + thread1value.getValue());
				count++;
				if (count > 5) {
					System.out.println("thread1 exit");
					break;
				}

			}
		}
	}

	public class thread2 extends Thread {
		private valuetest thread2value;

		public thread2(valuetest v) {
			this.thread2value = v;
		}

		public void run() {
			this.thread2value.setValue("bbb");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println("i am thread2 and value is: " + this.thread2value.getValue());
			System.out.println("thread2 exit");
		}
	}

	public static void main(String[] args) {
		
		valuetest vt = new valuetest("aaa");
		new Thread2InThread1().new thread1(vt, 0).start();		
	}
}

