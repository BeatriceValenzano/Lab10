package it.polito.tdp.rivers.model;

import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	RiversDAO dao;
	Simulator sim;
	
	public Model() {
		dao = new RiversDAO();
	}
	
	public List<River> getFiumi() {
		return dao.getAllRivers();
	}
	
	
	public List<Flow> listaFlussi(River r){
		
		List<Flow> flow = dao.getInfo(r);
		double sumFlow = 0.0;
		double flowMed;
		r.setFlows(flow);
		for(Flow f : flow) {
			sumFlow += f.getFlow();
		}
		flowMed = sumFlow / flow.size();
		r.setFlowAvg(flowMed);
		return flow;
	}
	
	public double getCMed(double k, River r) {
		sim = new Simulator(k, r);
		sim.run();
		return sim.getOccupazioneMed();
	}
	
	public int getGiorni(double k, River r) {
		return sim.getnGiorniNOErogMin();
	}
}
