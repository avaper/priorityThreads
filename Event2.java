package p2;

/**
 * Clase Práctica 2, ejercicio 1b
 */
public class Event2
{
	static String id = null;
	
	/**
	 * Método que ejecutan los hilos de alta prioridad
	 */
	public synchronized void WaitAltaPrioridad()
	{
		System.out.println("Hilo de alta prioridad esperando");
		
		while(id != null)
		{			
			id = "ALTA";
			
			try 
			{		
				wait();
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
		}
		
		id = null;
		
		System.out.println("Hilo de alta prioridad finalizado");
	}
	
	/**
	 * Método que ejecutan los hilos de baja prioridad
	 */
	public synchronized void WaitBajaPrioridad()
	{
		System.out.println("Hilo de baja prioridad esperando");
		
		while(id != null)
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
		Event2 evento = new Event2();
		
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

		while(Event2.id != null)
		{
			evento.signalEvent();
		}

		altaPrioridad.join();
		bajaPrioridad.join();
		
		System.out.println(Event2.id);
	}
}

