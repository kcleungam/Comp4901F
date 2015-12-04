function f(n){
	var path;
	if(n==1)
		path="data/FearlessLeader_WithBoolean_0.csv";
	else
		path="data/FearlessLeader_WithBoolean_1.csv";
d3.csv(path, function(data) {
    console.log(data);
    var dataobj = { children: data };
    var pack = d3.layout.pack().padding(2).size([800,600]).sort(function(a,b) { return b.count - a.count; })
        .value(function(d) { return d.count; });
    //pack = pack.padding(2).size([800,600]).sort(function(a,b) { return b.count - a.count; });
    var nodes = pack.nodes(dataobj);
    nodes = nodes.filter(function(it) { return it.parent; });
    var color = d3.scale.category10();
    d3.select("#root > svg")
        .selectAll("circle")
        //.data(nodes)
        //.enter()
        //.append("circle")
        //.attr("r",function(d){return d})
        //.attr("transform", function(d){return "translate("+d.x+","+d.y+")";})// 建立 circle 的 Selection
        .data(nodes)                         // 綁定 selection 與資料
        .enter()                             // 對於任何沒被對應而落單的資料 ...
        .append("circle")                    // 新增一個 circle 標籤
        .attr({
            cx: function(it) { return it.x; }, // 用 x,y 當圓心
            cy: function(it) { return it.y; },
            r : function(it) { return it.r; }, // 用 r 當半徑
            fill: function(it) { return color(it.status); },
            stroke: "#444",                    // 邊框畫深灰色
        });

    d3.select("#root > svg").selectAll("text").data(nodes).enter()
        .append("text")
        .attr({
            x: function(it) { return it.x; },
            y: function(it) { return it.y; },
            "text-anchor": "middle",                    // 文字水平置中
        }).text(function(it)  { return (it.count>0?it.id:""); }); // 設定文字為國名

});/**
 * Created by RickyLo on 1/12/2015.
 */
}