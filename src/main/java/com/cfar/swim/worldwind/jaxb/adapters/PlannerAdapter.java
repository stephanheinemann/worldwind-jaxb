/*
 * Copyright (c) 2021, Stephan Heinemann (UVic Center for Aerospace Research)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.cfar.swim.worldwind.jaxb.adapters;

import java.math.BigInteger;

import javax.activation.UnsupportedDataTypeException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.cfar.swim.worldwind.aircraft.Aircraft;
import com.cfar.swim.worldwind.environments.Environment;
import com.cfar.swim.worldwind.jaxb.scenario.ADRRT;
import com.cfar.swim.worldwind.jaxb.scenario.ADStar;
import com.cfar.swim.worldwind.jaxb.scenario.ARAStar;
import com.cfar.swim.worldwind.jaxb.scenario.ARRT;
import com.cfar.swim.worldwind.jaxb.scenario.AStar;
import com.cfar.swim.worldwind.jaxb.scenario.Algorithm;
import com.cfar.swim.worldwind.jaxb.scenario.Anytime;
import com.cfar.swim.worldwind.jaxb.scenario.CostPolicy;
import com.cfar.swim.worldwind.jaxb.scenario.DRRT;
import com.cfar.swim.worldwind.jaxb.scenario.Distribution;
import com.cfar.swim.worldwind.jaxb.scenario.Dynamic;
import com.cfar.swim.worldwind.jaxb.scenario.Errors;
import com.cfar.swim.worldwind.jaxb.scenario.Extension;
import com.cfar.swim.worldwind.jaxb.scenario.HRRT;
import com.cfar.swim.worldwind.jaxb.scenario.Heuristic;
import com.cfar.swim.worldwind.jaxb.scenario.Neighbors;
import com.cfar.swim.worldwind.jaxb.scenario.OADRRT;
import com.cfar.swim.worldwind.jaxb.scenario.OADStar;
import com.cfar.swim.worldwind.jaxb.scenario.Online;
import com.cfar.swim.worldwind.jaxb.scenario.Planner;
import com.cfar.swim.worldwind.jaxb.scenario.RRT;
import com.cfar.swim.worldwind.jaxb.scenario.RRTStar;
import com.cfar.swim.worldwind.jaxb.scenario.RiskPolicy;
import com.cfar.swim.worldwind.jaxb.scenario.Sampling;
import com.cfar.swim.worldwind.jaxb.scenario.Strategy;
import com.cfar.swim.worldwind.jaxb.scenario.ThetaStar;
import com.cfar.swim.worldwind.jaxb.scenario.Variant;
import com.cfar.swim.worldwind.planners.AnytimePlanner;
import com.cfar.swim.worldwind.planners.DynamicPlanner;
import com.cfar.swim.worldwind.planners.OnlinePlanner;
import com.cfar.swim.worldwind.planners.cgs.adstar.ADStarPlanner;
import com.cfar.swim.worldwind.planners.cgs.arastar.ARAStarPlanner;
import com.cfar.swim.worldwind.planners.cgs.astar.ForwardAStarPlanner;
import com.cfar.swim.worldwind.planners.cgs.oadstar.OADStarPlanner;
import com.cfar.swim.worldwind.planners.cgs.thetastar.ThetaStarPlanner;
import com.cfar.swim.worldwind.planners.rrt.adrrt.ADRRTreePlanner;
import com.cfar.swim.worldwind.planners.rrt.arrt.ARRTreePlanner;
import com.cfar.swim.worldwind.planners.rrt.brrt.RRTreePlanner;
import com.cfar.swim.worldwind.planners.rrt.drrt.DRRTreePlanner;
import com.cfar.swim.worldwind.planners.rrt.hrrt.HRRTreeAlgorithm;
import com.cfar.swim.worldwind.planners.rrt.hrrt.HRRTreePlanner;
import com.cfar.swim.worldwind.planners.rrt.hrrt.HRRTreeQualityVariant;
import com.cfar.swim.worldwind.planners.rrt.oadrrt.OADRRTreePlanner;
import com.cfar.swim.worldwind.planners.rrt.rrtstar.RRTreeStarPlanner;

/**
 * Adapts a planner.
 * 
 * @author Stephan Heinemann
 *
 */
