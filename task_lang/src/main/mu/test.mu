{
a = true;
b = false;
c = 4;
d = 3;

while (d > 0) {
	if a {
  		log (c+d)*2;
	}
	else {
  		log "goodbye";
	}
	log "yeah!";
	break;
	d = d-1;
}

log "Done!";
}