var errors = document.getElementById('numErr').innerHTML + '';
errors = errors.split(" ");
errors = errors[1];

var ptProb = document.getElementById('numPot').innerHTML + '';
ptProb = ptProb.split(" ");
ptProb = ptProb[2];

var lProb = document.getElementById('numLik').innerHTML + '';
lProb = lProb.split(" ");
lProb = lProb[2];

var dist = [
            ['Errors', errors],
            ['Potential Problems', ptProb],
            ['Likely Problems', lProb]
        ];

var eCol = [];
var pCol = [];
var lCol = [];


if(errors > 0)
{
	var eVal = document.getElementById('errTable');
	console.log("error val", eVal);
	for(i = 0; i < eVal.childElementCount; i++)
	{
		var toAdd = [];
		//Was errTable. before
		toAdd.push(eVal.childNodes[i].innerHTML + '');
		var inst = eVal.childNodes[i+1].childNodes[0].childNodes[3].innerHTML + '';
		inst = inst.split(" ");
		inst = inst[1];
		toAdd.push(parseInt(inst));
		console.log("toadd", toAdd);
		eCol.push(toAdd);
		i++;
	}
}

if(ptProb > 0)
{
	var pVal = document.getElementById('pTable');
	console.log("pot val", pVal);
	for(i = 0; i < pVal.childElementCount; i++)
	{
		var toAdd = [];
		toAdd.push(pVal.childNodes[i].innerHTML + '');
		var inst = pVal.childNodes[i+1].childNodes[0].childNodes[3].innerHTML + '';
		inst = inst.split(" ");
		inst = inst[1];
		toAdd.push(parseInt(inst));
		console.log("toadd", toAdd);
		pCol.push(toAdd);
		i++;
	}
}

if(lProb > 0)
{
	var lVal = document.getElementById('lTable');
	console.log("likely val", lVal);
	for(i = 0; i < lVal.childElementCount; i++)
	{
		var toAdd = [];
		toAdd.push(lVal.childNodes[i].innerHTML + '');
		var inst = lVal.childNodes[i+1].childNodes[0].childNodes[3].innerHTML + '';
		inst = inst.split(" ");
		inst = inst[1];
		toAdd.push(parseInt(inst));
		console.log("toadd", toAdd);
		lCol.push(toAdd);
		i++;
	}
}

var chart = c3.generate({
    bindto: '#chart',
	data: {
        columns: dist,
        type : 'pie',
        onclick: function(d,i){step(d);}//,
        //onclick: function (d, i) { console.log("onclick", d, i); },
        //onmouseover: function (d, i) { console.log("onmouseover", d, i); },
        //onmouseout: function (d, i) { console.log("onmouseout", d, i); }
    }
});

function step(d)
{
	if(d.id == "Errors")
	{
		console.log("eCol",eCol);
		
		chart.load({
			bindto: '#chart',
			columns: eCol,
			//unload: ['Errors','Potential Problems','Likely Problems']
		});
		
		chart.unload(
				['Errors','Potential Problems','Likely Problems'],
				50
				);
	}else if(d.id == "Potential Problems")
	{
		console.log("pCol",pCol);
		
		chart.load({
			bindto: '#chart',
			columns: pCol,
			
		});
		
		chart.unload(
			['Errors','Potential Problems','Likely Problems']
		,50);
	}else if(d.id == "Likely Problems")
	{
		console.log("lCol",lCol);
		
		chart.load({
			bindto: '#chart',
			columns: lCol,
			
		});
		
		chart.unload(
			['Errors','Potential Problems','Likely Problems']
		,50);
	}
}
