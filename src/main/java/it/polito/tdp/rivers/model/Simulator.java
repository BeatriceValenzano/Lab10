package it.polito.tdp.rivers.model;

import java.util.PriorityQueue;

public class Simulator {
	
//	STATO DEL SISTEMA
	private double quantitaAcquaBacino;  //quantita di acqua che c'è nel tempo t nel bacino
	
//	PARAMETRI IN INGRESSO 
	private double occupazioneMaxBacino;  //massimo metricubi che stanno nel bacino
//	private double flussoIngresso;   la parte di acqua che entra nel bacino
	private double flussoUscita;     //la parte di acqua che entra in diga e si immette nel fiume

	//  PARAMETRI IN USCITA
	private int nGiorniNOErogMin;
	private double occupazioneMed;
	
//	CODA DEGLI EVENTI
	PriorityQueue<Flow> queue = new PriorityQueue<Flow>();
	
	public Simulator(double k, River r) {
		this.occupazioneMaxBacino = k * 30 * r.getFlowAvg();
		this.quantitaAcquaBacino = occupazioneMaxBacino / 2;
		this.queue.addAll(r.getFlows());
		this.flussoUscita = 0.8 * r.getFlowAvg();
		this.nGiorniNOErogMin = 0;
	}
	
	public void run() {
		
		int size = queue.size();
		double cMed = 0.0;
		while(!this.queue.isEmpty()) {
			Flow f = this.queue.poll();
			System.out.println(f.toString());
			double flowEntry = f.getFlow();
			double p = Math.random();
			double flowExit;
			if(p<0.05) {
				flowExit = 10*this.flussoUscita;
			} else {
				flowExit = this.flussoUscita;
			}
			
			if(flowEntry>flowExit) {
				quantitaAcquaBacino += flowEntry - flowExit;
				if(quantitaAcquaBacino>occupazioneMaxBacino) {
					quantitaAcquaBacino=occupazioneMaxBacino;
				}
			} else {
				quantitaAcquaBacino -= flowExit - flowEntry;  //se esce più acqua di quanto ne entra avrò una diminuzione della quantità 
				if(quantitaAcquaBacino < 0 
					/*l'acqua che entra + l'acqua nel bacino è minore di quella in uscita allora incremento i giorni*/) {
					nGiorniNOErogMin++;
					quantitaAcquaBacino = 0; //poichè non può avere una quanità negativa di acqua IMPOSSIBILE
				}
			}
			
			cMed += quantitaAcquaBacino;
		}
		
		occupazioneMed = cMed / size;
		
	}
	
	public int getnGiorniNOErogMin() {
		return nGiorniNOErogMin;
	}

	public double getOccupazioneMed() {
		return occupazioneMed;
	}
}
