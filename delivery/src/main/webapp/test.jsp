<!DOCTYPE html>
<html>
 <head> 
  <title>Test of delivery server</title> 
  <link href="scripts/bootstrap.min.css" rel="stylesheet" /> 
  <script src="scripts/jquery.min.js"></script> 
  <script src="scripts/bootstrap.min.js"></script> 
  <script src="scripts/test.js"></script> 
 </head> 
 <body> 
 <div id="warningDivId" style="opacity:0; text-align:center;" class="navbar-fixed-top alert alert-warning">This execution can only be conducted when server is deployed. The static webpage on GitHub is only for showing the end front. Thank you for understanding.</div>
  <div class="container" style="margin-top:30px; margin-left:auto; margin-right:auto; width:60%;"> 
   <div style="margin-left:auto; margin-right:auto; text-align:center"> 
    <h2>Test of delivery server</h2> 
   </div>
   <hr />
   <!-- interface choose -->  
   <div id="interfaceChooseId" class="col-lg-9"> 
    <div class="input-group"> 
     <div class="input-group-btn"> 
      <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"> Choose an interface <span class="caret"></span> </button> 
      <ul class="dropdown-menu"> 
      </ul>
     </div> 
     <input type="text" disabled="true" class="form-control" /> 
    </div> 
   </div>
   <!-- end of interface choose -->
   <br />
   <hr />
   <table class="table">   
    <thead> 
     <tr> 
      <th>delete this row</th> 
      <th>key of get params</th> 
      <th>value of get params</th> 
     </tr> 
    </thead> 
    <tbody> 
    </tbody> 
   </table>
   <button type="button" id="addButtonId" class="btn btn-primary">add a new row</button>&nbsp;&nbsp;&nbsp;
   <button type="button" id="submitButtonId" class="btn btn-primary">submit</button>
   <br><br><code id="urlCodeId">This is url</code>
   <pre id="resultId" style="margin-top:40px;">This is the result</pre>
  </div>  
 </body>
</html>