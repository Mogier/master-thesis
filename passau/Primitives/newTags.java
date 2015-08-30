public void findNewTags(ExecutionEngine engine){
	// Init
	HashMap<Node,BFSTraverser> traversers = new HashMap<Node, BFSTraverser>();
	HashMap<Node, ArrayList<Node>> ilots = new HashMap<Node, ArrayList<Node>>();
	ArrayList<PairNodeScore> tagsCandidats = new ArrayList<PairNodeScore>();
	boolean tousParcoursTermines = false;
	for(Node tagNode : RunExperiments.nodes.values()){
		traversers.put(tagNode, new BFSTraverser(tagNode));
		ilots.put(tagNode, new ArrayList<Node>());
	}	
	// Parcours
	Transaction tx = RunExperiments.graphDb.beginTx();
	try {
		while(tagsCandidats.size()<nbCandidates && !tousParcoursTermines){
			tousParcoursTermines=true;
			for(BFSTraverser traverser : traversers.values()){
				if(traverser.hasNext()){
					Node currentNode = traverser.next();
					Node currentRoot = traverser.getRootNode();
					ilots.get(currentRoot).add(currentNode);					
					if(!containsNode(currentNode, tagsCandidats)){
						List<Node> occurences = intersection(ilots, currentNode);
						if(occurences.size()>1){
							PairNodeScore p = new PairNodeScore(currentNode, 0.0);
							if(!RunExperiments.nodes.containsKey(p.getLabel())){
								double globalScore = computeScores(currentNode,ilots, engine);
								p.setScore(globalScore);
								tagsCandidats.add(p);
							}
						}
					}
					if(traverser.hasNext())
						tousParcoursTermines=false;
				}
			}
		}
		tx.success();
	} finally {
		tx.close();			
	}
	candidates = tagsCandidats;
	Collections.sort(candidates);
}