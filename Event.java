package p2;

/**
 * Clase Práctica 2, ejercicio 1a
 */
public class Event
{
	static int HilosAltaPrioridad = 0;
	static int hilosBajaPrioridad = 0;
	
	/**
	 * Método que ejecutan los hilos de alta prioridad
	 */
	public synchronized void WaitAltaPrioridad()
	{
		System.out.println("Hilo de alta prioridad esperando");
		
		HilosAltaPrioridad++;
		
		try 
		{		
			wait();
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		
		HilosAltaPrioridad--;
		
		System.out.println("Hilo de alta prioridad finalizado");
	}
	
	/**
	 * Método que ejecutan los hilos de baja prioridad
	 */
	public synchronized void WaitBajaPrioridad()
	{
		System.out.println("Hilo de baja prioridad esperando");
		
		hilosBajaPrioridad++;
		
		while(HilosAltaPrioridad > 0)
		{			
			try 
			{		
				wait();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		hilosBajaPrioridad--;
		
		System.out.println("Hilo de baja prioridad finalizado");
	}
	
	/**
	 * Notifica a todos los eventos
	 */
	public synchronized void signalEvent()
	{
		notifyAll();
	}
	
	/**
	 * MAIN
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException 
	{
		Event evento = new Event();
		
		Thread altaPrioridad = new Thread(new Runnable() 
		{	
			@Override
			public void run() 
			{	
				System.out.println("Hilo de alta prioridad ejecutando");
				
				evento.WaitAltaPrioridad();
			}
		});		
		
		Thread bajaPrioridad = new Thread(new Runnable() 
		{
			@Override
			public void run() 
			{	
				System.out.println("Hilo de baja prioridad ejecutando");
				
				evento.WaitBajaPrioridad();
			}
		});
		
		altaPrioridad.start();
		bajaPrioridad.start();

		Thread.sleep(100);

		while(Event.HilosAltaPrioridad > 0 || Event.hilosBajaPrioridad > 0)
		{
			evento.signalEvent();
		}

		altaPrioridad.join();
		bajaPrioridad.join();
		
		System.out.println(Event.HilosAltaPrioridad);
	}
}