public class PlannerAdapter extends XmlAdapter<Planner, com.cfar.swim.worldwind.planners.Planner> {
	
	/** the unmarshalled aircraft of this planner adapter */
	private final Aircraft aircraft;
	
	/** the unmarshalled environment of this planner adapter */
	private final Environment environnment;
	
	/**
	 * Constructs a new planner adapter from an unmarshalled planner.
	 * 
	 * @param planner the unmarshalled planner
	 */
	public PlannerAdapter(com.cfar.swim.worldwind.planners.Planner planner) {
		this.aircraft = planner.getAircraft();
		this.environnment = planner.getEnvironment();
	}
	
	/**
	 * Constructs a new planner adapter from an unmarshalled aircraft and
	 * environment.
	 * 
	 * @param aircraft the unmarshalled aircraft
	 * @param environment the unmarshalled environment
	 */
	public PlannerAdapter(Aircraft aircraft, Environment environment) {
		this.aircraft = aircraft;
		this.environnment = environment;
	}
	
	/**
	 * Unmarshals a planner.
	 * 
	 * @param planner the planner to be unmarshalled
	 * 
	 * @return the unmarshalled planner
	 * 
	 * @throws Exception if the planner cannot be unmarshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public com.cfar.swim.worldwind.planners.Planner unmarshal(Planner planner) throws Exception {
		com.cfar.swim.worldwind.planners.Planner unmarshalledPlanner = null;
		
		if (null != planner.getAstar()) {
			unmarshalledPlanner = new ForwardAStarPlanner(aircraft, environnment);
		} else if (null != planner.getThetastar()) {
			unmarshalledPlanner = new ThetaStarPlanner(aircraft, environnment);
		} else if (null != planner.getArastar()) {
			unmarshalledPlanner = new ARAStarPlanner(aircraft, environnment);
			PlannerAdapter.injectAnytime(planner.getArastar().getAnytime(), (AnytimePlanner) unmarshalledPlanner);
		} else if (null != planner.getAdstar()) {
			unmarshalledPlanner = new ADStarPlanner(aircraft, environnment);
			PlannerAdapter.injectAnytime(planner.getAdstar().getAnytime(), (AnytimePlanner) unmarshalledPlanner);
			PlannerAdapter.injectDynamic(planner.getAdstar().getDynamic(), (DynamicPlanner) unmarshalledPlanner);
		} else if (null != planner.getOadstar()) {
			unmarshalledPlanner = new OADStarPlanner(aircraft, environnment);
			PlannerAdapter.injectAnytime(planner.getOadstar().getAnytime(), (AnytimePlanner) unmarshalledPlanner);
			PlannerAdapter.injectDynamic(planner.getOadstar().getDynamic(), (DynamicPlanner) unmarshalledPlanner);
			PlannerAdapter.injectOnline(planner.getOadstar().getOnline(), (OnlinePlanner) unmarshalledPlanner);
		} else if (null != planner.getBrrt()) {
			unmarshalledPlanner = new RRTreePlanner(aircraft, environnment);
			PlannerAdapter.injectSampling(planner.getBrrt().getSampling(), (RRTreePlanner) unmarshalledPlanner);
		} else if (null != planner.getRrtstar()) {
			unmarshalledPlanner = new RRTreeStarPlanner(aircraft, environnment);
			PlannerAdapter.injectSampling(planner.getRrtstar().getSampling(), (RRTreePlanner) unmarshalledPlanner);
		} else if (null != planner.getHrrt()) {
			unmarshalledPlanner = new HRRTreePlanner(aircraft, environnment);
			PlannerAdapter.injectSampling(planner.getHrrt().getSampling(), (RRTreePlanner) unmarshalledPlanner);
			PlannerAdapter.injectHeuristic(planner.getHrrt().getHeuristic(), (HRRTreePlanner) unmarshalledPlanner);
			((HRRTreePlanner) unmarshalledPlanner).setNeighborLimit(planner.getHrrt().getNeighbors().getNeighborLimit().intValue());
		} else if (null != planner.getDrrt()) {
			unmarshalledPlanner = new DRRTreePlanner(aircraft, environnment);
			PlannerAdapter.injectSampling(planner.getDrrt().getSampling(), (RRTreePlanner) unmarshalledPlanner);
			PlannerAdapter.injectHeuristic(planner.getDrrt().getHeuristic(), (HRRTreePlanner) unmarshalledPlanner);
			PlannerAdapter.injectDynamic(planner.getDrrt().getDynamic(), (DynamicPlanner) unmarshalledPlanner);
			((DRRTreePlanner) unmarshalledPlanner).setNeighborLimit(planner.getDrrt().getNeighbors().getNeighborLimit().intValue());
		} else if (null != planner.getArrt()) {
			unmarshalledPlanner = new ARRTreePlanner(aircraft, environnment);
			PlannerAdapter.injectSampling(planner.getArrt().getSampling(), (RRTreePlanner) unmarshalledPlanner);
			PlannerAdapter.injectAnytime(planner.getArrt().getAnytime(), (AnytimePlanner) unmarshalledPlanner);
			((ARRTreePlanner) unmarshalledPlanner).setNeighborLimit(planner.getArrt().getNeighbors().getNeighborLimit().intValue());
		} else if (null != planner.getAdrrt()) {
			unmarshalledPlanner = new ADRRTreePlanner(aircraft, environnment);
			PlannerAdapter.injectSampling(planner.getAdrrt().getSampling(), (RRTreePlanner) unmarshalledPlanner);
			PlannerAdapter.injectAnytime(planner.getAdrrt().getAnytime(), (AnytimePlanner) unmarshalledPlanner);
			PlannerAdapter.injectDynamic(planner.getAdrrt().getDynamic(), (DynamicPlanner) unmarshalledPlanner);
			((ADRRTreePlanner) unmarshalledPlanner).setNeighborLimit(planner.getAdrrt().getNeighbors().getNeighborLimit().intValue());
		} else if (null != planner.getOadrrt()) {
			unmarshalledPlanner = new OADRRTreePlanner(aircraft, environnment);
			PlannerAdapter.injectSampling(planner.getOadrrt().getSampling(), (RRTreePlanner) unmarshalledPlanner);
			PlannerAdapter.injectAnytime(planner.getOadrrt().getAnytime(), (AnytimePlanner) unmarshalledPlanner);
			PlannerAdapter.injectDynamic(planner.getOadrrt().getDynamic(), (DynamicPlanner) unmarshalledPlanner);
			PlannerAdapter.injectOnline(planner.getOadrrt().getOnline(), (OnlinePlanner) unmarshalledPlanner);
			((OADRRTreePlanner) unmarshalledPlanner).setNeighborLimit(planner.getOadrrt().getNeighbors().getNeighborLimit().intValue());
		} else {
			throw new UnsupportedDataTypeException("unsupported planner");
		}
		
		unmarshalledPlanner.setCostPolicy(com.cfar.swim.worldwind.planning.CostPolicy.valueOf(planner.getCostPolicy().name()));
		unmarshalledPlanner.setRiskPolicy(com.cfar.swim.worldwind.planning.RiskPolicy.valueOf(planner.getRiskPolicy().name()));
		
		return unmarshalledPlanner;
	}
	
	/**
	 * Marshals a planner.
	 * 
	 * @param planner the planner to be marshalled
	 * 
	 * @return the marshalled planner
	 * 
	 * @throws Exception if the planner cannot be marshalled
	 * 
	 * @see XmlAdapter#unmarshal(Object)
	 */
	@Override
	public Planner marshal(com.cfar.swim.worldwind.planners.Planner planner) throws Exception {
		Planner marshalledPlanner = new Planner();
		
		marshalledPlanner.setCostPolicy(CostPolicy.fromValue(planner.getCostPolicy().name()));
		marshalledPlanner.setRiskPolicy(RiskPolicy.fromValue(planner.getRiskPolicy().name()));
		
		if (planner instanceof ForwardAStarPlanner) {
			marshalledPlanner.setAstar(new AStar());
		} else if (planner instanceof ThetaStarPlanner) {
			marshalledPlanner.setThetastar(new ThetaStar());
		} else if (planner instanceof ARAStarPlanner) {
			ARAStar araStar = new ARAStar();
			araStar.setAnytime(PlannerAdapter.extractAnytime((AnytimePlanner) planner));
			marshalledPlanner.setArastar(araStar);
		} else if (planner instanceof ADStarPlanner) {
			ADStar adStar = new ADStar();
			adStar.setAnytime(PlannerAdapter.extractAnytime((AnytimePlanner) planner));
			adStar.setDynamic(PlannerAdapter.extractDynamic((DynamicPlanner) planner));
			marshalledPlanner.setAdstar(adStar);
		} else if (planner instanceof OADStarPlanner) {
			OADStar oadStar = new OADStar();
			oadStar.setAnytime(PlannerAdapter.extractAnytime((AnytimePlanner) planner));
			oadStar.setDynamic(PlannerAdapter.extractDynamic((DynamicPlanner) planner));
			oadStar.setOnline(PlannerAdapter.extractOnline((OnlinePlanner) planner));
			marshalledPlanner.setOadstar(oadStar);
		} else if (planner instanceof RRTreePlanner) {
			RRT rrt = new RRT();
			rrt.setSampling(PlannerAdapter.extractSampling((RRTreePlanner) planner));
			marshalledPlanner.setBrrt(rrt);
		} else if (planner instanceof RRTreeStarPlanner) {
			RRTStar rrtstar = new RRTStar();
			rrtstar.setSampling(PlannerAdapter.extractSampling((RRTreePlanner) planner));
			marshalledPlanner.setRrtstar(rrtstar);
		} else if (planner instanceof HRRTreePlanner) {
			HRRT hrrt = new HRRT();
			hrrt.setSampling(PlannerAdapter.extractSampling((RRTreePlanner) planner));
			hrrt.setHeuristic(PlannerAdapter.extractHeuristic((HRRTreePlanner) planner));
			Neighbors neighbors = new Neighbors();
			neighbors.setNeighborLimit(BigInteger.valueOf(((HRRTreePlanner) planner).getNeighborLimit()));
			hrrt.setNeighbors(neighbors);
			marshalledPlanner.setHrrt(hrrt);
		} else if (planner instanceof DRRTreePlanner) {
			DRRT drrt = new DRRT();
			drrt.setSampling(PlannerAdapter.extractSampling((RRTreePlanner) planner));
			drrt.setDynamic(PlannerAdapter.extractDynamic((DynamicPlanner) planner));
			drrt.setHeuristic(PlannerAdapter.extractHeuristic((HRRTreePlanner) planner));
			Neighbors neighbors = new Neighbors();
			neighbors.setNeighborLimit(BigInteger.valueOf(((DRRTreePlanner) planner).getNeighborLimit()));
			drrt.setNeighbors(neighbors);
			marshalledPlanner.setDrrt(drrt);
		} else if (planner instanceof ARRTreePlanner) {
			ARRT arrt = new ARRT();
			arrt.setSampling(PlannerAdapter.extractSampling((RRTreePlanner) planner));
			arrt.setAnytime(PlannerAdapter.extractAnytime((AnytimePlanner) planner));
			Neighbors neighbors = new Neighbors();
			neighbors.setNeighborLimit(BigInteger.valueOf(((ARRTreePlanner) planner).getNeighborLimit()));
			arrt.setNeighbors(neighbors);
			marshalledPlanner.setArrt(arrt);
		} else if (planner instanceof ADRRTreePlanner) {
			ADRRT adrrt = new ADRRT();
			adrrt.setSampling(PlannerAdapter.extractSampling((RRTreePlanner) planner));
			adrrt.setAnytime(PlannerAdapter.extractAnytime((AnytimePlanner) planner));
			adrrt.setDynamic(PlannerAdapter.extractDynamic((DynamicPlanner) planner));
			Neighbors neighbors = new Neighbors();
			neighbors.setNeighborLimit(BigInteger.valueOf(((ADRRTreePlanner) planner).getNeighborLimit()));
			adrrt.setNeighbors(neighbors);
			marshalledPlanner.setAdrrt(adrrt);
		} else if (planner instanceof OADRRTreePlanner) {
			OADRRT oadrrt = new OADRRT();
			oadrrt.setSampling(PlannerAdapter.extractSampling((RRTreePlanner) planner));
			oadrrt.setAnytime(PlannerAdapter.extractAnytime((AnytimePlanner) planner));
			oadrrt.setDynamic(PlannerAdapter.extractDynamic((DynamicPlanner) planner));
			oadrrt.setOnline(PlannerAdapter.extractOnline((OnlinePlanner) planner));
			Neighbors neighbors = new Neighbors();
			neighbors.setNeighborLimit(BigInteger.valueOf(((ADRRTreePlanner) planner).getNeighborLimit()));
			oadrrt.setNeighbors(neighbors);
			marshalledPlanner.setOadrrt(oadrrt);
		} else {
			throw new UnsupportedDataTypeException("unsupported planner");
		}
		
		return marshalledPlanner;
	}
	
