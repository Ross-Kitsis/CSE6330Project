var errors = document.getElementById('numErr').innerHTML + '';
errors = errors.split(" ");
errors = errors[1];

var ptProb = document.getElementById('numPot').innerHTML + '';
ptProb = ptProb.split(" ");
ptProb = ptProb[2];

var lProb = document.getElementById('numLik').innerHTML + '';
lProb = lProb.split(" ");
lProb = lProb[2];

var eCol = [];

if(errors > 0)
{
	var eVal = document.getElementById('errTable');
	console.log("error val", eVal);
	for(i = 0; i < eVal.childElementCount; i++)
	{
		var toAdd = [];
		toAdd.push(errTable.childNodes[i].innerHTML + '');
		var inst = errTable.childNodes[i+1].childNodes[0].childNodes[3].innerHTML + '';
		inst = inst.split(" ");
		inst = inst[1];
		toAdd.push(parseInt(inst));
		console.log("toadd", toAdd);
		eCol.push(toAdd);
		i++;
	}
}

var chart = c3.generate({
    bindto: '#chart',
	data: {
        columns: [
            ['Errors', errors],
            ['Potential Problems', ptProb],
            ['Likely Problems', lProb]
        ],
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
			unload: ['Errors','Potential Problems','Likely Problems']
		});
		
		/*
		chart.unload({
			ids: ['Errors','Potential Problems','Likely Problems']
		});
		*/

	}
}