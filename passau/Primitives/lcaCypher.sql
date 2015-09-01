MATCH path=(node1 {uri: URI_NODE1})-[:PARENT|:EQUIV*]->
lca
<-[:PARENT|:EQUIV*]-(node2 {uri:URI_NODE2})
RETURN lca 
ORDER BY length(path)
LIMIT 1