	/**
	 * Extracts anytime properties from an anytime planner.
	 * 
	 * @param planner the anytime planner
	 * 
	 * @return the extracted anytime properties
	 */
	private static Anytime extractAnytime(AnytimePlanner planner) {
		Anytime anytime = new Anytime();
		anytime.setMinimumQuality(planner.getMinimumQuality());
		anytime.setMaximumQuality(planner.getMaximumQuality());
		anytime.setQualityImprovement(planner.getQualityImprovement());
		return anytime;
	}
	
	/**
	 * Injects anytime properties into an anytime planner.
	 * 
	 * @param anytime the anytime properties to be injected
	 * 
	 * @param planner the anytime planner
	 */
	private static void injectAnytime(Anytime anytime, AnytimePlanner planner) {
		planner.setMinimumQuality(anytime.getMinimumQuality());
		planner.setMaximumQuality(anytime.getMaximumQuality());
		planner.setQualityImprovement(anytime.getQualityImprovement());
	}
	
	/**
	 * Extracts dynamic properties from a dynamic planner.
	 * 
	 * @param planner the dynamic planner
	 * 
	 * @return the extracted dynamic properties
	 */
	private static Dynamic extractDynamic(DynamicPlanner planner) {
		Dynamic dynamic = new Dynamic();
		dynamic.setSignificantChange(planner.getSignificantChange());
		return dynamic;
	}
	
