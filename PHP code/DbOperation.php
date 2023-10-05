<?php

	define('DB_HOST', 'localhost');
	define('DB_USER', 'accezukn_accez');
	define('DB_PASS', 'umj67eiE3-U3');
	define('DB_NAME', 'accezukn_noskheha');

	class DbOperation{
		
		private $connection;
		function __construct()
		{
			$this->connection = new mysqli(DB_HOST,DB_USER,DB_PASS,DB_NAME);
			if(mysqli_connect_errno()){
				echo "Failed to connect to MySQL: ".mysqli_connect_error();
			}
			mysqli_query($this->connection,"SET NAMES 'utf8'");
			return $this -> connection;
		}

		function GetSetting(){
			$response = array();
			//$id = '2';
			$stmt = $this -> connection -> prepare(" SELECT * FROM setting ");
			$stmt -> bind_result($id,$a,$b,$c);
			$stmt -> execute();
			
			$settings = array();
			while($stmt -> fetch()){
				$setting = array();
				
				$setting['id'] = $id;
				$setting['a']  = $a;
				$setting['b']  = $b;
				$setting['c']  = $c;
				
				array_push($settings,$setting);
			}
			
			$response['setting'] = $settings;
			return $response;
			
				//$response['error'] = false;
				//$response['version'] = $data;	
				//$response['message'] = $message;
			
			//$response['error'] = true;
			//$response['version'] = 'error';
			//$response['message'] = 'no exist';	
			//return $response;
		}
		
		//############################################################################
		
		function AllBook(){
			include 'db.php';
			try{
				$sql = 'delete from noskheha';
				$s = $pdo -> prepare($sql);
				$s -> execute();
				
				$sql = 'ALTER TABLE noskheha AUTO_INCREMENT = 1';
				$s = $pdo -> prepare($sql);
				$s -> execute();
			}
			catch(PDOException $e){
				$error = 'Error noskheha';
				exit();
			}
			
			$dir    = '../noskheha/archive';
			$names = scandir($dir);
			
			$count = sizeof($names);
    
			for($i = 2; $i < $count ; $i++){
		
				$stmt = $this -> connection -> prepare("INSERT INTO noskheha
				(name)VALUES(?)");
				$stmt -> bind_param("s",$names[$i]);
				$stmt -> execute();
			}
		}
		
		function EslaheAlefbayeTohid(){
			include 'db.php';
			try{
				$sql = 'delete from eslahe_alefbaye_tohid';
				$s = $pdo -> prepare($sql);
				$s -> execute();
				
				$sql = 'ALTER TABLE eslahe_alefbaye_tohid AUTO_INCREMENT = 1';
				$s = $pdo -> prepare($sql);
				$s -> execute();
			}
			catch(PDOException $e){
				$error = 'Error eslahe_alefbaye_tohid';
				exit();
			}
			
			$dir    = '../noskheha/eslahe_alefbaye_tohid';
			$names  = scandir($dir);
			
			$count  = sizeof($names);
    
			for($i = 2; $i < $count ; $i++){
		
				$stmt = $this -> connection -> prepare("INSERT INTO eslahe_alefbaye_tohid (name)VALUES(?)");
				$stmt -> bind_param("s",$names[$i]);
				$stmt -> execute();
			}
		}
		
		function TahrimeSokhan(){
			include 'db.php';
			try{
				$sql = 'delete from tahrime_sokhan';
				$s = $pdo -> prepare($sql);
				$s -> execute();
				
				$sql = 'ALTER TABLE tahrime_sokhan AUTO_INCREMENT = 1';
				$s = $pdo -> prepare($sql);
				$s -> execute();
			}
			catch(PDOException $e){
				$error = 'Error tahrime_sokhan';
				exit();
			}
			
			$dir    = '../noskheha/tahrime_sokhan';
			$names  = scandir($dir);
			
			$count  = sizeof($names);
    
			for($i = 2; $i < $count ; $i++){
		
				$stmt = $this -> connection -> prepare("INSERT INTO tahrime_sokhan (name)VALUES(?)");
				$stmt -> bind_param("s",$names[$i]);
				$stmt -> execute();
			}
		}
		
		function Motefareghe(){
			include 'db.php';
			try{
				$sql = 'delete from motefareghe';
				$s = $pdo -> prepare($sql);
				$s -> execute();
				
				$sql = 'ALTER TABLE motefareghe AUTO_INCREMENT = 1';
				$s = $pdo -> prepare($sql);
				$s -> execute();
			}
			catch(PDOException $e){
				$error = 'Error motefareghe';
				exit();
			}
			
			$dir    = '../noskheha/motefareghe';
			$names  = scandir($dir);
			
			$count  = sizeof($names);
    
			for($i = 2; $i < $count ; $i++){
		
				$stmt = $this -> connection -> prepare("INSERT INTO motefareghe (name)VALUES(?)");
				$stmt -> bind_param("s",$names[$i]);
				$stmt -> execute();
			}
		}
		
		function MatneFarmayeshat(){
			include 'db.php';
			try{
				$sql = 'delete from matne_farmayeshat';
				$s = $pdo -> prepare($sql);
				$s -> execute();
				
				$sql = 'ALTER TABLE matne_farmayeshat AUTO_INCREMENT = 1';
				$s = $pdo -> prepare($sql);
				$s -> execute();
			}
			catch(PDOException $e){
				$error = 'Error matne_farmayeshat';
				exit();
			}
			
			$dir    = '../noskheha/matne_farmayeshat';
			$names  = scandir($dir);
			
			$count  = sizeof($names);
    
			for($i = 2; $i < $count ; $i++){
		
				$stmt = $this -> connection -> prepare("INSERT INTO matne_farmayeshat (name)VALUES(?)");
				$stmt -> bind_param("s",$names[$i]);
				$stmt -> execute();
			}
		}
		
		function Music(){
			include 'db.php';
			try{
				$sql = 'delete from music';
				$s = $pdo -> prepare($sql);
				$s -> execute();
				
				$sql = 'ALTER TABLE music AUTO_INCREMENT = 1';
				$s = $pdo -> prepare($sql);
				$s -> execute();
			}
			catch(PDOException $e){
				$error = 'Error Music';
				exit();
			}
			$dir    = '../noskheha/music';
			$names  = scandir($dir);
			
			$count  = sizeof($names);
    
			for($i = 2; $i < $count ; $i++){
		
				$stmt = $this -> connection -> prepare("INSERT INTO music (name)VALUES(?)");
				$stmt -> bind_param("s",$names[$i]);
				$stmt -> execute();
			}
		}
		
		//##########################################################################
		
		function GetList(){
			$stmt = $this -> connection -> prepare("SELECT * FROM noskheha");
			$stmt -> execute();
			$stmt -> bind_result($id,$name);
			$users = array();
			while($stmt->fetch()){
				$user = array();
				$user['id'] = $id;
				$user['name'] = $name;
				array_push($users,$user);
			}
			return $users;
		}
		
		function GetAllListEAT(){
			$stmt = $this -> connection -> prepare("SELECT * FROM eslahe_alefbaye_tohid");
			$stmt -> execute();
			$stmt -> bind_result($id,$name);
			$items = array();
			while($stmt->fetch()){
				$item = array();
				$item['id'] = $id;
				$item['name'] = $name;
				array_push($items,$item);
			}
			return $items;
		}
		
		function GetAllListTahrameSokhan(){
			$stmt = $this -> connection -> prepare("SELECT * FROM tahrime_sokhan");
			$stmt -> execute();
			$stmt -> bind_result($id,$name);
			$items = array();
			while($stmt->fetch()){
				$item = array();
				$item['id'] = $id;
				$item['name'] = $name;
				array_push($items,$item);
			}
			return $items;
		}
		
		function GetAllListMotefareghe(){
			$stmt = $this -> connection -> prepare("SELECT * FROM motefareghe");
			$stmt -> execute();
			$stmt -> bind_result($id,$name);
			$items = array();
			while($stmt->fetch()){
				$item = array();
				$item['id'] = $id;
				$item['name'] = $name;
				array_push($items,$item);
			}
			return $items;
		}
		
		function GetAllListMusic(){
			$stmt = $this -> connection -> prepare("SELECT * FROM music");
			$stmt -> execute();
			$stmt -> bind_result($id,$name);
			$items = array();
			while($stmt->fetch()){
				$item = array();
				$item['id'] = $id;
				$item['name'] = $name;
				array_push($items,$item);
			}
			return $items;
		}
		
		// جستجو
		function Query($query){
			$response = array();

			$qry = "'".'%'.$query.'%'."'";
			$stmt = $this -> connection -> prepare("SELECT * FROM noskheha WHERE name LIKE $qry");
			$stmt -> bind_result($id,$name);
			
			if($stmt -> execute()){
				$users = array();
				while($stmt -> fetch()){
					$user = array();		
					$user['id'] = $id;	
					$user['name']  = $name;		
		
					array_push($users,$user);
				}
				
				$response['result'] = $users;
				
				//$response['result'] = $users;
				return $response;
				
				
			}else{
				$response ['error'] = true;
				$response ['message'] = 'error 0';
			}
			return $response;
		}
		
		function QueryTadrisVaTavasol(){
			$response        = array();
			$items           = array();
			$tadrisVaTavasol = array();
			
			$tadrisVaTavasol = ['6572','6571','6570','6565','6564','6558','6557','6556','6551','6550','6544','6543','6542','6536','6535','6529','6528','6527','6522','6521','6514','6513','6512','6506','6505','6499','6498','6497','6490','6489','6483','6482','6481','6475','6474','6467','6466','6465','6459','6458','6451','6450','6449','6443','6442','6435','6434','6433','6427','6426','6419','6418','6417','6411','6410','6404','6403','6402','6397','6396','6389','6388','6387','6381','6380','6374','6373','6372','6367','6366','6360','6359','6358','6354','6353','6347','6346','6342','6341','6338','6337','6336','6331','6330','6329','6324','6323','6317','6316','6315','6310','6309','6303','6302','6301','6296','6295','6289','6288','6287'
							   ,'6282','6281','6275','6274','6273','6268','6267','6261','6260','6259','6254','6253','6247','6246','6245','6240','6239','6233','6232','6231','6226','6225','6219','6218','6217','6212','6211','6205','6204','6203','6198','6197','6191','6190','6189','6184','6183','6177','6176','6175','6170','6169','6164','6163','6162','6157','6156','6150','6149','6148','6143','6142','6136','6135','6134','6129','6128','6122','6121','6120','6115','6114','6108','6107','6106','6101','6100','6094','6093','6092','6087','6086','6080','6079','6078','6073','6072','6066','6065','6064','6059','6058','6052','6051','6050','6045','6044','6038','6037','6036','6031','6030','6024','6023','6022','6017','6010','6009','6008','6003'
							   ,'6002','5996','5995','5994','5989','5988','5981','5980','5979','5975','5974','5968','5967','5966','5961','5960','5954','5953','5952','5947','5946','5941','5940','5939','5934','5933','5927','5926','5925','5920','5919','5913','5912','5911','5906','5905','5899','5898','5897','5892','5891','5885','5884','5883','5878','5877','5871','5870','5869','5864','5863','5862','5857','5856','5850','5849','5843','5842','5841','5836','5835','5829','5828','5827','5821','5820','5816','5813','5812','5811','5805','5804','5798','5797','5795','5792','5789','5788','5784','5781','5780','5779','5775','5773','5772','5769','5766','5765','5764','5760','5757','5756','5752','5749','5748','5747','5741','5740','5737','5734'
							   ,'5733','5732','5729','5726','5725','5723','5719','5717','5716','5715','5713','5709','5707','5706','5704','5699','5698','5697','5695','5690','5689','5687','5683','5681','5680','5679','5678','5674','5672','5671','5669','5667','5664','5663','5662','5660','5654','5653','5651','5649','5648','5647','5646','5644','5640','5638','5637','5635','5632','5631','5630','5629','5627','5624','5621','5620','5618','5615','5612','5611','5610','5608','5604','5602','5601','5599','5596','5593','5592','5591','5589','5585','5583','5582','5580','5577','5576','5575','5573','5570','5567','5566','5564','5560','5559','5558','5557','5555','5553','5552','5550','5549','5547','5544','5543','5542','5541','5539','5536','5533'
							   ,'5532','5530','5828','5826','5825','5824','5823','5521','5519','5516','5515','5513','5510','5508','5507','5506','5505','5503','5500','5497','5496','5494','5491','5489','5488','5487','5486','5484','5481','5478','5477','5475','5472','5470','5469','5468','5467','5465','5462','5459','5458','5456','5453','5451','5450','5449','5448','5446','5443','5439','5438','5436','5433','5432','5430','5429','5428','5426','5423','5420','5419','5417','5415','5414','5412','5411','5410','5409','5407','5404','5401','5400','5398','5396','5394','5393','5392','5391','5389','5386','5385','5383','5382','5380','5378','5377','5376','5375','5374','5373','5371','5369','5368','5367','5366','5364','5361','5360','5358','5357'
							   ,'5356','5354','5351','5348','5347','5345','5342','5341','5339','5338','5337','5335','5332','5330','5329','5328','5326','5323','5321','5320','5319','5318','5316','5314','5313','5310','5309','5307','5304','5301','5300','5299','5297','5294','5292','5291','5289','5287','5286','5285','5284','5283','5282','5280','5287','5277','5276','5274','5273'
							   ,'2841','2819','2804','2789','2773','2758','2741','2729','2704','2690','2675','2657','2638','2618','2599','2581','2534','2502','2481','2460','2440','2418','2392'];
			$length = count($tadrisVaTavasol);
			
			for ($i = $length-1 ; $i >= 0 ; $i--) {

				$qry = "'".'%'.$tadrisVaTavasol[$i].'%'."'";
				$stmt = $this -> connection -> prepare("SELECT * FROM noskheha WHERE name LIKE $qry");
				$stmt -> bind_result($id,$name);
				
				if($stmt -> execute()){
					while($stmt -> fetch()){
						$item        = array();		
						$item['id']  = $id;	
						$item['name']= $name;		
			
						array_push($items,$item);
					}
				}
					
				$response['result'] = $items;
			}
			return $response;
		}
		
		function Increment(){
			
			$id = 1;
			$stmt = $this -> connection -> prepare(" SELECT * FROM counter WHERE id = $id ");
			$stmt -> bind_result($id,$counter);
			
			if($stmt -> execute()){
				while($stmt -> fetch()){
					
				}
				$counter = intval($counter) + 1;
			
				$stmt = $this -> connection -> prepare("UPDATE counter SET counter=? WHERE id=?");
				$stmt -> bind_param("ii",$counter,$id);
				$stmt -> execute();
			}
		}
		
		function OneTimePassword($mobile,$OneTimePassword){
			$api = new \Ghasedak\GhasedakApi('3554ebda040a38ea436b5a9ab830c0c7ca3055296991667f9c58820a56de8bdc'); // change the key with your API key which you've got form your Ghasedak account
			$response = $api -> Verify("$mobile",1,"book","$OneTimePassword");
			return $response;
		}
		
		function GetPassword($mobile){			
			$response = array();
			$stmt = $this -> connection -> prepare(" SELECT * FROM users WHERE mobile = $mobile ");
			$stmt -> bind_result($id,$mobile,$name,$password);
			
			if($stmt -> execute()){
				while($stmt -> fetch()){
					//$response ['message'] = 'Sent sms';
					$api = new \Ghasedak\GhasedakApi('3554ebda040a38ea436b5a9ab830c0c7ca3055296991667f9c58820a56de8bdc'); // change the key with your API key which you've got form your Ghasedak account
					$response['result'] = $api -> Verify("$mobile",1,"book","$password");
					$response ['error'] = false;
					$response ['message'] = 'Sent';
					return $response;				
				}								
				$response ['error'] = true;
				$response ['message'] = 'no exist';
				return $response;
			}else{
				$response ['error'] = true;
				$response ['message'] = 'error 7';
			}
			return $response;			
		}
		
		function CheckUser($mobile){
			$response = array();
			
			$stmt = $this -> connection -> prepare(" SELECT * FROM users WHERE mobile = $mobile ");
			$stmt -> bind_result($id,$mobile,$name,$password);
			$stmt -> execute();
			
			// اگر بتواند فچ  کند یعنی کاربری پیدا کرده است.
			while($stmt -> fetch()){
				$response['error'] = false;
				$response['message'] = 'exist';
				return $response;
			}
			$response['error'] = true;
			$response['message'] = 'not_exist';	
			return $response;			
		}
		
		function LoginUser($mobile,$passwordInput){
			$response = array();
			
			$stmt = $this -> connection -> prepare(" SELECT * FROM users WHERE mobile = $mobile ");
			$stmt -> bind_result($id,$mobile,$name,$password);
			$stmt -> execute();
			
			// اگر بتواند فچ  کند یعنی کاربری پیدا کرده است.
			while($stmt -> fetch()){
				if ($passwordInput === $password) {
					$response['error'] = false;
					$response['message'] = 'login beshe';
					$response['name'] = $name;
					return $response;
				}
			}
			$response['error'] = true;
			$response['message'] = 'password ghalate';	
			return $response;			
		}
		
		function RegisterUser($mobile,$name,$password){
			$response = array();
			
			$stmt = $this -> connection -> prepare("INSERT INTO users (mobile,name,password) VALUES(?,?,?)");
			$stmt -> bind_param("sss",$mobile,$name,$password);
			if($stmt -> execute()){
				$response ['error'] = false;
				$response ['message'] = "registered";
				return $response;
			}else {
				$response ['error'] = true;
				$response ['message'] = "not_registered";
				return $response;
			}
		}
		
		function SendNewTalking($mobile,$name,$subject,$question,$date){
			$response = array();
			
			$stmtA = $this -> connection -> prepare("INSERT INTO question (mobile,name,subject,question,date) VALUES(?,?,?,?,?)");
			$stmtA -> bind_param("sssss",$mobile,$name,$subject,$question,$date);
			if($stmtA -> execute()){
				$response ['message'] = "ok";
				return $response;
			}else {
				$response ['message'] = "not_ok";
				return $response;
			}
		}
		
		function GetAllTalkQuestion(){
			$response = array();
			
			// Question
			$stmt = $this -> connection -> prepare(" SELECT * FROM question ORDER BY id DESC");
			$stmt -> bind_result($id,$mobile,$name,$subject,$question,$date);
			$stmt -> execute();
			
			$items = array();
			while($stmt -> fetch()){
				$item = array();
				$item['id'] = $id;
				$item['name'] = $name;
				$item['subject'] = $subject;
				$item['question'] = $question;
				$item['date'] = $date;
				
				array_push($items,$item);
			}
			
			$response ['questions'] = $items;
			return $response;
		}
		
		function GetAllResponse($id_question){
			$res = array();
			
			// Response
			$stmt = $this -> connection -> prepare(" SELECT * FROM response WHERE id_question = $id_question");
			$stmt -> bind_result($id,$id_question,$name,$response,$date);
			$stmt -> execute();
			
			$items = array();
			while($stmt -> fetch()){
				$item = array();
				$item['id'] = $id;
				$item['id_question'] = $id_question;
				$item['name'] = $name;
				$item['response'] = $response;
				$item['date'] = $date;
				
				array_push($items,$item);
			}
			
			$res ['responses'] = $items;
			return $res;
		}
		
		function SendResponse($id_question,$name,$response,$date){
			$res = array();
			
			$stmt = $this -> connection -> prepare("INSERT INTO response (id_question,name,response,date) VALUES(?,?,?,?)");
			$stmt -> bind_param("isss",$id_question,$name,$response,$date);
			if($stmt -> execute()){
				$res ['message'] = "ok";
				return $res;
			}else {
				$res ['message'] = "not_ok";
				return $res;
			}
			
			
		}
		
		function WithoutResponse(){
			$res = array();
			
			// Question
			$stmtQ = $this -> connection -> prepare(" SELECT * FROM question ");
			$stmtQ -> bind_result($id,$mobile,$name,$subject,$question,$date);
			$stmtQ -> execute();
			
			$itemsQ = array();
			while($stmtQ -> fetch()){
				$itemQ = array();
				$itemQ['id'] = $id;
				$itemQ['name'] = $name;
				$itemQ['subject'] = $subject;
				$itemQ['question'] = $question;
				$itemQ['date'] = $date;
				
				array_push($itemsQ,$itemQ);
			}
			
			$res ['question'] = $itemsQ;
			
			// Response
			$stmtR = $this -> connection -> prepare(" SELECT * FROM response ");
			$stmtR -> bind_result($id,$id_question,$name,$response,$date);
			$stmtR -> execute();
			
			$itemsR = array();
			while($stmtR -> fetch()){
				$itemR = array();
				$itemR['id_question'] = $id_question;
				
				array_push($itemsR,$itemR);
			}
			
			$res ['response'] = $itemsR;
			
			return $res;			
		}
		
		function GetMyQuestion($mobile){
			$res = array();
			
			// Question
			$stmtQ = $this -> connection -> prepare(" SELECT * FROM question WHERE mobile = $mobile");
			$stmtQ -> bind_result($id,$mobile,$name,$subject,$question,$date);
			$stmtQ -> execute();
			
			$itemsQ = array();
			while($stmtQ -> fetch()){
				$itemQ = array();
				$itemQ['id'] = $id;
				$itemQ['name'] = $name;
				$itemQ['subject'] = $subject;
				$itemQ['question'] = $question;
				$itemQ['date'] = $date;
				
				array_push($itemsQ,$itemQ);
			}
			
			$res ['question'] = $itemsQ;
			return $res;
		}
		
		function GetMyFavorite($id){
			$res = array();
			
			// Question
			$stmtQ = $this -> connection -> prepare(" SELECT * FROM question WHERE id = $id");
			$stmtQ -> bind_result($id,$mobile,$name,$subject,$question,$date);
			$stmtQ -> execute();

			while($stmtQ -> fetch()){
				$res['id']       = $id;
				$res['name']     = $name;
				$res['subject']  = $subject;
				$res['question'] = $question;
				$res['date']     = $date;
			}
			return $res;
		}
	}