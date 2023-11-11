<?php

	require_once 'DbOperation.php';
	require __DIR__ . '/src/GhasedakApi.php';
	
	function isTheseParameterAvailable($params){
		$available=true;
		$missingParams="";
		
		foreach($params as $param){
			if(!isset($_POST[$param]) || strlen($_POST[$param])<=0){
				$available=false;
				$missingParams=$missingParams. ", ".$param;
			}
		}
		
		if(!$available){
			$response=array();
			$response['error']=true;
			$response['message']='Parameters '.$missingParams.' missing';
			echo json_encode($response);
			die();
		}
	}

	$response = array();
	if(isset($_GET['apicall'])){
		
		switch($_GET['apicall']){		

			case 'get_setting':
				$db = new DbOperation();
				$response = $db -> GetSetting();														
			break;
			
			//################## write to badabase #######################
			case 'all_book':
				$db = new DbOperation();
				$response ['all_book'] = $db -> AllBook();
			break;
			
			case 'eslahe_alefbaye_tohid':
				$db = new DbOperation();
				$response ['eslahe_alefbaye_tohid'] = $db -> EslaheAlefbayeTohid();
			break;
			
			case 'tahrime_sokhan':
				$db = new DbOperation();
				$response ['tahrime_sokhan'] = $db -> TahrimeSokhan();
			break;
			
			case 'motefareghe':
				$db = new DbOperation();
				$response ['motefareghe'] = $db -> Motefareghe();
			break;
			
			case 'matne_farmayeshat':
				$db = new DbOperation();
				$response ['matne_farmayeshat'] = $db -> MatneFarmayeshat();
			break;
			
			case 'music':
				$db = new DbOperation();
				$response ['music'] = $db -> Music();
			break;
			
			case 'padkast':
				$db = new DbOperation();
				$response ['padkast'] = $db -> Padkast();
			break;
			
			case 'halghehaye_mafghoode':
				$db = new DbOperation();
				$response ['halghehaye_mafghoode'] = $db -> HalghehayeMafghoode();
			break;
			
			case 'tarke_moadelate_tekrari':
				$db = new DbOperation();
				$response ['tarke_moadelate_tekrari'] = $db -> TarkeMoadelateTekrari();
			break;
			//############################################################
			
			// نمایش لیست کامل
			case 'get_all_list':
				$db = new DbOperation();
				$response = $db -> GetList();
			break;
			
			case 'get_all_list_eslahe_alefbaye_tohid':
				$db = new DbOperation();
				$response = $db -> GetAllListEAT();
			break;
			
			case 'get_all_list_tahrime_sokhan':
				$db = new DbOperation();
				$response = $db -> GetAllListTahrameSokhan();
			break;
			
			case 'get_all_list_motefareghe':
				$db = new DbOperation();
				$response = $db -> GetAllListMotefareghe();
			break;
			
			case 'get_all_list_music':
				$db = new DbOperation();
				$response = $db -> GetAllListMusic();
			break;
			
			case 'get_all_list_padkast':
				$db = new DbOperation();
				$response = $db -> GetAllListPadkast();
			break;
			
			case 'get_all_list_halghehaye_mafghoode':
				$db = new DbOperation();
				$response = $db -> GetAllListHalghehayeMafghoode();
			break;
			
			case 'get_all_list_tarke_moadelate_tekrari':
				$db = new DbOperation();
				$response = $db -> GetAllListTarkeMoadelateTekrari();
			break;
			
			// query
			case 'query':
				isTheseParameterAvailable(array('query'));			
							
				$db = new DbOperation();
				$response = $db -> Query($_POST['query']);
			break;
			
			case 'query_tadris_va_tavasol':			
							
				$db = new DbOperation();
				$response = $db -> QueryTadrisVaTavasol();
			break;
			
			case 'increment':
				$db = new DbOperation();
				$db -> Increment();
			break;
						
			case 'check_user':
				isTheseParameterAvailable(array('mobile'));
				
				$db = new DbOperation();
				$response = $db -> CheckUser($_POST['mobile']);
			break;
					
			case 'login_user':
				isTheseParameterAvailable(array('mobile','password'));
				
				$db = new DbOperation();
				$response = $db -> LoginUser($_POST['mobile'],$_POST['password']);
			break;
			
			case 'one_time_password':
				isTheseParameterAvailable(array('mobile','one_time_password'));			
							
				$db = new DbOperation();
				$response = $db -> OneTimePassword($_POST['mobile'],$_POST['one_time_password']);
			break;
			
			case 'forget_password';
				$dbA = new DbOperation();
				$result = $dbA -> CheckUser($_POST['mobile']);
				if($result['error']){
					$response['error'] = true;
					$response['message'] = 'no exist';
				}else{
					$dbB = new DbOperation();
					$response = $dbB -> GetPassword($_POST['mobile']);
				}
			break;
			
			case 'register':
				isTheseParameterAvailable(array('mobile','name','password'));			
				
				$db = new DbOperation();
				$response = $db -> RegisterUser($_POST['mobile'],$_POST['name'],$_POST['password']);		
			break;
			
			case 'send_new_talking':
				isTheseParameterAvailable(array('mobile'));			
							
				$db = new DbOperation();
				$response = $db -> SendNewTalking($_POST['mobile'],$_POST['name'],$_POST['subject'],$_POST['question'],$_POST['date']);
			break;
			
			case 'get_all_talk_question':			
							
				$db = new DbOperation();
				$response = $db -> GetAllTalkQuestion();
			break;
			
			case 'get_all_response':			
				isTheseParameterAvailable(array('id_question'));
				
				$db = new DbOperation();
				$response = $db -> GetAllResponse($_POST['id_question']);
			break;
			
			case 'send_response':						
				isTheseParameterAvailable(array('id_question','name','response'));	
				
				$db = new DbOperation();
				$response = $db -> SendResponse($_POST['id_question'],$_POST['name'],$_POST['response'],$_POST['date']);
			break;
			
			case 'without_response':
				
				$db = new DbOperation();
				$response = $db -> WithoutResponse();
			break;
			
			case 'get_my_question':
				isTheseParameterAvailable(array('mobile'));
				
				$db = new DbOperation();
				$response = $db -> GetMyQuestion($_POST['mobile']);
			break;
			
			case 'get_my_favorite':
				isTheseParameterAvailable(array('id'));
				
				$db = new DbOperation();
				$response = $db -> GetMyFavorite($_POST['id']);
			break;
			
		}//switch

	}else{
		$response['error']=true;
		$response['message']='Invalid API Call';
	}

	echo json_encode($response);

?>