	/**
	 * Injects dynamic properties into a dynamic planner.
	 * 
	 * @param dynamic the dynamic properties to be injected
	 * @param planner the dynamic planner
	 */
	private static void injectDynamic(Dynamic dynamic, DynamicPlanner planner) {
		planner.setSignificantChange(dynamic.getSignificantChange());
	}
	
	/**
	 * Extracts online properties from an online planner.
	 * 
	 * @param planner the online planner
	 * 
	 * @return the extracted online properties
	 * 
	 * @throws Exception if the online properties cannot be extracted
	 */
	private static Online extractOnline(OnlinePlanner planner) throws Exception {
		Online online = new Online();
		online.setMinDeliberation(new DurationAdapter().marshal(planner.getMinDeliberation()));
		online.setMaxDeliberation(new DurationAdapter().marshal(planner.getMaxDeliberation()));
		Errors errors = new Errors();
		errors.setMaxTrackError(new TrackErrorAdapter().marshal(planner.getMaxTrackError()));
		errors.setMaxTakeOffError(new TrackPointErrorAdapter().marshal(planner.getMaxTakeOffError()));
		errors.setMaxLandingError(new TrackPointErrorAdapter().marshal(planner.getMaxLandingError()));
		online.setErrors(errors);
		return online;
	}
	
