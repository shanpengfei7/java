package com.pnfy.tankwar.picture;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood /*extends Thread*/ {
	int x, y, w, h;
	TankClient tc;
	private static int step;
	
	
	private boolean live = true;
	
	public void setLive(boolean live) {
		this.live = live;
	}
	
	public boolean isLive() {
		return live;
	}

	
	private int[][]  pos = {
			{250, 200},{400,250},{550,200},{550, 400},{400, 300},{250, 400}
	};
	
	
	public Blood() {
		x = pos[0][0];
		y = pos[0][1];
		w = h = 30;
	}
	//BloodMove bloodMove = new BloodMove();
	//Thread thread = new Thread();
	
	public void draw(Graphics g) {
		if(!live) return;
		Color c = g.getColor();
		g.setColor(Color. MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		//move();
		//thread.start();
		//BloodMove().start();
		new Thread(new BloodMove()).start();
	}

	
	/*private void move() {
		
		if (step == pos.length) {
			step = 0;
		}
		
		x = pos[step][0];
		y = pos[step][1];
		
		//step ++;
	
	}*/
	
	
	
	public Rectangle getReat() {
		return new Rectangle(x, y, w, h);
	}
	
/*	public void BloodMove(){
		
		while(true){
			
			try {
				x = pos[step][0];
				y = pos[step][1];
				
			} catch (Exception e) {
				
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}*/
	private class BloodMove implements Runnable {						
		public void run() {
			while(true) {
				synchronized (pos) {
					
				
				try {
					//move();
					
					x = pos[step][0];
					y = pos[step][1];
					
					
					Thread.sleep(1000);
					step ++;
					
					/*System.out.println();
					System.out.println();
					System.out.println(step ++);
					System.out.println();*/
					if (step == pos.length) {
					//if (step >= 6) {
						step = 0;
					}
				} catch (InterruptedException e) {
						e.printStackTrace();
				}
				}
			}
		}		
	}

}


//}

//class BloodMove implements Runnable {
	/*public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
//}

/*//class BloodMove extends Thread {
	int x, y, w, h;
	int step = 0;
	
	private int[][]  pos = {
			{250, 200},{400,250},{550,200},{550, 400},{400, 300},{250, 400}
	};
	
	public BloodMove() {
		x = pos[0][0];
		y = pos[0][1];
		w = h = 30;
	}
	
	public void run() {
		if (step == pos.length) {
			step = 0;
		}
		
		x = pos[step][0];
		y = pos[step][1];
		
		step ++;
		try {
			Thread.sleep(1000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}





*/




