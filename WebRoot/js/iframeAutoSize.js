function IFrameReSizeWidth(iframename){
	var pTar = document.getElementById(iframename);
	if(pTar){
		if(pTar.contentDocument&&pTar.contentDocument.body.offsetWidth){
			pTar.width=pTar.contentDocument.body.offsetWidth;
		}else if(pTar.document&&pTar.Document.body.scrollWidth){
			pTar.width=pTar.Document.body.scrollWidth;
		}
	}
	
}