	/**
	 * Injects online properties into an online planner.
	 * 
	 * @param online the online properties to be injected
	 * @param planner the online planner
	 * 
	 * @throws Exception if the online properties cannot be injected
	 */
	private static void injectOnline(Online online, OnlinePlanner planner) throws Exception {
		if (null != online.getMinDeliberation()) {
			planner.setMinDeliberation(new DurationAdapter().unmarshal(online.getMinDeliberation()));
		}
		if (null != online.getMaxDeliberation()) {
			planner.setMaxDeliberation(new DurationAdapter().unmarshal(online.getMaxDeliberation()));
		}
		planner.setMaxTrackError(new TrackErrorAdapter().unmarshal(online.getErrors().getMaxTrackError()));
		planner.setMaxTakeOffError(new TrackPointErrorAdapter().unmarshal(online.getErrors().getMaxTakeOffError()));
		planner.setMaxLandingError(new TrackPointErrorAdapter().unmarshal(online.getErrors().getMaxLandingError()));
	}
	
	/**
	 * Extracts sampling properties from a RRT planner.
	 * 
	 * @param planner the RRT planner
	 * 
	 * @return the extracted sampling properties
	 */
	private static Sampling extractSampling(RRTreePlanner planner) {
		Sampling sampling = new Sampling();
		sampling.setBias(BigInteger.valueOf(planner.getBias()));
		sampling.setDistribution(Distribution.fromValue(planner.getSampling().name()));
		sampling.setEpsilon(planner.getEpsilon());
		sampling.setExtension(Extension.fromValue(planner.getExtension().name()));
		sampling.setGoalThreshold(planner.getGoalThreshold());
		sampling.setMaxIterations(BigInteger.valueOf(planner.getMaxIterations()));
		sampling.setStrategy(Strategy.fromValue(planner.getStrategy().name()));
		return sampling;
	}
	
