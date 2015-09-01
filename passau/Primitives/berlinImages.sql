SELECT 
  imagefiltred.id, 
  imagetagfiltred.tag 
FROM 
  gettytag, 
  imagefiltred, 
  imagetagfiltred
WHERE 
  imagefiltred.id = imagetagfiltred.imageid AND
  gettytag.text = imagetagfiltred.tag AND
  gettytag.gettytag = true AND
  imagefiltred.lat >= 50 AND 
  imagefiltred.lat <= 55 AND 
  imagefiltred.lon >= 5 AND 
  imagefiltred.lon <= 1;