package discs


object Visualise {
  def generateHtml(points: List[Point], discs: List[Disc], totalArea: Double): String = {
    val pointEls = points.map { point =>
      s"""<circle class="point" cx='${point.x}' cy='${1000 - point.y}' r='2' fill='#0A2342' stroke="#0A2342"
         |data-label='${point.label}' data-radius='-' data-position-x='x = ${point.x}' data-position-y='y = ${point.y}' data-pinned="false"
         |onmouseover="highlight(this);"
         |onmouseout="unHighlight(this);"
         |onclick="togglePin(this);"
         |/>""".stripMargin
    }
    val discEls = discs.map { disc =>
      s"""<circle class="disc" cx='${disc.centre.x}' cy='${1000 - disc.centre.y}' r='${disc.radius}'
         |data-label='${disc.centre.label}' data-radius='${disc.radius}' data-position-x='x = ${disc.centre.x}' data-position-y='y = ${disc.centre.y}' data-pinned="false"
         |onmouseover="highlight(this);"
         |onmouseout="unHighlight(this);"
         |onclick="togglePin(this);"
         |fill='#FBBE4B' fill-opacity="0.4" stroke="#FBBE4B" stroke-width="1" />""".stripMargin
    }
    s"""<html>
       |<head>
       |  <title>Discs answer</title>
       |  <style>
       |    html {
       |      font-family: sans-serif;
       |      background-color: #FBBE4B;
       |      color: #0A2342;
       |    }
       |    input.pinned-disc-info {
       |      padding: 5px;
       |      border: solid 1px #006666;
       |      background-color: #FFFDF7;
       |      width: 400px;
       |      margin-top: 1px;
       |    }
       |    input.pinned-disc-info.xy {
       |      width: 198px;
       |      margin-right: 4px;
       |    }
       |    label {
       |      display: block;
       |      float: left;
       |      min-width: 85px;
       |      padding-top: 5px;
       |    }
       |    .answer {
       |      padding: 5px;
       |      font-family: monospace;
       |      background-color: #2CA58D;
       |      color: #FFFDF7;
       |      font-weight: bold;
       |    }
       |  </style>
       |  <script>
       |    function highlight(el) {
       |      if (el.getAttribute('data-pinned') !== 'true') {
       |        el.setAttribute('fill', '#2CA58D');
       |        el.setAttribute('stroke', '#2CA58D');
       |      }
       |    }
       |    function unHighlight(el) {
       |      var colour = el.classList.contains("disc") ? "#FBBE4B" : "#0A2342";
       |      if (el.getAttribute('data-pinned') !== 'true') {
       |        el.setAttribute('fill', colour);
       |        el.setAttribute('stroke', colour);
       |      }
       |    }
       |    function togglePin(el) {
       |      document.querySelector('[name=current-label]').value = '';
       |      document.querySelector('[name=current-radius]').value = '';
       |      document.querySelector('[name=current-position-x]').value = '';
       |      document.querySelector('[name=current-position-y]').value = '';
       |      console.log(el, el.getAttribute('data-pinned'));
       |      if (el.getAttribute('data-pinned') == 'true') {
       |        unPinAll();
       |      } else {
       |        unPinAll();
       |        el.setAttribute('data-pinned', 'true');
       |        el.setAttribute('fill', '#DD442C');
       |        el.setAttribute('stroke', '#DD442C');
       |        document.querySelector('[name=current-label]').value = el.getAttribute("data-label");
       |        document.querySelector('[name=current-radius]').value = el.getAttribute("data-radius");
       |        document.querySelector('[name=current-position-x]').value = el.getAttribute("data-position-x");
       |        document.querySelector('[name=current-position-y]').value = el.getAttribute("data-position-y");
       |      }
       |    }
       |    function unPin(el) {
       |      var colour = el.classList.contains("disc") ? "#FBBE4B" : "#0A2342";
       |      el.setAttribute('data-pinned', 'false');
       |      el.setAttribute('fill', colour);
       |      el.setAttribute('stroke', colour);
       |    }
       |    function unPinAll() {
       |      document.querySelector('.pinned-disc-info').value = '';
       |      [].forEach.call(document.querySelectorAll("circle"), function(el) {
       |        unPin(el);
       |      });
       |    }
       |  </script>
       |</head>
       |<body>
       |  <p>
       |    <div>
       |      <label for="current-label">Label</label>
       |      <input type="text" class="pinned-disc-info" name="current-label" value=""
       |        placeholder="Click on a disc or point below to see its information" />
       |    </div>
       |    <div>
       |      <label for="current-label">Radius</label>
       |      <input type="text" class="pinned-disc-info" name="current-radius" value="" />
       |    </div>
       |    <div>
       |      <label for="current-position-x">Position</label>
       |      <input type="text" class="pinned-disc-info xy" name="current-position-x" value="" /><input type="text" class="pinned-disc-info xy" name="current-position-y" value="" />
       |    </div>
       |    <div>
       |      <p>Total area covered:
       |        <span class="answer">$totalArea</span>
       |      </p>
       |      <p>Discs used:
       |        <span class="answer">${discs.length} / 50</span>
       |      </p>
       |    </div>
       |  </p>
       |  <svg style="margin: 10px; background-colour: #FFFDF7; border: solid 1px #0A2342;" height="1000" width="1000">
       |    <rect width="100%" height="100%" fill="#FFFDF7"/>
       |    ${discEls.mkString("\n")}
       |    ${pointEls.mkString("\n")}
       |  </svg>
       |</body>
       |</html>
     """.stripMargin
  }
}