	/**
	 * Injects sampling properties into a RRT planner.
	 * 
	 * @param sampling the sampling properties to be injected
	 * @param planner the RRT planner
	 */
	private static void injectSampling(Sampling sampling, RRTreePlanner planner) {
		planner.setBias(sampling.getBias().intValue());
		planner.setSampling(com.cfar.swim.worldwind.planners.rrt.Sampling.valueOf(sampling.getDistribution().name()));
		planner.setEpsilon(sampling.getEpsilon());
		planner.setExtension(com.cfar.swim.worldwind.planners.rrt.Extension.valueOf(sampling.getExtension().name()));
		planner.setGoalThreshold(sampling.getGoalThreshold());
		planner.setMaxIterations(sampling.getMaxIterations().intValue());
		planner.setStrategy(com.cfar.swim.worldwind.planners.rrt.Strategy.valueOf(sampling.getStrategy().name()));
	}
	
	/**
	 * Extracts heuristic properties from a heuristic RRT planner.
	 * 
	 * @param planner the heuristic RRT planner
	 * 
	 * @return the extracted heuristic properties
	 */
	private static Heuristic extractHeuristic(HRRTreePlanner planner) {
		Heuristic heuristic = new Heuristic();
		heuristic.setAlgorithm(Algorithm.fromValue(planner.getAlgorithm().name()));
		heuristic.setQualityBound(planner.getQualityBound());
		heuristic.setVariant(Variant.fromValue(planner.getVariant().name()));
		return heuristic;
	}
	
	/**
	 * Injects heuristic properties into a heuristic RRT planner.
	 * 
	 * @param heuristic the heuristic properties to be injected
	 * @param planner the heuristic RRT planner
	 */
	private static void injectHeuristic(Heuristic heuristic, HRRTreePlanner planner) {
		planner.setAlgorithm(HRRTreeAlgorithm.valueOf(heuristic.getAlgorithm().name()));
		planner.setQualityBound(heuristic.getQualityBound());
		planner.setVariant(HRRTreeQualityVariant.valueOf(heuristic.getVariant().name()));
	}
	
}
