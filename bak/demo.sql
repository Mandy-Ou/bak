-- MySQL dump 10.13  Distrib 5.6.16, for Win64 (x86_64)
--
-- Host: localhost    Database: demo
-- ------------------------------------------------------
-- Server version	5.6.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `demo`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `demo` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `demo`;

--
-- Table structure for table `act_ge_bytearray`
--

DROP TABLE IF EXISTS `act_ge_bytearray`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ge_bytearray` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTES_` longblob,
  `GENERATED_` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_BYTEARR_DEPL` (`DEPLOYMENT_ID_`),
  CONSTRAINT `ACT_FK_BYTEARR_DEPL` FOREIGN KEY (`DEPLOYMENT_ID_`) REFERENCES `act_re_deployment` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ge_bytearray`
--

LOCK TABLES `act_ge_bytearray` WRITE;
/*!40000 ALTER TABLE `act_ge_bytearray` DISABLE KEYS */;
INSERT INTO `act_ge_bytearray` VALUES ('2',1,'History.bpmn','1','<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:activiti=\"http://activiti.org/bpmn\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" typeLanguage=\"http://www.w3.org/2001/XMLSchema\" expressionLanguage=\"http://www.w3.org/1999/XPath\" targetNamespace=\"http://www.activiti.org/test\">\n  <process id=\"process1\" name=\"process1\">\n    <startEvent id=\"startevent1\" name=\"Start\"></startEvent>\n    <endEvent id=\"endevent1\" name=\"End\"></endEvent>\n    <userTask id=\"usertask1\" name=\"放款\"></userTask>\n    <sequenceFlow id=\"flow1\" name=\"放款\" sourceRef=\"startevent1\" targetRef=\"usertask1\"></sequenceFlow>\n    <sequenceFlow id=\"flow2\" name=\"贷款结束\" sourceRef=\"usertask1\" targetRef=\"endevent1\"></sequenceFlow>\n  </process>\n  <bpmndi:BPMNDiagram id=\"BPMNDiagram_process1\">\n    <bpmndi:BPMNPlane bpmnElement=\"process1\" id=\"BPMNPlane_process1\">\n      <bpmndi:BPMNShape bpmnElement=\"startevent1\" id=\"BPMNShape_startevent1\">\n        <omgdc:Bounds height=\"35\" width=\"35\" x=\"170\" y=\"90\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"endevent1\" id=\"BPMNShape_endevent1\">\n        <omgdc:Bounds height=\"35\" width=\"35\" x=\"660\" y=\"90\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape bpmnElement=\"usertask1\" id=\"BPMNShape_usertask1\">\n        <omgdc:Bounds height=\"55\" width=\"105\" x=\"400\" y=\"80\"></omgdc:Bounds>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNEdge bpmnElement=\"flow1\" id=\"BPMNEdge_flow1\">\n        <omgdi:waypoint x=\"205\" y=\"107\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"400\" y=\"107\"></omgdi:waypoint>\n        <bpmndi:BPMNLabel>\n          <omgdc:Bounds height=\"14\" width=\"100\" x=\"-22\" y=\"-17\"></omgdc:Bounds>\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge bpmnElement=\"flow2\" id=\"BPMNEdge_flow2\">\n        <omgdi:waypoint x=\"505\" y=\"107\"></omgdi:waypoint>\n        <omgdi:waypoint x=\"660\" y=\"107\"></omgdi:waypoint>\n        <bpmndi:BPMNLabel>\n          <omgdc:Bounds height=\"14\" width=\"100\" x=\"-42\" y=\"-17\"></omgdc:Bounds>\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n    </bpmndi:BPMNPlane>\n  </bpmndi:BPMNDiagram>\n</definitions>',0),('3',1,'History.png','1','�PNG\r\n\Z\n\0\0\0\rIHDR\0\0�\0\0\0\0\0�)��\0\0�IDATx���l��]p�cȕ�j�`���ҀXlR�񐨍�%���Gf�ji���,P\Z�\Z,�J��(�5X��)���%�M	�d�?R�G6/m�ti��I:�$�5��o����|�8����^/}d������|�����s\0@@G�\0\04�\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��\0\Z\0��hb�Ba|||hh��������]�yOK`ppplllff&��B�\Z�f�w�޴�L�a���O���ʱc�gg۳Ҽ�%�gϞM�6�e��C�+��$4\0�\'L����x����˪�N�9��CZ8�N��{E�j��ɤ�����Ю�_����G}d�ڵǏ�{u�R���û�q=�����;��EK\Z�f�w�����g�\n*R_��m����W\Z�Ch\0�F�P���~���ٹ�*R�/�d͚ߜ���{��\"��i���\r�νS�O\r�~�K���j�ڶ��FFF�^u��h\Z���{���%����e�]�^�;{){�R�����?��o?u9��B�T!;�N�����x�͟�}��K={x���7�UG�\Z�����s��s�sJ���q>�.�go�eo]��ɲ?���;n��u����N\\�N��/d��e����œ\rڰ�_8��ٙ���E\r\\C�P���غuk:�������t����kϱ����Μ=9;w�T)�s%�t��Cs�W�~����_���x��sYvn.{{6{k6Km��_(����Yi��]q�A�=66v}��ВXPjm�y�c���R#۳��\'�=Y�G}l˖����R\'�n�:��Kj��z�����R��+W���\\v�bv��{OH)���/��WN�q�C���.�ġg��zm[U.ͬ<�ҍ��p�m����3bk*K�w귯�A���ޝ;w�l�ELSSS��اppyv�q8xhӦϤƷ���\'<g�;���ە���V���_����+��w���f���K=����񡎎�O�Ѧ�>s���׶U�|3��*o���ۦ�y�J�p:��撩��ݝBFp���Liltt4%�\'�=y͸PY�<{x����7o^Thmm�YU�i;wp�U��]����g/fo���|7{��>�я~��~��[?2���\rO?{��mU+ܺ��s�O�4Զ���BGÐQw�K>/M$�����UK)u�_��+��ڲ������g������m�����������Ȣ�Be}uǎ���6��}���=�?g���]Pq�?S��������3\r����==�k>�_��[�~�k���w�����KW_x`��׶U�d�l�\r�����n\Z�N�T��R�[u���{?_�	����gΦ{ժU�twwOMM5Q{-V\ZH�!������N�w+�p����B5�$��Jjn�\'�*-��ޞ�~r9{�������O~�w��Kُٛ���,ņO=]�ڶ��o`u��u�z~m��햗���߅h��Ö-��_���999�`\\m�Xilhh��C������}�����l�#�%Q���������3/NONM<<}𹣇^8��O}�;/��߇�����|����}�����]wn�3��w;����o[��k>_h(�۶]G��{�7R\\(�ohp��]+�����Ȼ��g�Ã��y�֊���\ZZU�p�\'�UC�[����}�#�?���U�pk�\"����.��ZF:�+�r�xo�`����4�����F�.�����z{{�01��{>�s�μgn�t�;��-|�T��ڧw�q��pV-�V>44��jj�-+�LLL�g���1T�#�>V����뎱��/u�Đ2撇���\\���}ޤ�fh�}f�i�jh�rF-�>h�a�{\ZLgd8m��S�]��,I�]�|êU����m�p��t\r�%O�����/W[Omh����5C�i��ʫ�U�$X衺+�V�$D����hm������ٙҖ��>w�B����w_�H�b��@__�b/ɰ��\r���=�+�g\Z��\Z�\Z�T���o`�A��ȶP5���;��s\ZL@�I�wi6�������U\ZlWWW�H[|�����Lj˔J�v���_�i\r�s�B}Y�3��*�����J���{���\"K[mD��.G��:�P�ի�KC���o-bj���\r/khx`۶����g�f2>>��u>���Zl��޴�4�R�]گ���-y�}�=�})���֒���͛w�ܹ���}O6��T9z�h����[���S��� 4����B�K����&��ۥ��[��j�ZR[��$Ս�+ǎ�^ݝ���4\n��M7�T�>�����<�*���Ŗ�Ќ\Zo��.=�}x�K�����L_���s�/tvv�=�4�ʋ�\'7�|�]w�U��W��ʕ7�bKhhR\r���-��/��j�����Z���n�+�nЕ+\'�b��6�r�y\"�3�����\ZBC[���y�ȑ\ry�(���[o-���Ei�7==��w��u�ز\r6��B�K�/S^�)��IҒ�ڲ^��T>�����H��Ļ�;�/?Z\r\'r��Q/41���8�U���:��4�ʧ���?}�liൗjВ�����C�v-kh8p��ڵk�Q��k���q���GFFR�Y�hq�ü�j��֕�WN[^+���x[H�w��}uǎ%�_:r�4���ު�Ғ����ۗ�Rb��BɦM��{Fi&cccu�����\r\ru������I5�FGGK�*u�K�o?�m[i�CCCU�Ւ�Zj�)�.kh���߻wo�3J�(�z_[��9yP}�BO�|(�)/�=�U����.��p������ׯ__\Zx�,U�Ւ�ݲ~25�Ԡ��.YnWw���s��Q��m0I������Xz鐯�k߳��%��iH��Ʉ���/?�`\Zx޳H�(�}_ͥ*G]�]��|���4�4�u\'>�U��)��5k�,�Ɇ;6l(\rvpp�v�ZR�+\n)�.��\ZR#^���ԩSy�\"���\'�A.��4��=忍_��4WNCޫ��733S���R]L���C�v>55U;R-����r�ƍN3��W�˥ʣ��Q�o��\'�XBC�ھ}{��uvv��w/_9v��B6o�\\w�ZWݱa�Ҿ+�ґ#�3fy�-��~%�*���F�$Uݓ�4WNCޫ��nݺ�n>��o�ꐩ�N�vyP�$֒�jfff�ڵK����gΦƗ�o޳E�)�_Υʣ�����)����KhhaǏ���*���(N�~��:;;��߿��$�3==�rÍ_�<%�;6lx����!Z�*4T��7�V@�Ǘ�K���^����C����}���Ғ�����6S��w%R�X&�G��?��Uo�W����v��>\'���{ձ�&&&��:�ߧ��d���O޹qc�p\Z\'�Lh��������l������}?��>Xz�#ew��W�-��LOO�ŪRg^��s�\n_ݱ������)s4xW�LK�Z�PH���zR~���]������)j�=���bh�V�-��M����[����J:�[衡�����$�K\r���K��\rR&��pM�g��oܹqczΦM��{utXZ���=��\Z�J��?guM}}}����kI4R(&&&R�+����WJ�����e���.^|n~��jQ%4���{��9�޽��U�www����Ν;���{Z�4Rg��ߞ�?�UBKEK��������9�������o�����hB�4FGGGF��}7�\\�Oc��hB�4�?��󱋅g�U�gbb\"�UG�\Z�f������*R�:�7CCy�4Z��\04�B�������ɲ�U�:uj_O��ff��hB�d���{{o���Ȳ)�P�Đ����_�{u�R���LNN��0:�\'Y������{z����ǼW�Fh\0�R�P������_�:�x�V33�5>��CC���}�vޫ�$4\0Ml������j�//��ں�n\Z�=冼W-Kh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 Dh\0\0B�\0 �����\\�hI�\0\0\0\0IEND�B`�',0);
/*!40000 ALTER TABLE `act_ge_bytearray` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ge_property`
--

DROP TABLE IF EXISTS `act_ge_property`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ge_property` (
  `NAME_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `VALUE_` varchar(300) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  PRIMARY KEY (`NAME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ge_property`
--

LOCK TABLES `act_ge_property` WRITE;
/*!40000 ALTER TABLE `act_ge_property` DISABLE KEYS */;
INSERT INTO `act_ge_property` VALUES ('historyLevel','3',1),('next.dbid','1201',13),('schema.history','create(5.10)',1),('schema.version','5.10',1);
/*!40000 ALTER TABLE `act_ge_property` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_actinst`
--

DROP TABLE IF EXISTS `act_hi_actinst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_actinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ACT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `ASSIGNEE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_ACT_INST_START` (`START_TIME_`),
  KEY `ACT_IDX_HI_ACT_INST_END` (`END_TIME_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_actinst`
--

LOCK TABLES `act_hi_actinst` WRITE;
/*!40000 ALTER TABLE `act_hi_actinst` DISABLE KEYS */;
INSERT INTO `act_hi_actinst` VALUES ('1002','process1:1:4','1001','1001','startevent1','Start','startEvent',NULL,'2014-03-17 20:17:35','2014-03-17 20:17:35',10),('1003','process1:1:4','1001','1001','usertask1','放款','userTask','22','2014-03-17 20:17:35',NULL,NULL),('102','process1:1:4','101','101','startevent1','Start','startEvent',NULL,'2014-03-11 22:15:32','2014-03-11 22:15:32',12),('103','process1:1:4','101','101','usertask1','放款','userTask','22','2014-03-11 22:15:32',NULL,NULL),('1102','process1:1:4','1101','1101','startevent1','Start','startEvent',NULL,'2014-04-02 16:10:29','2014-04-02 16:10:29',34),('1103','process1:1:4','1101','1101','usertask1','放款','userTask','22','2014-04-02 16:10:29',NULL,NULL),('1106','process1:1:4','1105','1105','startevent1','Start','startEvent',NULL,'2014-04-02 16:18:01','2014-04-02 16:18:01',0),('1107','process1:1:4','1105','1105','usertask1','放款','userTask','22','2014-04-02 16:18:01',NULL,NULL),('1110','process1:1:4','1109','1109','startevent1','Start','startEvent',NULL,'2014-04-02 16:21:31','2014-04-02 16:21:31',0),('1111','process1:1:4','1109','1109','usertask1','放款','userTask','22','2014-04-02 16:21:31','2014-04-02 16:27:14',343954),('1113','process1:1:4','1109','1109','endevent1','End','endEvent',NULL,'2014-04-02 16:27:14','2014-04-02 16:27:14',0),('1115','process1:1:4','1114','1114','startevent1','Start','startEvent',NULL,'2014-04-02 16:48:55','2014-04-02 16:48:55',0),('1116','process1:1:4','1114','1114','usertask1','放款','userTask','22','2014-04-02 16:48:55','2014-04-02 16:51:14',139145),('1118','process1:1:4','1114','1114','endevent1','End','endEvent',NULL,'2014-04-02 16:51:14','2014-04-02 16:51:14',0),('202','process1:1:4','201','201','startevent1','Start','startEvent',NULL,'2014-03-12 17:59:21','2014-03-12 17:59:21',8),('203','process1:1:4','201','201','usertask1','放款','userTask','22','2014-03-12 17:59:21','2014-03-12 18:36:31',2230991),('205','process1:1:4','201','201','endevent1','End','endEvent',NULL,'2014-03-12 18:36:31','2014-03-12 18:36:31',0),('207','process1:1:4','206','206','startevent1','Start','startEvent',NULL,'2014-03-12 18:49:40','2014-03-12 18:49:40',0),('208','process1:1:4','206','206','usertask1','放款','userTask','22','2014-03-12 18:49:40',NULL,NULL),('302','process1:1:4','301','301','startevent1','Start','startEvent',NULL,'2014-03-12 21:06:49','2014-03-12 21:06:49',8),('303','process1:1:4','301','301','usertask1','放款','userTask','22','2014-03-12 21:06:49',NULL,NULL),('402','process1:1:4','401','401','startevent1','Start','startEvent',NULL,'2014-03-14 11:17:54','2014-03-14 11:17:54',15),('403','process1:1:4','401','401','usertask1','放款','userTask','22','2014-03-14 11:17:54',NULL,NULL),('502','process1:1:4','501','501','startevent1','Start','startEvent',NULL,'2014-03-14 13:50:14','2014-03-14 13:50:14',6),('503','process1:1:4','501','501','usertask1','放款','userTask','22','2014-03-14 13:50:14','2014-03-14 13:52:03',109621),('505','process1:1:4','501','501','endevent1','End','endEvent',NULL,'2014-03-14 13:52:03','2014-03-14 13:52:03',0),('507','process1:1:4','506','506','startevent1','Start','startEvent',NULL,'2014-03-14 15:03:55','2014-03-14 15:03:55',0),('508','process1:1:4','506','506','usertask1','放款','userTask','22','2014-03-14 15:03:55','2014-03-14 15:13:37',582186),('510','process1:1:4','506','506','endevent1','End','endEvent',NULL,'2014-03-14 15:13:37','2014-03-14 15:13:37',0),('6','process1:1:4','5','5','startevent1','Start','startEvent',NULL,'2014-03-10 22:09:48','2014-03-10 22:09:48',10),('602','process1:1:4','601','601','startevent1','Start','startEvent',NULL,'2014-03-15 11:21:08','2014-03-15 11:21:08',6),('603','process1:1:4','601','601','usertask1','放款','userTask','22','2014-03-15 11:21:08',NULL,NULL),('7','process1:1:4','5','5','usertask1','放款','userTask','22','2014-03-10 22:09:48',NULL,NULL),('702','process1:1:4','701','701','startevent1','Start','startEvent',NULL,'2014-03-17 10:19:36','2014-03-17 10:19:36',11),('703','process1:1:4','701','701','usertask1','放款','userTask','22','2014-03-17 10:19:36','2014-03-17 11:11:24',3108665),('705','process1:1:4','701','701','endevent1','End','endEvent',NULL,'2014-03-17 11:11:24','2014-03-17 11:11:24',0),('707','process1:1:4','706','706','startevent1','Start','startEvent',NULL,'2014-03-17 11:15:19','2014-03-17 11:15:19',0),('708','process1:1:4','706','706','usertask1','放款','userTask','22','2014-03-17 11:15:19','2014-03-17 11:17:06',107294),('710','process1:1:4','706','706','endevent1','End','endEvent',NULL,'2014-03-17 11:17:06','2014-03-17 11:17:06',1),('802','process1:1:4','801','801','startevent1','Start','startEvent',NULL,'2014-03-17 12:34:06','2014-03-17 12:34:06',6),('803','process1:1:4','801','801','usertask1','放款','userTask','22','2014-03-17 12:34:06','2014-03-17 12:35:57',111980),('805','process1:1:4','801','801','endevent1','End','endEvent',NULL,'2014-03-17 12:35:57','2014-03-17 12:35:57',1),('902','process1:1:4','901','901','startevent1','Start','startEvent',NULL,'2014-03-17 17:56:21','2014-03-17 17:56:21',12),('903','process1:1:4','901','901','usertask1','放款','userTask','22','2014-03-17 17:56:21',NULL,NULL),('906','process1:1:4','905','905','startevent1','Start','startEvent',NULL,'2014-03-17 18:26:48','2014-03-17 18:26:48',0),('907','process1:1:4','905','905','usertask1','放款','userTask','22','2014-03-17 18:26:48','2014-03-17 18:30:35',227704),('909','process1:1:4','905','905','endevent1','End','endEvent',NULL,'2014-03-17 18:30:35','2014-03-17 18:30:35',0);
/*!40000 ALTER TABLE `act_hi_actinst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_attachment`
--

DROP TABLE IF EXISTS `act_hi_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_attachment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `URL_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `CONTENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_attachment`
--

LOCK TABLES `act_hi_attachment` WRITE;
/*!40000 ALTER TABLE `act_hi_attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_comment`
--

DROP TABLE IF EXISTS `act_hi_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_comment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MESSAGE_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `FULL_MSG_` longblob,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_comment`
--

LOCK TABLES `act_hi_comment` WRITE;
/*!40000 ALTER TABLE `act_hi_comment` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_detail`
--

DROP TABLE IF EXISTS `act_hi_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_detail` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `VAR_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TIME_` datetime NOT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_HI_DETAIL_PROC_INST` (`PROC_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_ACT_INST` (`ACT_INST_ID_`),
  KEY `ACT_IDX_HI_DETAIL_TIME` (`TIME_`),
  KEY `ACT_IDX_HI_DETAIL_NAME` (`NAME_`),
  KEY `ACT_IDX_HI_DETAIL_TASK_ID` (`TASK_ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_detail`
--

LOCK TABLES `act_hi_detail` WRITE;
/*!40000 ALTER TABLE `act_hi_detail` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_hi_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_procinst`
--

DROP TABLE IF EXISTS `act_hi_procinst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_procinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `START_USER_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `END_ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `PROC_INST_ID_` (`PROC_INST_ID_`),
  UNIQUE KEY `ACT_UNIQ_HI_BUS_KEY` (`PROC_DEF_ID_`,`BUSINESS_KEY_`),
  KEY `ACT_IDX_HI_PRO_INST_END` (`END_TIME_`),
  KEY `ACT_IDX_HI_PRO_I_BUSKEY` (`BUSINESS_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_procinst`
--

LOCK TABLES `act_hi_procinst` WRITE;
/*!40000 ALTER TABLE `act_hi_procinst` DISABLE KEYS */;
INSERT INTO `act_hi_procinst` VALUES ('1001','1001',NULL,'process1:1:4','2014-03-17 20:17:35',NULL,NULL,NULL,'startevent1',NULL,NULL,NULL),('101','101',NULL,'process1:1:4','2014-03-11 22:15:32',NULL,NULL,NULL,'startevent1',NULL,NULL,NULL),('1101','1101',NULL,'process1:1:4','2014-04-02 16:10:29',NULL,NULL,NULL,'startevent1',NULL,NULL,NULL),('1105','1105',NULL,'process1:1:4','2014-04-02 16:18:01',NULL,NULL,NULL,'startevent1',NULL,NULL,NULL),('1109','1109',NULL,'process1:1:4','2014-04-02 16:21:31','2014-04-02 16:27:14',343957,NULL,'startevent1','endevent1',NULL,NULL),('1114','1114',NULL,'process1:1:4','2014-04-02 16:48:55','2014-04-02 16:51:14',139148,NULL,'startevent1','endevent1',NULL,NULL),('201','201',NULL,'process1:1:4','2014-03-12 17:59:21','2014-03-12 18:36:31',2230992,NULL,'startevent1','endevent1',NULL,NULL),('206','206',NULL,'process1:1:4','2014-03-12 18:49:40',NULL,NULL,NULL,'startevent1',NULL,NULL,NULL),('301','301',NULL,'process1:1:4','2014-03-12 21:06:49',NULL,NULL,NULL,'startevent1',NULL,NULL,NULL),('401','401',NULL,'process1:1:4','2014-03-14 11:17:54',NULL,NULL,NULL,'startevent1',NULL,NULL,NULL),('5','5',NULL,'process1:1:4','2014-03-10 22:09:48',NULL,NULL,NULL,'startevent1',NULL,NULL,NULL),('501','501',NULL,'process1:1:4','2014-03-14 13:50:14','2014-03-14 13:52:03',109622,NULL,'startevent1','endevent1',NULL,NULL),('506','506',NULL,'process1:1:4','2014-03-14 15:03:55','2014-03-14 15:13:37',582186,NULL,'startevent1','endevent1',NULL,NULL),('601','601',NULL,'process1:1:4','2014-03-15 11:21:08',NULL,NULL,NULL,'startevent1',NULL,NULL,NULL),('701','701',NULL,'process1:1:4','2014-03-17 10:19:36','2014-03-17 11:11:24',3108666,NULL,'startevent1','endevent1',NULL,NULL),('706','706',NULL,'process1:1:4','2014-03-17 11:15:19','2014-03-17 11:17:06',107295,NULL,'startevent1','endevent1',NULL,NULL),('801','801',NULL,'process1:1:4','2014-03-17 12:34:06','2014-03-17 12:35:57',111982,NULL,'startevent1','endevent1',NULL,NULL),('901','901',NULL,'process1:1:4','2014-03-17 17:56:21',NULL,NULL,NULL,'startevent1',NULL,NULL,NULL),('905','905',NULL,'process1:1:4','2014-03-17 18:26:48','2014-03-17 18:30:35',227718,NULL,'startevent1','endevent1',NULL,NULL);
/*!40000 ALTER TABLE `act_hi_procinst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_hi_taskinst`
--

DROP TABLE IF EXISTS `act_hi_taskinst`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_hi_taskinst` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME_` datetime NOT NULL,
  `END_TIME_` datetime DEFAULT NULL,
  `DURATION_` bigint(20) DEFAULT NULL,
  `DELETE_REASON_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `DUE_DATE_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_hi_taskinst`
--

LOCK TABLES `act_hi_taskinst` WRITE;
/*!40000 ALTER TABLE `act_hi_taskinst` DISABLE KEYS */;
INSERT INTO `act_hi_taskinst` VALUES ('1004','process1:1:4','usertask1','1001','1001','放款',NULL,NULL,NULL,'22','2014-03-17 20:17:35',NULL,NULL,NULL,50,NULL),('104','process1:1:4','usertask1','101','101','放款',NULL,NULL,NULL,'22','2014-03-11 22:15:32',NULL,NULL,NULL,50,NULL),('1104','process1:1:4','usertask1','1101','1101','放款',NULL,NULL,NULL,'22','2014-04-02 16:10:29',NULL,NULL,NULL,50,NULL),('1108','process1:1:4','usertask1','1105','1105','放款',NULL,NULL,NULL,'22','2014-04-02 16:18:01',NULL,NULL,NULL,50,NULL),('1112','process1:1:4','usertask1','1109','1109','放款',NULL,NULL,NULL,'22','2014-04-02 16:21:31','2014-04-02 16:27:14',343939,'completed',50,NULL),('1117','process1:1:4','usertask1','1114','1114','放款',NULL,NULL,NULL,'22','2014-04-02 16:48:55','2014-04-02 16:51:14',139140,'completed',50,NULL),('204','process1:1:4','usertask1','201','201','放款',NULL,NULL,NULL,'22','2014-03-12 17:59:21','2014-03-12 18:36:31',2230986,'completed',50,NULL),('209','process1:1:4','usertask1','206','206','放款',NULL,NULL,NULL,'22','2014-03-12 18:49:40',NULL,NULL,NULL,50,NULL),('304','process1:1:4','usertask1','301','301','放款',NULL,NULL,NULL,'22','2014-03-12 21:06:49',NULL,NULL,NULL,50,NULL),('404','process1:1:4','usertask1','401','401','放款',NULL,NULL,NULL,'22','2014-03-14 11:17:54',NULL,NULL,NULL,50,NULL),('504','process1:1:4','usertask1','501','501','放款',NULL,NULL,NULL,'22','2014-03-14 13:50:14','2014-03-14 13:52:03',109617,'completed',50,NULL),('509','process1:1:4','usertask1','506','506','放款',NULL,NULL,NULL,'22','2014-03-14 15:03:55','2014-03-14 15:13:37',582183,'completed',50,NULL),('604','process1:1:4','usertask1','601','601','放款',NULL,NULL,NULL,'22','2014-03-15 11:21:08',NULL,NULL,NULL,50,NULL),('704','process1:1:4','usertask1','701','701','放款',NULL,NULL,NULL,'22','2014-03-17 10:19:36','2014-03-17 11:11:24',3108661,'completed',50,NULL),('709','process1:1:4','usertask1','706','706','放款',NULL,NULL,NULL,'22','2014-03-17 11:15:19','2014-03-17 11:17:06',107293,'completed',50,NULL),('8','process1:1:4','usertask1','5','5','放款',NULL,NULL,NULL,'22','2014-03-10 22:09:48',NULL,NULL,NULL,50,NULL),('804','process1:1:4','usertask1','801','801','放款',NULL,NULL,NULL,'22','2014-03-17 12:34:06','2014-03-17 12:35:57',111976,'completed',50,NULL),('904','process1:1:4','usertask1','901','901','放款',NULL,NULL,NULL,'22','2014-03-17 17:56:21',NULL,NULL,NULL,50,NULL),('908','process1:1:4','usertask1','905','905','放款',NULL,NULL,NULL,'22','2014-03-17 18:26:48','2014-03-17 18:30:35',227234,'completed',50,NULL);
/*!40000 ALTER TABLE `act_hi_taskinst` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_group`
--

DROP TABLE IF EXISTS `act_id_group`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_group` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_group`
--

LOCK TABLES `act_id_group` WRITE;
/*!40000 ALTER TABLE `act_id_group` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_group` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_info`
--

DROP TABLE IF EXISTS `act_id_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_info` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VALUE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD_` longblob,
  `PARENT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_info`
--

LOCK TABLES `act_id_info` WRITE;
/*!40000 ALTER TABLE `act_id_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_membership`
--

DROP TABLE IF EXISTS `act_id_membership`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_membership` (
  `USER_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `GROUP_ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`USER_ID_`,`GROUP_ID_`),
  KEY `ACT_FK_MEMB_GROUP` (`GROUP_ID_`),
  CONSTRAINT `ACT_FK_MEMB_GROUP` FOREIGN KEY (`GROUP_ID_`) REFERENCES `act_id_group` (`ID_`),
  CONSTRAINT `ACT_FK_MEMB_USER` FOREIGN KEY (`USER_ID_`) REFERENCES `act_id_user` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_membership`
--

LOCK TABLES `act_id_membership` WRITE;
/*!40000 ALTER TABLE `act_id_membership` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_membership` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_id_user`
--

DROP TABLE IF EXISTS `act_id_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_id_user` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `FIRST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LAST_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PWD_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PICTURE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_id_user`
--

LOCK TABLES `act_id_user` WRITE;
/*!40000 ALTER TABLE `act_id_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_id_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_re_deployment`
--

DROP TABLE IF EXISTS `act_re_deployment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_re_deployment` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DEPLOY_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_re_deployment`
--

LOCK TABLES `act_re_deployment` WRITE;
/*!40000 ALTER TABLE `act_re_deployment` DISABLE KEYS */;
INSERT INTO `act_re_deployment` VALUES ('1',NULL,'2014-03-10 14:07:07');
/*!40000 ALTER TABLE `act_re_deployment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_re_procdef`
--

DROP TABLE IF EXISTS `act_re_procdef`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_re_procdef` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `CATEGORY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `VERSION_` int(11) DEFAULT NULL,
  `DEPLOYMENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DGRM_RESOURCE_NAME_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `HAS_START_FORM_KEY_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_PROCDEF` (`KEY_`,`VERSION_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_re_procdef`
--

LOCK TABLES `act_re_procdef` WRITE;
/*!40000 ALTER TABLE `act_re_procdef` DISABLE KEYS */;
INSERT INTO `act_re_procdef` VALUES ('process1:1:4',1,'http://www.activiti.org/test','process1','process1',1,'1','History.bpmn','History.png',0,1);
/*!40000 ALTER TABLE `act_re_procdef` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_event_subscr`
--

DROP TABLE IF EXISTS `act_ru_event_subscr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_event_subscr` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `EVENT_TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EVENT_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACTIVITY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CONFIGURATION_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `CREATED_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_EVENT_SUBSCR_CONFIG_` (`CONFIGURATION_`),
  KEY `ACT_FK_EVENT_EXEC` (`EXECUTION_ID_`),
  CONSTRAINT `ACT_FK_EVENT_EXEC` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_event_subscr`
--

LOCK TABLES `act_ru_event_subscr` WRITE;
/*!40000 ALTER TABLE `act_ru_event_subscr` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_event_subscr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_execution`
--

DROP TABLE IF EXISTS `act_ru_execution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_execution` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BUSINESS_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_EXEC_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `IS_ACTIVE_` tinyint(4) DEFAULT NULL,
  `IS_CONCURRENT_` tinyint(4) DEFAULT NULL,
  `IS_SCOPE_` tinyint(4) DEFAULT NULL,
  `IS_EVENT_SCOPE_` tinyint(4) DEFAULT NULL,
  `SUSPENSION_STATE_` int(11) DEFAULT NULL,
  `CACHED_ENT_STATE_` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `ACT_UNIQ_RU_BUS_KEY` (`PROC_DEF_ID_`,`BUSINESS_KEY_`),
  KEY `ACT_IDX_EXEC_BUSKEY` (`BUSINESS_KEY_`),
  KEY `ACT_FK_EXE_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_EXE_PARENT` (`PARENT_ID_`),
  KEY `ACT_FK_EXE_SUPER` (`SUPER_EXEC_`),
  CONSTRAINT `ACT_FK_EXE_PARENT` FOREIGN KEY (`PARENT_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_EXE_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ACT_FK_EXE_SUPER` FOREIGN KEY (`SUPER_EXEC_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_execution`
--

LOCK TABLES `act_ru_execution` WRITE;
/*!40000 ALTER TABLE `act_ru_execution` DISABLE KEYS */;
INSERT INTO `act_ru_execution` VALUES ('1001',1,'1001',NULL,NULL,'process1:1:4',NULL,'usertask1',1,0,1,0,1,2),('101',1,'101',NULL,NULL,'process1:1:4',NULL,'usertask1',1,0,1,0,1,2),('1101',1,'1101',NULL,NULL,'process1:1:4',NULL,'usertask1',1,0,1,0,1,2),('1105',1,'1105',NULL,NULL,'process1:1:4',NULL,'usertask1',1,0,1,0,1,2),('206',1,'206',NULL,NULL,'process1:1:4',NULL,'usertask1',1,0,1,0,1,2),('301',1,'301',NULL,NULL,'process1:1:4',NULL,'usertask1',1,0,1,0,1,2),('401',1,'401',NULL,NULL,'process1:1:4',NULL,'usertask1',1,0,1,0,1,2),('5',1,'5',NULL,NULL,'process1:1:4',NULL,'usertask1',1,0,1,0,1,2),('601',1,'601',NULL,NULL,'process1:1:4',NULL,'usertask1',1,0,1,0,1,2),('901',1,'901',NULL,NULL,'process1:1:4',NULL,'usertask1',1,0,1,0,1,2);
/*!40000 ALTER TABLE `act_ru_execution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_identitylink`
--

DROP TABLE IF EXISTS `act_ru_identitylink`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_identitylink` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `GROUP_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `USER_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_IDENT_LNK_USER` (`USER_ID_`),
  KEY `ACT_IDX_IDENT_LNK_GROUP` (`GROUP_ID_`),
  KEY `ACT_IDX_ATHRZ_PROCEDEF` (`PROC_DEF_ID_`),
  KEY `ACT_FK_TSKASS_TASK` (`TASK_ID_`),
  CONSTRAINT `ACT_FK_ATHRZ_PROCEDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TSKASS_TASK` FOREIGN KEY (`TASK_ID_`) REFERENCES `act_ru_task` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_identitylink`
--

LOCK TABLES `act_ru_identitylink` WRITE;
/*!40000 ALTER TABLE `act_ru_identitylink` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_identitylink` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_job`
--

DROP TABLE IF EXISTS `act_ru_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_job` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `LOCK_EXP_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `LOCK_OWNER_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXCLUSIVE_` tinyint(1) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROCESS_INSTANCE_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `RETRIES_` int(11) DEFAULT NULL,
  `EXCEPTION_STACK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `EXCEPTION_MSG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `DUEDATE_` timestamp NULL DEFAULT NULL,
  `REPEAT_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_TYPE_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `HANDLER_CFG_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_FK_JOB_EXCEPTION` (`EXCEPTION_STACK_ID_`),
  CONSTRAINT `ACT_FK_JOB_EXCEPTION` FOREIGN KEY (`EXCEPTION_STACK_ID_`) REFERENCES `act_ge_bytearray` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_job`
--

LOCK TABLES `act_ru_job` WRITE;
/*!40000 ALTER TABLE `act_ru_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_task`
--

DROP TABLE IF EXISTS `act_ru_task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_task` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL DEFAULT '',
  `REV_` int(11) DEFAULT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIPTION_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TASK_DEF_KEY_` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OWNER_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ASSIGNEE_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DELEGATION_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PRIORITY_` int(11) DEFAULT NULL,
  `CREATE_TIME_` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `DUE_DATE_` datetime DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_TASK_CREATE` (`CREATE_TIME_`),
  KEY `ACT_FK_TASK_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_TASK_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_TASK_PROCDEF` (`PROC_DEF_ID_`),
  CONSTRAINT `ACT_FK_TASK_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCDEF` FOREIGN KEY (`PROC_DEF_ID_`) REFERENCES `act_re_procdef` (`ID_`),
  CONSTRAINT `ACT_FK_TASK_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_task`
--

LOCK TABLES `act_ru_task` WRITE;
/*!40000 ALTER TABLE `act_ru_task` DISABLE KEYS */;
INSERT INTO `act_ru_task` VALUES ('1004',2,'1001','1001','process1:1:4','放款',NULL,NULL,'usertask1',NULL,'22',NULL,50,'2014-03-17 12:17:35',NULL),('104',2,'101','101','process1:1:4','放款',NULL,NULL,'usertask1',NULL,'22',NULL,50,'2014-03-11 14:15:32',NULL),('1104',2,'1101','1101','process1:1:4','放款',NULL,NULL,'usertask1',NULL,'22',NULL,50,'2014-04-02 08:10:29',NULL),('1108',2,'1105','1105','process1:1:4','放款',NULL,NULL,'usertask1',NULL,'22',NULL,50,'2014-04-02 08:18:01',NULL),('209',2,'206','206','process1:1:4','放款',NULL,NULL,'usertask1',NULL,'22',NULL,50,'2014-03-12 10:49:40',NULL),('304',2,'301','301','process1:1:4','放款',NULL,NULL,'usertask1',NULL,'22',NULL,50,'2014-03-12 13:06:49',NULL),('404',2,'401','401','process1:1:4','放款',NULL,NULL,'usertask1',NULL,'22',NULL,50,'2014-03-14 03:17:54',NULL),('604',2,'601','601','process1:1:4','放款',NULL,NULL,'usertask1',NULL,'22',NULL,50,'2014-03-15 03:21:08',NULL),('8',2,'5','5','process1:1:4','放款',NULL,NULL,'usertask1',NULL,'22',NULL,50,'2014-03-10 14:09:48',NULL),('904',2,'901','901','process1:1:4','放款',NULL,NULL,'usertask1',NULL,'22',NULL,50,'2014-03-17 09:56:21',NULL);
/*!40000 ALTER TABLE `act_ru_task` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `act_ru_variable`
--

DROP TABLE IF EXISTS `act_ru_variable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `act_ru_variable` (
  `ID_` varchar(64) COLLATE utf8_bin NOT NULL,
  `REV_` int(11) DEFAULT NULL,
  `TYPE_` varchar(255) COLLATE utf8_bin NOT NULL,
  `NAME_` varchar(255) COLLATE utf8_bin NOT NULL,
  `EXECUTION_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TASK_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `BYTEARRAY_ID_` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `DOUBLE_` double DEFAULT NULL,
  `LONG_` bigint(20) DEFAULT NULL,
  `TEXT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  `TEXT2_` varchar(4000) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_`),
  KEY `ACT_IDX_VARIABLE_TASK_ID` (`TASK_ID_`),
  KEY `ACT_FK_VAR_EXE` (`EXECUTION_ID_`),
  KEY `ACT_FK_VAR_PROCINST` (`PROC_INST_ID_`),
  KEY `ACT_FK_VAR_BYTEARRAY` (`BYTEARRAY_ID_`),
  CONSTRAINT `ACT_FK_VAR_BYTEARRAY` FOREIGN KEY (`BYTEARRAY_ID_`) REFERENCES `act_ge_bytearray` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_EXE` FOREIGN KEY (`EXECUTION_ID_`) REFERENCES `act_ru_execution` (`ID_`),
  CONSTRAINT `ACT_FK_VAR_PROCINST` FOREIGN KEY (`PROC_INST_ID_`) REFERENCES `act_ru_execution` (`ID_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `act_ru_variable`
--

LOCK TABLES `act_ru_variable` WRITE;
/*!40000 ALTER TABLE `act_ru_variable` DISABLE KEYS */;
/*!40000 ALTER TABLE `act_ru_variable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_address`
--

DROP TABLE IF EXISTS `crm_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_address` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `contactor` varchar(30) DEFAULT NULL,
  `customerId` double DEFAULT NULL,
  `housType` double DEFAULT NULL,
  `isDefault` int(11) DEFAULT NULL,
  `manCount` int(11) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `relation` varchar(50) DEFAULT NULL,
  `resideType` double DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `zipcode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_address`
--

LOCK TABLES `crm_address` WRITE;
/*!40000 ALTER TABLE `crm_address` DISABLE KEYS */;
INSERT INTO `crm_address` VALUES (1,'2013-12-25 16:00:00',8,3,-1,-1,NULL,'2014-02-21 07:30:55',1,NULL,'佛山市顺德区龙江镇世埠新坑路伏龙里三巷7号','刘德辉',1,21,1,3,NULL,'父子',28,NULL,NULL),(2,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:55',1,NULL,'佛山市顺德区龙江镇世埠新坑路伏龙里三巷7号','刘德辉',1,21,1,3,NULL,'父子',28,NULL,NULL),(3,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:55',1,NULL,'佛山市顺德区龙江镇世埠新坑路伏龙里三巷7号','刘信文',2,21,1,0,'13928502398',NULL,26,NULL,NULL),(4,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:55',1,NULL,' 广东省佛山高明区荷城街道知言街52号1梯301','文增容',3,21,1,0,NULL,'夫妻',28,NULL,NULL),(5,'2013-12-26 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:30:55',1,NULL,'佛山市高明区荷城街道丰成街16号2梯201房','费佑强',41,21,1,0,NULL,NULL,28,NULL,NULL),(6,'2014-02-24 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,'111','11',51,21,1,0,'15013552655',NULL,25,'150135526558',NULL);
/*!40000 ALTER TABLE `crm_address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_category`
--

DROP TABLE IF EXISTS `crm_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `category` int(11) DEFAULT NULL,
  `custType` int(11) DEFAULT NULL,
  `inCustomerId` double DEFAULT NULL,
  `projectId` double DEFAULT NULL,
  `relCustomerId` double DEFAULT NULL,
  `uuid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_category`
--

LOCK TABLES `crm_category` WRITE;
/*!40000 ALTER TABLE `crm_category` DISABLE KEYS */;
INSERT INTO `crm_category` VALUES (1,0,1,2,NULL,6,NULL),(2,0,1,3,NULL,7,NULL),(3,0,1,4,NULL,8,NULL),(4,0,1,12,NULL,17,NULL),(5,0,1,19,NULL,39,NULL),(6,2,1,73,NULL,101,NULL),(7,2,0,74,NULL,79,NULL),(8,2,0,75,NULL,77,NULL),(9,2,0,76,NULL,77,NULL),(10,1,0,51,9,50,NULL),(11,1,0,55,21,55,NULL),(12,0,1,40,NULL,67,NULL);
/*!40000 ALTER TABLE `crm_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_consort`
--

DROP TABLE IF EXISTS `crm_consort`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_consort` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `cardNum` varchar(50) DEFAULT NULL,
  `cardType` double DEFAULT NULL,
  `coninterest` varchar(100) DEFAULT NULL,
  `conjobunit` varchar(60) DEFAULT NULL,
  `contactTel` varchar(20) DEFAULT NULL,
  `customerId` double DEFAULT NULL,
  `degree` double DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `hometown` double DEFAULT NULL,
  `job` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `nation` double DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `qqmsnNum` varchar(20) DEFAULT NULL,
  `workAddress` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_consort`
--

LOCK TABLES `crm_consort` WRITE;
/*!40000 ALTER TABLE `crm_consort` DISABLE KEYS */;
INSERT INTO `crm_consort` VALUES (1,'2014-02-24 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,111,'2014-02-10 16:00:00','44011119830804461X',7,NULL,NULL,NULL,51,17,NULL,14,'11','11',16,NULL,NULL,NULL);
/*!40000 ALTER TABLE `crm_consort` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_contactor`
--

DROP TABLE IF EXISTS `crm_contactor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_contactor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `customerId` double DEFAULT NULL,
  `idcrad` varchar(40) DEFAULT NULL,
  `job` varchar(30) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `orgName` varchar(50) DEFAULT NULL,
  `orgTel` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `relation` varchar(30) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_contactor`
--

LOCK TABLES `crm_contactor` WRITE;
/*!40000 ALTER TABLE `crm_contactor` DISABLE KEYS */;
INSERT INTO `crm_contactor` VALUES (1,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:55',1,NULL,'佛山市顺德区龙江镇世埠新坑路伏龙里三巷7号',26,'1987-01-17 16:00:00',1,'440681198701184332','法定代表人','刘德辉','佛山市高明骏有纺织染整有限公司',NULL,NULL,'父子',NULL),(2,'2014-02-24 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,'12565698595','12565698595',120,'2014-02-24 16:00:00',51,'44011119830804461X','12565698595','111','12565698595','12565698595','15013552658','ere','12565698595');
/*!40000 ALTER TABLE `crm_contactor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_creditinfo`
--

DROP TABLE IF EXISTS `crm_creditinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_creditinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `creitType` double DEFAULT NULL,
  `customerId` double DEFAULT NULL,
  `monthAmount` double DEFAULT NULL,
  `totalAmount` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_creditinfo`
--

LOCK TABLES `crm_creditinfo` WRITE;
/*!40000 ALTER TABLE `crm_creditinfo` DISABLE KEYS */;
INSERT INTO `crm_creditinfo` VALUES (1,'2013-12-25 16:00:00',8,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,0,0,41,1,0,0),(2,'2014-02-24 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,'11111',111110,1111110,39,51,110,111110);
/*!40000 ALTER TABLE `crm_creditinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_custbase`
--

DROP TABLE IF EXISTS `crm_custbase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_custbase` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `cardNum` varchar(50) DEFAULT NULL,
  `cardType` double DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `custLevel` int(11) DEFAULT NULL,
  `custType` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `refId` double DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_custbase`
--

LOCK TABLES `crm_custbase` WRITE;
/*!40000 ALTER TABLE `crm_custbase` DISABLE KEYS */;
INSERT INTO `crm_custbase` VALUES (1,'2014-04-02 07:25:18',9,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000027292',0,'C2013122504374600',1,1,'佛山市高明区炫味食品有限公司',NULL,3),(2,'2013-12-25 16:00:00',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'440623196312054275',7,'C2013122609130102',1,0,'刘信文',NULL,3),(3,'2013-12-25 16:00:00',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'440681198701184332',7,'C2013122609401304',1,0,'刘德辉',NULL,3),(4,'2013-12-25 16:00:00',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'510226196704227476',7,'C2013122609515406',1,0,'高启良',NULL,3),(5,'2013-12-25 16:00:00',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'440623195205064238',7,'C2013122610130608',1,0,'黄长禧',NULL,3),(6,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440623197004221734',7,'C2013122610272910',1,0,'陈竹勤',NULL,3),(7,'2014-04-02 07:25:14',8,3,NULL,-1,8,'2013-12-25 16:00:00',1,NULL,'440684000010437 ',0,'C2013122610385812',1,1,'佛山市高明利德丰陶瓷有限公司',NULL,3),(8,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440622193903195029',7,'C2013122610544514',1,0,'梁科弟',NULL,3),(9,'2014-04-02 07:25:10',8,3,NULL,-1,8,'2013-12-25 16:00:00',1,NULL,'440684000010445',0,'C2013122611344316',1,1,'佛山市高明吉利陶瓷有限公司',NULL,3),(10,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440622195809225032',7,'C2013122611391618',1,0,'何松',NULL,3),(11,'2014-04-02 07:25:05',8,3,NULL,-1,8,'2013-12-25 16:00:00',1,NULL,'440684000030155',0,'C2013122611433320',1,1,'佛山市高明大黄蜂陶瓷有限公司',NULL,3),(12,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440682199011235017',7,'C2013122611471922',1,0,'罗嘉俊',NULL,3),(13,'2014-04-02 07:25:01',8,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440681000268000',0,'C2013122611520724',1,1,'佛山市顺德区富延贸易有限公司',NULL,3),(14,'2013-12-25 16:00:00',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'352626197010170362',7,'C2013122602124126',1,0,'程美淑',NULL,3),(15,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'352626197009270411',7,'C2013122602175728',1,0,'陈文前',NULL,3),(16,'2013-12-25 16:00:00',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'350881198207080377',7,'C2013122602230830',1,0,'李伟成',NULL,3),(17,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'350881198206050387',7,'C2013122602285532',1,0,'陈巧贞',NULL,3),(18,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440520197607155954',7,'C2013122602353434',1,0,'陈宏楷',NULL,3),(19,'2014-04-02 07:24:57',10,3,NULL,-1,10,'2013-12-25 16:00:00',1,NULL,'440684000035573',0,'C2013122602362136',1,1,'佛山市高明邱氏饲料发展有限公司',NULL,3),(20,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440684198202056941',7,'C2013122602395538',1,0,'梁靖怡',NULL,3),(21,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'441502198411222126',7,'C2013122602430640',1,0,'柯惠敏',NULL,3),(22,'2014-04-02 07:24:53',8,3,NULL,-1,8,'2013-12-25 16:00:00',1,NULL,'440600400001914',0,'C2013122602455642',1,1,'佛山市高明银海广场有限公司',NULL,3),(23,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624196204270014',7,'C2013122602551344',1,0,'邵志锋',NULL,3),(24,'2014-04-02 07:24:49',8,3,NULL,-1,8,'2013-12-25 16:00:00',1,NULL,'440684000021031',0,'C2013122603035246',1,1,'佛山市高明华盈商贸有限公司',NULL,3),(25,'2014-04-02 07:24:44',8,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684600207909',0,'C2013122603135748',1,1,'佛山市高明君临一品茶行',NULL,3),(26,'2014-04-02 07:24:38',8,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000005306',0,'C2013122603171350',1,1,'佛山市高明赛纳置业有限公司',NULL,3),(27,'2014-04-02 07:23:42',8,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000003884',0,'C2013122603331952',1,1,'佛山市高明区银海房地产有限公司',NULL,3),(28,'2014-04-02 07:23:37',8,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440600400016174',0,'C2013122603375754',1,1,'广东南亮玻璃科技有限公司',NULL,3),(29,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440601196909263813',7,'C2013122603435356',1,0,'陈铭波',NULL,3),(30,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624196611044612',7,'C2013122603450758',1,0,'李国中',NULL,3),(31,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197207031515',7,'C2013122603502960',1,0,'曹伟源',NULL,3),(32,'2013-12-25 16:00:00',2,8,NULL,-1,10,'2013-12-25 16:00:00',-1,NULL,'440684000020836',0,'C2013122604020462',1,1,'佛山市高明新永业玻璃制品有限公司',NULL,3),(33,'2013-12-25 16:00:00',10,3,NULL,1,10,'2013-12-25 16:00:00',1,NULL,'440622196412042822',7,'C2013122604083064',1,0,'梁暖银',NULL,3),(34,'2013-12-25 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197009232711',7,'C2013122604105266',1,0,'严小海',NULL,3),(35,'2013-12-25 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440106197409264026',7,'C2013122604134168',1,0,'杜琼珍',NULL,3),(36,'2013-12-25 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197005230014',7,'C2013122604155670',1,0,'金伟杰',NULL,3),(37,'2013-12-25 16:00:00',23,2,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000001983',0,'C2013122605152872',1,1,'佛山市高明区合基物流有限公司',NULL,3),(38,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624196711020829',7,'C2013122708271774',1,0,'关惠连',NULL,3),(39,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440684198111181528',7,'C2013122708294176',1,0,'利淑琼',NULL,3),(40,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197307046925',7,'C2013122708315378',1,0,'何燕芳',NULL,3),(41,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197203010039',7,'C2013122708343980',1,0,'谭国华',NULL,3),(42,'2014-04-02 07:24:34',8,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000027604',0,'C2013122708375582',1,1,'佛山市高明天朝家具有限公司',NULL,3),(43,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'652901198002020430',7,'C2013122708431784',1,0,'张承强',NULL,3),(44,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'350321197912186443',7,'C2013122708452086',1,0,'陈荔娟',NULL,3),(45,'2014-04-02 07:23:29',8,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000039154',0,'C2013122708481388',1,1,'佛山市高明联星电路板有限公司',NULL,3),(46,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624196511065037',7,'C2013122708504090',1,0,'李树志',NULL,3),(47,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624196903185011',7,'C2013122708524492',1,0,'李树植',NULL,3),(48,'2014-04-02 07:23:33',8,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000014745',0,'C2013122708552294',1,1,'佛山市高明区庆昌果仁食品有限公司',NULL,3),(49,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197308270419',7,'C2013122708594196',1,0,'苏海文',NULL,3),(50,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440725196802161231',7,'C2013122709013498',1,0,'杨远明',NULL,3),(51,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440725196112051211',7,'C20131227090313100',1,0,'杨悦明',NULL,3),(52,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440725196308061227',7,'C20131227090450102',1,0,'劳宝平',NULL,3),(53,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197708071523',7,'C20131227090651104',1,0,'陆雪颜',NULL,3),(54,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624196611125711',7,'C20131227090900106',1,0,'朱林方',NULL,3),(55,'2014-04-02 07:22:24',8,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000000271',0,'C20131227091352108',1,1,'佛山市美嘉油墨涂料有限公司',NULL,3),(56,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440623196708056007',7,'C20131227091618110',1,0,'陈佩红',NULL,3),(57,'2014-04-02 07:23:24',9,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000028960',0,'C20131227110135112',1,1,'佛山市高明区盈富纺织染整有限公司',NULL,3),(58,'2013-12-27 06:57:12',9,3,NULL,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624196110010078',7,'C20131227025712114',1,0,'黄镁安',NULL,3),(59,'2013-12-26 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197212240047',7,'C20131227030532116',1,0,'谭凤英',NULL,3),(60,'2013-12-26 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'522229196512294431',7,'C20131227031238118',1,0,'费佑强',NULL,3),(61,'2013-12-26 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624196803152335',7,'C20131227034147120',1,0,'钟润文',NULL,3),(62,'2013-12-26 16:00:00',2,8,NULL,1,2,'2013-12-26 16:00:00',-1,NULL,'440624197206290013',7,'C20131227035319122',1,0,'李春华',NULL,3),(63,'2013-12-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:30:56',-1,NULL,'440684198309230014',7,'C20131227035912124',1,0,'罗琪',NULL,3),(64,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'44062419640515081X',7,'C20131230095955126',1,0,'关广俭',NULL,3),(65,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'44062419691105381X',7,'C20131230100220128',1,0,'温日辉',NULL,3),(66,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197107190017',7,'C20131230100419130',1,0,'黎伟雄',NULL,3),(67,'2014-04-02 07:25:44',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'44062419761211041X',7,'C20131230100652132',1,0,'黎德雄',NULL,3),(68,'2014-04-02 07:25:39',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'44122419840503733X',7,'C20131230100850134',1,0,'蔡云锋',NULL,3),(69,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197401013214',7,'C20131230101111136',1,0,'区志华',NULL,3),(70,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197410010031',7,'C20131230101347138',1,0,'甘建堂',NULL,3),(71,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624195709200041',7,'C20131230101705140',1,0,'陆凤莲',NULL,3),(72,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440684198711220412',7,'C20131230101901142',1,0,'杜琳辉',NULL,3),(73,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624196412033216',7,'C20131230102114144',1,0,'梁江',NULL,3),(74,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440682197910015019',7,'C20131230102253146',1,0,'罗永贃',NULL,3),(75,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197911235714',7,'C20131230044738148',1,0,'徐志辉',NULL,3),(76,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197205182723',7,'C20131230044946150',1,0,'彭文华',NULL,3),(77,'2013-12-29 16:00:00',10,3,NULL,1,10,'2014-01-16 16:00:00',1,NULL,'412928197812010024',7,'C20131230045216152',1,0,'刘雪源',NULL,3),(78,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440784198809044221',7,'C20131230045505154',1,0,'张志芳',NULL,3),(79,'2013-12-29 16:00:00',10,3,NULL,1,10,'2014-01-13 16:00:00',1,NULL,'440102196909154077',7,'C20131230045640156',1,0,'王兴华',NULL,3),(80,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440682198611145053',7,'C20131230045856158',1,0,'罗永球',NULL,3),(81,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'510226197011269651',7,'C20131230050047160',1,0,'邓四建',NULL,3),(82,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440622197106085028',7,'C20131230050311162',1,0,'罗见心',NULL,3),(83,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'510226196305249627',7,'C20131230050527164',1,0,'王国玲',NULL,3),(84,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'442829197111152210',7,'C20131230050711166',1,0,'何汝秋',NULL,3),(85,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197702052313',7,'C20131230051039168',1,0,'刘以辉',NULL,3),(86,'2013-12-29 16:00:00',10,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197206023855',7,'C20131230051419170',1,0,'龚启邦',NULL,3),(87,'2014-04-02 07:23:20',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000020836',0,'C20131230053556172',1,1,'佛山市高明新永业玻璃制品有限公司',NULL,3),(88,'2014-04-02 07:23:15',10,3,NULL,-1,10,'2013-12-29 16:00:00',1,NULL,'440684000001983',0,'C20131230054247174',1,1,'佛山市高明区合基物流有限公司',NULL,3),(89,'2014-04-02 07:23:10',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000014421',0,'C20131230060158176',1,1,'佛山市高明区文昌物业投资有限公司',NULL,3),(90,'2014-04-02 07:22:18',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000007205',0,'C20131230060713178',1,1,'佛山市高明区明城镇置业投资服务有限公司',NULL,3),(91,'2014-04-02 07:22:13',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000016128',0,'C20131230060910180',1,1,'佛山市高明区明城民诚置业有限公司',NULL,3),(92,'2014-04-02 07:22:09',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000014341',0,'C20131230061054182',1,1,'佛山市高明区明城镇市政工程公司',NULL,3),(93,'2014-04-02 07:22:05',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000013613',0,'C20131230061229184',1,1,'佛山市保升电路板有限公司',NULL,3),(94,'2014-04-02 07:22:00',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000030139',0,'C20131230061505186',1,1,'佛山市富悦湾农业发展有限公司',NULL,3),(95,'2014-04-02 07:21:47',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000027268',0,'C20131230061827188',1,1,'佛山市高明区腾茂装饰工程有限公司',NULL,3),(96,'2014-04-02 07:21:52',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000014147',0,'C20131230062106190',1,1,'佛山市高明区杨和镇建和城镇建设开发有限公司',NULL,3),(97,'2014-04-02 07:21:43',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000038594',0,'C20131230062300192',1,1,'佛山市高明区和源资产管理有限公司',NULL,3),(98,'2014-04-02 07:21:38',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000019127',0,'C20131230062437194',1,1,'佛山市高明宝林木业有限公司',NULL,3),(99,'2014-04-02 07:21:34',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000027612',0,'C20131230062653196',1,1,'佛山市金盈利建材有限公司',NULL,3),(100,'2014-04-02 07:21:29',10,3,-1,-1,NULL,'2014-02-21 07:30:56',1,NULL,'440684000033993',0,'C20131230063014198',1,1,'佛山市汇胜贸易有限公司',NULL,3),(101,'2014-04-02 07:21:18',10,3,NULL,-1,10,'2014-01-13 16:00:00',1,NULL,'440684000033977',0,'C20131230063342200',1,1,'佛山市银胜贸易有限公司',NULL,3),(102,'2014-01-01 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624196909163219',7,'C20140102113115202',1,0,'杜汉其',NULL,3),(103,'2014-01-01 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197805235032',7,'C20140102113424204',1,0,'黄华年',NULL,3),(104,'2014-01-01 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197511305015',7,'C20140102113629206',1,0,'黄永年',NULL,3),(105,'2014-01-01 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624197712080414',7,'C20140102113851208',1,0,'谢国伟',NULL,3),(106,'2014-01-01 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,'440624196906030082',7,'C20140102114024210',1,0,'钟小玲',NULL,3),(107,'2014-04-02 07:25:53',2,8,-1,-1,NULL,'0000-00-00 00:00:00',-1,NULL,'44011119830804461X',7,'C20140225102347212',1,0,'??? ',NULL,3),(108,'2014-04-02 07:25:58',2,8,-1,-1,NULL,'0000-00-00 00:00:00',-1,NULL,'44011119830804461X',7,'C20140225102637214',1,0,'test',NULL,3),(109,'2014-04-02 07:21:24',2,8,-1,-1,NULL,'0000-00-00 00:00:00',-1,NULL,'440106197708049051',0,'C20140225110125216',1,1,'伍学共花店',NULL,3),(110,'2014-04-02 07:32:50',9,3,-1,-1,NULL,'0000-00-00 00:00:00',1,NULL,'440112199306159422',7,'C20140312055646218',1,0,'毛含莲 ',NULL,3),(111,'2014-04-02 07:32:46',9,3,-1,-1,NULL,'0000-00-00 00:00:00',1,NULL,'440106199108282166',7,'C20140312064743220',1,0,'费谷兰 ',NULL,3),(112,'2014-04-02 07:26:26',9,3,-1,-1,NULL,'0000-00-00 00:00:00',1,NULL,'440116198006237311',7,'C20140312085615222',1,0,'石谱班 ',NULL,3),(113,'2014-04-02 07:26:20',9,3,-1,-1,NULL,'0000-00-00 00:00:00',1,NULL,'440106197708049051',7,'C20140314111622224',1,0,'伍学共 ',NULL,3),(114,'2014-04-02 07:26:08',2,8,-1,-1,NULL,'0000-00-00 00:00:00',-1,NULL,'440116199106169437',7,'C20140314014753226',1,0,'薛规畅 ',NULL,3),(115,'2014-04-02 07:26:13',9,3,-1,-1,NULL,'0000-00-00 00:00:00',1,NULL,'440116198511242234',7,'C20140314025906228',1,0,'宣叔华 ',NULL,3),(116,'2014-04-02 07:32:42',9,3,-1,-1,NULL,'0000-00-00 00:00:00',1,NULL,'44011119850810199X',7,'C20140317111419230',1,0,'洪豆焕',NULL,3),(117,'2014-04-02 07:32:38',9,3,-1,-1,NULL,'0000-00-00 00:00:00',1,NULL,'44011519840620781X',7,'C20140317001522232',1,0,'赵驹菘',NULL,3),(118,'2014-04-02 07:32:33',9,3,NULL,-1,9,'2014-03-16 16:00:00',1,NULL,'440114199409134044',7,'C20140317062138234',1,0,'范笑卉',NULL,3),(119,'2014-04-02 07:31:44',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'510226198703043340',7,'C20140402032921236',1,0,'黄芳',NULL,3),(120,'2014-04-02 07:36:08',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'42118119840122083X',7,'C20140402033451238',1,0,'李波',NULL,3),(121,'2014-04-02 07:38:48',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'360430198910133522',7,'C20140402033721240',1,0,'李静怡',NULL,3),(122,'2014-04-02 07:48:50',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'243599677',0,'C20140402034655242',1,1,'佛山方圆商贸有限公司',NULL,3),(123,'2014-04-02 07:50:11',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'8758934590',0,'C20140402034943244',1,1,'佛山万达电子有限公司',NULL,3),(124,'2014-04-01 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,'467347846989',0,'C20140402035221246',1,1,'顺德康健医疗器械有限公司',NULL,3),(125,'2014-04-01 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,'37438439096',0,'C20140402035521248',1,1,'佛山光明电子有限公司',NULL,3),(126,'2014-04-02 08:08:31',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'510226197507273304',7,'C20140402040728250',1,0,'涂建清',NULL,3),(127,'2014-04-09 03:40:04',24,1,NULL,1,24,'2014-04-08 16:00:00',1,NULL,'440301103359161',0,'C20140409104143252',1,1,'深圳市君天恒讯科技有限公司',NULL,3),(128,'2014-04-08 16:00:00',24,1,-1,1,NULL,'0000-00-00 00:00:00',1,NULL,'320705197807133565',7,'C20140409104059252',1,0,'袁岚',NULL,3),(129,'2014-04-09 03:00:18',24,1,NULL,1,24,'2014-04-08 16:00:00',1,NULL,'15272819810224001X',7,'C20140409104404256',1,0,'韩乐权',NULL,3),(130,'2014-04-09 03:50:47',24,1,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'15272819810224001X',7,'C20140409115047258',1,0,'韩乐权',NULL,3),(131,'2014-04-09 16:00:00',24,1,-1,1,NULL,'0000-00-00 00:00:00',1,NULL,'412726199004236733',7,'C20140410104303260',1,0,'郭华伟',NULL,3),(132,'2014-04-13 16:00:00',24,1,-1,1,NULL,'0000-00-00 00:00:00',1,NULL,'43070319770108506X',7,'C20140414021730262',1,0,'张三',NULL,3);
/*!40000 ALTER TABLE `crm_custbase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_custblack`
--

DROP TABLE IF EXISTS `crm_custblack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_custblack` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `baseId` double DEFAULT NULL,
  `custType` int(11) DEFAULT NULL,
  `customerId` double DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_custblack`
--

LOCK TABLES `crm_custblack` WRITE;
/*!40000 ALTER TABLE `crm_custblack` DISABLE KEYS */;
/*!40000 ALTER TABLE `crm_custblack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_custcompany`
--

DROP TABLE IF EXISTS `crm_custcompany`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_custcompany` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `ccoTel` varchar(50) DEFAULT NULL,
  `ccoaaddress` varchar(50) DEFAULT NULL,
  `ccokind` double DEFAULT NULL,
  `cconame` varchar(50) DEFAULT NULL,
  `currency` double DEFAULT NULL,
  `custType` int(11) DEFAULT NULL,
  `customerId` double DEFAULT NULL,
  `empCount` int(11) DEFAULT NULL,
  `landNum` varchar(50) DEFAULT NULL,
  `legal` varchar(50) DEFAULT NULL,
  `linkman` varchar(50) DEFAULT NULL,
  `monthly` double DEFAULT NULL,
  `nationNum` varchar(50) DEFAULT NULL,
  `offNum` varchar(50) DEFAULT NULL,
  `orgNum` varchar(50) DEFAULT NULL,
  `premises` varchar(50) DEFAULT NULL,
  `profit` double DEFAULT NULL,
  `regDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `regcaptial` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_custcompany`
--

LOCK TABLES `crm_custcompany` WRITE;
/*!40000 ALTER TABLE `crm_custcompany` DISABLE KEYS */;
INSERT INTO `crm_custcompany` VALUES (1,'2013-12-26 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:30:56',1,NULL,NULL,NULL,34,'佛山市高明区骏辉装饰工程有限公司',50,1,41,0,NULL,'费佑强',NULL,0,NULL,'440684000031176','58466121-5',NULL,0,'2011-10-16 16:00:00',500000),(2,'2014-02-25 02:58:24',2,8,NULL,1,2,'2014-02-24 16:00:00',-1,'1111','15013556589','111',32,'111',50,0,51,1110,'1111','11111','11',11110,'11','1111','1111','11111',110,'2014-02-24 16:00:00',11110);
/*!40000 ALTER TABLE `crm_custcompany` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_customerinfo`
--

DROP TABLE IF EXISTS `crm_customerinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_customerinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `accAddress` varchar(20) DEFAULT NULL,
  `accNature` int(11) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `baseId` double DEFAULT NULL,
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `cardNum` varchar(50) DEFAULT NULL,
  `cardType` double DEFAULT NULL,
  `cendDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `contactTel` varchar(20) DEFAULT NULL,
  `contactor` varchar(30) DEFAULT NULL,
  `degree` double DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `hometown` double DEFAULT NULL,
  `inAddress` varchar(100) DEFAULT NULL,
  `inArea` varchar(20) DEFAULT NULL,
  `job` varchar(20) DEFAULT NULL,
  `maristal` double DEFAULT NULL,
  `mnemonic` varchar(20) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `nation` double DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `qqmsnNum` varchar(20) DEFAULT NULL,
  `registerTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `regman` double DEFAULT NULL,
  `serialNum` varchar(20) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `workAddress` varchar(100) DEFAULT NULL,
  `workOrg` varchar(50) DEFAULT NULL,
  `zipcode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_customerinfo`
--

LOCK TABLES `crm_customerinfo` WRITE;
/*!40000 ALTER TABLE `crm_customerinfo` DISABLE KEYS */;
INSERT INTO `crm_customerinfo` VALUES (48,'2013-12-30 02:07:16',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,NULL,0,37,67,'1976-12-10 16:00:00','44062419761211041X',7,'2014-02-21 07:30:57','13928536868','黎德雄',20,NULL,NULL,NULL,'荷城街道西黎村127号','1,1,1,5',NULL,11,NULL,'黎德雄',124,'13928536868',NULL,'2013-12-30 02:07:16',10,'S20131230100652132',0,0,NULL,NULL,NULL),(49,'2013-12-30 02:09:14',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,NULL,0,29,68,'1984-05-02 16:00:00','44122419840503733X',7,'2014-02-21 07:30:57','18033223268','蔡云锋',19,NULL,NULL,NULL,'高明碧桂园一期一街83号208房','1,1,1,5',NULL,11,NULL,'蔡云锋',124,'18033223268',NULL,'2013-12-30 02:09:14',10,'S20131230100850134',0,0,NULL,NULL,NULL),(50,'2014-02-25 02:23:53',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,107,'2014-02-25 02:23:53','44011119830804461X',7,'2014-02-25 02:23:53',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'??? ',NULL,NULL,NULL,'2014-02-25 02:23:53',2,'S20140225102347212',1,0,NULL,NULL,NULL),(51,'2014-02-25 02:55:48',2,8,NULL,1,2,'2014-02-24 16:00:00',-1,NULL,NULL,0,23,108,'2014-02-24 16:00:00','44011119830804461X',7,'2014-02-24 16:00:00',NULL,NULL,17,NULL,NULL,14,NULL,NULL,NULL,10,NULL,'test',16,NULL,NULL,'2014-02-25 02:26:45',2,'S20140225102637214',1,0,NULL,NULL,NULL),(52,'2014-03-12 09:57:10',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,NULL,NULL,110,'2014-03-12 09:57:10','440112199306159422',7,'2014-03-12 09:57:10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'毛含莲 ',NULL,NULL,NULL,'2014-03-12 09:57:10',9,'S20140312055646218',0,0,NULL,NULL,NULL),(53,'2014-03-12 10:47:59',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,NULL,NULL,111,'2014-03-12 10:47:59','440106199108282166',7,'2014-03-12 10:47:59',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'费谷兰 ',NULL,NULL,NULL,'2014-03-12 10:47:59',9,'S20140312064743220',1,0,NULL,NULL,NULL),(54,'2014-03-12 12:56:22',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,NULL,NULL,112,'2014-03-12 12:56:22','440116198006237311',7,'2014-03-12 12:56:22',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'石谱班 ',NULL,NULL,NULL,'2014-03-12 12:56:22',9,'S20140312085615222',0,0,NULL,NULL,NULL),(55,'2014-03-14 03:16:47',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,NULL,NULL,113,'2014-03-14 03:16:47','440106197708049051',7,'2014-03-14 03:16:47',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'伍学共 ',NULL,NULL,NULL,'2014-03-14 03:16:47',9,'S20140314111622224',0,0,NULL,NULL,NULL),(56,'2014-03-14 05:48:17',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,NULL,NULL,NULL,114,'2014-03-14 05:48:17','440116199106169437',7,'2014-03-14 05:48:17',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'薛规畅 ',NULL,NULL,NULL,'2014-03-14 05:48:17',2,'S20140314014753226',0,0,NULL,NULL,NULL),(57,'2014-03-14 06:59:30',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,NULL,NULL,115,'2014-03-14 06:59:30','440116198511242234',7,'2014-03-14 06:59:30',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'宣叔华 ',NULL,NULL,NULL,'2014-03-14 06:59:30',9,'S20140314025906228',0,0,NULL,NULL,NULL),(58,'2014-03-17 03:14:24',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,NULL,NULL,116,'2014-03-17 03:14:24','44011119850810199X',7,'2014-03-17 03:14:24',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'洪豆焕',NULL,NULL,NULL,'2014-03-17 03:14:24',9,'S20140317111419230',0,0,NULL,NULL,NULL),(59,'2014-03-17 04:15:27',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,NULL,NULL,117,'2014-03-17 04:15:27','44011519840620781X',7,'2014-03-17 04:15:27',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'赵驹菘',NULL,NULL,NULL,'2014-03-17 04:15:27',9,'S20140317001522232',0,0,NULL,NULL,NULL),(60,'2014-03-17 10:23:55',9,3,NULL,1,9,'2014-03-17 10:24:38',1,NULL,NULL,NULL,NULL,118,'2014-03-17 10:21:21','440114199409134044',7,'2014-03-17 10:21:21',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'范笑卉',NULL,NULL,NULL,'2014-03-17 10:22:03',9,'S20140317062138234',1,0,NULL,NULL,NULL),(61,'2014-04-02 07:31:44',2,8,NULL,1,2,'2014-04-02 07:31:44',-1,NULL,NULL,0,36,119,'1987-03-03 16:00:00','510226198703043340',7,'2020-02-28 16:00:00',NULL,NULL,19,NULL,NULL,15,NULL,NULL,NULL,11,NULL,'黄芳',16,NULL,NULL,'2014-04-02 07:29:40',2,'S20140402032921236',1,0,NULL,NULL,NULL),(62,'2014-04-02 07:36:08',2,8,NULL,1,2,'2014-04-02 07:36:08',-1,NULL,NULL,0,30,120,'1984-01-21 16:00:00','42118119840122083X',7,'2026-09-22 16:00:00',NULL,NULL,19,NULL,NULL,15,NULL,NULL,NULL,11,NULL,'李波',16,NULL,NULL,'2014-04-02 07:34:58',2,'S20140402033451238',0,0,NULL,NULL,NULL),(63,'2014-04-02 07:38:48',2,8,NULL,1,2,'2014-04-02 07:38:48',-1,NULL,NULL,0,25,121,'1989-10-12 16:00:00','360430198910133522',7,'2026-08-17 16:00:00',NULL,NULL,19,NULL,NULL,14,NULL,NULL,NULL,11,NULL,'李静怡',16,NULL,NULL,'2014-04-02 07:37:28',2,'S20140402033721240',1,0,NULL,NULL,NULL),(64,'2014-04-02 08:08:31',2,8,NULL,1,2,'2014-04-02 08:08:31',-1,NULL,NULL,0,39,126,'1975-07-26 16:00:00','510226197507273304',7,'2026-11-09 16:00:00',NULL,NULL,18,NULL,NULL,14,NULL,NULL,NULL,11,NULL,'涂建清',16,NULL,NULL,'2014-04-02 08:07:41',2,'S20140402040728250',1,0,NULL,NULL,NULL),(65,'2014-04-09 02:42:04',24,1,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,NULL,NULL,128,'2014-04-09 02:42:04','320705197807133565',7,'2014-04-09 02:42:04',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'袁岚',NULL,NULL,NULL,'2014-04-09 02:42:04',24,'S20140409104059252',1,0,NULL,NULL,NULL),(66,'2014-04-09 03:00:18',24,1,NULL,1,24,'2014-04-09 03:00:18',1,NULL,NULL,NULL,NULL,129,'2014-04-09 02:44:10','15272819810224001X',7,'2014-04-09 02:44:10',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'韩乐权',NULL,NULL,NULL,'2014-04-09 02:44:10',24,'S20140409104404256',0,0,NULL,NULL,NULL),(67,'2014-04-09 03:51:51',24,1,NULL,-1,NULL,'2014-04-09 03:51:51',1,NULL,NULL,0,36,130,'1981-02-23 16:00:00','15272819810224001X',7,'2031-02-27 16:00:00',NULL,NULL,20,NULL,NULL,14,'中信红树湾16C-2203','1,1,1,1',NULL,11,NULL,'韩乐权',16,'13534228888',NULL,'2014-04-09 03:50:47',24,NULL,0,0,'南山区高新区德赛科技大厦19楼1903C','深圳市君天恒讯科技有限公司',NULL),(68,'2014-04-10 02:43:52',24,1,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,NULL,NULL,131,'2014-04-10 02:43:52','412726199004236733',7,'2014-04-10 02:43:52',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'郭华伟',NULL,NULL,NULL,'2014-04-10 02:43:52',24,'S20140410104303260',0,0,NULL,NULL,NULL),(69,'2014-04-14 06:29:43',24,1,NULL,1,24,'2014-04-13 16:00:00',1,NULL,NULL,0,37,132,'1977-01-07 16:00:00','43070319770108506X',7,'2025-04-15 16:00:00',NULL,NULL,19,NULL,NULL,15,NULL,NULL,NULL,10,NULL,'张三',16,NULL,NULL,'2014-04-14 06:17:42',24,'S20140414021730262',0,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `crm_customerinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_eassure`
--

DROP TABLE IF EXISTS `crm_eassure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_eassure` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `asbalance` double DEFAULT NULL,
  `asendDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `asstartDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `ecustomerId` double DEFAULT NULL,
  `inverse` double DEFAULT NULL,
  `object` varchar(50) DEFAULT NULL,
  `term` varchar(50) DEFAULT NULL,
  `thing` varchar(225) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_eassure`
--

LOCK TABLES `crm_eassure` WRITE;
/*!40000 ALTER TABLE `crm_eassure` DISABLE KEYS */;
/*!40000 ALTER TABLE `crm_eassure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_ebank`
--

DROP TABLE IF EXISTS `crm_ebank`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_ebank` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `account` varchar(25) DEFAULT NULL,
  `accountType` int(11) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `bankOrg` varchar(80) DEFAULT NULL,
  `ecustomerId` double DEFAULT NULL,
  `orderDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `setAmount` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_ebank`
--

LOCK TABLES `crm_ebank` WRITE;
/*!40000 ALTER TABLE `crm_ebank` DISABLE KEYS */;
INSERT INTO `crm_ebank` VALUES (1,'2014-04-09 03:59:25',24,1,-1,-1,NULL,'0000-00-00 00:00:00',1,NULL,'44201581500052526901',1,0,'中国建设银行股份有限公司深圳景苑支行',NULL,'2011-11-23 16:00:00',0),(2,'2014-04-09 03:59:27',24,1,NULL,1,24,'2014-04-08 16:00:00',1,NULL,'44201581500052526901',1,0,'中国建设银行股份有限公司深圳景苑支行',NULL,'2011-11-23 16:00:00',0);
/*!40000 ALTER TABLE `crm_ebank` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_ebankborr`
--

DROP TABLE IF EXISTS `crm_ebankborr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_ebankborr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `asstype` varchar(80) DEFAULT NULL,
  `creditBreed` varchar(80) DEFAULT NULL,
  `ecustomerId` double DEFAULT NULL,
  `limits` varchar(10) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `result` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_ebankborr`
--

LOCK TABLES `crm_ebankborr` WRITE;
/*!40000 ALTER TABLE `crm_ebankborr` DISABLE KEYS */;
/*!40000 ALTER TABLE `crm_ebankborr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_eclass`
--

DROP TABLE IF EXISTS `crm_eclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_eclass` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `Tel` varchar(20) DEFAULT NULL,
  `birthday` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `cardNumer` varchar(20) DEFAULT NULL,
  `cardType` double DEFAULT NULL,
  `degree` double DEFAULT NULL,
  `ecustomerId` double DEFAULT NULL,
  `fugleName` varchar(30) DEFAULT NULL,
  `hometown` double DEFAULT NULL,
  `incomeTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `isMember` int(11) DEFAULT NULL,
  `job` varchar(20) DEFAULT NULL,
  `nation` double DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_eclass`
--

LOCK TABLES `crm_eclass` WRITE;
/*!40000 ALTER TABLE `crm_eclass` DISABLE KEYS */;
/*!40000 ALTER TABLE `crm_eclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_ecustomer`
--

DROP TABLE IF EXISTS `crm_ecustomer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_ecustomer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `areaNumber` varchar(30) DEFAULT NULL,
  `baseId` double DEFAULT NULL,
  `comeTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `contactor` varchar(30) DEFAULT NULL,
  `contacttel` varchar(20) DEFAULT NULL,
  `currency` double DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `empCount` int(11) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `finaIdCard` varchar(20) DEFAULT NULL,
  `finaManager` varchar(30) DEFAULT NULL,
  `finaTel` varchar(20) DEFAULT NULL,
  `inAddress` varchar(200) DEFAULT NULL,
  `incapital` double DEFAULT NULL,
  `insCount` int(11) DEFAULT NULL,
  `kind` double DEFAULT NULL,
  `legalIdCard` varchar(20) DEFAULT NULL,
  `legalMan` varchar(30) DEFAULT NULL,
  `legalTel` varchar(20) DEFAULT NULL,
  `licence` varchar(30) DEFAULT NULL,
  `licencedate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `loanBank` varchar(100) DEFAULT NULL,
  `loanLog` varchar(10) DEFAULT NULL,
  `loanNumber` varchar(30) DEFAULT NULL,
  `managerIdCard` varchar(20) DEFAULT NULL,
  `managerName` varchar(30) DEFAULT NULL,
  `managerTel` varchar(20) DEFAULT NULL,
  `manamount` int(11) DEFAULT NULL,
  `mnemonic` varchar(20) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `orgcode` varchar(20) DEFAULT NULL,
  `patentNumber` varchar(30) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `regaddress` varchar(100) DEFAULT NULL,
  `regcapital` double DEFAULT NULL,
  `registerTime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `regman` double DEFAULT NULL,
  `rent` double DEFAULT NULL,
  `serialNum` varchar(20) DEFAULT NULL,
  `shortName` varchar(50) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `taxNumber` varchar(30) DEFAULT NULL,
  `tradNumber` varchar(50) DEFAULT NULL,
  `trade` double DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `zipCode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_ecustomer`
--

LOCK TABLES `crm_ecustomer` WRITE;
/*!40000 ALTER TABLE `crm_ecustomer` DISABLE KEYS */;
INSERT INTO `crm_ecustomer` VALUES (1,'2013-12-25 08:38:19',9,3,NULL,1,9,'2013-12-25 16:00:00',1,NULL,'1,1,1,5','粤地税字440608570142027',1,'2014-02-21 07:30:57','黎肇明','88232020',50,NULL,0,'88262355',NULL,NULL,NULL,'荷城街道荷富路以西横江村村口志锋公司侧',200000,0,34,'441221197711035734','黎肇明','13928503303',NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,'441221197711035734','黎肇明','13928503303',0,NULL,'佛山市高明区炫味食品有限公司','440684000027292',NULL,'13928503303',NULL,200000,'2013-12-25 08:38:19',9,0,'S2013122504374600','炫味',0,'粤国税字440624570142027号','440684000027292',97,NULL,NULL),(2,'2013-12-26 02:39:24',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'1,1,1,5',NULL,7,'2002-07-07 16:00:00','廖伟良','13809700222',NULL,NULL,0,NULL,NULL,NULL,NULL,'更合镇新圩更合大道67号',0,0,34,'440622193903195029','梁科弟',NULL,NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明利德丰陶瓷有限公司','440684000010437 ',NULL,'13809700222',NULL,0,'2013-12-26 02:39:24',8,0,'S2013122610385812','利德丰陶瓷',0,NULL,'440684000010437 ',97,NULL,NULL),(3,'2013-12-26 03:35:07',8,3,NULL,1,8,'2013-12-26 03:42:09',1,NULL,'1,1,1,5',NULL,9,'2002-02-04 16:00:00','廖伟良','13809700222',NULL,NULL,0,NULL,NULL,NULL,NULL,'更楼镇沧江工业园西园',0,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明吉利陶瓷有限公司','73618094-8',NULL,'13809700222',NULL,0,'2013-12-26 03:35:07',8,0,'S2013122611344316','吉利陶瓷',0,NULL,'440684000010445',97,NULL,NULL),(4,'2013-12-26 03:43:57',8,3,NULL,1,8,'2013-12-26 03:48:46',1,NULL,'1,1,1,5',NULL,11,'2011-08-10 16:00:00','廖伟良','13809700222',NULL,NULL,0,NULL,NULL,NULL,NULL,'更合镇更合大道71号',0,0,34,'440682199011235017','罗嘉俊',NULL,NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明大黄蜂陶瓷有限公司','57974558-8',NULL,'13809700222',NULL,0,'2013-12-26 03:43:57',8,0,'S2013122611433320','大黄蜂陶瓷',0,NULL,'440684000030155',99,NULL,NULL),(5,'2013-12-26 03:52:31',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'1,1,1,2',NULL,13,'2011-01-09 16:00:00','廖伟良','13809700222',NULL,NULL,0,NULL,NULL,NULL,NULL,'乐从镇乐从大道东B268号佛山奥园美林水岸B-D栋商铺209铺位',0,0,34,NULL,'岑剑辉',NULL,NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市顺德区富延贸易有限公司','440681000268000',NULL,'13809700222',NULL,0,'2013-12-26 03:52:31',8,0,'S2013122611520724','富延贸易',0,NULL,'440681000268000',99,NULL,NULL),(6,'2013-12-26 06:36:44',10,3,NULL,1,10,'2013-12-26 06:41:34',1,NULL,'1,1,1,5',NULL,19,'2012-09-12 16:00:00','邱少坤','13924529938',50,NULL,0,NULL,NULL,NULL,NULL,'更合镇吉田村委会黄茅墩铺',50000,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明邱氏饲料发展有限公司','05375051-8',NULL,'13924529938','佛山市高明区更合镇吉田村委会黄茅墩铺',50000,'2013-12-26 06:36:44',10,0,'S2013122602362136','邱氏',0,NULL,'440684000035573',100,NULL,NULL),(7,'2013-12-26 06:46:21',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'1,1,1,5','440608618099751',22,'1996-09-15 16:00:00','邵志锋',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'沿江路',0,0,34,NULL,'黄志言','13928534333',NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明银海广场有限公司','440600400001914',NULL,'13702857123',NULL,0,'2013-12-26 06:46:21',8,0,'S2013122602455642','银海广场',0,'440624618099751','440600400001914',106,NULL,NULL),(8,'2013-12-26 07:04:16',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,NULL,NULL,24,'2008-12-31 16:00:00','邵志锋','13702857123',NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,0,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明华盈商贸有限公司','440684000021031',NULL,'13702857123',NULL,0,'2013-12-26 07:04:16',8,0,'S2013122603035246','华盈商贸',0,NULL,'440684000021031',99,NULL,NULL),(9,'2013-12-26 07:14:22',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'1,1,1,5',NULL,25,'2010-05-12 16:00:00','邵志锋','13702857123',NULL,NULL,0,NULL,NULL,NULL,NULL,'荷城街道沿江路463号6座首层C8铺',0,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明君临一品茶行','440684600207909',NULL,'13702857123',NULL,0,'2013-12-26 07:14:22',8,0,'S2013122603135748','君临茶行',0,NULL,'440684600207909',99,NULL,NULL),(10,'2013-12-26 07:17:36',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'1,1,1,5',NULL,26,'2009-08-23 16:00:00','邵志锋','13702857123',NULL,NULL,0,NULL,NULL,NULL,NULL,'荷城街道沿江路483号',0,0,34,NULL,'邵志锋',NULL,NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明赛纳置业有限公司','440684000005306',NULL,'13702857123',NULL,0,'2013-12-26 07:17:36',8,0,'S2013122603171350','赛纳置业',0,NULL,'440684000005306',106,NULL,NULL),(11,'2013-12-26 07:33:42',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'1,1,1,5',NULL,27,'1995-12-07 16:00:00','邵志锋','13702857123',NULL,NULL,0,NULL,NULL,NULL,NULL,'荷城街道沿江路463号',0,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明区银海房地产有限公司','440684000003884',NULL,'13702857123',NULL,0,'2013-12-26 07:33:42',8,0,'S2013122603331952','银海房地产',0,NULL,'440684000003884',105,NULL,NULL),(12,'2013-12-26 07:38:20',8,3,NULL,1,8,'2013-12-25 16:00:00',1,NULL,'1,1,1,5',NULL,28,'2004-03-17 16:00:00','江文俊','13392200411',NULL,NULL,0,NULL,NULL,NULL,NULL,'沧江工业园东园富湾镇',0,0,35,'441221197711035734','黎肇明','13928503303',NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'广东南亮玻璃科技有限公司','440600400016174',NULL,'13392200411',NULL,0,'2013-12-26 07:38:20',8,0,'S2013122603375754','南亮玻璃',0,NULL,'440600400016174',97,NULL,NULL),(13,'2013-12-26 08:02:28',2,8,NULL,1,10,'2013-12-26 08:08:09',-1,NULL,'1,1,1,5',NULL,32,'2014-02-21 07:30:57','梁暖银','13902417814',NULL,NULL,0,NULL,NULL,NULL,NULL,'更合镇延兴街6、8号',500000,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明新永业玻璃制品有限公司','76156012－5',NULL,'13902417814','佛山市高明区更合镇延兴街6、8号',500000,'2013-12-26 08:02:28',2,0,'S2013122604020462','新永业玻璃',0,NULL,'440684000020836',97,NULL,NULL),(14,'2013-12-26 09:16:08',23,2,NULL,1,23,'2013-12-25 16:00:00',1,NULL,'1,1,1,5',NULL,37,'2014-02-21 07:30:57','甘建堂','13702957541',NULL,NULL,0,NULL,NULL,NULL,NULL,'荷城街道广安路19路4座104铺',10,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明区合基物流有限公司','440684000001983',NULL,'13702957541','佛山市高明区荷城街道广安路19号4座104铺',10,'2013-12-26 09:16:08',23,0,'S2013122605152872','合基物流',0,NULL,'440684000001983',98,NULL,NULL),(15,'2013-12-27 00:38:18',8,3,NULL,1,8,'2013-12-26 16:00:00',1,NULL,'1,1,1,5','440608782968605',42,'2005-11-29 16:00:00','张承强','13702632020',NULL,NULL,0,NULL,NULL,NULL,NULL,'荷城街道西安庆洲开发区',0,0,34,'652901198002020430','张承强','13702632020',NULL,'2014-02-21 07:30:57',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明天朝家具有限公司','440684000027604',NULL,'13702632020',NULL,10000000,'2013-12-27 00:38:18',8,0,'S2013122708375582','天朝家具',0,'440624782968605','440684000027604',97,NULL,NULL),(16,'2013-12-27 00:48:37',8,3,NULL,1,8,'2013-12-26 16:00:00',1,NULL,'1,1,1,5',NULL,45,'2003-04-01 16:00:00','李树志','13380210688',NULL,NULL,0,NULL,NULL,NULL,NULL,'沧江工业园',0,0,34,'440624196511065037','李树志','13380210688',NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明联星电路板有限公司','440684000039154',NULL,'13380210688',NULL,0,'2013-12-27 00:48:37',8,0,'S2013122708481388','联星电路板',0,NULL,'440684000039154',97,NULL,NULL),(17,'2013-12-27 00:55:46',8,3,NULL,1,8,'2013-12-26 16:00:00',1,NULL,'1,1,1,5',NULL,48,'2005-05-15 16:00:00','严扶庆','13908259488',NULL,NULL,0,NULL,NULL,NULL,NULL,'更合镇更合大道598、600号',0,0,34,'440624195706201532','严扶庆','13908259488',NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明区庆昌果仁食品有限公司','440684000014745',NULL,'13908259488',NULL,0,'2013-12-27 00:55:46',8,0,'S2013122708552294','庆昌果仁',0,NULL,'440684000014745',97,NULL,NULL),(18,'2013-12-27 01:14:15',8,3,NULL,1,8,'2013-12-26 16:00:00',1,NULL,'1,1,1,5',NULL,55,'2005-11-02 16:00:00','陈佩红','13802482880',NULL,NULL,0,NULL,NULL,NULL,NULL,'更合镇更合大道',0,0,34,'440623197103191219','李军辉',NULL,NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市美嘉油墨涂料有限公司','440684000000271',NULL,'13802482880',NULL,0,'2013-12-27 01:14:15',8,0,'S20131227091352108','美嘉油墨',0,NULL,'440684000000271',97,NULL,NULL),(19,'2013-12-27 03:02:01',9,3,NULL,1,9,'2013-12-26 16:00:00',1,NULL,'1,1,1,5','粤地税字440608797760401号',57,'2007-01-16 16:00:00','黄镁安','88862938',50,NULL,0,NULL,NULL,'谭凤英',NULL,'荷城街道纺织城丽景路',3000000,0,34,'440624196110010078','黄镁安','13928511088',NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,'440624196110010078','黄镁安','13928511088',0,NULL,'佛山市高明区盈富纺织染整有限公司','440684000028960',NULL,'13928511088','佛山市高明区荷城街道纺织城丽景路',3000000,'2013-12-27 03:02:01',9,0,'S20131227110135112','盈富',0,'粤国税字440624797760401号','440684000028960',97,NULL,'528500'),(20,'2013-12-30 09:36:20',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,87,'2004-03-31 16:00:00','梁暖银','13902417814',50,NULL,0,NULL,NULL,NULL,NULL,'更合镇延兴街6、8号',500000,0,34,NULL,'梁暖银','13902417814',NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,'梁暖银','13902417814',0,NULL,'佛山市高明新永业玻璃制品有限公司','440684000020836',NULL,'13902417814',NULL,500000,'2013-12-30 09:36:20',10,0,'S20131230053556172','新永业玻璃',0,NULL,'440684000020836',97,NULL,NULL),(21,'2013-12-30 09:43:15',10,3,NULL,1,10,'2013-12-30 09:48:10',1,NULL,'1,1,1,5',NULL,88,'2007-09-28 16:00:00','甘建堂','13702957541',NULL,NULL,0,NULL,NULL,NULL,NULL,'荷城街道广安路19路4座104铺',100000,0,34,NULL,'甘建堂','13702957541',NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,'甘建堂','13702957541',0,NULL,'佛山市高明区合基物流有限公司','666541282-4',NULL,'13702957541',NULL,100000,'2013-12-30 09:43:15',10,0,'S20131230054247174','合基物流',0,NULL,'440684000001983',106,NULL,NULL),(22,'2013-12-30 10:02:21',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,89,'2014-02-21 07:30:58','白海明','13928533218',NULL,NULL,0,NULL,NULL,NULL,NULL,'明城镇明七路388号',0,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明区文昌物业投资有限公司','440684000014421',NULL,'13928533218',NULL,0,'2013-12-30 10:02:21',10,0,'S20131230060158176','文昌物业',0,NULL,'440684000014421',106,NULL,NULL),(23,'2013-12-30 10:07:37',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,90,'2014-02-21 07:30:58','白海明','13928533218',NULL,NULL,0,NULL,NULL,NULL,NULL,'明城镇',0,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明区明城镇置业投资服务有限公司','440684000007205',NULL,'13928533218',NULL,0,'2013-12-30 10:07:37',10,0,'S20131230060713178','置业投资',0,NULL,'440684000007205',106,NULL,NULL),(24,'2013-12-30 10:09:35',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,91,'2014-02-21 07:30:58','白海明','13928533218',NULL,NULL,0,NULL,NULL,NULL,NULL,'明城镇',0,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明区明城民诚置业有限公司','440684000016128',NULL,'13928533218',NULL,0,'2013-12-30 10:09:35',10,0,'S20131230060910180','民诚置业',0,NULL,'440684000016128',106,NULL,NULL),(25,'2013-12-30 10:11:18',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,92,'2014-02-21 07:30:58','白海明','13928533218',NULL,NULL,0,NULL,NULL,NULL,NULL,'明城镇',0,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明区明城镇市政工程公司','440684000014341',NULL,'13928533218',NULL,0,'2013-12-30 10:11:18',10,0,'S20131230061054182','市政工程',0,NULL,'440684000014341',106,NULL,NULL),(26,'2013-12-30 10:13:07',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,93,'2009-03-19 16:00:00','周礼良','13609093239',NULL,NULL,0,NULL,NULL,NULL,NULL,'荷城沧江工业园海天大道1号2栋A区',150000,0,34,NULL,'周礼良','13609093239',NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,'周礼良','13609093239',0,NULL,'佛山市保升电路板有限公司','440684000013613',NULL,'13609093239','佛山市高明区荷城沧江工业园海天大道1号2栋A区',150000,'2013-12-30 10:13:07',10,0,'S20131230061229184','保升电路',0,NULL,'440684000013613',97,NULL,NULL),(27,'2013-12-30 10:15:27',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,94,'2011-08-09 16:00:00','林翠芳','13928544668',NULL,NULL,0,NULL,NULL,NULL,NULL,'荷城街道江湾区江根村良坑山地上晒谷房屋',500000,0,34,NULL,'陈建方',NULL,NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市富悦湾农业发展有限公司','440684000030139',NULL,'13928544668','佛山市高明区荷城街道江湾区江根村良坑山地上晒谷房屋',500000,'2013-12-30 10:15:27',10,0,'S20131230061505186','富悦湾',0,NULL,'440684000030139',100,NULL,NULL),(28,'2013-12-30 10:18:49',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,95,'2014-02-21 07:30:58','邓腾茂','13902853008',NULL,NULL,0,NULL,NULL,NULL,NULL,'荷城街道文明松涛四巷之4、5铺',0,0,34,NULL,'邓腾茂','13902853008',NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,'邓腾茂','13902853008',0,NULL,'佛山市高明区腾茂装饰工程有限公司','440684000027268',NULL,'13902853008','广东省佛山市高明区荷城街道文明松涛四巷之4、5铺',0,'2013-12-30 10:18:49',10,0,'S20131230061827188','腾茂装饰',0,NULL,'440684000027268',98,NULL,NULL),(29,'2013-12-30 10:21:29',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,96,'2014-02-21 07:30:58','林志锋','13902852360',NULL,NULL,0,NULL,NULL,NULL,NULL,'杨和镇和顺路78号',10000000,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明区杨和镇建和城镇建设开发有限公司','440684000014147',NULL,'13902852360','广东省佛山市高明区杨和镇和顺路78号',10000000,'2013-12-30 10:21:29',10,0,'S20131230062106190','城镇建设',0,NULL,'440684000014147',106,NULL,NULL),(30,'2013-12-30 10:23:24',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,97,'2014-02-21 07:30:58','林志锋','13902852360',NULL,NULL,0,NULL,NULL,NULL,NULL,'杨和镇高明大道中66号之二',8000000,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市高明区和源资产管理有限公司','440684000038594',NULL,'13902852360','广东省佛山市高明区杨和镇高明大道中66号之二',8000000,'2013-12-30 10:23:24',10,0,'S20131230062300192','和源资产',0,NULL,'440684000038594',106,NULL,NULL),(31,'2013-12-30 10:25:00',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,98,'2014-02-21 07:30:58','彭文华','13702857789',NULL,NULL,0,NULL,NULL,NULL,NULL,'明城镇明喜路1街12号',0,0,34,NULL,'彭文华','13702857789',NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,'彭文华','13702857789',0,NULL,'佛山市高明宝林木业有限公司','440684000019127',NULL,'13702857789','佛山市高明区明城镇明喜路1街12号',0,'2013-12-30 10:25:00',10,0,'S20131230062437194','宝林木业',0,NULL,'440684000019127',99,NULL,NULL),(32,'2013-12-30 10:27:16',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,99,'2011-03-21 16:00:00','罗永贃','13702985826',NULL,NULL,0,NULL,NULL,NULL,NULL,'明城镇高明大道西163号',2800000,0,34,'440682197910015019','罗永贃','13702985826',NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,'440682197910015019','罗永贃','13702985826',0,NULL,'佛山市金盈利建材有限公司','440684000027612',NULL,'13702985826','佛山市高明区明城镇高明大道西163号',2800000,'2013-12-30 10:27:16',10,0,'S20131230062653196','金盈利',0,NULL,'440684000027612',97,NULL,NULL),(33,'2013-12-30 10:30:36',10,3,NULL,1,10,'2013-12-29 16:00:00',1,NULL,'1,1,1,5',NULL,100,'2014-02-21 07:30:58','洗荣佳',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'荷城街道沿江路463号3座2112',100000,0,34,NULL,NULL,NULL,NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市汇胜贸易有限公司','440684000033993',NULL,NULL,'佛山市高明区荷城街道沿江路463号3座2112',100000,'2013-12-30 10:30:36',10,0,'S20131230063014198','汇胜贸易',0,NULL,'440684000033993',106,NULL,NULL),(34,'2013-12-30 10:34:04',10,3,NULL,1,10,'2014-01-14 09:05:17',1,NULL,NULL,NULL,101,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,NULL,0,0,NULL,NULL,NULL,NULL,NULL,'2014-02-21 07:30:58',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山市银胜贸易有限公司','59743460-1',NULL,NULL,NULL,NULL,'2013-12-30 10:34:04',10,0,'S20131230063342200',NULL,0,NULL,'440684000033977',NULL,NULL,NULL),(35,'2014-02-25 03:04:36',2,8,NULL,1,2,'2014-02-24 16:00:00',-1,'备注说明','1,1,1,1','440106197708049051',109,'2011-03-08 16:00:00','22','15013558667',50,'1245@163.com',50,'3333','440106197708049051','34','15013558667','2222',200000,50,32,'440106197708049051','333','15013558667','440106197708049051','2014-02-24 16:00:00','工商银行','622220235','6222202356564896','440106197708049051','23','15013558667',50,NULL,'伍学共花店','440106197708049051','440106197708049051','15013558667','2121245',200000,'2014-02-25 03:01:29',2,121110,'S20140225110125216','2222',0,'440106197708049051','440106197708049051',42,'http://www.wuxuegong.com','125658'),(36,'2014-04-02 07:49:08',2,8,NULL,1,2,'2014-04-02 07:49:08',-1,NULL,'1,1,1,1',NULL,122,'2010-07-31 16:00:00','陈建成',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'汾江路88号',0,0,34,NULL,NULL,NULL,NULL,'2014-04-02 07:49:04',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山方圆商贸有限公司','32657689070',NULL,NULL,NULL,0,'2014-04-02 07:46:59',2,0,'S20140402034655242','佛山方圆商贸',0,NULL,'243599677',43,NULL,NULL),(37,'2014-04-02 07:51:18',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'1,1,1,4',NULL,123,'2000-10-24 16:00:00','王明',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'春风路114号',0,0,37,NULL,NULL,NULL,NULL,'2014-04-02 07:51:18',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山万达电子有限公司','8758934590',NULL,NULL,NULL,0,'2014-04-02 07:49:46',2,0,'S20140402034943244','万达电子',0,NULL,'8758934590',43,NULL,NULL),(38,'2014-04-02 07:54:11',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'1,1,1,2',NULL,124,'2003-05-19 16:00:00','赵大伟',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'容桂路5号建成大厦1109',0,0,38,NULL,NULL,NULL,NULL,'2014-04-02 07:54:11',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'顺德康健医疗器械有限公司','467347846989',NULL,NULL,NULL,0,'2014-04-02 07:52:23',2,0,'S20140402035221246','顺德康健',0,NULL,'467347846989',42,NULL,NULL),(39,'2014-04-02 08:04:51',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,NULL,NULL,125,'2009-10-11 16:00:00','洪明山',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'大沥镇工业园路',0,0,36,NULL,NULL,NULL,NULL,'2014-04-02 08:04:51',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'佛山光明电子有限公司','37438439096',NULL,NULL,NULL,0,'2014-04-02 07:55:23',2,0,'S20140402035521248','光明电子',0,NULL,'37438439096',43,NULL,NULL),(40,'2014-04-09 03:57:54',24,1,NULL,1,24,'2014-04-08 16:00:00',1,NULL,'1,1,1,1',NULL,127,'2007-05-17 16:00:00','韩乐权',NULL,NULL,NULL,0,NULL,NULL,NULL,NULL,'德赛科技大厦19楼1903C',0,0,34,'15272819810224001X','韩乐权',NULL,NULL,'2014-04-08 16:00:00',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,'深圳市君天恒讯科技有限公司','440301103359161',NULL,'13534228888',NULL,0,'2014-04-09 02:41:49',24,0,'S20140409104143252','深圳市君天恒讯科技有限公司',0,NULL,'440301103359161',43,NULL,NULL);
/*!40000 ALTER TABLE `crm_ecustomer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_eeqstruct`
--

DROP TABLE IF EXISTS `crm_eeqstruct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_eeqstruct` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `ecustomerId` double DEFAULT NULL,
  `inAmount` double DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `outType` varchar(50) DEFAULT NULL,
  `percents` double DEFAULT NULL,
  `storderDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_eeqstruct`
--

LOCK TABLES `crm_eeqstruct` WRITE;
/*!40000 ALTER TABLE `crm_eeqstruct` DISABLE KEYS */;
INSERT INTO `crm_eeqstruct` VALUES (1,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:58',1,NULL,3,110000,'何松','47',11,'2002-02-04 16:00:00'),(2,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:58',1,NULL,3,880000,'廖杏桃','47',NULL,'2002-02-04 16:00:00'),(3,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:58',1,NULL,4,1000000,'罗嘉俊','47',100,'2011-08-10 16:00:00'),(4,'2013-12-25 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:30:58',1,NULL,1,20,'黎肇明','47',100,'2011-03-01 16:00:00'),(5,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:58',1,NULL,17,49,'严扶庆','47',90,'2005-05-15 16:00:00'),(6,'2013-12-26 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:58',1,NULL,17,5,'严伟国','47',10,'2005-05-15 16:00:00'),(7,'2013-12-26 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:30:58',1,NULL,19,100,'陈振兴','47',33.33,'2007-01-09 16:00:00'),(8,'2013-12-26 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:30:58',1,NULL,19,100,'黄镁安','47',33.33,'2007-01-09 16:00:00'),(9,'2013-12-26 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:30:58',1,NULL,19,100,'阳日江','47',33.33,'2007-01-09 16:00:00'),(10,'2014-04-08 16:00:00',24,1,-1,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,48000000,'韩乐权','47',96,'2007-05-17 16:00:00'),(11,'2014-04-08 16:00:00',24,1,-1,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,48000000,'韩乐权','47',96,'2007-05-17 16:00:00');
/*!40000 ALTER TABLE `crm_eeqstruct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_efinance`
--

DROP TABLE IF EXISTS `crm_efinance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_efinance` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `ecustomerId` double DEFAULT NULL,
  `endDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `hasBalance` smallint(6) DEFAULT NULL,
  `hasCash` smallint(6) DEFAULT NULL,
  `hasProfit` smallint(6) DEFAULT NULL,
  `reportType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_efinance`
--

LOCK TABLES `crm_efinance` WRITE;
/*!40000 ALTER TABLE `crm_efinance` DISABLE KEYS */;
/*!40000 ALTER TABLE `crm_efinance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_eownerborr`
--

DROP TABLE IF EXISTS `crm_eownerborr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_eownerborr` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `asstype` varchar(80) DEFAULT NULL,
  `creditBreed` varchar(80) DEFAULT NULL,
  `ecustomerId` double DEFAULT NULL,
  `limits` varchar(10) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `onwer` varchar(50) DEFAULT NULL,
  `onwerType` double DEFAULT NULL,
  `result` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_eownerborr`
--

LOCK TABLES `crm_eownerborr` WRITE;
/*!40000 ALTER TABLE `crm_eownerborr` DISABLE KEYS */;
/*!40000 ALTER TABLE `crm_eownerborr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_estate`
--

DROP TABLE IF EXISTS `crm_estate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_estate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `area` double DEFAULT NULL,
  `buyDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `buyType` int(11) DEFAULT NULL,
  `contributions` double DEFAULT NULL,
  `customerId` double DEFAULT NULL,
  `houseYear` int(11) DEFAULT NULL,
  `installments` int(11) DEFAULT NULL,
  `loanAmount` double DEFAULT NULL,
  `loanYear` varchar(10) DEFAULT NULL,
  `mortBank` varchar(100) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `runtime` varchar(10) DEFAULT NULL,
  `supCount` int(11) DEFAULT NULL,
  `totalInstall` int(11) DEFAULT NULL,
  `whoLived` varchar(50) DEFAULT NULL,
  `zAmount` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_estate`
--

LOCK TABLES `crm_estate` WRITE;
/*!40000 ALTER TABLE `crm_estate` DISABLE KEYS */;
INSERT INTO `crm_estate` VALUES (1,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:58',1,NULL,'佛山市顺德区龙江镇世埠新坑路伏龙里三巷7号',0,'2000-12-31 16:00:00',1,0,1,NULL,0,0,'0',NULL,0,'0',0,0,NULL,0),(2,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:58',1,NULL,'佛山市顺德区龙江镇世埠新坑路伏龙里三巷7号',0,'2000-12-31 16:00:00',1,0,2,NULL,0,0,'0',NULL,0,'0',0,0,NULL,0),(3,'2014-02-24 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,'111',11110,'2014-02-10 16:00:00',0,1110,51,2013,0,111110,'10','11',1110,'0',0,0,NULL,1110);
/*!40000 ALTER TABLE `crm_estate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_guacustomer`
--

DROP TABLE IF EXISTS `crm_guacustomer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_guacustomer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `cardNum` varchar(50) DEFAULT NULL,
  `cardType` int(11) DEFAULT NULL,
  `contactTel` varchar(30) DEFAULT NULL,
  `inAddress` varchar(100) DEFAULT NULL,
  `inArea` varchar(20) DEFAULT NULL,
  `isgua` int(11) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `phone` varchar(30) DEFAULT NULL,
  `relation` varchar(50) DEFAULT NULL,
  `baseId` double DEFAULT NULL,
  `contactor` varchar(50) DEFAULT NULL,
  `categoryId` bigint(20) DEFAULT NULL,
  `projectId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_guacustomer`
--

LOCK TABLES `crm_guacustomer` WRITE;
/*!40000 ALTER TABLE `crm_guacustomer` DISABLE KEYS */;
INSERT INTO `crm_guacustomer` VALUES (3,'2014-01-19 16:00:00',2,8,NULL,1,2,'2014-01-19 16:00:00',-1,NULL,'xx',0,'13702958333',NULL,NULL,1,'刘以辉','13702958333','xxxxx',86,NULL,NULL,NULL),(4,'2014-01-19 16:00:00',2,8,NULL,1,2,'2014-01-19 16:00:00',-1,NULL,'xx',1,'sss','wwww','1,1,1,5',1,'sss','ssss','sssss',86,NULL,NULL,NULL),(5,'2014-01-19 16:00:00',2,8,NULL,-1,2,'2014-01-19 16:00:00',-1,NULL,'xxxxxx',0,NULL,'xxxx','1,1,1,3',1,'xxxxxx','xxxxx','xxx',101,NULL,NULL,NULL),(6,'2014-01-19 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:30:58',-1,NULL,'440684000014147',3,NULL,'杨和镇和顺路78号','1,1,1,5',1,'佛山市高明区杨和镇建和城镇建设开发有限公司','13902852360','朋友',103,NULL,NULL,NULL),(7,'2014-01-19 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:30:58',-1,NULL,'xxxx',0,'xxx','xxxxx','1,1,1,2',1,'xxxx','xx','xx',103,'xxx',NULL,NULL),(8,'2014-02-24 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,'44011119830804461X',0,'111','111','1,1,1,1',1,'??? ','111','11',108,'111',NULL,NULL);
/*!40000 ALTER TABLE `crm_guacustomer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_otherinfo`
--

DROP TABLE IF EXISTS `crm_otherinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_otherinfo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `custType` int(11) DEFAULT NULL,
  `customerId` double DEFAULT NULL,
  `formType` int(11) DEFAULT NULL,
  `otherName` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_otherinfo`
--

LOCK TABLES `crm_otherinfo` WRITE;
/*!40000 ALTER TABLE `crm_otherinfo` DISABLE KEYS */;
INSERT INTO `crm_otherinfo` VALUES (1,'2014-02-24 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,'2222',0,51,28,'22');
/*!40000 ALTER TABLE `crm_otherinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crm_work`
--

DROP TABLE IF EXISTS `crm_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `crm_work` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `customerId` double DEFAULT NULL,
  `dept` varchar(100) DEFAULT NULL,
  `fax` varchar(20) DEFAULT NULL,
  `income` double DEFAULT NULL,
  `industry` varchar(50) DEFAULT NULL,
  `job` varchar(50) DEFAULT NULL,
  `nature` double DEFAULT NULL,
  `orgName` varchar(100) DEFAULT NULL,
  `payDay` int(11) DEFAULT NULL,
  `payment` varchar(50) DEFAULT NULL,
  `syears` int(11) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `zipcode` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crm_work`
--

LOCK TABLES `crm_work` WRITE;
/*!40000 ALTER TABLE `crm_work` DISABLE KEYS */;
INSERT INTO `crm_work` VALUES (1,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:59',1,NULL,NULL,1,'董事长室',NULL,1000000,NULL,'董事长',NULL,'佛山市高明骏有纺织染整有限公司',0,NULL,30,NULL,NULL,NULL),(2,'2013-12-25 16:00:00',8,3,-1,1,NULL,'2014-02-21 07:30:59',1,NULL,NULL,2,'总经理室',NULL,100000,NULL,'法定代表人',NULL,'佛山市高明骏有纺织染整有限公司',0,NULL,3,NULL,NULL,NULL),(3,'2014-02-24 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,NULL,51,'11',NULL,0,NULL,'111',NULL,'1111',0,NULL,110,NULL,NULL,NULL);
/*!40000 ALTER TABLE `crm_work` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_adamountlog`
--

DROP TABLE IF EXISTS `fc_adamountlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_adamountlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accountId` bigint(20) DEFAULT NULL,
  `advanceAmountId` bigint(20) NOT NULL,
  `fcnumber` varchar(30) DEFAULT NULL,
  `rectDate` datetime NOT NULL,
  `yamount` decimal(19,2) NOT NULL,
  `zmount` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_adamountlog`
--

LOCK TABLES `fc_adamountlog` WRITE;
/*!40000 ALTER TABLE `fc_adamountlog` DISABLE KEYS */;
INSERT INTO `fc_adamountlog` VALUES (1,'2014-03-14 17:19:48',1,-1,NULL,1,NULL,NULL,-1,NULL,NULL,1,NULL,'2014-03-14 00:00:00',0.00,200.00),(2,'2014-03-14 17:27:05',1,-1,NULL,1,NULL,NULL,-1,NULL,NULL,1,NULL,'2014-03-14 00:00:00',0.00,3000.00),(3,'2014-03-18 11:17:20',1,-1,NULL,1,NULL,NULL,-1,NULL,6,4,NULL,'2014-03-18 00:00:00',0.00,20000.00),(4,'2014-03-18 11:21:31',1,-1,NULL,1,NULL,NULL,-1,NULL,6,4,NULL,'2014-03-18 00:00:00',0.00,1000.00),(5,'2014-03-18 11:23:51',1,-1,NULL,1,NULL,NULL,-1,NULL,6,5,NULL,'2014-03-18 00:00:00',0.00,10000.00);
/*!40000 ALTER TABLE `fc_adamountlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_advanceamount`
--

DROP TABLE IF EXISTS `fc_advanceamount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_advanceamount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accountId` bigint(20) DEFAULT NULL,
  `custType` int(11) NOT NULL,
  `customerId` bigint(20) NOT NULL,
  `fcnumber` varchar(30) DEFAULT NULL,
  `rectDate` datetime NOT NULL,
  `totalamount` decimal(19,2) NOT NULL,
  `yamount` decimal(19,2) NOT NULL,
  `zmount` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_advanceamount`
--

LOCK TABLES `fc_advanceamount` WRITE;
/*!40000 ALTER TABLE `fc_advanceamount` DISABLE KEYS */;
INSERT INTO `fc_advanceamount` VALUES (1,'2014-03-14 00:00:00',2,8,NULL,1,2,'2014-03-14 00:00:00',-1,NULL,NULL,0,57,NULL,'2014-03-14 00:00:00',3200.00,0.00,3200.00),(2,'2014-03-17 11:18:12',13,4,NULL,1,13,'2014-03-17 11:18:12',1,NULL,6,0,58,NULL,'2014-03-17 00:00:00',0.00,0.00,0.00),(3,'2014-03-17 12:37:46',13,4,NULL,-1,13,'2014-03-17 20:38:09',1,NULL,8,0,59,NULL,'2014-03-17 00:00:00',0.00,0.00,0.00),(4,'2014-03-18 00:00:00',2,8,NULL,1,2,'2014-03-18 00:00:00',-1,NULL,6,1,4,NULL,'2014-03-18 00:00:00',21000.00,0.00,21000.00),(5,'2014-03-18 00:00:00',2,8,-1,-1,NULL,NULL,-1,'222',6,1,33,NULL,'2014-03-18 00:00:00',10000.00,0.00,10000.00),(6,'2014-03-18 08:01:11',2,8,NULL,1,2,'2014-03-18 09:20:01',-1,NULL,6,0,59,NULL,'2014-03-18 00:00:00',0.00,0.00,0.00);
/*!40000 ALTER TABLE `fc_advanceamount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_amountrecords`
--

DROP TABLE IF EXISTS `fc_amountrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_amountrecords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accountId` bigint(20) DEFAULT NULL,
  `bussTag` int(11) NOT NULL,
  `casualId` bigint(20) DEFAULT NULL,
  `cat` decimal(19,2) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  `dat` decimal(19,2) NOT NULL,
  `fat` decimal(19,2) NOT NULL,
  `fcnumber` varchar(30) DEFAULT NULL,
  `invoceId` bigint(20) NOT NULL,
  `mat` decimal(19,2) NOT NULL,
  `pat` decimal(19,2) NOT NULL,
  `rat` decimal(19,2) NOT NULL,
  `rectDate` datetime NOT NULL,
  `tat` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_amountrecords`
--

LOCK TABLES `fc_amountrecords` WRITE;
/*!40000 ALTER TABLE `fc_amountrecords` DISABLE KEYS */;
INSERT INTO `fc_amountrecords` VALUES (48,'2014-03-18 09:20:01',2,8,NULL,1,2,'2014-03-18 09:20:01',-1,NULL,6,0,NULL,7455.96,12,0.00,0.00,'134',105,0.00,0.00,2000.00,'2014-03-18 00:00:00',9455.96),(49,'2014-03-18 09:20:01',2,8,NULL,1,2,'2014-03-18 09:20:01',-1,NULL,6,0,NULL,0.00,12,0.00,0.00,'135',104,0.00,0.00,266.67,'2014-03-18 00:00:00',266.67);
/*!40000 ALTER TABLE `fc_amountrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_apply`
--

DROP TABLE IF EXISTS `fc_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_apply` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `appAmount` decimal(19,2) NOT NULL,
  `appdate` datetime DEFAULT NULL,
  `arate` double DEFAULT NULL,
  `aunint` int(11) NOT NULL,
  `borAccount` varchar(50) DEFAULT NULL,
  `borBank` varchar(50) DEFAULT NULL,
  `breed` bigint(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `currentDate` datetime DEFAULT NULL,
  `custType` int(11) NOT NULL,
  `customerId` bigint(20) NOT NULL,
  `dayLoan` int(11) NOT NULL,
  `frate` double DEFAULT NULL,
  `ftype` int(11) NOT NULL,
  `funint` int(11) NOT NULL,
  `guaId` varchar(200) DEFAULT NULL,
  `inType` bigint(20) NOT NULL,
  `isadvance` int(11) DEFAULT NULL,
  `limitType` bigint(20) NOT NULL,
  `loanMain` bigint(20) NOT NULL,
  `loanType` varchar(30) NOT NULL,
  `mainLoanType` varchar(30) DEFAULT NULL,
  `manager` bigint(20) DEFAULT NULL,
  `mgrtype` int(11) DEFAULT NULL,
  `monthLoan` int(11) NOT NULL,
  `mrate` double DEFAULT NULL,
  `munint` int(11) NOT NULL,
  `payType` varchar(20) NOT NULL,
  `paydPhases` int(11) DEFAULT NULL,
  `payremark` longtext,
  `phAmount` double NOT NULL,
  `prate` double DEFAULT NULL,
  `procId` varchar(20) DEFAULT NULL,
  `punint` int(11) NOT NULL,
  `rate` double NOT NULL,
  `rateType` int(11) NOT NULL,
  `realMan` varchar(60) DEFAULT NULL,
  `reason` longtext,
  `referrals` varchar(50) DEFAULT NULL,
  `sourceDesc` longtext,
  `sourceType` bigint(20) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `totalPhases` int(11) DEFAULT NULL,
  `unint` int(11) NOT NULL,
  `urate` double DEFAULT NULL,
  `utype` int(11) NOT NULL,
  `uunint` int(11) NOT NULL,
  `xstatus` int(11) NOT NULL,
  `yearLoan` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_apply`
--

LOCK TABLES `fc_apply` WRITE;
/*!40000 ALTER TABLE `fc_apply` DISABLE KEYS */;
INSERT INTO `fc_apply` VALUES (1,'2014-03-09 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,100000.00,'2014-03-09 00:00:00',0,2,NULL,NULL,11,'A2014030905103800',NULL,0,48,0,50,1,2,NULL,65,0,77,59,'70',NULL,2,0,6,0,2,'P0001',NULL,NULL,0,0,NULL,2,20,1,NULL,NULL,NULL,NULL,NULL,0,NULL,2,50,1,2,0,0),(2,'2014-03-10 00:00:00',10,3,NULL,1,10,'2014-03-10 00:00:00',1,NULL,100000.00,'2014-03-10 00:00:00',0,1,NULL,NULL,11,'A2014031009565602',NULL,0,48,0,3,1,1,NULL,65,0,77,59,'70',NULL,10,1,12,2,1,'P0001',NULL,NULL,0,0,'5',1,2,1,NULL,NULL,NULL,NULL,NULL,1,NULL,1,3,1,1,0,0),(3,'2014-03-11 00:00:00',10,3,-1,1,NULL,NULL,1,NULL,100000.00,'2014-03-11 00:00:00',0,1,'4211212121212112','招商银行',11,'A2014031110140404',NULL,0,49,0,3,1,1,NULL,65,0,77,59,'70',NULL,10,0,12,0,1,'P0001',NULL,NULL,0,0,'101',1,2,1,NULL,NULL,NULL,NULL,NULL,1,NULL,1,3,1,1,0,0),(4,'2014-03-12 00:00:00',9,3,-1,1,22,'2014-03-12 18:36:22',1,'放款单审批后，生成了放款单[2014-03-12 06:36:22]',20000.00,'2014-03-12 00:00:00',0,1,'6220236523654584','招商银行',11,'A2014031205574506',NULL,0,52,0,0,1,1,NULL,65,0,78,60,'71',NULL,9,0,0,0,1,'P0006',NULL,NULL,0,0,'201',1,2,1,NULL,NULL,NULL,NULL,NULL,15,13,1,0,1,1,0,1),(5,'2014-03-12 00:00:00',9,3,-1,1,22,'2014-03-12 18:52:13',1,'放款单审批后，生成了放款单[2014-03-12 06:52:13]',20000.00,'2014-03-12 00:00:00',0,1,'622023652658954','招商银行',11,'A2014031206481208',NULL,0,53,0,0,1,1,NULL,66,0,78,59,'71',NULL,9,0,0,0,1,'P0001',NULL,NULL,0,0,'206',1,2,1,NULL,NULL,NULL,NULL,NULL,1,13,1,0,1,1,0,1),(6,'2014-03-12 00:00:00',9,3,-1,1,22,'2014-03-12 21:09:39',1,'放款单审批后，生成了放款单[2014-03-12 09:09:39]',20000.00,'2014-03-12 00:00:00',0,1,'622','招商银行',11,'A2014031208571710',NULL,0,54,0,0,1,1,NULL,66,0,77,60,'72',NULL,9,0,0,0,1,'P0006',NULL,NULL,0,0,'301',1,2,1,NULL,NULL,NULL,NULL,NULL,1,12,1,0,1,1,0,1),(7,'2014-03-14 00:00:00',9,3,-1,1,2,'2014-03-14 11:30:25',1,'放款单审批后，生成了放款单[2014-03-14 11:20:00]',100000.00,'2014-03-14 00:00:00',0,1,'22','11',11,'A2014031411170412',NULL,0,55,0,0,1,1,NULL,66,0,77,60,'72',NULL,9,0,0,0,1,'P0008',2,NULL,0,0,'401',1,2,1,NULL,NULL,NULL,NULL,NULL,4,13,1,0,1,1,0,1),(8,'2014-03-14 00:00:00',9,3,-1,1,13,'2014-03-14 13:52:40',1,'放款单审批后，生成了放款单[2014-03-14 01:51:57]',100000.00,'2014-03-14 00:00:00',0,1,'622202365521544','招商银行',11,'A2014031401491914',NULL,0,56,0,0,1,1,NULL,66,0,77,59,'71',NULL,9,0,0,0,1,'P0006',NULL,NULL,0,0,'501',1,2,1,NULL,NULL,NULL,NULL,NULL,3,13,1,0,1,1,0,1),(9,'2014-03-14 00:00:00',9,3,-1,1,13,'2014-03-14 15:14:27',1,'放款单审批后，生成了放款单[2014-03-14 03:13:14]',100000.00,'2014-03-14 00:00:00',0,1,'62220236523','招商银行',11,'A2014031402594916',NULL,0,57,0,0,1,1,NULL,65,0,77,59,'71',NULL,9,0,0,0,1,'P0002',NULL,NULL,5000,0,'506',1,2,1,NULL,NULL,NULL,NULL,NULL,3,13,1,0,1,1,0,1),(10,'2014-03-15 00:00:00',9,3,-1,1,22,'2014-03-15 11:21:55',1,NULL,20000.00,'2014-03-15 00:00:00',5,1,'62220236256589554','招商银行',11,'A2014031511183418',NULL,0,55,0,3,1,1,NULL,66,0,78,60,'71',NULL,9,0,0,0,1,'P0001',NULL,'贷款',0,3,'601',1,2,1,'洪为','申请成功',NULL,'还款',81,1,NULL,1,3,1,1,0,1),(11,'2014-03-17 00:00:00',9,3,-1,1,22,'2014-03-17 11:11:10',1,'放款单审批后，生成了放款单[2014-03-17 11:11:10]',200000.00,'2014-03-17 00:00:00',0,1,'622202365212544','招商银行',11,'A2014031710172420',NULL,0,52,0,0,1,1,NULL,66,0,78,60,'70',NULL,9,0,0,0,1,'P0008',NULL,NULL,0,0,'701',1,2,1,'张杰',NULL,'张宏',NULL,NULL,15,13,1,0,1,1,0,1),(12,'2014-03-17 00:00:00',9,3,-1,1,-99999999,'2014-04-26 03:00:01',1,'放款单审批后，生成了放款单[2014-03-17 11:16:59]',300000.00,'2014-03-17 00:00:00',0,1,'62220236525841','招商银行',11,'A2014031711143322',NULL,0,58,0,3,1,1,NULL,66,0,78,59,'71',NULL,9,0,0,0,1,'P0006',1,NULL,0,0,'706',1,2,1,NULL,NULL,NULL,NULL,NULL,5,13,1,3,1,1,0,1),(13,'2014-03-17 00:00:00',9,3,-1,1,-99999999,'2014-04-26 03:00:01',1,'放款单审批后，生成了放款单[2014-03-17 12:35:51]',100000.00,'2014-03-17 00:00:00',0,1,'6222023652365121','招商银行',11,'A2014031700153324',NULL,0,59,0,3,1,1,NULL,65,0,78,59,'71',NULL,9,0,0,0,1,'P0006',1,NULL,0,0,'801',1,2,1,NULL,NULL,NULL,NULL,83,5,13,1,3,1,1,3,1),(14,'2014-03-17 00:00:00',9,3,-1,1,22,'2014-03-17 18:02:17',1,'放款单审批后，生成了放款单[2014-03-17 06:02:17]',100000.00,'2014-03-17 00:00:00',0,1,'62220236451','招商银行',11,'A2014031705553826',NULL,0,53,0,3,1,1,NULL,66,0,77,59,'70',NULL,9,0,0,0,1,'P0006',NULL,NULL,0,0,'901',1,2,1,NULL,NULL,NULL,NULL,NULL,1,13,1,3,1,1,0,1),(15,'2014-03-17 00:00:00',9,3,-1,1,-99999999,'2014-04-26 03:00:01',1,'放款单审批后，生成了放款单[2014-03-17 06:30:28]',10000.00,'2014-03-17 00:00:00',0,1,'62220236522547','招商银行',11,'A2014031706253728',NULL,0,60,0,3,1,1,NULL,66,0,78,60,'72',NULL,9,0,0,0,1,'P0006',1,NULL,0,0,'905',1,2,1,NULL,NULL,NULL,NULL,NULL,5,13,1,3,1,1,0,1),(16,'2014-03-17 00:00:00',9,3,-1,1,22,'2014-03-17 21:03:22',1,NULL,20000.00,'2014-03-17 00:00:00',0,1,'22','11',11,'A2014031708165230',NULL,0,54,0,0,1,1,NULL,67,0,78,59,'70',NULL,9,0,0,0,1,'P0006',NULL,NULL,0,0,'1001',1,2,1,NULL,NULL,NULL,NULL,NULL,1,NULL,1,0,1,1,0,1),(17,'2014-04-02 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,300000.00,'2014-04-02 00:00:00',0,1,NULL,NULL,11,'A2014040204085932',NULL,0,64,0,0,1,1,NULL,65,0,78,59,'70',NULL,2,0,6,0,1,'P0001',NULL,NULL,0,0,'1101',1,2,1,NULL,NULL,NULL,NULL,NULL,1,NULL,1,0,1,1,0,0),(18,'2014-04-02 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,300000.00,'2014-04-02 00:00:00',0,1,NULL,NULL,11,'A2014040204170834',NULL,0,64,0,0,1,1,NULL,65,0,78,59,'70',NULL,2,0,6,0,1,'P0001',NULL,NULL,0,0,'1105',1,2,1,NULL,NULL,NULL,NULL,NULL,1,NULL,1,0,1,1,0,0),(19,'2014-04-02 00:00:00',2,8,-1,1,22,'2014-04-02 16:26:40',-1,'放款单审批后，生成了放款单[2014-04-02 04:26:40]',300000.00,'2014-04-02 00:00:00',0,1,'3273874996','中国银行',11,'A2014040204204936',NULL,0,64,0,0,1,1,NULL,65,0,78,59,'70',NULL,2,0,6,0,1,'P0001',NULL,NULL,0,0,'1109',1,2,1,NULL,NULL,NULL,NULL,NULL,15,6,1,0,1,1,0,0),(20,'2014-04-02 00:00:00',24,1,-1,1,22,'2014-04-02 16:50:50',1,'放款单审批后，生成了放款单[2014-04-02 04:50:50]',500000.00,'2014-04-02 00:00:00',0,1,'37858368994','中国银行',11,'A2014040204474238',NULL,0,62,0,0,1,1,NULL,65,0,78,59,'70',NULL,24,0,6,0,1,'P0001',NULL,NULL,0,0,'1114',1,2,1,NULL,NULL,NULL,NULL,NULL,15,6,1,0,1,1,0,0),(21,'2014-04-09 00:00:00',24,1,-1,1,NULL,NULL,1,NULL,5000000.00,'2014-04-05 00:00:00',2,1,NULL,NULL,10,'A2014040911015840',NULL,0,66,5,0,1,1,NULL,69,0,77,60,'70',NULL,24,0,8,0,1,'P0001',NULL,'备货、流动资金',0,0,NULL,1,2,1,NULL,NULL,NULL,'银行应收帐款，房子的空间',NULL,0,NULL,1,0,1,1,0,2014);
/*!40000 ALTER TABLE `fc_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_appraise`
--

DROP TABLE IF EXISTS `fc_appraise`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_appraise` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `address` varchar(200) DEFAULT NULL,
  `appAmount` decimal(19,2) NOT NULL,
  `applyId` bigint(20) NOT NULL,
  `auditDate` datetime DEFAULT NULL,
  `breed` bigint(20) NOT NULL,
  `custType` int(11) NOT NULL,
  `customerId` bigint(20) NOT NULL,
  `dayLoan` int(11) NOT NULL,
  `hostMan` bigint(20) NOT NULL,
  `monthLoan` int(11) NOT NULL,
  `opinion` longtext,
  `rate` double NOT NULL,
  `rateType` int(11) NOT NULL,
  `recordMan` bigint(20) NOT NULL,
  `resultTag` int(11) DEFAULT NULL,
  `situation` longtext NOT NULL,
  `yearLoan` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_appraise`
--

LOCK TABLES `fc_appraise` WRITE;
/*!40000 ALTER TABLE `fc_appraise` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_appraise` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_auditamount`
--

DROP TABLE IF EXISTS `fc_auditamount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_auditamount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `appAmount` decimal(19,2) DEFAULT NULL,
  `arecordId` bigint(20) NOT NULL,
  `dayLoan` int(11) DEFAULT NULL,
  `monthLoan` int(11) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `rateType` int(11) DEFAULT NULL,
  `yearLoan` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_auditamount`
--

LOCK TABLES `fc_auditamount` WRITE;
/*!40000 ALTER TABLE `fc_auditamount` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_auditamount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_bfamount`
--

DROP TABLE IF EXISTS `fc_bfamount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_bfamount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accountId` bigint(20) DEFAULT NULL,
  `bfamount` decimal(19,2) NOT NULL,
  `canamount` decimal(19,2) NOT NULL,
  `custBaseId` bigint(20) NOT NULL,
  `lastDate` datetime DEFAULT NULL,
  `netamount` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_bfamount`
--

LOCK TABLES `fc_bfamount` WRITE;
/*!40000 ALTER TABLE `fc_bfamount` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_bfamount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_bfrecords`
--

DROP TABLE IF EXISTS `fc_bfrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_bfrecords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `bfamountId` bigint(20) NOT NULL,
  `cat` decimal(19,2) NOT NULL,
  `fcnumber` varchar(30) DEFAULT NULL,
  `netamount` decimal(19,2) NOT NULL,
  `rectDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_bfrecords`
--

LOCK TABLES `fc_bfrecords` WRITE;
/*!40000 ALTER TABLE `fc_bfrecords` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_bfrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_casualrecords`
--

DROP TABLE IF EXISTS `fc_casualrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_casualrecords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accountId` bigint(20) DEFAULT NULL,
  `bussTag` int(11) NOT NULL,
  `cat` decimal(19,2) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  `dat` decimal(19,2) NOT NULL,
  `fat` decimal(19,2) NOT NULL,
  `fcnumber` varchar(30) DEFAULT NULL,
  `mat` decimal(19,2) NOT NULL,
  `pat` decimal(19,2) NOT NULL,
  `rat` decimal(19,2) NOT NULL,
  `rectDate` datetime NOT NULL,
  `tat` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_casualrecords`
--

LOCK TABLES `fc_casualrecords` WRITE;
/*!40000 ALTER TABLE `fc_casualrecords` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_casualrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_childplan`
--

DROP TABLE IF EXISTS `fc_childplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_childplan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `einterest` decimal(19,2) NOT NULL,
  `emgrAmount` decimal(19,2) NOT NULL,
  `interest` decimal(19,2) NOT NULL,
  `loanInvoceId` bigint(20) NOT NULL,
  `mgrAmount` decimal(19,2) NOT NULL,
  `phases` int(11) NOT NULL,
  `principal` decimal(19,2) NOT NULL,
  `reprincipal` decimal(19,2) NOT NULL,
  `totalAmount` decimal(19,2) NOT NULL,
  `xpayDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_childplan`
--

LOCK TABLES `fc_childplan` WRITE;
/*!40000 ALTER TABLE `fc_childplan` DISABLE KEYS */;
INSERT INTO `fc_childplan` VALUES (1,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,266.67,0.00,386.67,1,0.00,1,0.00,20000.00,386.67,'2014-04-10 00:00:00'),(2,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,280.00,0.00,400.00,1,0.00,2,1491.19,18508.81,1891.19,'2014-05-10 00:00:00'),(3,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,271.46,0.00,382.52,1,0.00,3,1508.68,17000.13,1891.19,'2014-06-10 00:00:00'),(4,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,238.00,0.00,340.00,1,0.00,4,1551.19,15448.94,1891.19,'2014-07-10 00:00:00'),(5,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,226.58,0.00,319.28,1,0.00,5,1571.91,13877.03,1891.19,'2014-08-10 00:00:00'),(6,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,203.53,0.00,286.79,1,0.00,6,1604.40,12272.63,1891.19,'2014-09-10 00:00:00'),(7,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,171.82,0.00,245.45,1,0.00,7,1645.74,10626.89,1891.19,'2014-10-10 00:00:00'),(8,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,155.86,0.00,219.62,1,0.00,8,1671.57,8955.32,1891.19,'2014-11-10 00:00:00'),(9,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,125.37,0.00,179.11,1,0.00,9,1712.09,7243.23,1891.19,'2014-12-10 00:00:00'),(10,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,106.23,0.00,149.69,1,0.00,10,1741.50,5501.73,1891.19,'2015-01-10 00:00:00'),(11,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,80.69,0.00,113.70,1,0.00,11,1777.49,3724.24,1891.19,'2015-02-10 00:00:00'),(12,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,47.17,0.00,69.52,1,0.00,12,1821.67,1902.57,1891.19,'2015-03-10 00:00:00'),(13,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,26.64,0.00,1.27,1,0.00,13,1902.57,0.00,1903.84,'2015-03-11 00:00:00'),(14,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,266.67,0.00,386.67,2,0.00,1,0.00,20000.00,386.67,'2014-04-10 00:00:00'),(15,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,280.00,0.00,400.00,2,0.00,2,0.00,20000.00,400.00,'2014-05-10 00:00:00'),(16,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,293.33,0.00,413.33,2,0.00,3,0.00,20000.00,413.33,'2014-06-10 00:00:00'),(17,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,280.00,0.00,400.00,2,0.00,4,0.00,20000.00,400.00,'2014-07-10 00:00:00'),(18,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,293.33,0.00,413.33,2,0.00,5,0.00,20000.00,413.33,'2014-08-10 00:00:00'),(19,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,293.33,0.00,413.33,2,0.00,6,0.00,20000.00,413.33,'2014-09-10 00:00:00'),(20,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,280.00,0.00,400.00,2,0.00,7,0.00,20000.00,400.00,'2014-10-10 00:00:00'),(21,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,293.33,0.00,413.33,2,0.00,8,0.00,20000.00,413.33,'2014-11-10 00:00:00'),(22,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,280.00,0.00,400.00,2,0.00,9,0.00,20000.00,400.00,'2014-12-10 00:00:00'),(23,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,293.33,0.00,413.33,2,0.00,10,0.00,20000.00,413.33,'2015-01-10 00:00:00'),(24,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,293.33,0.00,413.33,2,0.00,11,0.00,20000.00,413.33,'2015-02-10 00:00:00'),(25,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,253.33,0.00,373.33,2,0.00,12,0.00,20000.00,373.33,'2015-03-10 00:00:00'),(26,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,26.67,0.00,13.33,2,0.00,13,20000.00,0.00,20013.33,'2015-03-11 00:00:00'),(27,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,0.00,0.00,413.33,3,0.00,1,0.00,20000.00,413.33,'2014-04-10 00:00:00'),(28,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,280.00,0.00,400.00,3,0.00,2,1643.56,18356.44,2043.56,'2014-05-10 00:00:00'),(29,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,269.23,0.00,379.37,3,0.00,3,1664.19,16692.25,2043.56,'2014-06-10 00:00:00'),(30,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,233.69,0.00,333.85,3,0.00,4,1709.71,14982.54,2043.56,'2014-07-10 00:00:00'),(31,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,219.74,0.00,309.64,3,0.00,5,1733.92,13248.62,2043.56,'2014-08-10 00:00:00'),(32,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,194.31,0.00,273.80,3,0.00,6,1769.75,11478.87,2043.56,'2014-09-10 00:00:00'),(33,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,160.70,0.00,229.58,3,0.00,7,1813.98,9664.89,2043.56,'2014-10-10 00:00:00'),(34,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,141.75,0.00,199.74,3,0.00,8,1843.82,7821.07,2043.56,'2014-11-10 00:00:00'),(35,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,109.49,0.00,156.42,3,0.00,9,1887.14,5933.93,2043.56,'2014-12-10 00:00:00'),(36,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,87.03,0.00,122.63,3,0.00,10,1920.92,4013.01,2043.56,'2015-01-10 00:00:00'),(37,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,58.86,0.00,82.94,3,0.00,11,1960.62,2052.39,2043.56,'2015-02-10 00:00:00'),(38,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,26.00,0.00,36.94,3,0.00,12,2052.39,0.00,2089.33,'2015-03-09 00:00:00'),(39,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1200.00,0.00,1800.00,4,0.00,1,0.00,100000.00,1800.00,'2014-04-10 00:00:00'),(40,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1400.00,0.00,2000.00,4,0.00,2,0.00,100000.00,2000.00,'2014-05-10 00:00:00'),(41,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1466.67,0.00,2066.67,4,0.00,3,2000.00,98000.00,4066.67,'2014-06-10 00:00:00'),(42,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1372.00,0.00,1960.00,4,0.00,4,2000.00,96000.00,3960.00,'2014-07-10 00:00:00'),(43,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1408.00,0.00,1984.00,4,0.00,5,2000.00,94000.00,3984.00,'2014-08-10 00:00:00'),(44,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1378.67,0.00,1942.67,4,0.00,6,0.00,94000.00,1942.67,'2014-09-10 00:00:00'),(45,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1316.00,0.00,1880.00,4,0.00,7,0.00,94000.00,1880.00,'2014-10-10 00:00:00'),(46,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1378.67,0.00,1942.67,4,0.00,8,0.00,94000.00,1942.67,'2014-11-10 00:00:00'),(47,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1316.00,0.00,1880.00,4,0.00,9,2000.00,92000.00,3880.00,'2014-12-10 00:00:00'),(48,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1349.33,0.00,1901.33,4,0.00,10,2000.00,90000.00,3901.33,'2015-01-10 00:00:00'),(49,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1320.00,0.00,1860.00,4,0.00,11,2000.00,88000.00,3860.00,'2015-02-10 00:00:00'),(50,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,1114.67,0.00,1642.67,4,0.00,12,2000.00,86000.00,3642.67,'2015-03-10 00:00:00'),(51,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,229.33,0.00,172.00,4,0.00,13,86000.00,0.00,86172.00,'2015-03-13 00:00:00'),(52,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,1200.00,0.00,1800.00,5,0.00,1,0.00,100000.00,1800.00,'2014-04-10 00:00:00'),(53,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,1400.00,0.00,2000.00,5,0.00,2,7455.96,92544.04,9455.96,'2014-05-10 00:00:00'),(54,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,1357.31,0.00,1912.58,5,0.00,3,7543.38,85000.66,9455.96,'2014-06-10 00:00:00'),(55,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,1190.01,0.00,1700.01,5,0.00,4,7755.95,77244.71,9455.96,'2014-07-10 00:00:00'),(56,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,1132.92,0.00,1596.39,5,0.00,5,7859.57,69385.14,9455.96,'2014-08-10 00:00:00'),(57,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,1017.65,0.00,1433.96,5,0.00,6,8022.00,61363.14,9455.96,'2014-09-10 00:00:00'),(58,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,859.08,0.00,1227.26,5,0.00,7,8228.70,53134.44,9455.96,'2014-10-10 00:00:00'),(59,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,779.31,0.00,1098.11,5,0.00,8,8357.85,44776.59,9455.96,'2014-11-10 00:00:00'),(60,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,626.87,0.00,895.53,5,0.00,9,8560.43,36216.16,9455.96,'2014-12-10 00:00:00'),(61,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,531.17,0.00,748.47,5,0.00,10,8707.49,27508.67,9455.96,'2015-01-10 00:00:00'),(62,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,403.46,0.00,568.51,5,0.00,11,8887.45,18621.22,9455.96,'2015-02-10 00:00:00'),(63,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,235.87,0.00,347.60,5,0.00,12,9108.36,9512.86,9455.96,'2015-03-10 00:00:00'),(64,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,120.50,0.00,19.03,5,0.00,13,9512.86,0.00,9531.89,'2015-03-13 00:00:00'),(65,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,1200.00,0.00,1800.00,6,0.00,1,5000.00,95000.00,6800.00,'2014-04-10 00:00:00'),(66,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,1330.00,0.00,1900.00,6,0.00,2,5000.00,90000.00,6900.00,'2014-05-10 00:00:00'),(67,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,1320.00,0.00,1860.00,6,0.00,3,5000.00,85000.00,6860.00,'2014-06-10 00:00:00'),(68,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,1190.00,0.00,1700.00,6,0.00,4,5000.00,80000.00,6700.00,'2014-07-10 00:00:00'),(69,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,1173.33,0.00,1653.33,6,0.00,5,5000.00,75000.00,6653.33,'2014-08-10 00:00:00'),(70,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,1100.00,0.00,1550.00,6,0.00,6,5000.00,70000.00,6550.00,'2014-09-10 00:00:00'),(71,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,980.00,0.00,1400.00,6,0.00,7,5000.00,65000.00,6400.00,'2014-10-10 00:00:00'),(72,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,953.33,0.00,1343.33,6,0.00,8,5000.00,60000.00,6343.33,'2014-11-10 00:00:00'),(73,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,840.00,0.00,1200.00,6,0.00,9,5000.00,55000.00,6200.00,'2014-12-10 00:00:00'),(74,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,806.67,0.00,1136.67,6,0.00,10,5000.00,50000.00,6136.67,'2015-01-10 00:00:00'),(75,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,733.33,0.00,1033.33,6,0.00,11,5000.00,45000.00,6033.33,'2015-02-10 00:00:00'),(76,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,570.00,0.00,840.00,6,0.00,12,5000.00,40000.00,5840.00,'2015-03-10 00:00:00'),(77,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,106.67,0.00,80.00,6,0.00,13,40000.00,0.00,40080.00,'2015-03-13 00:00:00'),(78,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,2000.00,0.00,3200.00,8,0.00,1,0.00,200000.00,3200.00,'2014-04-10 00:00:00'),(79,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,2800.00,0.00,4000.00,8,0.00,2,0.00,200000.00,4000.00,'2014-05-10 00:00:00'),(80,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,2933.33,0.00,4133.33,8,0.00,3,8000.00,192000.00,12133.33,'2014-06-10 00:00:00'),(81,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,2688.00,0.00,3840.00,8,0.00,4,8000.00,184000.00,11840.00,'2014-07-10 00:00:00'),(82,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,2698.67,0.00,3802.67,8,0.00,5,8000.00,176000.00,11802.67,'2014-08-10 00:00:00'),(83,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,2581.33,0.00,3637.33,8,0.00,6,8000.00,168000.00,11637.33,'2014-09-10 00:00:00'),(84,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,2352.00,0.00,3360.00,8,0.00,7,8000.00,160000.00,11360.00,'2014-10-10 00:00:00'),(85,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,2346.67,0.00,3306.67,8,0.00,8,8000.00,152000.00,11306.67,'2014-11-10 00:00:00'),(86,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,2128.00,0.00,3040.00,8,0.00,9,8000.00,144000.00,11040.00,'2014-12-10 00:00:00'),(87,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,2112.00,0.00,2976.00,8,0.00,10,8000.00,136000.00,10976.00,'2015-01-10 00:00:00'),(88,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,1994.67,0.00,2810.67,8,0.00,11,8000.00,128000.00,10810.67,'2015-02-10 00:00:00'),(89,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,1621.33,0.00,2389.33,8,0.00,12,8000.00,120000.00,10389.33,'2015-03-10 00:00:00'),(90,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,560.00,0.00,480.00,8,0.00,13,120000.00,0.00,120480.00,'2015-03-16 00:00:00'),(91,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,3000.00,0.00,4800.00,9,0.00,1,0.00,300000.00,4800.00,'2013-03-10 00:00:00'),(92,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,4400.00,0.00,6200.00,9,0.00,2,22167.88,277832.12,28367.88,'2013-04-10 00:00:00'),(93,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,3889.65,0.00,5556.64,9,0.00,3,22811.24,255020.88,28367.88,'2013-05-10 00:00:00'),(94,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,3740.31,0.00,5270.43,9,0.00,4,23097.45,231923.43,28367.88,'2013-06-10 00:00:00'),(95,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,3246.93,0.00,4638.47,9,0.00,5,23729.41,208194.02,28367.88,'2013-07-10 00:00:00'),(96,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,3053.51,0.00,4302.68,9,0.00,6,24065.20,184128.82,28367.88,'2013-08-10 00:00:00'),(97,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,2700.56,0.00,3805.33,9,0.00,7,24562.55,159566.27,28367.88,'2013-09-10 00:00:00'),(98,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,2233.93,0.00,3191.33,9,0.00,8,25176.55,134389.72,28367.88,'2013-10-10 00:00:00'),(99,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,1971.05,0.00,2777.39,9,0.00,9,25590.49,108799.23,28367.88,'2013-11-10 00:00:00'),(100,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,1523.19,0.00,2175.98,9,0.00,10,26191.89,82607.34,28367.88,'2013-12-10 00:00:00'),(101,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,1211.57,0.00,1707.22,9,0.00,11,26660.66,55946.68,28367.88,'2014-01-10 00:00:00'),(102,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,820.55,0.00,1156.23,9,0.00,12,27211.65,28735.03,28367.88,'2014-02-10 00:00:00'),(103,'2014-03-17 11:16:59',22,2,NULL,1,NULL,NULL,1,NULL,306.51,0.00,57.47,9,0.00,13,28735.03,0.00,28792.50,'2014-02-13 00:00:00'),(104,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,0.00,0.00,266.67,10,0.00,1,0.00,100000.00,266.67,'2013-11-10 00:00:00'),(105,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,1400.00,0.00,2000.00,10,0.00,2,7455.96,92544.04,9455.96,'2013-12-10 00:00:00'),(106,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,1357.31,0.00,1912.58,10,0.00,3,7543.38,85000.66,9455.96,'2014-01-10 00:00:00'),(107,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,1246.68,0.00,1756.68,10,0.00,4,7699.28,77301.38,9455.96,'2014-02-10 00:00:00'),(108,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,979.15,0.00,1442.96,10,0.00,5,8013.00,69288.38,9455.96,'2014-03-10 00:00:00'),(109,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,1016.23,0.00,1431.96,10,0.00,6,8024.00,61264.38,9455.96,'2014-04-10 00:00:00'),(110,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,857.70,0.00,1225.29,10,0.00,7,8230.67,53033.71,9455.96,'2014-05-10 00:00:00'),(111,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,777.83,0.00,1096.03,10,0.00,8,8359.93,44673.78,9455.96,'2014-06-10 00:00:00'),(112,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,625.43,0.00,893.48,10,0.00,9,8562.48,36111.30,9455.96,'2014-07-10 00:00:00'),(113,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,529.63,0.00,746.30,10,0.00,10,8709.66,27401.64,9455.96,'2014-08-10 00:00:00'),(114,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,401.89,0.00,566.30,10,0.00,11,8889.66,18511.98,9455.96,'2014-09-10 00:00:00'),(115,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,259.17,0.00,370.24,10,0.00,12,9085.72,9426.26,9455.96,'2014-10-10 00:00:00'),(116,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,138.25,0.00,163.39,10,0.00,13,9426.26,0.00,9589.65,'2014-11-05 00:00:00'),(117,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,866.67,0.00,1466.67,11,0.00,1,0.00,100000.00,1466.67,'2014-01-10 00:00:00'),(118,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,1466.67,0.00,2066.67,11,0.00,2,7389.29,92610.71,9455.96,'2014-02-10 00:00:00'),(119,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,1173.07,0.00,1728.73,11,0.00,3,7727.23,84883.48,9455.96,'2014-03-10 00:00:00'),(120,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,1244.96,0.00,1754.26,11,0.00,4,7701.70,77181.78,9455.96,'2014-04-10 00:00:00'),(121,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,1080.54,0.00,1543.64,11,0.00,5,7912.32,69269.46,9455.96,'2014-05-10 00:00:00'),(122,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,1015.95,0.00,1431.57,11,0.00,6,8024.39,61245.07,9455.96,'2014-06-10 00:00:00'),(123,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,857.43,0.00,1224.90,11,0.00,7,8231.06,53014.01,9455.96,'2014-07-10 00:00:00'),(124,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,777.54,0.00,1095.62,11,0.00,8,8360.34,44653.67,9455.96,'2014-08-10 00:00:00'),(125,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,654.92,0.00,922.84,11,0.00,9,8533.12,36120.55,9455.96,'2014-09-10 00:00:00'),(126,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,505.69,0.00,722.41,11,0.00,10,8733.55,27387.00,9455.96,'2014-10-10 00:00:00'),(127,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,401.68,0.00,566.00,11,0.00,11,8889.96,18497.04,9455.96,'2014-11-10 00:00:00'),(128,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,258.96,0.00,369.94,11,0.00,12,9086.02,9411.02,9455.96,'2014-12-10 00:00:00'),(129,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,87.84,0.00,50.19,11,0.00,13,9411.02,0.00,9461.21,'2014-12-18 00:00:00'),(130,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,93.33,0.00,153.33,12,0.00,1,0.00,10000.00,153.33,'2014-01-10 00:00:00'),(131,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,146.67,0.00,206.67,12,0.00,2,738.93,9261.07,945.60,'2014-02-10 00:00:00'),(132,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,117.31,0.00,172.87,12,0.00,3,772.72,8488.35,945.60,'2014-03-10 00:00:00'),(133,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,124.50,0.00,175.43,12,0.00,4,770.17,7718.18,945.60,'2014-04-10 00:00:00'),(134,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,108.05,0.00,154.36,12,0.00,5,791.23,6926.95,945.60,'2014-05-10 00:00:00'),(135,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,101.60,0.00,143.16,12,0.00,6,802.44,6124.51,945.60,'2014-06-10 00:00:00'),(136,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,85.74,0.00,122.49,12,0.00,7,823.11,5301.40,945.60,'2014-07-10 00:00:00'),(137,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,77.75,0.00,109.56,12,0.00,8,836.03,4465.37,945.60,'2014-08-10 00:00:00'),(138,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,65.49,0.00,92.28,12,0.00,9,853.31,3612.06,945.60,'2014-09-10 00:00:00'),(139,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,50.57,0.00,72.24,12,0.00,10,873.35,2738.71,945.60,'2014-10-10 00:00:00'),(140,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,40.17,0.00,56.60,12,0.00,11,889.00,1849.71,945.60,'2014-11-10 00:00:00'),(141,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,25.90,0.00,36.99,12,0.00,12,908.60,941.11,945.60,'2014-12-10 00:00:00'),(142,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,9.41,0.00,4.39,12,0.00,13,941.11,0.00,945.50,'2014-12-17 00:00:00'),(143,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,0.00,0.00,6000.00,14,0.00,1,0.00,300000.00,6000.00,'2014-05-02 00:00:00'),(144,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,6000.00,0.00,6200.00,14,0.00,2,0.00,300000.00,6200.00,'2014-06-02 00:00:00'),(145,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,5800.00,0.00,6000.00,14,0.00,3,0.00,300000.00,6000.00,'2014-07-02 00:00:00'),(146,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,6000.00,0.00,6200.00,14,0.00,4,0.00,300000.00,6200.00,'2014-08-02 00:00:00'),(147,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,6000.00,0.00,6200.00,14,0.00,5,0.00,300000.00,6200.00,'2014-09-02 00:00:00'),(148,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,5800.00,0.00,6000.00,14,0.00,6,300000.00,0.00,306000.00,'2014-10-02 00:00:00'),(149,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,0.00,0.00,10000.00,15,0.00,1,0.00,500000.00,10000.00,'2014-05-02 00:00:00'),(150,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,10000.00,0.00,10333.33,15,0.00,2,0.00,500000.00,10333.33,'2014-06-02 00:00:00'),(151,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,9666.67,0.00,10000.00,15,0.00,3,0.00,500000.00,10000.00,'2014-07-02 00:00:00'),(152,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,10000.00,0.00,10333.33,15,0.00,4,0.00,500000.00,10333.33,'2014-08-02 00:00:00'),(153,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,10000.00,0.00,10333.33,15,0.00,5,0.00,500000.00,10333.33,'2014-09-02 00:00:00'),(154,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,9666.67,0.00,9666.67,15,0.00,6,500000.00,0.00,509666.67,'2014-10-01 00:00:00');
/*!40000 ALTER TABLE `fc_childplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_cmnmapping`
--

DROP TABLE IF EXISTS `fc_cmnmapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_cmnmapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `cellIndex` int(11) NOT NULL,
  `dataType` int(11) NOT NULL,
  `fmt` varchar(50) DEFAULT NULL,
  `fun` varchar(60) DEFAULT NULL,
  `mapingCmns` varchar(60) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `tdsId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_cmnmapping`
--

LOCK TABLES `fc_cmnmapping` WRITE;
/*!40000 ALTER TABLE `fc_cmnmapping` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_cmnmapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_exeitems`
--

DROP TABLE IF EXISTS `fc_exeitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_exeitems` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `dat` decimal(19,2) NOT NULL,
  `exemptId` bigint(20) NOT NULL,
  `fat` decimal(19,2) NOT NULL,
  `formId` bigint(20) NOT NULL,
  `mat` decimal(19,2) NOT NULL,
  `pat` decimal(19,2) NOT NULL,
  `rat` decimal(19,2) NOT NULL,
  `totalAmount` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_exeitems`
--

LOCK TABLES `fc_exeitems` WRITE;
/*!40000 ALTER TABLE `fc_exeitems` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_exeitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_exempt`
--

DROP TABLE IF EXISTS `fc_exempt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_exempt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `appDate` datetime NOT NULL,
  `appDept` varchar(50) DEFAULT NULL,
  `breed` bigint(20) NOT NULL,
  `cashier` bigint(20) DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  `endDate` datetime DEFAULT NULL,
  `etype` int(11) NOT NULL,
  `exeDate` datetime DEFAULT NULL,
  `exeItems` varchar(20) DEFAULT NULL,
  `isBackAmount` int(11) NOT NULL,
  `managerId` bigint(20) NOT NULL,
  `procId` varchar(20) DEFAULT NULL,
  `reason` longtext,
  `startDate` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `totalAmount` decimal(19,2) NOT NULL,
  `xstatus` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_exempt`
--

LOCK TABLES `fc_exempt` WRITE;
/*!40000 ALTER TABLE `fc_exempt` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_exempt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_extcontract`
--

DROP TABLE IF EXISTS `fc_extcontract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_extcontract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `applyMan` varchar(50) DEFAULT NULL,
  `asignDate` datetime DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  `ctype` int(11) NOT NULL,
  `dayLoan` int(11) NOT NULL,
  `eendDate` datetime DEFAULT NULL,
  `endAmount` decimal(19,2) DEFAULT NULL,
  `estartDate` datetime DEFAULT NULL,
  `extAmount` decimal(19,2) DEFAULT NULL,
  `extensionId` bigint(20) NOT NULL,
  `gsignDate` datetime DEFAULT NULL,
  `guaCode` varchar(20) DEFAULT NULL,
  `guarantor` varchar(50) DEFAULT NULL,
  `isadvance` int(11) NOT NULL,
  `loanCode` varchar(20) NOT NULL,
  `manager` varchar(50) DEFAULT NULL,
  `mgrtype` int(11) NOT NULL,
  `monthLoan` int(11) NOT NULL,
  `mrate` double NOT NULL,
  `oendDate` datetime DEFAULT NULL,
  `ostartDate` datetime DEFAULT NULL,
  `otherRemark` longtext,
  `payType` varchar(50) NOT NULL,
  `phAmount` decimal(19,2) NOT NULL,
  `rate` double NOT NULL,
  `rateType` int(11) NOT NULL,
  `signDate` datetime NOT NULL,
  `yearLoan` int(11) NOT NULL,
  `munint` int(11) NOT NULL,
  `unint` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_extcontract`
--

LOCK TABLES `fc_extcontract` WRITE;
/*!40000 ALTER TABLE `fc_extcontract` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_extcontract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_extension`
--

DROP TABLE IF EXISTS `fc_extension`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_extension` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `applyDate` datetime DEFAULT NULL,
  `applyMan` varchar(100) DEFAULT NULL,
  `breed` bigint(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `comeDate` datetime DEFAULT NULL,
  `contractId` bigint(20) NOT NULL,
  `ctype` int(11) NOT NULL,
  `dayLoan` int(11) NOT NULL,
  `eendDate` datetime DEFAULT NULL,
  `endAmount` decimal(19,2) NOT NULL,
  `estartDate` datetime DEFAULT NULL,
  `extAmount` decimal(19,2) NOT NULL,
  `extInRate` double NOT NULL,
  `extInRateType` int(11) DEFAULT NULL,
  `extReason` longtext,
  `guaCode` varchar(20) DEFAULT NULL,
  `isadvance` int(11) NOT NULL,
  `loanCode` varchar(20) NOT NULL,
  `managerId` bigint(20) DEFAULT NULL,
  `mgrtype` int(11) NOT NULL,
  `monthLoan` int(11) NOT NULL,
  `mrate` double NOT NULL,
  `oendDate` datetime DEFAULT NULL,
  `ostartDate` datetime DEFAULT NULL,
  `paySource` varchar(255) DEFAULT NULL,
  `payType` varchar(20) NOT NULL,
  `phAmount` decimal(19,2) NOT NULL,
  `procId` varchar(20) DEFAULT NULL,
  `rate` double NOT NULL,
  `rateType` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `yearLoan` int(11) NOT NULL,
  `munint` int(11) DEFAULT NULL,
  `unint` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_extension`
--

LOCK TABLES `fc_extension` WRITE;
/*!40000 ALTER TABLE `fc_extension` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_extension` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_extplan`
--

DROP TABLE IF EXISTS `fc_extplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_extplan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `actionType` int(11) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  `formId` bigint(20) NOT NULL,
  `interest` decimal(19,2) NOT NULL,
  `mgrAmount` decimal(19,2) NOT NULL,
  `phases` int(11) NOT NULL,
  `principal` decimal(19,2) NOT NULL,
  `reprincipal` decimal(19,2) NOT NULL,
  `totalAmount` decimal(19,2) NOT NULL,
  `xpayDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_extplan`
--

LOCK TABLES `fc_extplan` WRITE;
/*!40000 ALTER TABLE `fc_extplan` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_extplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_flexplan`
--

DROP TABLE IF EXISTS `fc_flexplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_flexplan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `contractId` bigint(20) NOT NULL,
  `phases` int(11) NOT NULL,
  `principal` decimal(19,2) NOT NULL,
  `xpayDate` datetime NOT NULL,
  `dataSource` int(11) NOT NULL,
  `overalLogId` bigint(20) DEFAULT NULL,
  `formId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_flexplan`
--

LOCK TABLES `fc_flexplan` WRITE;
/*!40000 ALTER TABLE `fc_flexplan` DISABLE KEYS */;
INSERT INTO `fc_flexplan` VALUES (1,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,1,0.00,'2014-04-10 00:00:00',0,NULL,NULL),(2,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,2,0.00,'2014-05-10 00:00:00',0,NULL,NULL),(3,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,3,2000.00,'2014-06-10 00:00:00',0,NULL,NULL),(4,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,4,2000.00,'2014-07-10 00:00:00',0,NULL,NULL),(5,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,5,2000.00,'2014-08-10 00:00:00',0,NULL,NULL),(6,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,6,0.00,'2014-09-10 00:00:00',0,NULL,NULL),(7,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,7,0.00,'2014-10-10 00:00:00',0,NULL,NULL),(8,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,8,0.00,'2014-11-10 00:00:00',0,NULL,NULL),(9,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,9,2000.00,'2014-12-10 00:00:00',0,NULL,NULL),(10,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,10,2000.00,'2015-01-10 00:00:00',0,NULL,NULL),(11,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,11,2000.00,'2015-02-10 00:00:00',0,NULL,NULL),(12,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,12,2000.00,'2015-03-10 00:00:00',0,NULL,NULL),(13,'2014-03-14 11:19:32',22,2,NULL,1,NULL,NULL,1,NULL,6,13,86000.00,'2015-03-13 00:00:00',0,NULL,NULL),(14,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,1,0.00,'2014-04-10 00:00:00',0,NULL,NULL),(15,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,2,0.00,'2014-05-10 00:00:00',0,NULL,NULL),(16,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,3,8000.00,'2014-06-10 00:00:00',0,NULL,NULL),(17,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,4,8000.00,'2014-07-10 00:00:00',0,NULL,NULL),(18,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,5,8000.00,'2014-08-10 00:00:00',0,NULL,NULL),(19,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,6,8000.00,'2014-09-10 00:00:00',0,NULL,NULL),(20,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,7,8000.00,'2014-10-10 00:00:00',0,NULL,NULL),(21,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,8,8000.00,'2014-11-10 00:00:00',0,NULL,NULL),(22,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,9,8000.00,'2014-12-10 00:00:00',0,NULL,NULL),(23,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,10,8000.00,'2015-01-10 00:00:00',0,NULL,NULL),(24,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,11,8000.00,'2015-02-10 00:00:00',0,NULL,NULL),(25,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,12,8000.00,'2015-03-10 00:00:00',0,NULL,NULL),(26,'2014-03-17 10:24:14',22,2,NULL,1,NULL,NULL,1,NULL,10,13,120000.00,'2015-03-16 00:00:00',0,NULL,NULL);
/*!40000 ALTER TABLE `fc_flexplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_free`
--

DROP TABLE IF EXISTS `fc_free`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_free` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `exempt` int(11) NOT NULL,
  `formId` bigint(20) NOT NULL,
  `freeamount` decimal(19,2) NOT NULL,
  `lastDate` datetime DEFAULT NULL,
  `loanInvoceId` bigint(20) NOT NULL,
  `provision` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `yamount` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_free`
--

LOCK TABLES `fc_free` WRITE;
/*!40000 ALTER TABLE `fc_free` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_free` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_freerecords`
--

DROP TABLE IF EXISTS `fc_freerecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_freerecords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accountId` bigint(20) NOT NULL,
  `amount` decimal(19,2) NOT NULL,
  `bussTag` int(11) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  `fcnumber` varchar(30) DEFAULT NULL,
  `invoceId` bigint(20) NOT NULL,
  `rectDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_freerecords`
--

LOCK TABLES `fc_freerecords` WRITE;
/*!40000 ALTER TABLE `fc_freerecords` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_freerecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_fundswater`
--

DROP TABLE IF EXISTS `fc_fundswater`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_fundswater` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `amountlogId` bigint(20) NOT NULL,
  `amounts` decimal(19,2) NOT NULL,
  `bussTag` int(11) NOT NULL,
  `otherSort` bigint(20) DEFAULT NULL,
  `ownfundsId` bigint(20) NOT NULL,
  `waterType` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_fundswater`
--

LOCK TABLES `fc_fundswater` WRITE;
/*!40000 ALTER TABLE `fc_fundswater` DISABLE KEYS */;
INSERT INTO `fc_fundswater` VALUES (1,'2014-03-14 11:22:42',13,4,NULL,1,NULL,NULL,1,NULL,80,100000.00,0,NULL,1,1),(2,'2014-03-14 11:30:16',2,8,NULL,1,NULL,NULL,-1,NULL,81,1800.00,2,NULL,1,2),(3,'2014-03-14 11:30:25',2,8,NULL,1,NULL,NULL,-1,NULL,82,4066.67,2,NULL,1,2),(4,'2014-03-14 11:30:25',2,8,NULL,1,NULL,NULL,-1,NULL,83,2000.00,2,NULL,1,2),(5,'2014-03-14 11:30:25',2,8,NULL,1,NULL,NULL,-1,NULL,84,3960.00,2,NULL,1,2),(6,'2014-03-14 13:52:40',13,4,NULL,1,NULL,NULL,1,NULL,85,100000.00,0,NULL,1,1),(7,'2014-03-14 15:14:23',13,4,NULL,1,NULL,NULL,1,NULL,86,100000.00,0,NULL,1,1),(8,'2014-03-14 15:14:27',13,4,NULL,1,NULL,NULL,1,NULL,87,100000.00,0,NULL,1,1),(9,'2014-03-17 11:17:32',13,4,NULL,1,NULL,NULL,1,NULL,88,300000.00,0,NULL,1,1),(10,'2014-03-17 11:18:12',13,4,NULL,1,NULL,NULL,1,NULL,89,28367.88,2,NULL,1,2),(11,'2014-03-17 11:18:12',13,4,NULL,1,NULL,NULL,1,NULL,90,28367.88,2,NULL,1,2),(12,'2014-03-17 11:18:12',13,4,NULL,1,NULL,NULL,1,NULL,91,28367.88,2,NULL,1,2),(13,'2014-03-17 11:18:12',13,4,NULL,1,NULL,NULL,1,NULL,92,4800.00,2,NULL,1,2),(14,'2014-03-17 11:18:12',13,4,NULL,1,NULL,NULL,1,NULL,93,28367.88,2,NULL,1,2),(15,'2014-03-17 11:18:12',13,4,NULL,1,NULL,NULL,1,NULL,94,28367.88,2,NULL,1,2),(16,'2014-03-17 11:18:12',13,4,NULL,1,NULL,NULL,1,NULL,95,28367.88,2,NULL,1,2),(17,'2014-03-17 11:18:12',13,4,NULL,1,NULL,NULL,1,NULL,96,28367.88,2,NULL,1,2),(18,'2014-03-17 12:13:10',2,8,NULL,1,NULL,NULL,-1,NULL,97,5000.00,4,NULL,1,2),(19,'2014-03-17 12:36:18',13,4,NULL,1,NULL,NULL,1,NULL,98,100000.00,0,NULL,1,1),(20,'2014-03-17 12:37:46',13,4,NULL,1,NULL,NULL,1,NULL,99,266.67,2,NULL,2,2),(21,'2014-03-17 12:37:46',13,4,NULL,1,NULL,NULL,1,NULL,100,9455.96,2,NULL,2,2),(22,'2014-03-17 12:38:04',13,4,NULL,1,NULL,NULL,1,NULL,101,9455.96,2,NULL,1,2),(23,'2014-03-17 18:31:01',13,4,NULL,1,NULL,NULL,1,NULL,102,10000.00,0,NULL,1,1),(24,'2014-03-17 18:31:36',13,4,NULL,1,NULL,NULL,1,NULL,103,153.33,2,NULL,1,2),(25,'2014-03-17 18:32:50',13,4,NULL,1,NULL,NULL,1,NULL,104,2000.00,4,NULL,1,2),(26,'2014-03-17 19:38:12',13,4,NULL,1,NULL,NULL,1,NULL,105,266.67,2,NULL,1,2),(27,'2014-03-17 19:38:12',13,4,NULL,1,NULL,NULL,1,NULL,106,9455.96,2,NULL,1,2),(28,'2014-03-17 19:38:12',13,4,NULL,1,NULL,NULL,1,NULL,107,9455.96,2,NULL,1,2),(29,'2014-03-17 19:42:51',2,8,NULL,1,NULL,NULL,-1,NULL,108,2000.00,4,NULL,1,2),(30,'2014-03-17 19:45:06',2,8,NULL,1,NULL,NULL,-1,NULL,109,2000.00,4,NULL,1,2),(31,'2014-03-17 19:53:41',2,8,NULL,1,NULL,NULL,-1,NULL,110,5000.00,4,NULL,1,2),(32,'2014-03-17 20:38:10',13,4,NULL,1,NULL,NULL,1,NULL,111,9455.96,2,NULL,1,2),(33,'2014-03-17 20:38:10',13,4,NULL,1,NULL,NULL,1,NULL,112,266.67,2,NULL,1,2),(34,'2014-03-17 20:38:10',13,4,NULL,1,NULL,NULL,1,NULL,113,9455.96,2,NULL,1,2),(35,'2014-03-17 20:52:05',2,8,NULL,1,NULL,NULL,-1,NULL,114,2000.00,4,NULL,1,2),(36,'2014-03-17 20:53:13',2,8,NULL,1,NULL,NULL,-1,NULL,115,2000.00,4,NULL,1,2),(37,'2014-03-17 20:54:13',2,8,NULL,1,NULL,NULL,-1,NULL,116,5000.00,4,NULL,1,2),(38,'2014-03-17 21:37:56',2,8,NULL,1,NULL,NULL,-1,NULL,117,2000.00,4,NULL,1,2),(39,'2014-03-17 21:39:05',2,8,NULL,1,NULL,NULL,-1,NULL,118,2000.00,4,NULL,1,2),(40,'2014-03-18 10:10:48',2,8,NULL,1,NULL,NULL,-1,NULL,119,5000.00,4,NULL,1,2),(41,'2014-03-18 11:27:05',2,8,NULL,1,NULL,NULL,-1,NULL,120,2000.00,4,NULL,1,2),(42,'2014-03-18 11:30:01',2,8,NULL,1,NULL,NULL,-1,NULL,121,2000.00,4,NULL,1,2),(43,'2014-03-18 11:31:24',2,8,NULL,1,NULL,NULL,-1,NULL,122,5000.00,4,NULL,1,2),(44,'2014-03-18 08:01:12',2,8,NULL,1,NULL,NULL,-1,NULL,123,9455.96,2,NULL,1,2),(45,'2014-03-18 08:01:12',2,8,NULL,1,NULL,NULL,-1,NULL,124,9455.96,2,NULL,1,2),(46,'2014-03-18 08:01:12',2,8,NULL,1,NULL,NULL,-1,NULL,125,9455.96,2,NULL,1,2),(47,'2014-03-18 08:01:12',2,8,NULL,1,NULL,NULL,-1,NULL,126,266.67,2,NULL,1,2),(48,'2014-03-18 08:02:18',2,8,NULL,1,NULL,NULL,-1,NULL,127,2000.00,4,NULL,1,2),(49,'2014-03-18 08:10:30',2,8,NULL,1,NULL,NULL,-1,NULL,128,2000.00,4,NULL,1,2),(50,'2014-03-18 08:23:41',2,8,NULL,1,NULL,NULL,-1,NULL,129,5000.00,4,NULL,1,2),(51,'2014-03-18 09:00:20',2,8,NULL,1,NULL,NULL,-1,NULL,130,500.00,4,NULL,1,2),(52,'2014-03-18 09:12:43',2,8,NULL,1,NULL,NULL,-1,NULL,131,495.56,4,NULL,1,2),(53,'2014-03-18 09:14:36',2,8,NULL,1,NULL,NULL,-1,NULL,132,9455.96,2,NULL,1,2),(54,'2014-03-18 09:14:36',2,8,NULL,1,NULL,NULL,-1,NULL,133,266.67,2,NULL,1,2),(55,'2014-03-18 09:20:01',2,8,NULL,1,NULL,NULL,-1,NULL,134,9455.96,2,NULL,1,2),(56,'2014-03-18 09:20:01',2,8,NULL,1,NULL,NULL,-1,NULL,135,266.67,2,NULL,1,2);
/*!40000 ALTER TABLE `fc_fundswater` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_funtempcfg`
--

DROP TABLE IF EXISTS `fc_funtempcfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_funtempcfg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `funTag` varchar(50) DEFAULT NULL,
  `menuId` bigint(20) DEFAULT NULL,
  `tempId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_funtempcfg`
--

LOCK TABLES `fc_funtempcfg` WRITE;
/*!40000 ALTER TABLE `fc_funtempcfg` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_funtempcfg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_gopinion`
--

DROP TABLE IF EXISTS `fc_gopinion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_gopinion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `extensionId` bigint(20) NOT NULL,
  `guarantor` varchar(30) NOT NULL,
  `legal` varchar(50) DEFAULT NULL,
  `opinion` varchar(200) NOT NULL,
  `signDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_gopinion`
--

LOCK TABLES `fc_gopinion` WRITE;
/*!40000 ALTER TABLE `fc_gopinion` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_gopinion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_guacontract`
--

DROP TABLE IF EXISTS `fc_guacontract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_guacontract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `appAmount` decimal(19,2) NOT NULL,
  `assMan` varchar(255) NOT NULL,
  `borrCode` varchar(30) NOT NULL,
  `clause` varchar(255) DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `custType` int(11) NOT NULL,
  `customerId` bigint(20) NOT NULL,
  `endDate` datetime NOT NULL,
  `formId` bigint(20) NOT NULL,
  `guarantorIds` varchar(255) DEFAULT NULL,
  `loanConId` bigint(20) NOT NULL,
  `rate` double DEFAULT NULL,
  `sdate` datetime NOT NULL,
  `startDate` datetime NOT NULL,
  `unint` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_guacontract`
--

LOCK TABLES `fc_guacontract` WRITE;
/*!40000 ALTER TABLE `fc_guacontract` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_guacontract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_loancontract`
--

DROP TABLE IF EXISTS `fc_loancontract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_loancontract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accName` varchar(50) DEFAULT NULL,
  `aphases` int(11) NOT NULL,
  `appAmount` decimal(19,2) NOT NULL,
  `arate` double NOT NULL,
  `aunint` int(11) NOT NULL,
  `borAccount` varchar(50) DEFAULT NULL,
  `borBank` varchar(50) DEFAULT NULL,
  `brate` double NOT NULL,
  `bunint` int(11) NOT NULL,
  `clause` varchar(255) DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `custType` int(11) NOT NULL,
  `customerId` bigint(20) NOT NULL,
  `dayLoan` int(11) NOT NULL,
  `doDate` datetime DEFAULT NULL,
  `endDate` datetime NOT NULL,
  `exteDate` datetime DEFAULT NULL,
  `formId` bigint(20) NOT NULL,
  `frate` double NOT NULL,
  `ftype` int(11) NOT NULL,
  `funint` int(11) NOT NULL,
  `isadvance` int(11) NOT NULL,
  `mgrtype` int(11) NOT NULL,
  `monthLoan` int(11) NOT NULL,
  `mrate` double NOT NULL,
  `munint` int(11) NOT NULL,
  `oldendDate` datetime DEFAULT NULL,
  `oldpayDate` datetime DEFAULT NULL,
  `payAccount` varchar(50) DEFAULT NULL,
  `payBank` varchar(50) DEFAULT NULL,
  `payDate` datetime NOT NULL,
  `payDay` int(11) DEFAULT NULL,
  `payType` varchar(255) NOT NULL,
  `phAmount` double NOT NULL,
  `prate` double NOT NULL,
  `punint` int(11) NOT NULL,
  `rate` double NOT NULL,
  `rateType` int(11) NOT NULL,
  `setdayType` int(11) NOT NULL,
  `unint` int(11) NOT NULL,
  `urate` double NOT NULL,
  `utype` int(11) NOT NULL,
  `uunint` int(11) NOT NULL,
  `yearLoan` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_loancontract`
--

LOCK TABLES `fc_loancontract` WRITE;
/*!40000 ALTER TABLE `fc_loancontract` DISABLE KEYS */;
INSERT INTO `fc_loancontract` VALUES (1,'2014-03-11 00:00:00',22,2,-1,1,NULL,NULL,1,NULL,'蔡云锋',0,100000.00,0,1,'4211212121212112','招商银行',0,1,NULL,'B2014031111040000',0,49,0,NULL,'2014-03-04 00:00:00',NULL,3,3,1,1,0,0,12,0,1,NULL,NULL,NULL,NULL,'2013-03-05 00:00:00',10,'P0001',0,0,1,2,1,2,1,3,1,1,0),(2,'2014-03-11 00:00:00',22,2,-1,1,NULL,NULL,1,NULL,'蔡云锋',0,100000.00,0,1,'4211212121212112','招商银行',0,1,NULL,'B2014031111180602',0,49,0,NULL,'2014-03-04 00:00:00',NULL,3,3,1,1,0,0,12,0,1,NULL,NULL,NULL,NULL,'2013-03-05 00:00:00',10,'P0001',0,0,1,2,1,2,1,3,1,1,0),(3,'2014-03-12 00:00:00',22,2,-1,1,22,'2014-03-12 18:36:22',1,'放款单审批后，生成了放款单[2014-03-12 06:36:22]','毛含莲 ',0,20000.00,0,1,'6220236523654584','招商银行',0,1,NULL,'B2014031206350204',0,52,0,NULL,'2015-03-11 00:00:00',NULL,4,0,1,1,0,0,0,0,1,NULL,NULL,'6220236523654584','招商银行','2014-03-12 00:00:00',10,'P0006',0,0,1,2,1,2,1,0,1,1,1),(4,'2014-03-12 00:00:00',22,2,-1,1,22,'2014-03-12 18:52:13',1,'放款单审批后，生成了放款单[2014-03-12 06:52:13]','费谷兰 ',0,20000.00,0,1,'622023652658954','招商银行',0,1,NULL,'B2014031206515006',0,53,0,NULL,'2015-03-11 00:00:00',NULL,5,0,1,1,0,0,0,0,1,NULL,NULL,'622023652658954','招商银行','2014-03-12 00:00:00',10,'P0001',0,0,1,2,1,2,1,0,1,1,1),(5,'2014-03-12 00:00:00',22,2,-1,1,22,'2014-03-12 21:09:39',1,'放款单审批后，生成了放款单[2014-03-12 09:09:39]','石谱班 ',0,20000.00,0,1,'622','招商银行',0,1,NULL,'B2014031209080608',0,54,0,NULL,'2015-03-09 00:00:00',NULL,6,0,1,1,0,0,0,0,1,NULL,NULL,NULL,NULL,'2014-03-10 00:00:00',10,'P0006',0,0,1,2,1,2,1,0,1,1,1),(6,'2014-03-14 00:00:00',22,2,-1,1,22,'2014-03-14 11:20:00',1,'放款单审批后，生成了放款单[2014-03-14 11:20:00]','伍学共 ',0,100000.00,0,1,'22','11',0,1,NULL,'B2014031411193210',0,55,0,NULL,'2015-03-13 00:00:00',NULL,7,0,1,1,0,0,0,0,1,NULL,NULL,NULL,NULL,'2014-03-14 00:00:00',10,'P0008',0,0,1,2,1,2,1,0,1,1,1),(7,'2014-03-14 00:00:00',22,2,-1,1,22,'2014-03-14 13:51:57',1,'放款单审批后，生成了放款单[2014-03-14 01:51:57]','薛规畅 ',0,100000.00,0,1,'622202365521544','招商银行',0,1,NULL,'B2014031401513612',0,56,0,NULL,'2015-03-13 00:00:00',NULL,8,0,1,1,0,0,0,0,1,NULL,NULL,NULL,NULL,'2014-03-14 00:00:00',10,'P0006',0,0,1,2,1,2,1,0,1,1,1),(8,'2014-03-14 00:00:00',22,2,-1,1,22,'2014-03-14 15:13:14',1,'放款单审批后，生成了放款单[2014-03-14 03:13:14]','宣叔华 ',0,100000.00,0,1,'62220236523','招商银行',0,1,NULL,'B2014031403125214',0,57,0,NULL,'2015-03-13 00:00:00',NULL,9,0,1,1,0,0,0,0,1,NULL,NULL,NULL,NULL,'2014-03-14 00:00:00',10,'P0002',5000,0,1,2,1,2,1,0,1,1,1),(9,'2014-03-15 00:00:00',22,2,-1,1,NULL,NULL,1,NULL,'伍学共 ',0,20000.00,5,1,'62220236256589554','招商银行',0,1,NULL,'B2014031511215516',0,55,0,'2014-03-15 00:00:00','2015-03-14 00:00:00',NULL,10,3,1,1,0,0,0,0,1,NULL,NULL,'62220236256589554','招商银行','2014-03-15 00:00:00',10,'P0001',0,3,1,2,1,2,1,3,1,1,1),(10,'2014-03-17 00:00:00',22,2,-1,1,22,'2014-03-17 11:11:10',1,'放款单审批后，生成了放款单[2014-03-17 11:11:10]','毛含莲 ',0,200000.00,0,1,'622202365212544','招商银行',0,1,NULL,'B2014031710241418',0,52,0,NULL,'2015-03-16 00:00:00',NULL,11,0,1,1,0,0,0,0,1,NULL,NULL,'622202365212544','招商银行','2014-03-17 00:00:00',10,'P0008',0,0,1,2,1,2,1,0,1,1,1),(11,'2014-03-17 00:00:00',22,2,-1,1,22,'2014-03-17 11:16:59',1,'放款单审批后，生成了放款单[2014-03-17 11:16:59]','洪豆焕',0,300000.00,0,1,'62220236525841','招商银行',0,1,NULL,'B2014031711163820',0,58,0,NULL,'2014-02-13 00:00:00',NULL,12,3,1,1,0,0,0,0,1,NULL,NULL,'62220236525841','招商银行','2013-02-14 00:00:00',10,'P0006',0,0,1,2,1,2,1,3,1,1,1),(12,'2014-03-17 00:00:00',22,2,-1,1,22,'2014-03-17 12:35:51',1,'放款单审批后，生成了放款单[2014-03-17 12:35:51]','赵驹菘',0,100000.00,0,1,'6222023652365121','招商银行',0,1,NULL,'B2014031700353822',0,59,0,NULL,'2014-11-05 00:00:00',NULL,13,3,1,1,0,0,0,0,1,NULL,NULL,NULL,NULL,'2013-11-06 00:00:00',10,'P0006',0,0,1,2,1,2,1,3,1,1,1),(13,'2014-03-17 00:00:00',22,2,-1,1,22,'2014-03-17 18:02:17',1,'放款单审批后，生成了放款单[2014-03-17 06:02:17]','费谷兰 ',0,100000.00,0,1,'62220236451','招商银行',0,1,NULL,'B2014031706013524',0,53,0,NULL,'2014-12-18 00:00:00',NULL,14,3,1,1,0,0,0,0,1,NULL,NULL,'62220236451','招商银行','2013-12-19 00:00:00',10,'P0006',0,0,1,2,1,2,1,3,1,1,1),(14,'2014-03-17 00:00:00',22,2,-1,1,22,'2014-03-17 18:30:28',1,'放款单审批后，生成了放款单[2014-03-17 06:30:28]','范笑卉',0,10000.00,0,1,'62220236522547','招商银行',0,1,NULL,'B2014031706300626',0,60,0,NULL,'2014-12-17 00:00:00',NULL,15,3,1,1,0,0,0,0,1,NULL,NULL,'62220236522547','招商银行','2013-12-18 00:00:00',10,'P0006',0,0,1,2,1,2,1,3,1,1,1),(15,'2014-03-17 00:00:00',22,2,-1,1,NULL,NULL,1,NULL,'石谱班 ',0,20000.00,0,1,'22','11',0,1,NULL,'B2014031709032228',0,54,0,NULL,'2015-03-16 00:00:00',NULL,16,0,1,1,0,0,0,0,1,NULL,NULL,NULL,NULL,'2014-03-17 00:00:00',10,'P0006',0,0,1,2,1,2,1,0,1,1,1),(16,'2014-04-02 00:00:00',22,2,-1,1,22,'2014-04-02 16:26:40',1,'放款单审批后，生成了放款单[2014-04-02 04:26:40]','涂建清',0,300000.00,0,1,'3273874996','中国银行',0,1,NULL,'B2014040204255530',0,64,0,NULL,'2014-10-02 00:00:00',NULL,19,0,1,1,0,0,6,0,1,NULL,'2014-04-02 00:00:00',NULL,NULL,'2014-04-02 00:00:00',NULL,'P0001',0,0,1,2,1,1,1,0,1,1,0),(17,'2014-04-02 00:00:00',22,2,-1,1,22,'2014-04-02 16:50:50',1,'放款单审批后，生成了放款单[2014-04-02 04:50:50]','李波',0,500000.00,0,1,'37858368994','中国银行',0,1,NULL,'B2014040204501032',0,62,0,NULL,'2014-10-01 00:00:00',NULL,20,0,1,1,0,0,6,0,1,NULL,'2014-04-02 00:00:00',NULL,NULL,'2014-04-02 00:00:00',NULL,'P0001',0,0,1,2,1,1,1,0,1,1,0);
/*!40000 ALTER TABLE `fc_loancontract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_loaninvoce`
--

DROP TABLE IF EXISTS `fc_loaninvoce`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_loaninvoce` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `account` varchar(30) DEFAULT NULL,
  `accountId` bigint(20) DEFAULT NULL,
  `auditState` int(11) NOT NULL,
  `cashier` bigint(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  `fcnumber` varchar(30) DEFAULT NULL,
  `formId` bigint(20) NOT NULL,
  `isNotys` int(11) DEFAULT NULL,
  `payAmount` decimal(19,2) NOT NULL,
  `payDate` datetime DEFAULT NULL,
  `payName` varchar(50) NOT NULL,
  `prate` double NOT NULL,
  `realDate` datetime DEFAULT NULL,
  `regBank` varchar(50) DEFAULT NULL,
  `startWay` int(11) DEFAULT NULL,
  `state` int(11) NOT NULL,
  `unAmount` decimal(19,2) DEFAULT NULL,
  `ysMat` decimal(19,2) DEFAULT NULL,
  `ysMatMonth` int(11) DEFAULT NULL,
  `ysRat` decimal(19,2) DEFAULT NULL,
  `ysRatMonth` int(11) DEFAULT NULL,
  `raiminamoto` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_loaninvoce`
--

LOCK TABLES `fc_loaninvoce` WRITE;
/*!40000 ALTER TABLE `fc_loaninvoce` DISABLE KEYS */;
INSERT INTO `fc_loaninvoce` VALUES (1,'2014-03-12 00:00:00',22,2,-1,1,22,'2014-03-12 18:36:21',1,NULL,'6220236523654584',NULL,2,13,'L2014031206351500',3,NULL,4,0,20000.00,'2014-03-12 00:00:00','毛含莲 ',0,'2014-03-12 00:00:00','招商银行',1,0,0.00,0.00,NULL,0.00,NULL,NULL),(2,'2014-03-12 00:00:00',22,2,-1,1,22,'2014-03-12 18:52:13',1,NULL,'622023652658954',NULL,2,13,'L2014031206515902',4,NULL,5,0,20000.00,'2014-03-12 00:00:00','费谷兰 ',0,'2014-03-12 00:00:00','招商银行',1,0,0.00,0.00,NULL,0.00,NULL,NULL),(3,'2014-03-12 00:00:00',22,2,-1,1,22,'2014-03-12 21:09:39',1,NULL,'622',NULL,2,13,'L2014031209081304',5,NULL,6,0,20000.00,'2014-03-10 00:00:00','石谱班 ',0,'2014-03-10 00:00:00','招商银行',1,0,0.00,0.00,NULL,0.00,NULL,NULL),(4,'2014-03-14 00:00:00',22,2,-1,1,13,'2014-03-14 11:22:42',1,NULL,'22',6,2,13,'L2014031411194806',6,'80',7,0,100000.00,'2014-03-14 00:00:00','伍学共 ',0,'2014-03-14 00:00:00','11',1,1,0.00,0.00,NULL,0.00,NULL,NULL),(5,'2014-03-14 00:00:00',22,2,-1,1,13,'2014-03-14 13:52:40',1,NULL,'622202365521544',6,2,13,'L2014031401513908',7,'85',8,0,100000.00,'2014-03-14 00:00:00','薛规畅 ',0,'2014-03-14 00:00:00','招商银行',1,1,0.00,0.00,NULL,0.00,NULL,NULL),(6,'2014-03-14 00:00:00',22,2,-1,1,13,'2014-03-14 15:14:27',1,NULL,'62220236523',6,2,13,'L2014031403130110',8,'87',9,0,100000.00,'2014-03-14 00:00:00','宣叔华 ',0,'2014-03-14 00:00:00','招商银行',1,1,0.00,0.00,NULL,0.00,NULL,NULL),(7,'2014-03-15 00:00:00',22,2,-1,1,NULL,NULL,1,NULL,'62220236256589554',NULL,1,13,'L2014031511304012',9,NULL,10,0,20000.00,'2014-03-15 00:00:00','伍学共 ',0,'2014-03-15 00:00:00','招商银行',1,0,0.00,0.00,NULL,0.00,NULL,NULL),(8,'2014-03-17 00:00:00',22,2,-1,1,22,'2014-03-17 11:11:10',1,NULL,'622202365212544',NULL,2,13,'L2014031711105614',10,NULL,11,0,200000.00,'2014-03-17 00:00:00','毛含莲 ',0,'2014-03-17 00:00:00','招商银行',1,0,0.00,0.00,NULL,0.00,NULL,NULL),(9,'2014-03-17 00:00:00',22,2,-1,1,13,'2014-03-17 11:17:32',1,NULL,'62220236525841',6,2,13,'L2014031711164216',11,'88',12,0,300000.00,'2013-02-14 00:00:00','洪豆焕',0,'2013-02-14 00:00:00','招商银行',1,1,0.00,0.00,NULL,0.00,NULL,NULL),(10,'2014-03-17 00:00:00',22,2,-1,1,13,'2014-03-17 12:36:18',1,NULL,'6222023652365121',6,2,13,'L2014031700354118',12,'98',13,0,100000.00,'2013-11-06 00:00:00','赵驹菘',0,'2013-11-06 00:00:00','招商银行',1,1,0.00,0.00,NULL,0.00,NULL,NULL),(11,'2014-03-17 00:00:00',22,2,-1,1,22,'2014-03-17 18:02:16',1,NULL,'62220236451',NULL,2,13,'L2014031706015320',13,NULL,14,0,100000.00,'2013-12-19 00:00:00','费谷兰 ',0,'2013-12-19 00:00:00','招商银行',1,0,0.00,0.00,NULL,0.00,NULL,NULL),(12,'2014-03-17 00:00:00',22,2,-1,1,13,'2014-03-17 18:31:00',1,NULL,'62220236522547',6,2,13,'L2014031706301422',14,'102',15,0,10000.00,'2013-12-18 00:00:00','范笑卉',0,'2013-12-18 00:00:00','招商银行',1,1,0.00,0.00,NULL,0.00,NULL,NULL),(13,'2014-03-17 00:00:00',22,2,-1,1,NULL,NULL,1,NULL,'22',NULL,0,13,'L2014031709032724',15,NULL,16,0,20000.00,'2014-03-17 00:00:00','石谱班 ',0,'2014-03-17 00:00:00','11',1,0,0.00,0.00,NULL,0.00,NULL,NULL),(14,'2014-04-02 00:00:00',22,2,-1,1,22,'2014-04-02 16:26:40',1,NULL,'3273874996',NULL,2,13,'L2014040204261826',16,NULL,19,0,300000.00,'2014-04-02 00:00:00','涂建清',0,'2014-04-02 00:00:00','中国银行',1,0,0.00,0.00,NULL,0.00,NULL,NULL),(15,'2014-04-02 00:00:00',22,2,-1,1,22,'2014-04-02 16:50:50',1,NULL,'37858368994',NULL,2,13,'L2014040204502428',17,NULL,20,0,500000.00,'2014-04-02 00:00:00','李波',0,'2014-04-02 00:00:00','中国银行',1,0,0.00,0.00,NULL,0.00,NULL,NULL);
/*!40000 ALTER TABLE `fc_loaninvoce` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_minamount`
--

DROP TABLE IF EXISTS `fc_minamount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_minamount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `adate` datetime DEFAULT NULL,
  `adman` varchar(50) DEFAULT NULL,
  `adresult` varchar(50) DEFAULT NULL,
  `moamount` decimal(19,2) NOT NULL,
  `mpamount` decimal(19,2) NOT NULL,
  `opdate` datetime NOT NULL,
  `status` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_minamount`
--

LOCK TABLES `fc_minamount` WRITE;
/*!40000 ALTER TABLE `fc_minamount` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_minamount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_mortcontract`
--

DROP TABLE IF EXISTS `fc_mortcontract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_mortcontract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `appAmount` decimal(19,2) NOT NULL,
  `assMan` varchar(50) NOT NULL,
  `borrCode` varchar(30) NOT NULL,
  `clause` varchar(255) DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `conAmount` varchar(255) NOT NULL,
  `custType` int(11) NOT NULL,
  `customerId` bigint(20) NOT NULL,
  `endDate` datetime NOT NULL,
  `formId` bigint(20) NOT NULL,
  `loanConId` bigint(20) NOT NULL,
  `ownerNums` varchar(30) DEFAULT NULL,
  `pleIds` varchar(150) NOT NULL,
  `pleman` varchar(255) NOT NULL,
  `rate` double DEFAULT NULL,
  `sdate` datetime NOT NULL,
  `startDate` datetime NOT NULL,
  `unint` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_mortcontract`
--

LOCK TABLES `fc_mortcontract` WRITE;
/*!40000 ALTER TABLE `fc_mortcontract` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_mortcontract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_mortgage`
--

DROP TABLE IF EXISTS `fc_mortgage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_mortgage` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `address` varchar(150) DEFAULT NULL,
  `carrMan` varchar(30) DEFAULT NULL,
  `carrTime` datetime DEFAULT NULL,
  `charMan` varchar(30) DEFAULT NULL,
  `charTime` datetime DEFAULT NULL,
  `conTel` varchar(20) DEFAULT NULL,
  `formId` bigint(20) NOT NULL,
  `gtype` bigint(20) NOT NULL,
  `mman` varchar(30) DEFAULT NULL,
  `morTime` datetime DEFAULT NULL,
  `mpVal` decimal(19,2) NOT NULL,
  `name` varchar(60) NOT NULL,
  `oldVal` decimal(19,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `state` int(11) DEFAULT NULL,
  `unint` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_mortgage`
--

LOCK TABLES `fc_mortgage` WRITE;
/*!40000 ALTER TABLE `fc_mortgage` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_mortgage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_ointerestrecords`
--

DROP TABLE IF EXISTS `fc_ointerestrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_ointerestrecords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accountId` bigint(20) DEFAULT NULL,
  `amt` decimal(19,2) NOT NULL,
  `cmt` decimal(19,2) NOT NULL,
  `fcnumber` varchar(30) DEFAULT NULL,
  `interestId` bigint(20) NOT NULL,
  `rectDate` datetime NOT NULL,
  `tat` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_ointerestrecords`
--

LOCK TABLES `fc_ointerestrecords` WRITE;
/*!40000 ALTER TABLE `fc_ointerestrecords` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_ointerestrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_order`
--

DROP TABLE IF EXISTS `fc_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` bigint(20) DEFAULT NULL,
  `deptId` bigint(20) DEFAULT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` bigint(20) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `orders` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_order`
--

LOCK TABLES `fc_order` WRITE;
/*!40000 ALTER TABLE `fc_order` DISABLE KEYS */;
INSERT INTO `fc_order` VALUES (6,'2013-01-23 16:02:00',45,8,NULL,1,1,'2013-07-16 16:00:00',2,NULL,'L0001',1,'利息',1),(9,'2013-01-24 16:02:00',45,8,NULL,1,2,'2013-09-01 16:00:00',2,NULL,'L0002',1,'本金',2),(10,'2013-01-24 16:02:00',45,8,NULL,1,6,'2013-10-17 16:00:00',2,NULL,'L0003',2,'管理费',3),(11,'2013-01-24 16:02:00',45,8,NULL,1,6,'2013-10-17 16:00:00',2,NULL,'L0004',2,'罚息',1),(12,'2013-01-24 16:02:00',45,8,NULL,1,6,'2013-10-17 16:00:00',2,NULL,'L0005',2,'滞纳金',2);
/*!40000 ALTER TABLE `fc_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_outfunds`
--

DROP TABLE IF EXISTS `fc_outfunds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_outfunds` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accountId` bigint(20) NOT NULL,
  `bamount` decimal(19,2) NOT NULL,
  `code` varchar(20) NOT NULL,
  `endDate` datetime DEFAULT NULL,
  `lastDate` datetime NOT NULL,
  `name` varchar(50) NOT NULL,
  `orgType` int(11) NOT NULL,
  `payDay` int(11) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `totalAmount` decimal(19,2) NOT NULL,
  `uamount` decimal(19,2) NOT NULL,
  `unint` int(11) NOT NULL,
  `xrate` double NOT NULL,
  `xunint` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_outfunds`
--

LOCK TABLES `fc_outfunds` WRITE;
/*!40000 ALTER TABLE `fc_outfunds` DISABLE KEYS */;
INSERT INTO `fc_outfunds` VALUES (1,'2014-03-14 00:00:00',2,8,-1,-1,NULL,NULL,-1,NULL,6,0.00,'O2014031404513700','2014-03-14 00:00:00','2014-03-14 16:54:12','国开银行',1,10,2,'2012-06-06 00:00:00',6000000.00,6000000.00,1,2,1),(2,'2014-03-17 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,6,0.00,'O2014031709042602','2014-03-06 00:00:00','2014-03-17 21:05:07','擎华',2,4,20,'2014-03-03 00:00:00',35222.00,35222.00,1,0.1254,1),(3,'2014-03-18 00:00:00',2,8,NULL,1,2,'2014-03-18 00:00:00',-1,NULL,10,0.00,'O2014031810083404','2014-03-05 00:00:00','2014-03-18 00:00:00','同心日',2,9,0.125,'2014-03-03 00:00:00',372000.00,372000.00,1,0.25,1),(4,'2014-03-18 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,6,0.00,'O2014031800013106',NULL,'2014-03-18 12:02:33','国开银行',1,20,1.5,'2013-03-05 00:00:00',20000000.00,20000000.00,1,1,1);
/*!40000 ALTER TABLE `fc_outfunds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_outinterest`
--

DROP TABLE IF EXISTS `fc_outinterest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_outinterest` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `iamount` decimal(19,2) NOT NULL,
  `lastDate` datetime DEFAULT NULL,
  `osid` bigint(20) NOT NULL,
  `samount` decimal(19,2) NOT NULL,
  `status` int(11) NOT NULL,
  `xpayDate` datetime NOT NULL,
  `yamount` decimal(19,2) NOT NULL,
  `ysamount` decimal(19,2) NOT NULL,
  `dayamount` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_outinterest`
--

LOCK TABLES `fc_outinterest` WRITE;
/*!40000 ALTER TABLE `fc_outinterest` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_outinterest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_outsiderecords`
--

DROP TABLE IF EXISTS `fc_outsiderecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_outsiderecords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `amounts` decimal(19,2) NOT NULL,
  `changedate` datetime NOT NULL,
  `direction` int(11) NOT NULL,
  `fcnumber` varchar(30) DEFAULT NULL,
  `iamounts` decimal(19,2) NOT NULL,
  `latedays` int(11) NOT NULL,
  `mamounts` decimal(19,2) NOT NULL,
  `planId` bigint(20) NOT NULL,
  `prevDirection` int(11) DEFAULT NULL,
  `tabId` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_outsiderecords`
--

LOCK TABLES `fc_outsiderecords` WRITE;
/*!40000 ALTER TABLE `fc_outsiderecords` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_outsiderecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_overallog`
--

DROP TABLE IF EXISTS `fc_overallog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_overallog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `bpayType` varchar(50) DEFAULT NULL,
  `bussType` int(11) NOT NULL,
  `changeDate` datetime NOT NULL,
  `contractId` bigint(20) NOT NULL,
  `epayType` varchar(50) NOT NULL,
  `phAmount` decimal(19,2) DEFAULT NULL,
  `splanId` bigint(20) NOT NULL,
  `status` int(11) NOT NULL,
  `bphAmount` decimal(19,2) DEFAULT NULL,
  `formId` bigint(20) NOT NULL,
  `isfpamt` int(11) DEFAULT NULL,
  `reprincipal` decimal(19,2) DEFAULT NULL,
  `startDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_overallog`
--

LOCK TABLES `fc_overallog` WRITE;
/*!40000 ALTER TABLE `fc_overallog` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_overallog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_ownfunds`
--

DROP TABLE IF EXISTS `fc_ownfunds`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_ownfunds` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accountId` bigint(20) NOT NULL,
  `bamount` decimal(19,2) NOT NULL,
  `code` varchar(20) NOT NULL,
  `lastDate` datetime NOT NULL,
  `totalAmount` decimal(19,2) NOT NULL,
  `uamount` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_ownfunds`
--

LOCK TABLES `fc_ownfunds` WRITE;
/*!40000 ALTER TABLE `fc_ownfunds` DISABLE KEYS */;
INSERT INTO `fc_ownfunds` VALUES (1,'2014-03-14 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,6,810000.00,'O2014031411210700','2014-03-14 00:00:00',6364243.67,5554243.67),(2,'2014-03-17 12:37:46',13,4,NULL,1,NULL,NULL,1,NULL,8,0.00,'O20140317003746','2014-03-17 00:00:00',9722.63,9722.63);
/*!40000 ALTER TABLE `fc_ownfunds` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_paytype`
--

DROP TABLE IF EXISTS `fc_paytype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_paytype` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `amountFormula` varchar(255) DEFAULT NULL,
  `amountParams` varchar(150) DEFAULT NULL,
  `amoutDispFormula` varchar(255) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `inter` varchar(50) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `raDispFormula` varchar(255) DEFAULT NULL,
  `raFormula` varchar(255) DEFAULT NULL,
  `raParams` varchar(150) DEFAULT NULL,
  `rateDispFormula` varchar(255) DEFAULT NULL,
  `rateFormula` varchar(255) DEFAULT NULL,
  `rateParams` varchar(150) DEFAULT NULL,
  `pdfa` varchar(255) DEFAULT NULL,
  `prfa` varchar(255) DEFAULT NULL,
  `rps` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_paytype`
--

LOCK TABLES `fc_paytype` WRITE;
/*!40000 ALTER TABLE `fc_paytype` DISABLE KEYS */;
INSERT INTO `fc_paytype` VALUES (1,'2014-03-05 09:07:47',45,8,NULL,1,2,'2014-03-04 16:00:00',2,'1.按月付息，到期一次性还本\n2. 当期还本付息公式支持以下参数: rate(利率),mrate(管理费率),zphases(剩余期数),totalPhases(总期),appAmount(贷款金额),reprincipal(剩余金额),day_rate(日利率),day_mrate(日费率)',NULL,NULL,NULL,'P0001','MinterestEamountPayType','按月付息，到期一次性还本[OK]',NULL,NULL,NULL,NULL,NULL,NULL,'贷款余额*月利率*12/360*上一结息日至还款日的天数','reprincipal*rate*udays','reprincipal:BG,rate:F,udays:I'),(2,'2014-03-05 10:05:32',45,8,NULL,1,2,'2014-03-04 16:00:00',2,'1. 按月付息，分期还本\n2. 支持以下参数: rate(利率),mrate(管理费率),zphases(剩余期数),totalPhases(总期),appAmount(贷款金额),reprincipal(剩余金额),day_rate(日利率),day_mrate(日费率),phAmount(分期还本额)',NULL,NULL,NULL,'P0002','MinterestMamountPayType','按月付息，分期还本[OK]',NULL,NULL,NULL,NULL,NULL,NULL,'贷款余额*月利率*12/360*上一结息日至还款日的天数','reprincipal*rate*udays','reprincipal:BG,rate:F,udays:I'),(3,'2013-01-23 16:01:00',45,8,NULL,0,45,'2013-01-27 16:01:00',2,NULL,NULL,NULL,NULL,'P0003','SeasonInterestEamountPayType','按季付息，到期一次性还本',NULL,NULL,NULL,'本金*月利率','appAmount*rate','appAmount,rate',NULL,NULL,NULL),(4,'2013-01-23 16:01:00',45,8,NULL,0,45,'2013-01-27 16:01:00',2,'公式测试OK<br/>\n本金每3个月还一次，在每个季节的最后一个月开始还固定本金。最后一期还剩余的本金',NULL,NULL,NULL,'P0004','MinterestSeasonamountPayType','按月付息，分季还本',NULL,NULL,NULL,'上期贷款余额 * 月利率','reprincipal * rate','reprincipal,rate',NULL,NULL,NULL),(5,'2014-03-05 11:40:34',45,8,NULL,1,2,'2014-03-04 16:00:00',2,'1. 等额本金 OK\n2. 当期还本付息公式支持以下参数: rate(利率),mrate(管理费率),zphases(剩余期数),totalPhases(总期),appAmount(贷款金额),reprincipal(剩余金额),day_rate(日利率),day_mrate(日费率)',NULL,NULL,NULL,'P0005','EqualAmountPayType','等额本金[OK]',NULL,NULL,NULL,NULL,NULL,NULL,'贷款余额*月利率*12/360*上一结息日至还款日的天数','reprincipal*rate*udays','reprincipal:BG,rate:F,udays:I'),(6,'2014-03-05 09:07:58',-1,-1,NULL,1,2,'2014-03-04 16:00:00',0,'1. 等额本息不支持预收息 OK\n2. 当期还本付息公式支持以下参数: rate(利率),mrate(管理费率),zphases(剩余期数),totalPhases(总期),appAmount(贷款金额),reprincipal(剩余金额),day_rate(日利率),day_mrate(日费率)',NULL,NULL,NULL,'P0006','EqualInterestAmountPayType','等额本息[OK]',NULL,NULL,NULL,NULL,NULL,NULL,'贷款余额*月利率*12/360*上一结息日至还款日的天数','reprincipal*rate*udays','reprincipal:BG,rate:F,udays:I'),(7,'2014-03-05 11:17:06',45,8,NULL,1,2,'2014-03-04 16:00:00',2,'当期应还利息中的公式，在“到期一次还本付息”中是用来算总利息的\n公式测试OK',NULL,NULL,NULL,'P0007','EinterestAmountPayType','到期一次还本付息[OK]',NULL,NULL,NULL,NULL,NULL,NULL,'贷款余额*月利率*12/360*上一结息日至还款日的天数','reprincipal*rate*udays','reprincipal:BG,rate:F,udays:I'),(8,'2014-03-05 12:42:55',2,8,NULL,1,2,'2014-03-04 16:00:00',-1,NULL,NULL,NULL,NULL,'P0008','FlexibleAmountPayType','灵活还本[OK]',NULL,NULL,NULL,NULL,NULL,NULL,'贷款余额*月利率*12/360*上一结息日至还款日的天数','reprincipal*rate*udays','reprincipal:BG,rate:F,udays:I');
/*!40000 ALTER TABLE `fc_paytype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_plan`
--

DROP TABLE IF EXISTS `fc_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `contractId` bigint(20) NOT NULL,
  `delAmount` decimal(19,2) NOT NULL,
  `einterest` decimal(19,2) NOT NULL,
  `emgrAmount` decimal(19,2) NOT NULL,
  `exempt` int(11) NOT NULL,
  `extNo` int(11) DEFAULT NULL,
  `extcontractId` bigint(20) DEFAULT NULL,
  `interest` decimal(19,2) NOT NULL,
  `ipamount` decimal(19,2) NOT NULL,
  `istatus` int(11) NOT NULL,
  `lastDate` datetime DEFAULT NULL,
  `lateInterest` decimal(19,2) NOT NULL,
  `mgrAmount` decimal(19,2) NOT NULL,
  `mgrdays` int(11) NOT NULL,
  `mgrstatus` int(11) NOT NULL,
  `pdays` int(11) NOT NULL,
  `penAmount` decimal(19,2) NOT NULL,
  `phases` int(11) NOT NULL,
  `pistatus` int(11) NOT NULL,
  `ppamount` decimal(19,2) NOT NULL,
  `principal` decimal(19,2) NOT NULL,
  `provision` int(11) NOT NULL,
  `pstatus` int(11) NOT NULL,
  `ratedays` int(11) NOT NULL,
  `rdamount` decimal(19,2) DEFAULT NULL,
  `reprincipal` decimal(19,2) NOT NULL,
  `riamount` decimal(19,2) DEFAULT NULL,
  `rmamount` decimal(19,2) DEFAULT NULL,
  `rpamount` decimal(19,2) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `totalAmount` decimal(19,2) NOT NULL,
  `trDate` datetime DEFAULT NULL,
  `trdelAmount` decimal(19,2) NOT NULL,
  `trinterAmount` decimal(19,2) NOT NULL,
  `trmgrAmount` decimal(19,2) NOT NULL,
  `trpenAmount` decimal(19,2) NOT NULL,
  `xpayDate` datetime NOT NULL,
  `ydelAmount` decimal(19,2) NOT NULL,
  `yinterest` decimal(19,2) NOT NULL,
  `ymgrAmount` decimal(19,2) NOT NULL,
  `ypenAmount` decimal(19,2) NOT NULL,
  `yprincipal` decimal(19,2) NOT NULL,
  `ytotalAmount` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_plan`
--

LOCK TABLES `fc_plan` WRITE;
/*!40000 ALTER TABLE `fc_plan` DISABLE KEYS */;
INSERT INTO `fc_plan` VALUES (1,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,266.67,0.00,0,0,NULL,386.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,1,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,386.67,NULL,0.00,0.00,0.00,0.00,'2014-04-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(2,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,280.00,0.00,0,0,NULL,400.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,2,0,0.00,1491.19,0,0,0,0.00,18508.81,0.00,0.00,0.00,0,1891.19,NULL,0.00,0.00,0.00,0.00,'2014-05-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(3,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,271.46,0.00,0,0,NULL,382.52,0.00,0,NULL,0.00,0.00,0,0,0,0.00,3,0,0.00,1508.68,0,0,0,0.00,17000.13,0.00,0.00,0.00,0,1891.19,NULL,0.00,0.00,0.00,0.00,'2014-06-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(4,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,238.00,0.00,0,0,NULL,340.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,4,0,0.00,1551.19,0,0,0,0.00,15448.94,0.00,0.00,0.00,0,1891.19,NULL,0.00,0.00,0.00,0.00,'2014-07-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(5,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,226.58,0.00,0,0,NULL,319.28,0.00,0,NULL,0.00,0.00,0,0,0,0.00,5,0,0.00,1571.91,0,0,0,0.00,13877.03,0.00,0.00,0.00,0,1891.19,NULL,0.00,0.00,0.00,0.00,'2014-08-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(6,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,203.53,0.00,0,0,NULL,286.79,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,1604.40,0,0,0,0.00,12272.63,0.00,0.00,0.00,0,1891.19,NULL,0.00,0.00,0.00,0.00,'2014-09-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(7,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,171.82,0.00,0,0,NULL,245.45,0.00,0,NULL,0.00,0.00,0,0,0,0.00,7,0,0.00,1645.74,0,0,0,0.00,10626.89,0.00,0.00,0.00,0,1891.19,NULL,0.00,0.00,0.00,0.00,'2014-10-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(8,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,155.86,0.00,0,0,NULL,219.62,0.00,0,NULL,0.00,0.00,0,0,0,0.00,8,0,0.00,1671.57,0,0,0,0.00,8955.32,0.00,0.00,0.00,0,1891.19,NULL,0.00,0.00,0.00,0.00,'2014-11-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(9,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,125.37,0.00,0,0,NULL,179.11,0.00,0,NULL,0.00,0.00,0,0,0,0.00,9,0,0.00,1712.09,0,0,0,0.00,7243.23,0.00,0.00,0.00,0,1891.19,NULL,0.00,0.00,0.00,0.00,'2014-12-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(10,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,106.23,0.00,0,0,NULL,149.69,0.00,0,NULL,0.00,0.00,0,0,0,0.00,10,0,0.00,1741.50,0,0,0,0.00,5501.73,0.00,0.00,0.00,0,1891.19,NULL,0.00,0.00,0.00,0.00,'2015-01-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(11,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,80.69,0.00,0,0,NULL,113.70,0.00,0,NULL,0.00,0.00,0,0,0,0.00,11,0,0.00,1777.49,0,0,0,0.00,3724.24,0.00,0.00,0.00,0,1891.19,NULL,0.00,0.00,0.00,0.00,'2015-02-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(12,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,47.17,0.00,0,0,NULL,69.52,0.00,0,NULL,0.00,0.00,0,0,0,0.00,12,0,0.00,1821.67,0,0,0,0.00,1902.57,0.00,0.00,0.00,0,1891.19,NULL,0.00,0.00,0.00,0.00,'2015-03-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(13,'2014-03-12 18:36:22',22,2,NULL,1,NULL,NULL,1,NULL,3,0.00,26.64,0.00,0,0,NULL,1.27,0.00,0,NULL,0.00,0.00,0,0,0,0.00,13,0,0.00,1902.57,0,0,0,0.00,0.00,0.00,0.00,0.00,0,1903.84,NULL,0.00,0.00,0.00,0.00,'2015-03-11 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(14,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,266.67,0.00,0,0,NULL,386.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,1,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,386.67,NULL,0.00,0.00,0.00,0.00,'2014-04-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(15,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,280.00,0.00,0,0,NULL,400.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,2,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,400.00,NULL,0.00,0.00,0.00,0.00,'2014-05-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(16,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,293.33,0.00,0,0,NULL,413.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,3,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,413.33,NULL,0.00,0.00,0.00,0.00,'2014-06-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(17,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,280.00,0.00,0,0,NULL,400.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,4,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,400.00,NULL,0.00,0.00,0.00,0.00,'2014-07-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(18,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,293.33,0.00,0,0,NULL,413.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,5,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,413.33,NULL,0.00,0.00,0.00,0.00,'2014-08-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(19,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,293.33,0.00,0,0,NULL,413.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,413.33,NULL,0.00,0.00,0.00,0.00,'2014-09-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(20,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,280.00,0.00,0,0,NULL,400.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,7,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,400.00,NULL,0.00,0.00,0.00,0.00,'2014-10-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(21,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,293.33,0.00,0,0,NULL,413.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,8,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,413.33,NULL,0.00,0.00,0.00,0.00,'2014-11-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(22,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,280.00,0.00,0,0,NULL,400.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,9,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,400.00,NULL,0.00,0.00,0.00,0.00,'2014-12-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(23,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,293.33,0.00,0,0,NULL,413.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,10,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,413.33,NULL,0.00,0.00,0.00,0.00,'2015-01-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(24,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,293.33,0.00,0,0,NULL,413.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,11,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,413.33,NULL,0.00,0.00,0.00,0.00,'2015-02-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(25,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,253.33,0.00,0,0,NULL,373.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,12,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,373.33,NULL,0.00,0.00,0.00,0.00,'2015-03-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(26,'2014-03-12 18:52:13',22,2,NULL,1,NULL,NULL,1,NULL,4,0.00,26.67,0.00,0,0,NULL,13.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,13,0,0.00,20000.00,0,0,0,0.00,0.00,0.00,0.00,0.00,0,20013.33,NULL,0.00,0.00,0.00,0.00,'2015-03-11 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(27,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,0.00,0.00,0,0,NULL,413.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,1,0,0.00,0.00,0,0,0,0.00,20000.00,0.00,0.00,0.00,0,413.33,NULL,0.00,0.00,0.00,0.00,'2014-04-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(28,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,280.00,0.00,0,0,NULL,400.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,2,0,0.00,1643.56,0,0,0,0.00,18356.44,0.00,0.00,0.00,0,2043.56,NULL,0.00,0.00,0.00,0.00,'2014-05-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(29,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,269.23,0.00,0,0,NULL,379.37,0.00,0,NULL,0.00,0.00,0,0,0,0.00,3,0,0.00,1664.19,0,0,0,0.00,16692.25,0.00,0.00,0.00,0,2043.56,NULL,0.00,0.00,0.00,0.00,'2014-06-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(30,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,233.69,0.00,0,0,NULL,333.85,0.00,0,NULL,0.00,0.00,0,0,0,0.00,4,0,0.00,1709.71,0,0,0,0.00,14982.54,0.00,0.00,0.00,0,2043.56,NULL,0.00,0.00,0.00,0.00,'2014-07-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(31,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,219.74,0.00,0,0,NULL,309.64,0.00,0,NULL,0.00,0.00,0,0,0,0.00,5,0,0.00,1733.92,0,0,0,0.00,13248.62,0.00,0.00,0.00,0,2043.56,NULL,0.00,0.00,0.00,0.00,'2014-08-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(32,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,194.31,0.00,0,0,NULL,273.80,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,1769.75,0,0,0,0.00,11478.87,0.00,0.00,0.00,0,2043.56,NULL,0.00,0.00,0.00,0.00,'2014-09-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(33,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,160.70,0.00,0,0,NULL,229.58,0.00,0,NULL,0.00,0.00,0,0,0,0.00,7,0,0.00,1813.98,0,0,0,0.00,9664.89,0.00,0.00,0.00,0,2043.56,NULL,0.00,0.00,0.00,0.00,'2014-10-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(34,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,141.75,0.00,0,0,NULL,199.74,0.00,0,NULL,0.00,0.00,0,0,0,0.00,8,0,0.00,1843.82,0,0,0,0.00,7821.07,0.00,0.00,0.00,0,2043.56,NULL,0.00,0.00,0.00,0.00,'2014-11-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(35,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,109.49,0.00,0,0,NULL,156.42,0.00,0,NULL,0.00,0.00,0,0,0,0.00,9,0,0.00,1887.14,0,0,0,0.00,5933.93,0.00,0.00,0.00,0,2043.56,NULL,0.00,0.00,0.00,0.00,'2014-12-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(36,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,87.03,0.00,0,0,NULL,122.63,0.00,0,NULL,0.00,0.00,0,0,0,0.00,10,0,0.00,1920.92,0,0,0,0.00,4013.01,0.00,0.00,0.00,0,2043.56,NULL,0.00,0.00,0.00,0.00,'2015-01-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(37,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,58.86,0.00,0,0,NULL,82.94,0.00,0,NULL,0.00,0.00,0,0,0,0.00,11,0,0.00,1960.62,0,0,0,0.00,2052.39,0.00,0.00,0.00,0,2043.56,NULL,0.00,0.00,0.00,0.00,'2015-02-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(38,'2014-03-12 21:09:39',22,2,NULL,1,NULL,NULL,1,NULL,5,0.00,26.00,0.00,0,0,NULL,36.94,0.00,0,NULL,0.00,0.00,0,0,0,0.00,12,0,0.00,2052.39,0,0,0,0.00,0.00,0.00,0.00,0.00,0,2089.33,NULL,0.00,0.00,0.00,0.00,'2015-03-09 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(39,'2014-03-14 11:20:00',22,2,NULL,1,2,'2014-03-14 11:30:16',1,NULL,6,0.00,1200.00,0.00,0,0,NULL,1800.00,0.00,0,'2014-03-14 00:00:00',0.00,0.00,0,2,0,0.00,1,2,0.00,0.00,0,0,0,0.00,100000.00,0.00,0.00,0.00,2,1800.00,NULL,0.00,0.00,0.00,0.00,'2014-04-10 00:00:00',0.00,1800.00,0.00,0.00,0.00,1800.00),(40,'2014-03-14 11:20:00',22,2,NULL,1,2,'2014-03-14 11:30:25',1,NULL,6,0.00,1400.00,0.00,0,0,NULL,2000.00,0.00,0,'2014-03-14 00:00:00',0.00,0.00,0,2,0,0.00,2,2,0.00,0.00,0,0,0,0.00,100000.00,0.00,0.00,0.00,2,2000.00,NULL,0.00,0.00,0.00,0.00,'2014-05-10 00:00:00',0.00,2000.00,0.00,0.00,0.00,2000.00),(41,'2014-03-14 11:20:00',22,2,NULL,1,2,'2014-03-14 11:30:25',1,NULL,6,0.00,1466.67,0.00,0,0,NULL,2066.67,0.00,0,'2014-03-14 00:00:00',0.00,0.00,0,2,0,0.00,3,2,0.00,2000.00,0,0,0,0.00,98000.00,0.00,0.00,0.00,2,4066.67,NULL,0.00,0.00,0.00,0.00,'2014-06-10 00:00:00',0.00,2066.67,0.00,0.00,2000.00,4066.67),(42,'2014-03-14 11:20:00',22,2,NULL,1,2,'2014-03-14 11:30:25',1,NULL,6,0.00,1372.00,0.00,0,0,NULL,1960.00,0.00,0,'2014-03-14 00:00:00',0.00,0.00,0,2,0,0.00,4,2,0.00,2000.00,0,0,0,0.00,96000.00,0.00,0.00,0.00,2,3960.00,NULL,0.00,0.00,0.00,0.00,'2014-07-10 00:00:00',0.00,1960.00,0.00,0.00,2000.00,3960.00),(43,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,6,0.00,1408.00,0.00,0,0,NULL,1984.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,5,0,0.00,2000.00,0,0,0,0.00,94000.00,0.00,0.00,0.00,0,3984.00,NULL,0.00,0.00,0.00,0.00,'2014-08-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(44,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,6,0.00,1378.67,0.00,0,0,NULL,1942.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,0.00,0,0,0,0.00,94000.00,0.00,0.00,0.00,0,1942.67,NULL,0.00,0.00,0.00,0.00,'2014-09-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(45,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,6,0.00,1316.00,0.00,0,0,NULL,1880.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,7,0,0.00,0.00,0,0,0,0.00,94000.00,0.00,0.00,0.00,0,1880.00,NULL,0.00,0.00,0.00,0.00,'2014-10-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(46,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,6,0.00,1378.67,0.00,0,0,NULL,1942.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,8,0,0.00,0.00,0,0,0,0.00,94000.00,0.00,0.00,0.00,0,1942.67,NULL,0.00,0.00,0.00,0.00,'2014-11-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(47,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,6,0.00,1316.00,0.00,0,0,NULL,1880.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,9,0,0.00,2000.00,0,0,0,0.00,92000.00,0.00,0.00,0.00,0,3880.00,NULL,0.00,0.00,0.00,0.00,'2014-12-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(48,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,6,0.00,1349.33,0.00,0,0,NULL,1901.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,10,0,0.00,2000.00,0,0,0,0.00,90000.00,0.00,0.00,0.00,0,3901.33,NULL,0.00,0.00,0.00,0.00,'2015-01-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(49,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,6,0.00,1320.00,0.00,0,0,NULL,1860.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,11,0,0.00,2000.00,0,0,0,0.00,88000.00,0.00,0.00,0.00,0,3860.00,NULL,0.00,0.00,0.00,0.00,'2015-02-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(50,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,6,0.00,1114.67,0.00,0,0,NULL,1642.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,12,0,0.00,2000.00,0,0,0,0.00,86000.00,0.00,0.00,0.00,0,3642.67,NULL,0.00,0.00,0.00,0.00,'2015-03-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(51,'2014-03-14 11:20:00',22,2,NULL,1,NULL,NULL,1,NULL,6,0.00,229.33,0.00,0,0,NULL,172.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,13,0,0.00,86000.00,0,0,0,0.00,0.00,0.00,0.00,0.00,0,86172.00,NULL,0.00,0.00,0.00,0.00,'2015-03-13 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(52,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,1200.00,0.00,0,0,NULL,1800.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,1,0,0.00,0.00,0,0,0,0.00,100000.00,0.00,0.00,0.00,0,1800.00,NULL,0.00,0.00,0.00,0.00,'2014-04-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(53,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,1400.00,0.00,0,0,NULL,2000.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,2,0,0.00,7455.96,0,0,0,0.00,92544.04,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-05-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(54,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,1357.31,0.00,0,0,NULL,1912.58,0.00,0,NULL,0.00,0.00,0,0,0,0.00,3,0,0.00,7543.38,0,0,0,0.00,85000.66,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-06-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(55,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,1190.01,0.00,0,0,NULL,1700.01,0.00,0,NULL,0.00,0.00,0,0,0,0.00,4,0,0.00,7755.95,0,0,0,0.00,77244.71,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-07-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(56,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,1132.92,0.00,0,0,NULL,1596.39,0.00,0,NULL,0.00,0.00,0,0,0,0.00,5,0,0.00,7859.57,0,0,0,0.00,69385.14,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-08-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(57,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,1017.65,0.00,0,0,NULL,1433.96,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,8022.00,0,0,0,0.00,61363.14,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-09-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(58,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,859.08,0.00,0,0,NULL,1227.26,0.00,0,NULL,0.00,0.00,0,0,0,0.00,7,0,0.00,8228.70,0,0,0,0.00,53134.44,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-10-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(59,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,779.31,0.00,0,0,NULL,1098.11,0.00,0,NULL,0.00,0.00,0,0,0,0.00,8,0,0.00,8357.85,0,0,0,0.00,44776.59,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-11-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(60,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,626.87,0.00,0,0,NULL,895.53,0.00,0,NULL,0.00,0.00,0,0,0,0.00,9,0,0.00,8560.43,0,0,0,0.00,36216.16,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-12-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(61,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,531.17,0.00,0,0,NULL,748.47,0.00,0,NULL,0.00,0.00,0,0,0,0.00,10,0,0.00,8707.49,0,0,0,0.00,27508.67,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2015-01-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(62,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,403.46,0.00,0,0,NULL,568.51,0.00,0,NULL,0.00,0.00,0,0,0,0.00,11,0,0.00,8887.45,0,0,0,0.00,18621.22,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2015-02-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(63,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,235.87,0.00,0,0,NULL,347.60,0.00,0,NULL,0.00,0.00,0,0,0,0.00,12,0,0.00,9108.36,0,0,0,0.00,9512.86,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2015-03-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(64,'2014-03-14 13:51:57',22,2,NULL,1,NULL,NULL,1,NULL,7,0.00,120.50,0.00,0,0,NULL,19.03,0.00,0,NULL,0.00,0.00,0,0,0,0.00,13,0,0.00,9512.86,0,0,0,0.00,0.00,0.00,0.00,0.00,0,9531.89,NULL,0.00,0.00,0.00,0.00,'2015-03-13 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(65,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,1200.00,0.00,0,0,NULL,1800.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,1,0,0.00,5000.00,0,0,0,0.00,95000.00,0.00,0.00,0.00,0,6800.00,NULL,0.00,0.00,0.00,0.00,'2014-04-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(66,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,1330.00,0.00,0,0,NULL,1900.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,2,0,0.00,5000.00,0,0,0,0.00,90000.00,0.00,0.00,0.00,0,6900.00,NULL,0.00,0.00,0.00,0.00,'2014-05-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(67,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,1320.00,0.00,0,0,NULL,1860.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,3,0,0.00,5000.00,0,0,0,0.00,85000.00,0.00,0.00,0.00,0,6860.00,NULL,0.00,0.00,0.00,0.00,'2014-06-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(68,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,1190.00,0.00,0,0,NULL,1700.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,4,0,0.00,5000.00,0,0,0,0.00,80000.00,0.00,0.00,0.00,0,6700.00,NULL,0.00,0.00,0.00,0.00,'2014-07-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(69,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,1173.33,0.00,0,0,NULL,1653.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,5,0,0.00,5000.00,0,0,0,0.00,75000.00,0.00,0.00,0.00,0,6653.33,NULL,0.00,0.00,0.00,0.00,'2014-08-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(70,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,1100.00,0.00,0,0,NULL,1550.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,5000.00,0,0,0,0.00,70000.00,0.00,0.00,0.00,0,6550.00,NULL,0.00,0.00,0.00,0.00,'2014-09-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(71,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,980.00,0.00,0,0,NULL,1400.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,7,0,0.00,5000.00,0,0,0,0.00,65000.00,0.00,0.00,0.00,0,6400.00,NULL,0.00,0.00,0.00,0.00,'2014-10-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(72,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,953.33,0.00,0,0,NULL,1343.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,8,0,0.00,5000.00,0,0,0,0.00,60000.00,0.00,0.00,0.00,0,6343.33,NULL,0.00,0.00,0.00,0.00,'2014-11-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(73,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,840.00,0.00,0,0,NULL,1200.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,9,0,0.00,5000.00,0,0,0,0.00,55000.00,0.00,0.00,0.00,0,6200.00,NULL,0.00,0.00,0.00,0.00,'2014-12-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(74,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,806.67,0.00,0,0,NULL,1136.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,10,0,0.00,5000.00,0,0,0,0.00,50000.00,0.00,0.00,0.00,0,6136.67,NULL,0.00,0.00,0.00,0.00,'2015-01-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(75,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,733.33,0.00,0,0,NULL,1033.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,11,0,0.00,5000.00,0,0,0,0.00,45000.00,0.00,0.00,0.00,0,6033.33,NULL,0.00,0.00,0.00,0.00,'2015-02-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(76,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,570.00,0.00,0,0,NULL,840.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,12,0,0.00,5000.00,0,0,0,0.00,40000.00,0.00,0.00,0.00,0,5840.00,NULL,0.00,0.00,0.00,0.00,'2015-03-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(77,'2014-03-14 15:13:13',22,2,NULL,1,NULL,NULL,1,NULL,8,0.00,106.67,0.00,0,0,NULL,80.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,13,0,0.00,40000.00,0,0,0,0.00,0.00,0.00,0.00,0.00,0,40080.00,NULL,0.00,0.00,0.00,0.00,'2015-03-13 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(78,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,2000.00,0.00,0,0,NULL,3200.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,1,0,0.00,0.00,0,0,0,0.00,200000.00,0.00,0.00,0.00,0,3200.00,NULL,0.00,0.00,0.00,0.00,'2014-04-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(79,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,2800.00,0.00,0,0,NULL,4000.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,2,0,0.00,0.00,0,0,0,0.00,200000.00,0.00,0.00,0.00,0,4000.00,NULL,0.00,0.00,0.00,0.00,'2014-05-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(80,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,2933.33,0.00,0,0,NULL,4133.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,3,0,0.00,8000.00,0,0,0,0.00,192000.00,0.00,0.00,0.00,0,12133.33,NULL,0.00,0.00,0.00,0.00,'2014-06-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(81,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,2688.00,0.00,0,0,NULL,3840.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,4,0,0.00,8000.00,0,0,0,0.00,184000.00,0.00,0.00,0.00,0,11840.00,NULL,0.00,0.00,0.00,0.00,'2014-07-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(82,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,2698.67,0.00,0,0,NULL,3802.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,5,0,0.00,8000.00,0,0,0,0.00,176000.00,0.00,0.00,0.00,0,11802.67,NULL,0.00,0.00,0.00,0.00,'2014-08-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(83,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,2581.33,0.00,0,0,NULL,3637.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,8000.00,0,0,0,0.00,168000.00,0.00,0.00,0.00,0,11637.33,NULL,0.00,0.00,0.00,0.00,'2014-09-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(84,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,2352.00,0.00,0,0,NULL,3360.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,7,0,0.00,8000.00,0,0,0,0.00,160000.00,0.00,0.00,0.00,0,11360.00,NULL,0.00,0.00,0.00,0.00,'2014-10-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(85,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,2346.67,0.00,0,0,NULL,3306.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,8,0,0.00,8000.00,0,0,0,0.00,152000.00,0.00,0.00,0.00,0,11306.67,NULL,0.00,0.00,0.00,0.00,'2014-11-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(86,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,2128.00,0.00,0,0,NULL,3040.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,9,0,0.00,8000.00,0,0,0,0.00,144000.00,0.00,0.00,0.00,0,11040.00,NULL,0.00,0.00,0.00,0.00,'2014-12-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(87,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,2112.00,0.00,0,0,NULL,2976.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,10,0,0.00,8000.00,0,0,0,0.00,136000.00,0.00,0.00,0.00,0,10976.00,NULL,0.00,0.00,0.00,0.00,'2015-01-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(88,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,1994.67,0.00,0,0,NULL,2810.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,11,0,0.00,8000.00,0,0,0,0.00,128000.00,0.00,0.00,0.00,0,10810.67,NULL,0.00,0.00,0.00,0.00,'2015-02-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(89,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,1621.33,0.00,0,0,NULL,2389.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,12,0,0.00,8000.00,0,0,0,0.00,120000.00,0.00,0.00,0.00,0,10389.33,NULL,0.00,0.00,0.00,0.00,'2015-03-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(90,'2014-03-17 11:11:10',22,2,NULL,1,NULL,NULL,1,NULL,10,0.00,560.00,0.00,0,0,NULL,480.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,13,0,0.00,120000.00,0,0,0,0.00,0.00,0.00,0.00,0.00,0,120480.00,NULL,0.00,0.00,0.00,0.00,'2015-03-16 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(91,'2014-03-17 11:16:59',22,2,NULL,1,13,'2014-03-17 11:18:12',1,NULL,11,0.00,3000.00,0.00,0,0,NULL,4800.00,0.00,2,'2014-03-17 00:00:00',0.00,0.00,0,2,0,0.00,1,2,0.00,0.00,0,2,0,0.00,300000.00,0.00,0.00,0.00,2,4800.00,NULL,0.00,0.00,0.00,0.00,'2013-03-10 00:00:00',0.00,4800.00,0.00,0.00,0.00,4800.00),(92,'2014-03-17 11:16:59',22,2,NULL,1,13,'2014-03-17 11:18:12',1,NULL,11,0.00,4400.00,0.00,0,0,NULL,6200.00,0.00,2,'2014-03-17 00:00:00',0.00,0.00,0,2,0,0.00,2,2,0.00,22167.88,0,2,0,0.00,277832.12,0.00,0.00,0.00,2,28367.88,NULL,0.00,0.00,0.00,0.00,'2013-04-10 00:00:00',0.00,6200.00,0.00,0.00,22167.88,28367.88),(93,'2014-03-17 11:16:59',22,2,NULL,1,13,'2014-03-17 11:18:12',1,NULL,11,0.00,3889.65,0.00,0,0,NULL,5556.64,0.00,2,'2014-03-17 00:00:00',0.00,0.00,0,2,0,0.00,3,2,0.00,22811.24,0,2,0,0.00,255020.88,0.00,0.00,0.00,2,28367.88,NULL,0.00,0.00,0.00,0.00,'2013-05-10 00:00:00',0.00,5556.64,0.00,0.00,22811.24,28367.88),(94,'2014-03-17 11:16:59',22,2,NULL,1,13,'2014-03-17 11:18:12',1,NULL,11,0.00,3740.31,0.00,0,0,NULL,5270.43,0.00,2,'2014-03-17 00:00:00',0.00,0.00,0,2,0,0.00,4,2,0.00,23097.45,0,2,0,0.00,231923.43,0.00,0.00,0.00,2,28367.88,NULL,0.00,0.00,0.00,0.00,'2013-06-10 00:00:00',0.00,5270.43,0.00,0.00,23097.45,28367.88),(95,'2014-03-17 11:16:59',22,2,NULL,1,13,'2014-03-17 11:18:12',1,NULL,11,0.00,3246.93,0.00,0,0,NULL,4638.47,0.00,2,'2014-03-17 00:00:00',0.00,0.00,0,2,0,0.00,5,2,0.00,23729.41,0,2,0,0.00,208194.02,0.00,0.00,0.00,2,28367.88,NULL,0.00,0.00,0.00,0.00,'2013-07-10 00:00:00',0.00,4638.47,0.00,0.00,23729.41,28367.88),(96,'2014-03-17 11:16:59',22,2,NULL,1,13,'2014-03-17 11:18:12',1,NULL,11,0.00,3053.51,0.00,0,0,NULL,4302.68,0.00,2,'2014-03-17 00:00:00',0.00,0.00,0,2,0,0.00,6,2,0.00,24065.20,0,2,0,0.00,184128.82,0.00,0.00,0.00,2,28367.88,NULL,0.00,0.00,0.00,0.00,'2013-08-10 00:00:00',0.00,4302.68,0.00,0.00,24065.20,28367.88),(97,'2014-03-17 11:16:59',22,2,NULL,1,13,'2014-03-17 11:18:12',1,NULL,11,0.00,2700.56,0.00,0,0,NULL,3805.33,0.00,2,'2014-03-17 00:00:00',0.00,0.00,0,2,0,0.00,7,2,0.00,24562.55,0,2,0,0.00,159566.27,0.00,0.00,0.00,2,28367.88,NULL,0.00,0.00,0.00,0.00,'2013-09-10 00:00:00',0.00,3805.33,0.00,0.00,24562.55,28367.88),(98,'2014-03-17 11:16:59',22,2,NULL,1,13,'2014-03-17 11:18:12',1,NULL,11,0.00,2233.93,0.00,0,0,NULL,3191.33,0.00,2,'2014-03-17 00:00:00',0.00,0.00,0,2,0,0.00,8,2,0.00,25176.55,0,2,0,0.00,134389.72,0.00,0.00,0.00,2,28367.88,NULL,0.00,0.00,0.00,0.00,'2013-10-10 00:00:00',0.00,3191.33,0.00,0.00,25176.55,28367.88),(99,'2014-03-17 11:16:59',22,2,NULL,1,2,'2014-03-17 12:13:10',1,NULL,11,0.00,1971.05,0.00,0,0,NULL,2777.39,504.53,3,'2014-03-17 00:00:00',2292.41,0.00,0,2,167,4244.91,9,3,3740.38,25590.49,0,3,167,0.00,108799.23,0.00,0.00,0.00,4,28367.88,NULL,0.00,0.00,0.00,0.00,'2013-11-10 00:00:00',0.00,0.00,0.00,0.00,5000.00,5000.00),(100,'2014-03-17 11:16:59',22,2,NULL,1,2,'2014-03-17 12:13:10',1,NULL,11,0.00,1523.19,0.00,0,0,NULL,2175.98,319.32,3,NULL,2392.19,0.00,0,2,137,4162.97,10,3,3843.65,26191.89,0,3,137,0.00,82607.34,0.00,0.00,0.00,4,28367.88,NULL,0.00,0.00,0.00,0.00,'2013-12-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(101,'2014-03-17 11:16:59',22,2,NULL,1,2,'2014-03-17 12:13:10',1,NULL,11,0.00,1211.57,0.00,0,0,NULL,1707.22,190.80,3,NULL,1884.02,0.00,0,2,106,3170.48,11,3,2979.68,26660.66,0,3,106,0.00,55946.68,0.00,0.00,0.00,4,28367.88,NULL,0.00,0.00,0.00,0.00,'2014-01-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(102,'2014-03-17 11:16:59',22,2,NULL,1,2,'2014-03-17 12:13:10',1,NULL,11,0.00,820.55,0.00,0,0,NULL,1156.23,90.01,3,NULL,1360.58,0.00,0,2,75,2208.27,12,3,2118.26,27211.65,0,3,75,0.00,28735.03,0.00,0.00,0.00,4,28367.88,NULL,0.00,0.00,0.00,0.00,'2014-02-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(103,'2014-03-17 11:16:59',22,2,NULL,1,2,'2014-03-17 12:13:10',1,NULL,11,0.00,306.51,0.00,0,0,NULL,57.47,4.29,3,NULL,1379.28,0.00,0,2,72,2148.40,13,3,2144.11,28735.03,0,3,72,0.00,0.00,0.00,0.00,0.00,4,28792.50,NULL,0.00,0.00,0.00,0.00,'2014-02-13 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(104,'2014-03-17 12:35:51',22,2,NULL,1,2,'2014-03-18 09:20:01',1,NULL,12,0.00,0.00,0.00,0,0,NULL,266.67,0.00,2,'2014-03-18 00:00:00',0.00,0.00,0,2,0,0.00,1,2,0.00,0.00,0,2,0,0.00,100000.00,0.00,0.00,0.00,2,266.67,NULL,0.00,0.00,0.00,0.00,'2013-11-10 00:00:00',0.00,266.67,0.00,0.00,0.00,266.67),(105,'2014-03-17 12:35:51',22,2,NULL,1,2,'2014-03-18 09:20:01',1,NULL,12,0.00,1400.00,0.00,0,0,NULL,2000.00,0.00,2,'2014-03-18 00:00:00',0.00,0.00,0,2,0,0.00,2,2,0.00,7455.96,0,2,0,0.00,92544.04,0.00,0.00,0.00,2,9455.96,NULL,0.00,0.00,0.00,0.00,'2013-12-10 00:00:00',0.00,2000.00,0.00,0.00,7455.96,9455.96),(106,'2014-03-17 12:35:51',22,2,NULL,1,2,'2014-03-18 08:01:11',1,NULL,12,0.00,1357.31,0.00,0,0,NULL,1912.58,213.76,2,'2014-03-18 00:00:00',533.07,0.00,0,2,106,1056.83,3,2,843.07,7543.38,0,2,106,0.00,85000.66,0.00,0.00,0.00,4,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-01-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(107,'2014-03-17 12:35:51',22,2,NULL,1,2,'2014-03-18 09:12:42',1,NULL,12,0.00,1246.68,0.00,0,0,NULL,1756.68,136.75,0,'2014-03-22 00:00:00',384.96,0.00,0,0,75,736.09,4,0,599.34,7699.28,0,0,75,0.00,77301.38,0.00,0.00,0.00,4,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-02-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(108,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,12,0.00,979.15,0.00,0,0,NULL,1442.96,69.40,0,NULL,251.07,0.00,0,0,47,454.80,5,0,385.40,8013.00,0,0,47,0.00,69288.38,0.00,0.00,0.00,4,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-03-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(109,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,12,0.00,1016.23,0.00,0,0,NULL,1431.96,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,8024.00,0,0,0,0.00,61264.38,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-04-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(110,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,12,0.00,857.70,0.00,0,0,NULL,1225.29,0.00,0,NULL,0.00,0.00,0,0,0,0.00,7,0,0.00,8230.67,0,0,0,0.00,53033.71,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-05-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(111,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,12,0.00,777.83,0.00,0,0,NULL,1096.03,0.00,0,NULL,0.00,0.00,0,0,0,0.00,8,0,0.00,8359.93,0,0,0,0.00,44673.78,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-06-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(112,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,12,0.00,625.43,0.00,0,0,NULL,893.48,0.00,0,NULL,0.00,0.00,0,0,0,0.00,9,0,0.00,8562.48,0,0,0,0.00,36111.30,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-07-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(113,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,12,0.00,529.63,0.00,0,0,NULL,746.30,0.00,0,NULL,0.00,0.00,0,0,0,0.00,10,0,0.00,8709.66,0,0,0,0.00,27401.64,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-08-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(114,'2014-03-17 12:35:51',22,2,NULL,1,2,'2014-03-18 08:01:11',1,NULL,12,0.00,401.89,0.00,0,0,NULL,566.30,0.00,2,'2014-03-18 00:00:00',0.00,0.00,0,2,0,0.00,11,2,0.00,8889.66,0,2,0,0.00,18511.98,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-09-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(115,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,12,0.00,259.17,0.00,0,0,NULL,370.24,0.00,0,NULL,0.00,0.00,0,0,0,0.00,12,0,0.00,9085.72,0,0,0,0.00,9426.26,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-10-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(116,'2014-03-17 12:35:51',22,2,NULL,1,NULL,NULL,1,NULL,12,0.00,138.25,0.00,0,0,NULL,163.39,0.00,0,NULL,0.00,0.00,0,0,0,0.00,13,0,0.00,9426.26,0,0,0,0.00,0.00,0.00,0.00,0.00,0,9589.65,NULL,0.00,0.00,0.00,0.00,'2014-11-05 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(117,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,866.67,0.00,0,0,NULL,1466.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,1,0,0.00,0.00,0,0,0,0.00,100000.00,0.00,0.00,0.00,0,1466.67,NULL,0.00,0.00,0.00,0.00,'2014-01-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(118,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,1466.67,0.00,0,0,NULL,2066.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,2,0,0.00,7389.29,0,0,0,0.00,92610.71,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-02-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(119,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,1173.07,0.00,0,0,NULL,1728.73,0.00,0,NULL,0.00,0.00,0,0,0,0.00,3,0,0.00,7727.23,0,0,0,0.00,84883.48,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-03-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(120,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,1244.96,0.00,0,0,NULL,1754.26,0.00,0,NULL,0.00,0.00,0,0,0,0.00,4,0,0.00,7701.70,0,0,0,0.00,77181.78,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-04-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(121,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,1080.54,0.00,0,0,NULL,1543.64,0.00,0,NULL,0.00,0.00,0,0,0,0.00,5,0,0.00,7912.32,0,0,0,0.00,69269.46,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-05-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(122,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,1015.95,0.00,0,0,NULL,1431.57,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,8024.39,0,0,0,0.00,61245.07,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-06-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(123,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,857.43,0.00,0,0,NULL,1224.90,0.00,0,NULL,0.00,0.00,0,0,0,0.00,7,0,0.00,8231.06,0,0,0,0.00,53014.01,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-07-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(124,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,777.54,0.00,0,0,NULL,1095.62,0.00,0,NULL,0.00,0.00,0,0,0,0.00,8,0,0.00,8360.34,0,0,0,0.00,44653.67,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-08-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(125,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,654.92,0.00,0,0,NULL,922.84,0.00,0,NULL,0.00,0.00,0,0,0,0.00,9,0,0.00,8533.12,0,0,0,0.00,36120.55,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-09-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(126,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,505.69,0.00,0,0,NULL,722.41,0.00,0,NULL,0.00,0.00,0,0,0,0.00,10,0,0.00,8733.55,0,0,0,0.00,27387.00,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-10-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(127,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,401.68,0.00,0,0,NULL,566.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,11,0,0.00,8889.96,0,0,0,0.00,18497.04,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-11-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(128,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,258.96,0.00,0,0,NULL,369.94,0.00,0,NULL,0.00,0.00,0,0,0,0.00,12,0,0.00,9086.02,0,0,0,0.00,9411.02,0.00,0.00,0.00,0,9455.96,NULL,0.00,0.00,0.00,0.00,'2014-12-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(129,'2014-03-17 18:02:16',22,2,NULL,1,NULL,NULL,1,NULL,13,0.00,87.84,0.00,0,0,NULL,50.19,0.00,0,NULL,0.00,0.00,0,0,0,0.00,13,0,0.00,9411.02,0,0,0,0.00,0.00,0.00,0.00,0.00,0,9461.21,NULL,0.00,0.00,0.00,0.00,'2014-12-18 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(130,'2014-03-17 18:30:28',22,2,NULL,1,13,'2014-03-17 18:31:36',1,NULL,14,0.00,93.33,0.00,0,0,NULL,153.33,0.00,2,'2014-03-17 00:00:00',0.00,0.00,0,2,0,0.00,1,2,0.00,0.00,0,2,0,0.00,10000.00,0.00,0.00,0.00,2,153.33,NULL,0.00,0.00,0.00,0.00,'2014-01-10 00:00:00',0.00,153.33,0.00,0.00,0.00,153.33),(131,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,146.67,0.00,0,0,NULL,206.67,16.09,0,NULL,36.95,0.00,0,0,75,73.61,2,0,57.52,738.93,0,0,75,0.00,9261.07,0.00,0.00,0.00,4,945.60,NULL,0.00,0.00,0.00,0.00,'2014-02-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(132,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,117.31,0.00,0,0,NULL,172.87,0.00,0,NULL,0.00,0.00,0,0,0,0.00,3,0,0.00,772.72,0,0,0,0.00,8488.35,0.00,0.00,0.00,0,945.60,NULL,0.00,0.00,0.00,0.00,'2014-03-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(133,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,124.50,0.00,0,0,NULL,175.43,0.00,0,NULL,0.00,0.00,0,0,0,0.00,4,0,0.00,770.17,0,0,0,0.00,7718.18,0.00,0.00,0.00,0,945.60,NULL,0.00,0.00,0.00,0.00,'2014-04-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(134,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,108.05,0.00,0,0,NULL,154.36,0.00,0,NULL,0.00,0.00,0,0,0,0.00,5,0,0.00,791.23,0,0,0,0.00,6926.95,0.00,0.00,0.00,0,945.60,NULL,0.00,0.00,0.00,0.00,'2014-05-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(135,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,101.60,0.00,0,0,NULL,143.16,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,802.44,0,0,0,0.00,6124.51,0.00,0.00,0.00,0,945.60,NULL,0.00,0.00,0.00,0.00,'2014-06-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(136,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,85.74,0.00,0,0,NULL,122.49,0.00,0,NULL,0.00,0.00,0,0,0,0.00,7,0,0.00,823.11,0,0,0,0.00,5301.40,0.00,0.00,0.00,0,945.60,NULL,0.00,0.00,0.00,0.00,'2014-07-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(137,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,77.75,0.00,0,0,NULL,109.56,0.00,0,NULL,0.00,0.00,0,0,0,0.00,8,0,0.00,836.03,0,0,0,0.00,4465.37,0.00,0.00,0.00,0,945.60,NULL,0.00,0.00,0.00,0.00,'2014-08-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(138,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,65.49,0.00,0,0,NULL,92.28,0.00,0,NULL,0.00,0.00,0,0,0,0.00,9,0,0.00,853.31,0,0,0,0.00,3612.06,0.00,0.00,0.00,0,945.60,NULL,0.00,0.00,0.00,0.00,'2014-09-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(139,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,50.57,0.00,0,0,NULL,72.24,0.00,0,NULL,0.00,0.00,0,0,0,0.00,10,0,0.00,873.35,0,0,0,0.00,2738.71,0.00,0.00,0.00,0,945.60,NULL,0.00,0.00,0.00,0.00,'2014-10-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(140,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,40.17,0.00,0,0,NULL,56.60,0.00,0,NULL,0.00,0.00,0,0,0,0.00,11,0,0.00,889.00,0,0,0,0.00,1849.71,0.00,0.00,0.00,0,945.60,NULL,0.00,0.00,0.00,0.00,'2014-11-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(141,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,25.90,0.00,0,0,NULL,36.99,0.00,0,NULL,0.00,0.00,0,0,0,0.00,12,0,0.00,908.60,0,0,0,0.00,941.11,0.00,0.00,0.00,0,945.60,NULL,0.00,0.00,0.00,0.00,'2014-12-10 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(142,'2014-03-17 18:30:28',22,2,NULL,1,NULL,NULL,1,NULL,14,0.00,9.41,0.00,0,0,NULL,4.39,0.00,0,NULL,0.00,0.00,0,0,0,0.00,13,0,0.00,941.11,0,0,0,0.00,0.00,0.00,0.00,0.00,0,945.50,NULL,0.00,0.00,0.00,0.00,'2014-12-17 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(143,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,16,0.00,0.00,0.00,0,0,NULL,6000.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,1,0,0.00,0.00,0,0,0,0.00,300000.00,0.00,0.00,0.00,0,6000.00,NULL,0.00,0.00,0.00,0.00,'2014-05-02 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(144,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,16,0.00,6000.00,0.00,0,0,NULL,6200.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,2,0,0.00,0.00,0,0,0,0.00,300000.00,0.00,0.00,0.00,0,6200.00,NULL,0.00,0.00,0.00,0.00,'2014-06-02 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(145,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,16,0.00,5800.00,0.00,0,0,NULL,6000.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,3,0,0.00,0.00,0,0,0,0.00,300000.00,0.00,0.00,0.00,0,6000.00,NULL,0.00,0.00,0.00,0.00,'2014-07-02 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(146,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,16,0.00,6000.00,0.00,0,0,NULL,6200.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,4,0,0.00,0.00,0,0,0,0.00,300000.00,0.00,0.00,0.00,0,6200.00,NULL,0.00,0.00,0.00,0.00,'2014-08-02 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(147,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,16,0.00,6000.00,0.00,0,0,NULL,6200.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,5,0,0.00,0.00,0,0,0,0.00,300000.00,0.00,0.00,0.00,0,6200.00,NULL,0.00,0.00,0.00,0.00,'2014-09-02 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(148,'2014-04-02 16:26:40',22,2,NULL,1,NULL,NULL,1,NULL,16,0.00,5800.00,0.00,0,0,NULL,6000.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,300000.00,0,0,0,0.00,0.00,0.00,0.00,0.00,0,306000.00,NULL,0.00,0.00,0.00,0.00,'2014-10-02 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(149,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,17,0.00,0.00,0.00,0,0,NULL,10000.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,1,0,0.00,0.00,0,0,0,0.00,500000.00,0.00,0.00,0.00,0,10000.00,NULL,0.00,0.00,0.00,0.00,'2014-05-02 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(150,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,17,0.00,10000.00,0.00,0,0,NULL,10333.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,2,0,0.00,0.00,0,0,0,0.00,500000.00,0.00,0.00,0.00,0,10333.33,NULL,0.00,0.00,0.00,0.00,'2014-06-02 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(151,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,17,0.00,9666.67,0.00,0,0,NULL,10000.00,0.00,0,NULL,0.00,0.00,0,0,0,0.00,3,0,0.00,0.00,0,0,0,0.00,500000.00,0.00,0.00,0.00,0,10000.00,NULL,0.00,0.00,0.00,0.00,'2014-07-02 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(152,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,17,0.00,10000.00,0.00,0,0,NULL,10333.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,4,0,0.00,0.00,0,0,0,0.00,500000.00,0.00,0.00,0.00,0,10333.33,NULL,0.00,0.00,0.00,0.00,'2014-08-02 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(153,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,17,0.00,10000.00,0.00,0,0,NULL,10333.33,0.00,0,NULL,0.00,0.00,0,0,0,0.00,5,0,0.00,0.00,0,0,0,0.00,500000.00,0.00,0.00,0.00,0,10333.33,NULL,0.00,0.00,0.00,0.00,'2014-09-02 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00),(154,'2014-04-02 16:50:50',22,2,NULL,1,NULL,NULL,1,NULL,17,0.00,9666.67,0.00,0,0,NULL,9666.67,0.00,0,NULL,0.00,0.00,0,0,0,0.00,6,0,0.00,500000.00,0,0,0,0.00,0.00,0.00,0.00,0.00,0,509666.67,NULL,0.00,0.00,0.00,0.00,'2014-10-01 00:00:00',0.00,0.00,0.00,0.00,0.00,0.00);
/*!40000 ALTER TABLE `fc_plan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_planbackup`
--

DROP TABLE IF EXISTS `fc_planbackup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_planbackup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `contractId` bigint(20) NOT NULL,
  `delAmount` decimal(19,2) NOT NULL,
  `exempt` int(11) NOT NULL,
  `extNo` int(11) DEFAULT NULL,
  `extcontractId` bigint(20) DEFAULT NULL,
  `interest` decimal(19,2) NOT NULL,
  `lastDate` datetime DEFAULT NULL,
  `mgrAmount` decimal(19,2) NOT NULL,
  `mgrdays` int(11) NOT NULL,
  `mgrstatus` int(11) NOT NULL,
  `overalLogId` bigint(20) NOT NULL,
  `pdays` int(11) NOT NULL,
  `penAmount` decimal(19,2) NOT NULL,
  `phases` int(11) NOT NULL,
  `pistatus` int(11) NOT NULL,
  `principal` decimal(19,2) NOT NULL,
  `provision` int(11) NOT NULL,
  `ratedays` int(11) NOT NULL,
  `reprincipal` decimal(19,2) NOT NULL,
  `status` int(11) NOT NULL,
  `totalAmount` decimal(19,2) NOT NULL,
  `trdelAmount` decimal(19,2) NOT NULL,
  `trinterAmount` decimal(19,2) NOT NULL,
  `trmgrAmount` decimal(19,2) NOT NULL,
  `trpenAmount` decimal(19,2) NOT NULL,
  `xpayDate` datetime NOT NULL,
  `ydelAmount` decimal(19,2) NOT NULL,
  `yinterest` decimal(19,2) NOT NULL,
  `ymgrAmount` decimal(19,2) NOT NULL,
  `ypenAmount` decimal(19,2) NOT NULL,
  `yprincipal` decimal(19,2) NOT NULL,
  `ytotalAmount` decimal(19,2) NOT NULL,
  `backupType` int(11) NOT NULL,
  `einterest` decimal(19,2) NOT NULL,
  `planId` bigint(20) NOT NULL,
  `emgrAmount` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_planbackup`
--

LOCK TABLES `fc_planbackup` WRITE;
/*!40000 ALTER TABLE `fc_planbackup` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_planbackup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_plecontract`
--

DROP TABLE IF EXISTS `fc_plecontract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_plecontract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `appAmount` decimal(19,2) DEFAULT NULL,
  `assMan` varchar(50) DEFAULT NULL,
  `borrCode` varchar(30) DEFAULT NULL,
  `clause` varchar(255) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `conAmount` varchar(255) NOT NULL,
  `custType` bigint(20) NOT NULL,
  `customerId` bigint(20) NOT NULL,
  `endDate` datetime DEFAULT NULL,
  `formId` bigint(20) DEFAULT NULL,
  `loanConId` bigint(20) DEFAULT NULL,
  `ownerNums` varchar(30) DEFAULT NULL,
  `pleIds` varchar(150) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `sdate` datetime DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  `unint` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_plecontract`
--

LOCK TABLES `fc_plecontract` WRITE;
/*!40000 ALTER TABLE `fc_plecontract` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_plecontract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_pledge`
--

DROP TABLE IF EXISTS `fc_pledge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_pledge` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `address` varchar(150) DEFAULT NULL,
  `carrMan` varchar(30) DEFAULT NULL,
  `carrTime` datetime DEFAULT NULL,
  `charMan` varchar(30) DEFAULT NULL,
  `charTime` datetime DEFAULT NULL,
  `conTel` varchar(20) DEFAULT NULL,
  `formId` bigint(20) NOT NULL,
  `gtype` bigint(20) NOT NULL,
  `mman` varchar(30) DEFAULT NULL,
  `morTime` datetime DEFAULT NULL,
  `mpVal` decimal(19,2) NOT NULL,
  `name` varchar(60) NOT NULL,
  `oldVal` decimal(19,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `unint` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_pledge`
--

LOCK TABLES `fc_pledge` WRITE;
/*!40000 ALTER TABLE `fc_pledge` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_pledge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_prepayment`
--

DROP TABLE IF EXISTS `fc_prepayment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_prepayment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accountId` bigint(20) DEFAULT NULL,
  `adDate` datetime NOT NULL,
  `adamount` decimal(19,2) NOT NULL,
  `appDate` datetime NOT NULL,
  `appMan` varchar(30) NOT NULL,
  `breed` bigint(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  `exempt` int(11) DEFAULT NULL,
  `frate` double DEFAULT NULL,
  `freeamount` decimal(19,2) DEFAULT NULL,
  `imamount` decimal(19,2) NOT NULL,
  `isretreat` int(11) NOT NULL,
  `managerId` bigint(20) NOT NULL,
  `mgrDate` datetime NOT NULL,
  `notifyContent` varchar(255) DEFAULT NULL,
  `notifyDate` datetime DEFAULT NULL,
  `notifyMan` bigint(20) DEFAULT NULL,
  `notifyType` varchar(10) DEFAULT NULL,
  `payplanId` bigint(20) NOT NULL,
  `predDate` datetime DEFAULT NULL,
  `procId` varchar(20) DEFAULT NULL,
  `ptype` int(11) NOT NULL,
  `reason` longtext NOT NULL,
  `reviewDate` datetime DEFAULT NULL,
  `reviewer` bigint(20) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `tel` varchar(30) DEFAULT NULL,
  `totalAmount` decimal(19,2) NOT NULL,
  `trdelAmount` decimal(19,2) DEFAULT NULL,
  `treatment` int(11) DEFAULT NULL,
  `trfreeAmount` decimal(19,2) DEFAULT NULL,
  `trinterAmount` decimal(19,2) DEFAULT NULL,
  `trmgrAmount` decimal(19,2) DEFAULT NULL,
  `trpenAmount` decimal(19,2) DEFAULT NULL,
  `xstatus` int(11) DEFAULT NULL,
  `yfreeamount` decimal(19,2) DEFAULT NULL,
  `ytotalAmount` decimal(19,2) DEFAULT NULL,
  `changPayType` int(11) DEFAULT NULL,
  `epayType` varchar(20) DEFAULT NULL,
  `estartDate` datetime DEFAULT NULL,
  `funint` int(11) DEFAULT NULL,
  `isfpamt` int(11) DEFAULT NULL,
  `phAmount` decimal(19,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_prepayment`
--

LOCK TABLES `fc_prepayment` WRITE;
/*!40000 ALTER TABLE `fc_prepayment` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_prepayment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_prepaymentrecords`
--

DROP TABLE IF EXISTS `fc_prepaymentrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_prepaymentrecords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `accountId` bigint(20) DEFAULT NULL,
  `bussTag` int(11) NOT NULL,
  `cat` decimal(19,2) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  `dat` decimal(19,2) NOT NULL,
  `fat` decimal(19,2) NOT NULL,
  `fcnumber` varchar(30) DEFAULT NULL,
  `invoceId` bigint(20) NOT NULL,
  `mat` decimal(19,2) NOT NULL,
  `pat` decimal(19,2) NOT NULL,
  `rat` decimal(19,2) NOT NULL,
  `rectDate` datetime NOT NULL,
  `tat` decimal(19,2) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_prepaymentrecords`
--

LOCK TABLES `fc_prepaymentrecords` WRITE;
/*!40000 ALTER TABLE `fc_prepaymentrecords` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_prepaymentrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_printtemp`
--

DROP TABLE IF EXISTS `fc_printtemp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_printtemp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `custType` int(11) DEFAULT NULL,
  `icon` varchar(150) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `tempPath` varchar(200) DEFAULT NULL,
  `tempType` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_printtemp`
--

LOCK TABLES `fc_printtemp` WRITE;
/*!40000 ALTER TABLE `fc_printtemp` DISABLE KEYS */;
INSERT INTO `fc_printtemp` VALUES (1,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'C2013121603240200',0,NULL,'借款合同模板','WEB-INF/tontract/temp_dir/1386929911325.txt','1'),(2,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:05',-1,NULL,'C2013121604171202',0,NULL,'担保合同','WEB-INF/tontract/temp_dir/1387079355587.txt','1'),(3,'2013-12-15 16:00:00',2,8,NULL,1,2,'2014-01-22 16:00:00',-1,NULL,'C2013121605531204',0,NULL,'放款单 ','WEB-INF/tontract/temp_dir/1386754736772.txt','2');
/*!40000 ALTER TABLE `fc_printtemp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_projectchange`
--

DROP TABLE IF EXISTS `fc_projectchange`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_projectchange` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `appDate` datetime DEFAULT NULL,
  `bccontent` varchar(255) DEFAULT NULL,
  `breed` bigint(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `contractId` bigint(20) NOT NULL,
  `ctype` int(11) NOT NULL,
  `epayType` varchar(20) DEFAULT NULL,
  `estartDate` datetime DEFAULT NULL,
  `hmdate` datetime DEFAULT NULL,
  `hmgrId` bigint(20) DEFAULT NULL,
  `jycoe` varchar(20) DEFAULT NULL,
  `mmdate` datetime DEFAULT NULL,
  `mmgrId` bigint(20) DEFAULT NULL,
  `phAmount` decimal(19,2) DEFAULT NULL,
  `procId` varchar(20) DEFAULT NULL,
  `reason` longtext,
  `reprincipal` decimal(19,2) NOT NULL,
  `status` int(11) NOT NULL,
  `xstatus` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_projectchange`
--

LOCK TABLES `fc_projectchange` WRITE;
/*!40000 ALTER TABLE `fc_projectchange` DISABLE KEYS */;
/*!40000 ALTER TABLE `fc_projectchange` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_ptformula`
--

DROP TABLE IF EXISTS `fc_ptformula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_ptformula` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `amountFormula` varchar(255) DEFAULT NULL,
  `amountParams` varchar(150) DEFAULT NULL,
  `amoutDispFormula` varchar(255) DEFAULT NULL,
  `ftype` int(11) NOT NULL,
  `mgrDispFormula` varchar(255) DEFAULT NULL,
  `mgrFormula` varchar(255) DEFAULT NULL,
  `mgrParams` varchar(150) DEFAULT NULL,
  `paytypeId` bigint(20) NOT NULL,
  `raDispFormula` varchar(255) DEFAULT NULL,
  `raFormula` varchar(255) DEFAULT NULL,
  `raParams` varchar(150) DEFAULT NULL,
  `rateDispFormula` varchar(255) DEFAULT NULL,
  `rateFormula` varchar(255) DEFAULT NULL,
  `rateParams` varchar(150) DEFAULT NULL,
  `anum` int(11) DEFAULT NULL,
  `mnum` int(11) DEFAULT NULL,
  `rnum` int(11) DEFAULT NULL,
  `ranum` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_ptformula`
--

LOCK TABLES `fc_ptformula` WRITE;
/*!40000 ALTER TABLE `fc_ptformula` DISABLE KEYS */;
INSERT INTO `fc_ptformula` VALUES (1,'2014-02-27 00:00:00',2,8,NULL,1,2,'2014-03-01 00:00:00',-1,'RR','appAmount *0','appAmount:BG','第一期本金为0(不收本金)',2,'贷款本金 * 管理费率 * 12 / 360天 * 天数','appAmount *day_mrate * totalDays','appAmount:BG,day_mrate:O,totalDays:I',6,'第一期本金+第一期利息','principal+interest+mgrAmount','principal:BG,interest:BG,mgrAmount:BG','贷款本金 * 月利率 * 12 / 360天 * 天数','appAmount * day_rate * totalDays','appAmount:BG,day_rate:O,totalDays:I',3,2,1,4),(3,'2014-02-27 00:00:00',2,8,NULL,1,2,'2014-03-01 00:00:00',-1,NULL,'reprincipal*1','reprincipal:BG','最后一期本金=上一期本金余额',4,'剩余本金 * 管理费率 * 12 / 360天 * 天数','reprincipal * day_mrate* totalDays','reprincipal:BG,day_mrate:O,totalDays:I',6,'当期本金+当期利息+当期管理费','principal+interest+mgrAmount','principal:BG,interest:BG,mgrAmount:BG','剩余本金 * 月利率 * 12 / 360天 * 天数','reprincipal * day_rate* totalDays','reprincipal:BG,day_rate:O,totalDays:I',3,2,1,4),(4,'2014-02-27 00:00:00',2,8,NULL,1,2,'2014-03-01 00:00:00',-1,NULL,'totalAmount - interest - mgrAmount','totalAmount:BG,interest:BG,mgrAmount:BG','当期本息合计 - 当期利息 - 当期管理费',3,'贷款余额×管理费率×12÷360天×天数（30/31天）','reprincipal * day_mrate * totalDays','reprincipal:O,day_mrate:O,totalDays:I',6,'[贷款本金×月利率×（1+月利率）^还款月数-1]÷[（1+月利率）^(还款月数-1)－1]','appAmount*(rate+mrate) * $POW((rate+mrate+1),(totalPhases-1)) /  ($POW((rate+mrate+1),(totalPhases-1)) - 1)','appAmount:O,rate:O,mrate:O,totalPhases:I','贷款余额×月利率×12÷360天×天数（30/31天)','reprincipal * day_rate * totalDays','reprincipal:BG,day_rate:O,totalDays:I',4,2,1,3),(5,'2014-03-04 00:00:00',2,8,NULL,1,2,'2014-03-05 00:00:00',-1,NULL,'phAmount','phAmount:BG','分期还本额',2,'贷款本金 * 管理费率 * 12 / 360天 * 天数','appAmount * day_mrate * totalDays','appAmount:BG,day_mrate:D,totalDays:I',2,'本金+利息+管理费','principal+interest+mgrAmount','principal:BG,interest:BG,mgrAmount:BG','贷款本金 * 月利率 * 12 / 360天 * 天数','appAmount * day_rate * totalDays','appAmount:BG,day_rate:D,totalDays:I',3,2,1,4),(6,'2014-03-04 00:00:00',2,8,NULL,1,2,'2014-03-05 00:00:00',-1,NULL,'phAmount','phAmount:BG','分期还本金额',3,'剩余本金×管理费率×12÷360天×天数（30/31天）','reprincipal * day_mrate * totalDays','reprincipal:BG,day_mrate:D,totalDays:I',2,'当期本金+当期利息+当期管理费','principal+interest+mgrAmount','principal:BG,interest:BG,mgrAmount:BG','剩余本金×月利率×12÷360天×天数（30/31天）','reprincipal*day_rate * totalDays','reprincipal:BG,day_rate:D,totalDays:I',3,2,1,4),(7,'2014-03-04 00:00:00',2,8,NULL,1,2,'2014-03-05 00:00:00',-1,NULL,'reprincipal','reprincipal:BG','剩余本金',4,'剩余本金×管理费率×12÷360天×天数（30/31天）','reprincipal * day_mrate * totalDays','reprincipal:BG,day_mrate:D,totalDays:I',2,'当期本金+当期利息+当期管理费','principal+interest+mgrAmount','principal:BG,interest:BG,mgrAmount:BG','剩余本金×月利率×12÷360天×天数（30/31天）','reprincipal * day_rate * totalDays','reprincipal:BG,day_rate:D,totalDays:I',3,2,1,4),(8,'2014-03-05 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,'appAmount*0','appAmount:BG','不收本金',2,'贷款本金 * 管理费率 * 12 / 360天 * 天数','appAmount * day_mrate * totalDays','appAmount:BG,day_mrate:D,totalDays:I',1,'本金+利息+管理费','principal+interest+mgrAmount','principal:BG,interest:BG,mgrAmount:BG','贷款本金 * 月利率 * 12 / 360天 * 天数','appAmount * day_rate * totalDays','appAmount:BG,day_rate:D,totalDays:I',3,2,1,4),(9,'2014-03-05 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,'appAmount*0','appAmount:BG','不收本金',3,'贷款本金×管理费率×12÷360天×天数（30/31天）','appAmount * day_mrate * totalDays','appAmount:BG,day_mrate:D,totalDays:I',1,'当期本金+当期利息+当期管理费','principal+interest+mgrAmount','principal:BG,interest:BG,mgrAmount:BG','贷款本金×月利率×12÷360天×天数（30/31天）','appAmount * day_rate * totalDays','appAmount:BG,day_rate:D,totalDays:I',3,2,1,4),(10,'2014-03-05 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,'appAmount','appAmount:BG','贷款本金',4,'贷款本金×管理费率×12÷360天×天数（30/31天）','appAmount * day_mrate * totalDays','appAmount:BG,day_mrate:D,totalDays:I',1,'当期本金+当期利息+当期管理费','principal+interest+mgrAmount','principal:BG,interest:BG,mgrAmount:BG','贷款本金×月利率×12÷360天×天数（30/31天）','appAmount * day_rate * totalDays','appAmount:BG,day_rate:D,totalDays:I',3,2,1,4),(11,'2014-03-05 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,'appAmount/totalPhases','appAmount:BG,totalPhases:I',' 贷款总金额 / 期数',1,'上期贷款余额 * (管理费率*12/360)','reprincipal* day_mrate * totalDays','reprincipal:BG,day_mrate:D,totalDays:I',5,'本金+利息+管理费','principal+interest+mgrAmount','principal:BG,interest:BG,mgrAmount:BG','上期贷款余额 * (月利率*12/360)','reprincipal*day_rate*totalDays','reprincipal:BG,day_rate:D,totalDays:I',3,2,1,4),(12,'2014-03-05 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,'appAmount','appAmount:BG','贷款本金',1,'贷款本金 * 管理费率 * 12 / 360天 * 总天数','appAmount * (day_mrate* totalDays)','appAmount:BG,day_mrate:D,totalDays:I',7,'贷款本金+利息+管理费','principal+interest+mgrAmount','principal:BG,interest:BG,mgrAmount:BG','本金 X (月利率*12/360 X 总天数)','appAmount * (day_rate* totalDays)','appAmount:BG,day_rate:D,totalDays:I',3,2,1,4),(13,'2014-03-05 00:00:00',2,8,NULL,1,2,'2014-03-05 00:00:00',-1,NULL,'flexAmount','flexAmount:BG','指定期数的本金',1,'贷款余额 * (管理费率*12/360)','reprincipal* day_mrate * totalDays','reprincipal:BG,day_mrate:D,totalDays:I',8,'本金+利息+管理费','principal+interest+mgrAmount','principal:BG,interest:BG,mgrAmount:BG','贷款余额 * (月利率*12/360)','reprincipal*day_rate*totalDays','reprincipal:BG,day_rate:D,totalDays:I',3,2,1,4);
/*!40000 ALTER TABLE `fc_ptformula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_rate`
--

DROP TABLE IF EXISTS `fc_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_rate` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `bdate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `code` varchar(20) DEFAULT NULL,
  `formulaId` double DEFAULT NULL,
  `isFormula` int(11) DEFAULT NULL,
  `limits` int(11) DEFAULT NULL,
  `types` int(11) DEFAULT NULL,
  `val` double DEFAULT NULL,
  `unint` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_rate`
--

LOCK TABLES `fc_rate` WRITE;
/*!40000 ALTER TABLE `fc_rate` DISABLE KEYS */;
INSERT INTO `fc_rate` VALUES (6,'2014-03-09 08:23:06',45,8,NULL,1,2,'2014-03-08 16:00:00',2,'2012-03-13 当天及以后的贷款月利率为: 1.1% 望各位知悉。','2013-03-12 16:00:00','R2013031303500100',NULL,0,1,1,2,NULL),(7,'2013-03-12 16:00:00',45,8,-1,-1,NULL,'2014-02-21 07:31:05',2,NULL,'2013-03-12 16:00:00','R2013031305134012',NULL,0,1,1,1.2,NULL),(8,'2014-03-09 08:23:13',45,8,NULL,1,2,'2014-03-08 16:00:00',2,NULL,'2013-03-12 16:00:00','R2013031305230314',NULL,0,3,1,24,NULL),(9,'2014-03-09 08:24:16',45,8,NULL,1,2,'2014-03-08 16:00:00',2,NULL,'2013-03-12 16:00:00','R2013031305372616',NULL,0,2,1,0.04,NULL),(10,'2013-03-12 16:00:00',45,8,NULL,1,2,'2014-01-13 16:00:00',2,NULL,'2013-03-12 16:00:00','R2013031305434018',NULL,0,1,2,0,NULL),(11,'2013-03-12 16:00:00',45,8,NULL,1,2,'2014-01-13 16:00:00',2,NULL,'2013-03-12 16:00:00','R2013031305440920',NULL,0,2,2,0,NULL),(12,'2013-03-12 16:00:00',45,8,NULL,1,2,'2014-01-13 16:00:00',2,NULL,'2013-03-12 16:00:00','R2013031305455822',NULL,0,3,2,0,NULL),(13,'2013-03-12 16:00:00',45,8,NULL,1,2,'2014-01-13 16:00:00',2,NULL,'2013-03-12 16:00:00','R2013031305480324',NULL,0,1,3,50,NULL),(14,'2013-03-12 16:00:00',45,8,NULL,1,2,'2014-01-13 16:00:00',2,NULL,'2013-03-12 16:00:00','R2013031305495426',NULL,0,2,4,0,NULL),(15,'2013-03-12 16:00:00',45,8,NULL,1,2,'2014-01-15 16:00:00',2,NULL,'2013-03-12 16:00:00','R2013031305501628',NULL,0,4,5,0,NULL),(16,'2013-03-12 16:00:00',45,8,NULL,1,2,'2014-01-23 16:00:00',2,NULL,'2013-03-12 16:00:00','R2013031305504430',NULL,0,4,6,0,NULL);
/*!40000 ALTER TABLE `fc_rate` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_risklevel`
--

DROP TABLE IF EXISTS `fc_risklevel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_risklevel` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` bigint(20) DEFAULT NULL,
  `deptId` bigint(20) DEFAULT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` bigint(20) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `maxdays` int(11) DEFAULT NULL,
  `mindays` int(11) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `color` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_risklevel`
--

LOCK TABLES `fc_risklevel` WRITE;
/*!40000 ALTER TABLE `fc_risklevel` DISABLE KEYS */;
INSERT INTO `fc_risklevel` VALUES (11,'2013-01-27 16:02:00',45,8,NULL,1,2,'2014-01-13 16:00:00',2,'关注:\n 逾期天数范围 大于 0 天 并且 小于等于 91 天','R2013022802574200',90,0,'关注','#00FF16'),(12,'2013-01-27 16:02:00',45,8,NULL,1,2,'2014-01-13 16:00:00',2,'次级:\n 逾期天数范围 大于 91 天 并且 小于等于 120 天','R2013022803103122',120,90,'次级','#FFF700'),(13,'2013-01-27 16:02:00',45,8,NULL,1,2,'2014-01-13 16:00:00',2,'可疑:\n 逾期天数范围 大于 120 天 并且 小于等于 180 天','R2013022803143424',180,120,'可疑','#FF5200'),(14,'2013-01-27 16:02:00',45,8,NULL,1,2,'2014-01-13 16:00:00',2,'损失:\n 逾期天数范围 大于 180 天<br/>(如果后面一个天数为 -1 表示不参与比较)','R2013022803161426',-1,180,'损失','#FF0013');
/*!40000 ALTER TABLE `fc_risklevel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_taboutside`
--

DROP TABLE IF EXISTS `fc_taboutside`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_taboutside` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` datetime NOT NULL,
  `creator` bigint(20) NOT NULL,
  `deptId` bigint(20) NOT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` tinyint(4) NOT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` bigint(20) NOT NULL,
  `remark` longtext,
  `amounts` decimal(19,2) NOT NULL,
  `changeDate` datetime DEFAULT NULL,
  `contractId` bigint(20) NOT NULL,
  `damounts` decimal(19,2) NOT NULL,
  `direction` int(11) NOT NULL,
  `fcnumber` varchar(30) DEFAULT NULL,
  `flevel` bigint(20) NOT NULL,
  `iamounts` decimal(19,2) NOT NULL,
  `inouttype` int(11) NOT NULL,
  `mamounts` decimal(19,2) NOT NULL,
  `monthPharses` int(11) DEFAULT NULL,
  `pamounts` decimal(19,2) NOT NULL,
  `planId` bigint(20) DEFAULT NULL,
  `totalPharses` int(11) DEFAULT NULL,
  `xdate` datetime DEFAULT NULL,
  `xman` varchar(20) DEFAULT NULL,
  `xstatus` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_taboutside`
--

LOCK TABLES `fc_taboutside` WRITE;
/*!40000 ALTER TABLE `fc_taboutside` DISABLE KEYS */;
INSERT INTO `fc_taboutside` VALUES (1,'2014-03-17 11:18:33',13,4,NULL,1,-1,'2014-04-26 03:00:01',1,NULL,129389.72,NULL,11,0.00,0,NULL,13,17182.77,0,0.00,9,15935.03,99,5,NULL,NULL,2),(3,'2014-03-17 18:31:48',13,4,NULL,1,-1,'2014-04-26 03:00:01',1,NULL,738.93,NULL,14,0.00,0,NULL,11,243.62,0,0.00,2,73.61,131,1,NULL,NULL,2),(6,'2014-03-18 08:01:45',2,8,NULL,1,-1,'2014-04-26 03:00:01',-1,NULL,23255.66,NULL,12,0.00,0,NULL,12,6281.32,0,0.00,3,2247.72,106,3,NULL,NULL,2);
/*!40000 ALTER TABLE `fc_taboutside` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fc_tdscfg`
--

DROP TABLE IF EXISTS `fc_tdscfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fc_tdscfg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `dataCode` varchar(255) DEFAULT NULL,
  `dispType` int(11) DEFAULT NULL,
  `dstag` varchar(50) DEFAULT NULL,
  `htmlCode` varchar(255) DEFAULT NULL,
  `loadType` int(11) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `relyCmns` varchar(150) DEFAULT NULL,
  `relydsId` double DEFAULT NULL,
  `tempId` double DEFAULT NULL,
  `urlparams` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fc_tdscfg`
--

LOCK TABLES `fc_tdscfg` WRITE;
/*!40000 ALTER TABLE `fc_tdscfg` DISABLE KEYS */;
INSERT INTO `fc_tdscfg` VALUES (2,'2013-12-15 16:00:00',2,8,NULL,1,10,'2014-01-10 16:00:00',-1,NULL,'select A.code,A.assMan,D.code Dcode,D.doDate,A.startDate,A.endDate,A.guarantorIds \nfrom fc_GuaContract as A \ninner join fc_LoanContract as D on A.loanConId=D.id\nwhere A.id={contractId} and A.isenabled = 1',1,'guarantee',NULL,1,12,NULL,NULL,2,'contractId');
/*!40000 ALTER TABLE `fc_tdscfg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_acctgroup`
--

DROP TABLE IF EXISTS `fs_acctgroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_acctgroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `finsysId` double DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `refId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_acctgroup`
--

LOCK TABLES `fs_acctgroup` WRITE;
/*!40000 ALTER TABLE `fs_acctgroup` DISABLE KEYS */;
INSERT INTO `fs_acctgroup` VALUES (1,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'0',2,'资产',1),(2,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'101',2,'流动资产',1),(3,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'102',2,'非流动资产',1),(4,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'0',2,'负债',2),(5,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'201',2,'流动负债',2),(6,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'202',2,'非流动负债',2),(7,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'0',2,'权益',3),(8,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'300',2,'所有者权益',3),(9,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'0',2,'成本',4),(10,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'400',2,'成本',4),(11,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'0',2,'损益',5),(12,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'501',2,'营业收入',5),(13,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'502',2,'营业成本及税金',5),(14,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'503',2,'期间费用',5),(15,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'504',2,'其他收益',5),(16,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'505',2,'其他损失',5),(17,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'506',2,'以前年度损益调整',5),(18,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'507',2,'所得税',5),(19,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'0',2,'表外',6),(20,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'601',2,'表外科目',6),(21,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'0',2,'共同',7),(22,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'700',2,'共同',7);
/*!40000 ALTER TABLE `fs_acctgroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_amountlog`
--

DROP TABLE IF EXISTS `fs_amountlog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_amountlog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `accountId` double DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `bamount` double DEFAULT NULL,
  `bussTag` int(11) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `custCount` int(11) DEFAULT NULL,
  `famount` double DEFAULT NULL,
  `fcnumber` varchar(50) DEFAULT NULL,
  `invoceIds` varchar(255) DEFAULT NULL,
  `mamount` double DEFAULT NULL,
  `oamount` double DEFAULT NULL,
  `opdate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `pamount` double DEFAULT NULL,
  `ramount` double DEFAULT NULL,
  `refId` varchar(255) DEFAULT NULL,
  `reffinAccountId` varchar(255) DEFAULT NULL,
  `sumamount` double DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_amountlog`
--

LOCK TABLES `fs_amountlog` WRITE;
/*!40000 ALTER TABLE `fs_amountlog` DISABLE KEYS */;
INSERT INTO `fs_amountlog` VALUES (46,'2014-01-24 03:04:49',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,0,0,12,6,1,0,NULL,'1',0,0,'2012-12-31 16:00:00',0,900000,NULL,NULL,900000,3),(47,'2014-01-24 03:05:18',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,5000000,0,0,0,1,0,NULL,'5',0,0,'2012-12-31 16:00:00',0,0,NULL,NULL,5000000,3),(48,'2014-01-24 03:13:29',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,0,0,12,6,1,80000,NULL,'2',0,0,'2013-01-09 16:00:00',0,0,NULL,NULL,80000,3),(49,'2014-01-24 03:36:56',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,0,0,12,6,1,0,NULL,'3',200000,0,'2012-12-31 16:00:00',0,360000,NULL,NULL,560000,3),(50,'2014-01-24 03:37:15',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,2000000,0,0,0,1,0,NULL,'1',0,0,'2012-12-31 16:00:00',0,0,NULL,NULL,2000000,3),(51,'2014-01-27 10:15:53',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,0,0,12,6,1,100000,NULL,'4',0,0,'2012-12-31 16:00:00',0,0,NULL,NULL,100000,3),(52,'2014-01-27 10:16:21',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,5000000,0,0,0,1,0,NULL,'4',0,0,'2012-12-31 16:00:00',0,0,NULL,NULL,5000000,3),(53,'2014-01-27 10:27:37',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,1000000,0,12,6,1,0,NULL,'5',0,0,'2013-01-01 16:00:00',0,0,NULL,NULL,1000000,3),(54,'2014-01-27 10:29:57',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,1000000,0,12,6,1,0,NULL,'6',0,0,'2013-01-01 16:00:00',0,1000000,NULL,NULL,2000000,3),(55,'2014-01-27 10:30:21',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,3000000,0,12,6,1,0,NULL,'7',0,0,'2013-01-02 16:00:00',0,0,NULL,NULL,3000000,3),(56,'2014-01-27 10:33:42',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,0,0,12,6,1,0,NULL,'8',0,0,'2013-01-03 16:00:00',0,60000,NULL,NULL,60000,3),(57,'2014-01-27 11:01:03',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,1000000,0,12,6,1,0,NULL,'9',0,0,'2013-01-01 16:00:00',0,0,NULL,NULL,1000000,3),(58,'2014-01-27 11:02:25',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,1000000,0,12,6,1,0,NULL,'10',0,0,'2013-01-02 16:00:00',0,400000,NULL,NULL,1400000,3),(59,'2014-01-27 11:13:39',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,5000000,0,12,6,1,0,NULL,'13',0,0,'2013-01-10 16:00:00',0,30000,NULL,NULL,5930000,3),(60,'2014-01-27 12:28:08',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,0,0,12,6,1,120000,NULL,'14',0,0,'2012-12-31 16:00:00',0,180000,NULL,NULL,300000,3),(61,'2014-01-27 12:28:34',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,1000000,0,0,0,1,0,NULL,'3',0,0,'2012-12-31 16:00:00',0,0,NULL,NULL,1000000,3),(62,'2014-01-27 12:29:30',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,5000000,0,0,0,1,0,NULL,'2',0,0,'2012-12-31 16:00:00',0,0,NULL,NULL,5000000,3),(63,'2014-01-27 12:29:59',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,0,0,12,6,1,80000,NULL,'15',0,0,'2012-12-31 16:00:00',0,900000,NULL,NULL,980000,3),(64,'2014-01-27 12:32:17',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,1000000,0,12,6,1,0,NULL,'16',0,0,'2013-01-04 16:00:00',0,6000,NULL,NULL,1006000,3),(65,'2014-01-27 12:36:03',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,5000000,0,12,6,1,0,NULL,'17',0,0,'2013-01-04 16:00:00',0,800000,NULL,NULL,5800000,3),(66,'2014-02-06 03:45:53',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,3000000,0,0,0,1,0,NULL,'7',0,0,'2014-02-05 16:00:00',0,0,NULL,NULL,3000000,3),(67,'2014-02-06 03:46:03',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,2000000,0,0,0,1,0,NULL,'6',0,0,'2014-02-05 16:00:00',0,0,NULL,NULL,2000000,3),(68,'2014-02-06 03:47:26',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,11,0,0,12,6,1,0,NULL,'19',0,0,'2014-02-05 16:00:00',0,36000,NULL,NULL,36000,3),(69,'2014-02-06 03:47:44',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,11,0,0,12,6,1,0,NULL,'20',20000,0,'2014-02-05 16:00:00',0,22000,NULL,NULL,42000,3),(70,'2014-02-06 09:51:35',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,6,1000000,0,12,6,1,0,NULL,'28',0,0,'2014-02-05 16:00:00',0,0,NULL,NULL,1000000,3),(71,'2014-02-06 09:52:33',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,6,500000,0,12,6,1,0,NULL,'29',0,0,'2014-02-05 16:00:00',0,5000,NULL,NULL,505000,3),(72,'2014-02-06 09:59:15',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,6,500000,0,12,6,1,0,NULL,'30',0,0,'2014-02-05 16:00:00',0,0,NULL,NULL,500000,3),(73,'2014-02-06 12:09:12',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,0,0,12,6,1,0,NULL,'32',20000,0,'2014-02-05 16:00:00',0,22000,NULL,NULL,42000,3),(74,'2014-02-06 12:23:34',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,0,0,12,6,1,0,NULL,'33',33000,0,'2014-02-05 16:00:00',0,39000,NULL,NULL,72000,3),(75,'2014-02-06 12:39:46',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,0,0,12,6,1,0,NULL,'34',20000,0,'2014-02-05 16:00:00',0,22000,NULL,NULL,42000,3),(76,'2014-02-06 12:40:09',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,0,0,12,6,1,0,NULL,'35',33000,0,'2014-02-05 16:00:00',0,39000,NULL,NULL,72000,3),(77,'2014-02-07 04:19:21',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,1000000,0,12,6,1,0,NULL,'36',0,0,'2014-02-06 16:00:00',0,0,NULL,NULL,1000000,3),(78,'2014-02-07 04:20:44',13,4,NULL,1,NULL,'2014-02-21 07:31:06',1,NULL,6,2000000,0,12,6,1,0,NULL,'37',0,0,'2014-02-06 16:00:00',0,0,NULL,NULL,2000000,3),(79,'2014-03-06 08:07:06',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,100000,0,0,0,1,0,NULL,'13',0,0,'2013-03-07 16:00:00',0,0,NULL,NULL,100000,3),(80,'2014-03-14 03:22:42',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,100000,0,0,0,1,0,NULL,'4',0,0,'2014-03-13 16:00:00',0,0,NULL,NULL,100000,3),(81,'2014-03-14 03:30:16',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,0,0,2,2,1,0,NULL,'1',0,0,'2014-03-13 16:00:00',0,1800,NULL,NULL,1800,3),(82,'2014-03-14 03:30:25',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,2,2,1,0,NULL,'3',0,0,'2014-03-13 16:00:00',0,2066.67,NULL,NULL,4066.67,3),(83,'2014-03-14 03:30:25',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,0,0,2,2,1,0,NULL,'4',0,0,'2014-03-13 16:00:00',0,2000,NULL,NULL,2000,3),(84,'2014-03-14 03:30:25',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,2,2,1,0,NULL,'2',0,0,'2014-03-13 16:00:00',0,1960,NULL,NULL,3960,3),(85,'2014-03-14 05:52:40',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,100000,0,0,0,1,0,NULL,'5',0,0,'2014-03-13 16:00:00',0,0,NULL,NULL,100000,3),(86,'2014-03-14 07:14:23',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,100000,0,0,0,1,0,NULL,'6',0,0,'2014-03-13 16:00:00',0,0,NULL,NULL,100000,3),(87,'2014-03-14 07:14:27',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,100000,0,0,0,1,0,NULL,'6',0,0,'2014-03-13 16:00:00',0,0,NULL,NULL,100000,3),(88,'2014-03-17 03:17:32',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,300000,0,0,0,1,0,NULL,'9',0,0,'2013-02-13 16:00:00',0,0,NULL,NULL,300000,3),(89,'2014-03-17 03:18:12',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,25176.55,0,2,2,1,0,NULL,'5',0,0,'2014-03-16 16:00:00',0,3191.33,NULL,NULL,28367.88,3),(90,'2014-03-17 03:18:12',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,23729.41,0,2,2,1,0,NULL,'8',0,0,'2014-03-16 16:00:00',0,4638.47,NULL,NULL,28367.88,3),(91,'2014-03-17 03:18:12',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,22811.24,0,2,2,1,0,NULL,'10',0,0,'2014-03-16 16:00:00',0,5556.64,NULL,NULL,28367.88,3),(92,'2014-03-17 03:18:12',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,0,0,2,2,1,0,NULL,'12',0,0,'2014-03-16 16:00:00',0,4800,NULL,NULL,4800,3),(93,'2014-03-17 03:18:12',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,23097.45,0,2,2,1,0,NULL,'9',0,0,'2014-03-16 16:00:00',0,5270.43,NULL,NULL,28367.88,3),(94,'2014-03-17 03:18:12',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,24562.55,0,2,2,1,0,NULL,'6',0,0,'2014-03-16 16:00:00',0,3805.33,NULL,NULL,28367.88,3),(95,'2014-03-17 03:18:12',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,24065.2,0,2,2,1,0,NULL,'7',0,0,'2014-03-16 16:00:00',0,4302.68,NULL,NULL,28367.88,3),(96,'2014-03-17 03:18:12',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,22167.88,0,2,2,1,0,NULL,'11',0,0,'2014-03-16 16:00:00',0,6200,NULL,NULL,28367.88,3),(97,'2014-03-17 04:13:10',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,5000,0,6,2,1,0,NULL,'13',0,0,'2014-03-16 16:00:00',0,0,NULL,NULL,5000,3),(98,'2014-03-17 04:36:18',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,100000,0,0,0,1,0,NULL,'10',0,0,'2013-11-05 16:00:00',0,0,NULL,NULL,100000,3),(99,'2014-03-17 04:37:46',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,8,0,0,2,2,1,0,NULL,'15',0,0,'2014-03-16 16:00:00',0,266.67,NULL,NULL,266.67,3),(100,'2014-03-17 04:37:46',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,8,7455.96,0,2,2,1,0,NULL,'14',0,0,'2014-03-16 16:00:00',0,2000,NULL,NULL,9455.96,3),(101,'2014-03-17 04:38:04',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,7543.38,0,2,2,1,0,NULL,'16',0,0,'2014-03-16 16:00:00',0,1912.58,NULL,NULL,9455.96,3),(102,'2014-03-17 10:31:00',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,10000,0,0,0,1,0,NULL,'12',0,0,'2013-12-17 16:00:00',0,0,NULL,NULL,10000,3),(103,'2014-03-17 10:31:36',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,0,0,2,2,1,0,NULL,'17',0,0,'2014-03-16 16:00:00',0,153.33,NULL,NULL,153.33,3),(104,'2014-03-17 10:32:50',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,2000,0,6,2,1,0,NULL,'18',0,0,'2014-03-16 16:00:00',0,0,NULL,NULL,2000,3),(105,'2014-03-17 11:38:12',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,0,0,2,2,1,0,NULL,'21',0,0,'2014-03-16 16:00:00',0,266.67,NULL,NULL,266.67,3),(106,'2014-03-17 11:38:12',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,7543.38,0,2,2,1,0,NULL,'19',0,0,'2014-03-16 16:00:00',0,1912.58,NULL,NULL,9455.96,3),(107,'2014-03-17 11:38:12',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,7455.96,0,2,2,1,0,NULL,'20',0,0,'2014-03-16 16:00:00',0,2000,NULL,NULL,9455.96,3),(108,'2014-03-17 11:42:51',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,6,2,1,0,NULL,'22',0,0,'2014-03-16 16:00:00',0,0,NULL,NULL,2000,3),(109,'2014-03-17 11:45:06',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,6,2,1,0,NULL,'23',0,0,'2014-03-17 16:00:00',0,0,NULL,NULL,2000,3),(110,'2014-03-17 11:53:41',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2707.84,0,6,2,1,0,NULL,'24',0,0,'2014-03-18 16:00:00',349.56,1942.6,NULL,NULL,5000,3),(111,'2014-03-17 12:38:09',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,7455.96,0,2,2,1,0,NULL,'26',0,0,'2014-03-16 16:00:00',0,2000,NULL,NULL,9455.96,3),(112,'2014-03-17 12:38:09',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,0,0,2,2,1,0,NULL,'27',0,0,'2014-03-16 16:00:00',0,266.67,NULL,NULL,266.67,3),(113,'2014-03-17 12:38:09',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,6,7543.38,0,2,2,1,0,NULL,'25',0,0,'2014-03-16 16:00:00',0,1912.58,NULL,NULL,9455.96,3),(114,'2014-03-17 12:52:05',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,6,2,1,0,NULL,'28',0,0,'2014-03-16 16:00:00',0,0,NULL,NULL,2000,3),(115,'2014-03-17 12:53:13',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,6,2,1,0,NULL,'29',0,0,'2014-03-17 16:00:00',0,0,NULL,NULL,2000,3),(116,'2014-03-17 12:54:12',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2707.84,0,6,2,1,0,NULL,'30',0,0,'2014-03-18 16:00:00',349.56,1942.6,NULL,NULL,5000,3),(117,'2014-03-17 13:37:55',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,6,2,1,0,NULL,'31',0,0,'2014-03-16 16:00:00',0,0,NULL,NULL,2000,3),(118,'2014-03-17 13:39:05',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,6,2,1,0,NULL,'32',0,0,'2014-03-17 16:00:00',0,0,NULL,NULL,2000,3),(119,'2014-03-18 02:10:48',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2707.84,0,6,2,1,0,NULL,'33',0,0,'2014-03-18 16:00:00',349.56,1942.6,NULL,NULL,5000,3),(120,'2014-03-18 03:27:05',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,6,2,1,0,NULL,'34',0,0,'2014-03-16 16:00:00',0,0,NULL,NULL,2000,3),(121,'2014-03-18 03:30:01',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,6,2,1,0,NULL,'35',0,0,'2014-03-17 16:00:00',0,0,NULL,NULL,2000,3),(122,'2014-03-18 03:31:24',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2707.84,0,6,2,1,0,NULL,'36',0,0,'2014-03-18 16:00:00',349.56,1942.6,NULL,NULL,5000,3),(123,'2014-03-18 00:01:12',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,7455.96,0,2,2,1,0,NULL,'39',0,0,'2014-03-17 16:00:00',0,2000,NULL,NULL,9455.96,3),(124,'2014-03-18 00:01:12',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,7543.38,0,2,2,1,0,NULL,'38',0,0,'2014-03-17 16:00:00',0,1912.58,NULL,NULL,9455.96,3),(125,'2014-03-18 00:01:12',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,8889.66,0,2,2,1,0,NULL,'37',0,0,'2014-03-17 16:00:00',0,566.3,NULL,NULL,9455.96,3),(126,'2014-03-18 00:01:12',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,0,0,2,2,1,0,NULL,'40',0,0,'2014-03-17 16:00:00',0,266.67,NULL,NULL,266.67,3),(127,'2014-03-18 00:02:18',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,6,2,1,0,NULL,'41',0,0,'2014-03-16 16:00:00',0,0,NULL,NULL,2000,3),(128,'2014-03-18 00:10:30',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2000,0,6,2,1,0,NULL,'42',0,0,'2014-03-17 16:00:00',0,0,NULL,NULL,2000,3),(129,'2014-03-18 00:23:41',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,2707.84,0,6,2,1,0,NULL,'43',0,0,'2014-03-18 16:00:00',349.56,1942.6,NULL,NULL,5000,3),(130,'2014-03-18 01:00:20',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,500,0,6,2,1,0,NULL,'44',0,0,'2014-03-20 16:00:00',0,0,NULL,NULL,500,3),(131,'2014-03-18 01:12:43',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,491.44,0,6,2,1,0,NULL,'45',0,0,'2014-03-21 16:00:00',2.47,1.65,NULL,NULL,495.56,3),(132,'2014-03-18 01:14:36',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,7455.96,0,2,2,1,0,NULL,'46',0,0,'2014-03-17 16:00:00',0,2000,NULL,NULL,9455.96,3),(133,'2014-03-18 01:14:36',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,0,0,2,2,1,0,NULL,'47',0,0,'2014-03-17 16:00:00',0,266.67,NULL,NULL,266.67,3),(134,'2014-03-18 01:20:01',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,7455.96,0,2,2,1,0,NULL,'48',0,0,'2014-03-17 16:00:00',0,2000,NULL,NULL,9455.96,3),(135,'2014-03-18 01:20:01',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,6,0,0,2,2,1,0,NULL,'49',0,0,'2014-03-17 16:00:00',0,266.67,NULL,NULL,266.67,3);
/*!40000 ALTER TABLE `fs_amountlog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_bankaccount`
--

DROP TABLE IF EXISTS `fs_bankaccount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_bankaccount` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `actNumber` varchar(30) DEFAULT NULL,
  `bankName` varchar(100) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `finsysId` double DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `refId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_bankaccount`
--

LOCK TABLES `fs_bankaccount` WRITE;
/*!40000 ALTER TABLE `fs_bankaccount` DISABLE KEYS */;
INSERT INTO `fs_bankaccount` VALUES (3,'2013-04-20 03:46:33',45,8,NULL,1,NULL,'2014-02-21 07:31:06',2,NULL,'10000212333300000','建设银行广州分行','BK00001',2,'程明卫',128),(4,'2013-04-20 03:46:33',45,8,NULL,1,NULL,'2014-02-21 07:31:06',2,NULL,'2312986718986123896','工行北京路支行','BK0000002',2,'王林',131),(5,'2013-07-24 04:14:52',2,8,NULL,1,NULL,'2014-02-21 07:31:06',-1,NULL,'349356004919','中国银行北京市分行','BK0000003',2,'广东小额贷款控股金融有限公司帐户',194);
/*!40000 ALTER TABLE `fs_bankaccount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_bussfincfg`
--

DROP TABLE IF EXISTS `fs_bussfincfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_bussfincfg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `finsysId` double DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_bussfincfg`
--

LOCK TABLES `fs_bussfincfg` WRITE;
/*!40000 ALTER TABLE `fs_bussfincfg` DISABLE KEYS */;
INSERT INTO `fs_bussfincfg` VALUES (1,'2013-03-28 16:00:00',1,8,-1,-1,NULL,'2014-02-21 07:31:07',2,NULL,1,7),(2,'2013-03-28 16:00:00',1,8,-1,1,NULL,'2014-02-21 07:31:07',2,'将小额贷款中的扣收数据传到K3中',1,3),(3,'2014-03-06 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,1,3);
/*!40000 ALTER TABLE `fs_bussfincfg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_coderule`
--

DROP TABLE IF EXISTS `fs_coderule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_coderule` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `change` int(11) DEFAULT NULL,
  `fgroupId` varchar(50) DEFAULT NULL,
  `fnumber` int(11) DEFAULT NULL,
  `fperiod` int(11) DEFAULT NULL,
  `fyear` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_coderule`
--

LOCK TABLES `fs_coderule` WRITE;
/*!40000 ALTER TABLE `fs_coderule` DISABLE KEYS */;
/*!40000 ALTER TABLE `fs_coderule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_currency`
--

DROP TABLE IF EXISTS `fs_currency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_currency` (
  `id` bigint(20) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `erate` double DEFAULT NULL,
  `finsysId` double DEFAULT NULL,
  `fscale` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `refId` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_currency`
--

LOCK TABLES `fs_currency` WRITE;
/*!40000 ALTER TABLE `fs_currency` DISABLE KEYS */;
INSERT INTO `fs_currency` VALUES (1,'2013-04-01 13:51:39',45,8,NULL,1,NULL,'2014-02-21 07:31:07',2,NULL,'*',1,2,2,'*',0),(2,'2013-04-01 13:51:39',45,8,NULL,1,NULL,'2014-02-21 07:31:07',2,NULL,'RMB',1,2,2,'人民币',1),(3,'2013-04-01 13:51:39',45,8,NULL,1,NULL,'2014-02-21 07:31:07',2,NULL,'USA',6,2,2,'美元',1000),(4,'2013-04-01 13:51:39',45,8,NULL,1,NULL,'2014-02-21 07:31:07',2,NULL,'GBP',1,2,2,'英鎊',1001);
/*!40000 ALTER TABLE `fs_currency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_entrytemp`
--

DROP TABLE IF EXISTS `fs_entrytemp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_entrytemp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `accountId` varchar(30) DEFAULT NULL,
  `accountId2` varchar(30) DEFAULT NULL,
  `conditionId` double DEFAULT NULL,
  `currencyId` varchar(50) DEFAULT NULL,
  `fdc` smallint(6) DEFAULT NULL,
  `formulaId` double DEFAULT NULL,
  `isItemClass` smallint(6) DEFAULT NULL,
  `settleId` varchar(50) DEFAULT NULL,
  `summary` varchar(200) DEFAULT NULL,
  `voucherId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_entrytemp`
--

LOCK TABLES `fs_entrytemp` WRITE;
/*!40000 ALTER TABLE `fs_entrytemp` DISABLE KEYS */;
INSERT INTO `fs_entrytemp` VALUES (2,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1049','1008',21,'1',1,20,2,'5','计提利息(借方)',6),(3,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1008','1049',23,'1',0,22,NULL,'5','计提利息(贷方)',6),(4,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1097','1098',25,'1',1,24,2,NULL,'计提管理费(借方)',7),(5,'2013-04-14 16:00:00',45,8,NULL,1,45,'2013-04-14 16:00:00',2,NULL,'1098','1097',27,'1',0,26,NULL,'5','计提管理费(贷方)',7),(6,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1104','1099',29,'1',1,28,2,'5','计提提前还款手续费(凭证模板)',8),(7,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1099','1104',31,'1',0,30,NULL,'1','计提提前还款手续费(贷方)',8),(8,'2013-04-14 16:00:00',45,8,NULL,1,45,'2013-04-14 16:00:00',2,NULL,'1105','1100',33,'1',1,32,2,'1','计提罚息及滞纳金(借方)',9),(9,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1100','1105',35,'1',0,34,NULL,'5','计提罚息及滞纳金(贷方)',9),(10,'2013-04-14 16:00:00',45,8,NULL,1,2,'2013-07-23 16:00:00',2,NULL,'1111','1090',233,'1',1,232,2,'5','放款(借方)--短期贷款',10),(11,'2013-04-14 16:00:00',45,8,NULL,1,2,'2013-07-23 16:00:00',2,NULL,'1108','1091',NULL,'1',0,236,2,'5','放款(贷方)',10),(12,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-26 16:00:00',2,NULL,'1113','1096',40,'1',1,248,2,'5','放款手续费收回(借方)',11),(13,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-26 16:00:00',2,NULL,'1114','1094',246,'1',0,245,2,'5','放款手续费收回(贷方)',11),(14,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1094','1096',44,'1',1,43,2,'1','放款手续费豁免(借方)',12),(15,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1096','1094',46,NULL,0,45,NULL,'1','放款手续费豁免(贷方)',12),(16,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-24 16:00:00',2,NULL,'1113','1091',48,'1',1,239,2,'5','本息费收回(借方)',13),(17,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-24 16:00:00',2,NULL,'1112','1090',244,'1',0,241,2,'5','本息费收回--本金(贷方)',13),(18,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-24 16:00:00',2,NULL,'1049','1090',243,'1',0,240,2,'5','本息费收回--利息(贷方)',13),(19,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-24 16:00:00',2,NULL,'1098','1090',242,'1',0,238,2,'5','本息费收回--管理费(贷方)',13),(20,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1090','1046',56,'1',1,55,2,'5','预收客户款(借方)',14),(21,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1046','1090',58,'1',0,57,2,'5','预收客户款(贷方)',14),(22,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1049','1008',60,NULL,1,59,2,NULL,'豁免利息(借方)',15),(23,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1008','1049',62,NULL,0,61,NULL,NULL,'豁免利息(贷方)',15),(24,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1097','1098',64,NULL,1,63,2,NULL,'豁免管理费(借方)',16),(25,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1098','1097',66,NULL,0,65,NULL,NULL,'豁免管理费(贷方)',16),(26,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-11-03 16:00:00',2,NULL,'1090','1091',294,'1',1,293,2,'5','本息费收回--提前还款(借方)',17),(27,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-11-03 16:00:00',2,NULL,'1091','1090',297,'1',0,292,2,'5','本息费收回-提前还本(贷方)',17),(28,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-11-03 16:00:00',2,NULL,'1100','1090',296,'1',0,291,2,'5','本息费收回-罚息及滞纳金(贷方)',17),(29,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-11-03 16:00:00',2,NULL,'1099','1090',290,'1',0,289,2,'5','提前还款手续费(贷方)',17),(30,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-11-03 16:00:00',2,NULL,'1098','1090',288,'1',0,287,2,'5','管理费--提前还款(贷方)',17),(31,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-11-03 16:00:00',2,NULL,'1049','1090',286,'1',0,285,2,'5','利息--提前还款(贷方)',17),(32,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-11-03 16:00:00',2,NULL,'1091','1090',295,'1',0,284,2,'5','本金--提前还款(贷方)',17),(33,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1049','1008',83,NULL,1,82,2,NULL,'豁免利息',18),(34,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1008','1049',85,NULL,0,84,NULL,NULL,'豁免利息',18),(35,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1097','1098',87,NULL,1,86,2,NULL,'豁免管理费',19),(36,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1098','1097',89,NULL,0,88,NULL,NULL,'豁免管理费',19),(37,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1049','1090',91,'1',1,90,2,'5','返还多收利息',20),(38,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1090','1049',93,'1',0,92,NULL,'5','返还多收利息',20),(39,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1090','1091',95,'1',1,94,2,'5','预收款核销',21),(40,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1091','1090',97,'1',0,96,2,'5','预收款核销',21),(41,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1097','1090',99,'1',0,98,2,'5','预收款核销',21),(42,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1049','1090',101,'1',0,100,2,'5','预收款核销',21),(43,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1091','1090',103,'1',0,102,2,'5','预收款核销',21),(44,'2013-04-19 16:00:00',45,8,NULL,1,2,'2014-07-29 16:00:00',2,NULL,'1113','1091',260,'1',1,259,2,'5','本息费收回(逾期)',22),(45,'2013-04-19 16:00:00',45,8,NULL,1,2,'2014-07-29 16:00:00',2,NULL,'1112','1113',107,'1',0,258,2,'5','本息费收回(逾期)',22),(46,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-31 16:00:00',2,NULL,'1116','1090',257,'1',0,256,2,'5','本息费收回(逾期)',22),(47,'2013-04-19 16:00:00',45,8,NULL,1,2,'2014-07-29 16:00:00',2,NULL,'1097','1090',255,'1',0,254,2,'5','本息费收回(逾期)',22),(48,'2013-04-19 16:00:00',45,8,NULL,1,2,'2014-07-29 16:00:00',2,NULL,'1049','1090',253,'1',0,252,2,'5','本息费收回(逾期)',22),(49,'2013-04-19 16:00:00',45,8,NULL,1,2,'2014-07-29 16:00:00',2,NULL,'1112','1113',250,'1',0,249,2,'5','本息费收回(逾期)',22),(50,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1097','1098',117,NULL,1,116,2,NULL,'返还多收管理费',23),(51,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1098','1097',119,NULL,0,118,NULL,NULL,'返还多收管理费',23),(52,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1100','1105',121,'1',1,120,2,'5','返还多收罚息及滞纳金',24),(53,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1105','1100',123,'1',0,122,NULL,'5','返还多收罚息及滞纳金',24),(54,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1100','1090',125,'1',1,124,2,'5','息费返还',25),(55,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1097','1090',127,'1',1,126,2,'5','息费返还',25),(56,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1049','1090',129,'1',1,128,2,'5','息费返还',25),(57,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1090','1049',131,'1',0,130,2,'5','息费返还',25),(58,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1094','1096',133,'1',1,132,2,'5','放款手续费计提',26),(59,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1096','1094',135,'1',0,134,NULL,'5','放款手续费计提',26),(60,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1101','1049',137,'1',1,136,2,'5','表外逾期客户计提息费',27),(61,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1098','1101',139,'1',0,138,2,'5','表外逾期客户计提管理费',27),(62,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1008','1101',141,'1',0,140,2,'5','表外逾期客户计提利息',27),(63,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1102','1091',143,'1',1,142,2,'5','表内客户转表外',28),(64,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1102','1091',145,'1',1,144,2,'5','表内客户转表外',28),(65,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1091','1102',147,'1',0,146,2,'5','表内客户转表外',28),(66,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1091','1102',149,'1',0,148,2,'5','表内客户转表外',28),(67,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1008','1049',151,'1',1,150,2,'5','表内客户转表外',29),(68,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1049','1008',153,'1',0,152,NULL,'5','表内客户转表外',29),(69,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1098','1097',155,'1',1,154,2,'5','表内客户转表外',30),(70,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1097','1098',157,'1',0,156,NULL,'5','表内客户转表外',30),(71,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1101','1102',159,'1',1,158,NULL,'5','表内客户转表外',31),(72,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1098','1101',161,'1',0,160,2,'5','表内客户转表外',31),(73,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1008','1101',163,'1',0,162,2,'5','表内客户转表外',31),(74,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1102','1101',165,'1',1,164,2,'5','表外客户转表内',32),(75,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1102','1101',167,'1',1,166,2,'5','表外客户转表内',32),(76,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1101','1102',169,'1',0,168,2,'5','表外客户转表内',32),(77,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1101','1102',172,'1',0,171,2,'5','表外客户转表内',32),(78,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1098','1097',174,'1',1,173,2,'5','表外客户转表内',33),(79,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1008','1049',176,'1',1,175,2,'5','表外客户转表内',33),(80,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1101','1049',178,'1',0,177,NULL,'5','表外客户转表内',33),(81,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1008','1049',180,'1',1,179,2,'5','表外客户转表内',34),(82,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1049','1008',182,'1',0,181,NULL,'5','表外客户转表内',34),(83,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1098','1097',184,'1',1,183,2,'5','表外客户转表内',35),(84,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1097','1098',186,'1',0,185,NULL,'5','表外客户转表内',35),(85,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1101','1102',189,'1',1,187,2,'5','贷款核销',36),(86,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1101','1102',191,'1',1,190,2,'5','贷款核销',36),(87,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1102','1101',193,'1',0,192,2,'5','贷款核销',36),(88,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1102','1101',195,'1',0,194,2,'5','贷款核销',36),(89,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1102','1101',197,'1',1,196,NULL,'5','贷款核销',37),(90,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1101','1102',199,'1',0,198,2,'5','贷款核销',37),(91,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1101','1102',201,'1',0,200,2,'5','贷款核销',37),(92,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1101','1102',203,'1',1,202,NULL,'5','贷款核销',38),(93,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1102','1101',205,'1',0,204,2,'5','贷款核销',38),(94,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1049','1008',207,'1',1,206,2,'5','贷款核销',39),(95,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1008','1049',209,'1',0,208,2,'5','贷款核销',39),(96,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1097','1098',211,'1',1,210,2,'5','贷款核销',40),(97,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:07',2,NULL,'1098','1097',213,'1',0,212,2,'5','贷款核销',40),(98,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-31 16:00:00',2,NULL,'1097','1101',279,'1',1,278,2,'5','本息费收回(表外逾期)',41),(99,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-31 16:00:00',2,NULL,'1049','1101',277,'1',1,276,2,'5','本息费收回(表外逾期)',41),(100,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-31 16:00:00',2,NULL,'1101','1049',275,'1',0,274,NULL,'5','本息费收回(表外逾期)',41),(101,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-31 16:00:00',2,NULL,'1113','1112',273,'1',1,272,2,'5','本息费收回(表外逾期)',42),(102,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-31 16:00:00',2,NULL,'1116','1113',271,'1',0,270,2,'5','本息费收回(表外逾期)',42),(103,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-09-01 16:00:00',2,NULL,'1097','1113',268,'1',0,267,NULL,'5','本息费收回(表外逾期)',42),(104,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-09-01 16:00:00',2,NULL,'1049','1113',281,'1',0,280,NULL,'5','本息费收回(表外逾期)',42),(105,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-31 16:00:00',2,NULL,'1112','1113',264,'1',0,263,2,'5','本息费收回(表外逾期)',42),(106,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-12 16:00:00',2,NULL,'1112','1113',261,'1',0,262,2,'5','本息费收回(表外逾期)',42),(107,'2013-05-20 16:00:00',45,8,NULL,1,2,'2013-07-23 16:00:00',2,NULL,'1110','1090',237,'1',1,234,2,'4','放款(借方)--长期贷款',10);
/*!40000 ALTER TABLE `fs_entrytemp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_finbussobject`
--

DROP TABLE IF EXISTS `fs_finbussobject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_finbussobject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `className` varchar(80) DEFAULT NULL,
  `finsysId` double DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `tabName` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_finbussobject`
--

LOCK TABLES `fs_finbussobject` WRITE;
/*!40000 ALTER TABLE `fs_finbussobject` DISABLE KEYS */;
INSERT INTO `fs_finbussobject` VALUES (1,'2013-03-29 16:00:00',45,8,NULL,1,45,'2013-03-29 16:00:00',2,NULL,'CustBaseEntity',2,'客户基础资料信息','crm_CustBase'),(2,'2013-04-14 16:00:00',45,8,NULL,1,2,'2013-11-03 16:00:00',2,'实收金额日志信息表，所有与财务相关的金额数据都存入此表中','AmountLogEntity',2,'实收金额日志(财务接口)','fs_AmountLog'),(3,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'LoanContractEntity',2,'借款合同信息','fc_LoanContract'),(4,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,'放款单对象表','LoanInvoceEntity',2,'放款单','fc_LoanInvoce'),(5,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'FreeRecordsEntity',2,'实收手续费记录','fc_FreeRecords'),(6,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'AmountRecordsEntity',2,'实收金额记录','fc_AmountRecords'),(7,'2013-11-03 16:00:00',2,-1,-1,1,NULL,'2014-02-21 07:31:08',-1,'小额贷款系统中用于记录提前还款收款记录的表。','PrepaymentRecordsEntity',2,'实收提前还款记录','fc_PrepaymentRecords');
/*!40000 ALTER TABLE `fs_finbussobject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_fincustfield`
--

DROP TABLE IF EXISTS `fs_fincustfield`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_fincustfield` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `bussObjectId` varchar(255) DEFAULT NULL,
  `cmn` varchar(30) DEFAULT NULL,
  `dataType` int(11) DEFAULT NULL,
  `field` varchar(30) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_fincustfield`
--

LOCK TABLES `fs_fincustfield` WRITE;
/*!40000 ALTER TABLE `fs_fincustfield` DISABLE KEYS */;
INSERT INTO `fs_fincustfield` VALUES (2,'2013-03-29 16:00:00',45,8,NULL,1,45,'2013-03-29 16:00:00',2,'主要是引用了财务系统里的客户核算项ID','1','refId',4,'refId','客户核算项ID'),(3,'2013-03-29 16:00:00',45,8,NULL,1,45,'2013-03-29 16:00:00',2,NULL,'1','code',4,'code','客户编号'),(4,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'2','amount',3,'amount','本金'),(5,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'2','ramount',3,'ramount','利息'),(6,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'2','mamount',3,'mamount','管理费'),(7,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'2','pamount',3,'pamount','罚息金额'),(8,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'2','oamount',3,'oamount','滞纳金'),(9,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'2','famount',3,'famount','手续费'),(10,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'2','bamount',3,'bamount','提前还款手续费'),(11,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'2','sumamount',3,'sumamount','实收合计'),(12,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'2','opdate',2,'opdate','放/收款日期'),(13,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'2','accountId',4,'accountId','放/收款帐号ID'),(14,'2013-04-19 16:00:00',45,8,NULL,1,45,'2013-04-19 16:00:00',2,NULL,'3','yearLoan',4,'yearLoan','贷款期限[年]'),(15,'2013-04-19 16:00:00',45,8,NULL,1,45,'2013-04-19 16:00:00',2,NULL,'3','monthLoan',4,'monthLoan','贷款期限[月]'),(16,'2013-04-19 16:00:00',45,8,NULL,1,45,'2013-04-19 16:00:00',2,NULL,'3','dayLoan',4,'dayLoan','贷款期限[日]'),(17,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'3','appAmount',3,'appAmount','贷款金额'),(18,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'3','endDate',2,'endDate','贷款截止日期'),(19,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'4','payAmount',3,'payAmount','放款金额'),(20,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'5','amount',3,'amount','实收手续费金额'),(21,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'6','cat',3,'cat','实收本金'),(22,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'6','rat',3,'rat','实收利息'),(23,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'6','mat',3,'mat','实收管理费'),(24,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'6','pat',3,'pat','实收罚息'),(25,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'6','dat',3,'dat','实收滞纳金'),(26,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'6','fat',3,'fat','实收提前还款手续费'),(27,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:08',2,NULL,'6','tat',3,'tat','实收合计'),(28,'2013-07-23 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:08',-1,NULL,'2','reffinAccountId',4,'reffinAccountId','财务系统银行帐号refId'),(29,'2013-11-03 16:00:00',2,-1,-1,1,NULL,'2014-02-21 07:31:08',-1,NULL,'7','cat',3,'cat','实收本金'),(30,'2013-11-03 16:00:00',2,-1,-1,1,NULL,'2014-02-21 07:31:08',-1,NULL,'7','rat',3,'rat','实收利息'),(31,'2013-11-03 16:00:00',2,-1,-1,1,NULL,'2014-02-21 07:31:08',-1,NULL,'7','mat',3,'mat','实收管理费'),(32,'2013-11-03 16:00:00',2,-1,-1,1,NULL,'2014-02-21 07:31:08',-1,NULL,'7','pat',3,'pat','实收罚息'),(33,'2013-11-03 16:00:00',2,-1,-1,1,NULL,'2014-02-21 07:31:08',-1,NULL,'7','dat',3,'dat','实收滞纳金'),(34,'2013-11-03 16:00:00',2,-1,-1,1,NULL,'2014-02-21 07:31:08',-1,NULL,'7','fat',3,'fat','实收提前还款手续费'),(35,'2013-11-03 16:00:00',2,-1,-1,1,NULL,'2014-02-21 07:31:08',-1,NULL,'7','tat',3,'tat','实收合计');
/*!40000 ALTER TABLE `fs_fincustfield` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_finsyscfg`
--

DROP TABLE IF EXISTS `fs_finsyscfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_finsyscfg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `dataCode` varchar(50) DEFAULT NULL,
  `dbName` varchar(50) DEFAULT NULL,
  `interType` smallint(6) DEFAULT NULL,
  `language` varchar(50) DEFAULT NULL,
  `loginName` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `passWord` varchar(50) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `server` varchar(80) DEFAULT NULL,
  `spAccgroup` smallint(6) DEFAULT NULL,
  `spCurrency` smallint(6) DEFAULT NULL,
  `spCustomer` smallint(6) DEFAULT NULL,
  `spImclass` smallint(6) DEFAULT NULL,
  `spSettle` smallint(6) DEFAULT NULL,
  `spUserName` smallint(6) DEFAULT NULL,
  `spVhgroup` smallint(6) DEFAULT NULL,
  `validType` varchar(50) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `dbType` smallint(6) DEFAULT NULL,
  `maxSize` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_finsyscfg`
--

LOCK TABLES `fs_finsyscfg` WRITE;
/*!40000 ALTER TABLE `fs_finsyscfg` DISABLE KEYS */;
INSERT INTO `fs_finsyscfg` VALUES (1,'2014-03-18 03:27:22',45,8,NULL,0,2,'2013-12-15 16:00:00',2,'xxxx',NULL,'AIS20130315101734',1,NULL,'txr.com','K3 Wise12.0(金蝶)','123',1433,'192.168.1.12',1,1,1,1,1,1,1,NULL,'FS0001',1,2),(2,'2013-12-08 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:08',-1,NULL,NULL,'xzzzz',1,NULL,'txr.com','xxxx','123456',12222,'192.168.1.9',1,1,1,1,1,1,1,NULL,'12222',1,50);
/*!40000 ALTER TABLE `fs_finsyscfg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_itemclass`
--

DROP TABLE IF EXISTS `fs_itemclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_itemclass` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `bussObjectId` double DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `finsysId` double DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `refId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_itemclass`
--

LOCK TABLES `fs_itemclass` WRITE;
/*!40000 ALTER TABLE `fs_itemclass` DISABLE KEYS */;
INSERT INTO `fs_itemclass` VALUES (1,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'*',2,'*',0),(2,'2013-04-07 02:25:09',45,8,NULL,1,45,'2013-04-07 07:32:41',2,NULL,1,'001',2,'客户',1),(3,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'002',2,'部门',2),(4,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'003',2,'职员',3),(5,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'004',2,'物料',4),(6,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'005',2,'仓库',5),(7,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'006',2,'备注',6),(8,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'007',2,'计量单位',7),(9,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'008',2,'供应商',8),(10,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'i009',2,'现金流量项目',9),(11,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'2014',2,'费用',2014),(12,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'2023',2,'成本对象组',2023),(13,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'2001',2,'成本对象',2001),(14,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'2002',2,'劳务',2002),(15,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'2003',2,'成本项目',2003),(16,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'2004',2,'要素费用',2004),(17,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'cj014',2,'工作中心',14),(18,'2013-04-07 02:25:09',45,8,NULL,1,NULL,'2014-02-21 07:31:08',2,NULL,NULL,'2024',2,'银行账号',2024);
/*!40000 ALTER TABLE `fs_itemclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_itemtemp`
--

DROP TABLE IF EXISTS `fs_itemtemp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_itemtemp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `bussObjectId` double DEFAULT NULL,
  `entryId` double DEFAULT NULL,
  `fieldIds` varchar(60) DEFAULT NULL,
  `itemClassId` varchar(50) DEFAULT NULL,
  `fieldNames` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_itemtemp`
--

LOCK TABLES `fs_itemtemp` WRITE;
/*!40000 ALTER TABLE `fs_itemtemp` DISABLE KEYS */;
INSERT INTO `fs_itemtemp` VALUES (9,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,2,'2','1','客户核算项ID'),(10,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,4,'2','1','客户核算项ID'),(11,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,6,'2','1','客户核算项ID'),(12,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,8,'2','1','客户核算项ID'),(13,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,10,'2','1','客户核算项ID'),(14,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-23 16:00:00',2,NULL,2,11,'28','2024','财务系统银行帐号refId'),(15,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-26 16:00:00',2,NULL,2,12,'28','2024','财务系统银行帐号refId'),(16,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-26 16:00:00',2,NULL,1,13,'2','1','客户核算项ID'),(17,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,14,'2','1','客户核算项ID'),(18,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-24 16:00:00',2,NULL,2,16,'28','2024','财务系统银行帐号refId'),(19,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,17,'2','1','客户核算项ID'),(20,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,19,'2','1','客户核算项ID'),(21,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,18,'2','1','客户核算项ID'),(22,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,2,20,'13','2024','放/收款帐号ID'),(23,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,21,'2','1','客户核算项ID'),(24,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,22,'2','1','客户核算项ID'),(25,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,24,'2','1','客户核算项ID'),(26,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,2,26,'13','2024','放/收款帐号ID'),(27,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,27,'2','1','客户核算项ID'),(28,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,28,'2','1','客户核算项ID'),(29,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,29,'2','1','客户核算项ID'),(30,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,30,'2','1','客户核算项ID'),(31,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,31,'2','1','客户核算项ID'),(32,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,32,'2','1','客户核算项ID'),(33,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,33,'2','1','客户核算项ID'),(34,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,35,'2','1','客户核算项ID'),(35,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,37,'2','1','客户核算项ID'),(36,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,39,'2','1','客户核算项ID'),(37,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,40,'2','1','客户核算项ID'),(38,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,41,'2','1','客户核算项ID'),(39,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,42,'2','1','客户核算项ID'),(40,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,43,'2','1','客户核算项ID'),(41,'2013-04-19 16:00:00',45,8,NULL,1,2,'2014-07-29 16:00:00',2,NULL,2,44,'28','2024','财务系统银行帐号refId'),(42,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,45,'2','1','客户核算项ID'),(43,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-31 16:00:00',2,NULL,1,46,'2','1','客户核算项ID'),(44,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,47,'2','1','客户核算项ID'),(45,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,48,'2','1','客户核算项ID'),(46,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,49,'2','1','客户核算项ID'),(47,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,50,'2','1','客户核算项ID'),(48,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,52,'2','1','客户核算项ID'),(49,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,54,'2','1','客户核算项ID'),(50,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,55,'2','1','客户核算项ID'),(51,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,56,'2','1','客户核算项ID'),(52,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,2,57,'13','2024','放/收款帐号ID'),(53,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,58,'2','1','客户核算项ID'),(54,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,60,'2','1','客户核算项ID'),(55,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,61,'2','1','客户核算项ID'),(56,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,62,'2','1','客户核算项ID'),(57,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,63,'2','1','客户核算项ID'),(58,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,64,'2','1','客户核算项ID'),(59,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,65,'2','1','客户核算项ID'),(60,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,66,'2','1','客户核算项ID'),(61,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,67,'2','1','客户核算项ID'),(62,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,69,'2','1','客户核算项ID'),(63,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,72,'2','1','客户核算项ID'),(64,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,73,'2','1','客户核算项ID'),(65,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,74,'2','1','客户核算项ID'),(66,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,75,'2','1','客户核算项ID'),(67,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,76,'2','1','客户核算项ID'),(68,'2013-04-19 16:00:00',45,8,-1,-1,NULL,'2014-02-21 07:31:09',2,NULL,1,76,'2','1','客户核算项ID'),(69,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,77,'2','1','客户核算项ID'),(70,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,78,'2','1','客户核算项ID'),(71,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,79,'2','1','客户核算项ID'),(72,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,81,'2','1','客户核算项ID'),(73,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,83,'2','1','客户核算项ID'),(74,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,85,'2','1','客户核算项ID'),(75,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,86,'2','1','客户核算项ID'),(76,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,87,'2','1','客户核算项ID'),(77,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,88,'2','1','客户核算项ID'),(78,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,90,'2','1','客户核算项ID'),(79,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,91,'2','1','客户核算项ID'),(80,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,93,'2','1','客户核算项ID'),(81,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,94,'2','1','客户核算项ID'),(82,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,95,'2','1','客户核算项ID'),(83,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,96,'2','1','客户核算项ID'),(84,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,97,'2','1','客户核算项ID'),(85,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,98,'2','1','客户核算项ID'),(86,'2013-04-19 16:00:00',45,8,NULL,1,45,'2013-05-26 16:00:00',2,NULL,1,99,'2','1','客户核算项ID'),(87,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-09-01 16:00:00',2,NULL,2,101,'28','2024','财务系统银行帐号refId'),(88,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,102,'2','1','客户核算项ID'),(89,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,105,'2','1','客户核算项ID'),(90,'2013-04-19 16:00:00',45,8,NULL,1,45,'2013-05-26 16:00:00',2,NULL,1,106,'2','1','客户核算项ID'),(91,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:09',2,NULL,1,107,'2','1','客户核算项ID');
/*!40000 ALTER TABLE `fs_itemtemp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_settle`
--

DROP TABLE IF EXISTS `fs_settle`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_settle` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `finsysId` double DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `refId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_settle`
--

LOCK TABLES `fs_settle` WRITE;
/*!40000 ALTER TABLE `fs_settle` DISABLE KEYS */;
INSERT INTO `fs_settle` VALUES (1,'2013-04-07 02:25:42',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,'*',2,'*',0),(2,'2013-04-07 02:25:42',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,'JF01',2,'现金',1),(3,'2013-04-07 02:25:42',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,'JF02',2,'电汇',2),(4,'2013-04-07 02:25:42',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,'JF03',2,'信汇',3),(5,'2013-04-07 02:25:42',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,'JF04',2,'商业汇票',4),(6,'2013-04-07 02:25:42',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,'JF05',2,'银行汇票',5);
/*!40000 ALTER TABLE `fs_settle` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_subject`
--

DROP TABLE IF EXISTS `fs_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_subject` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `atype` int(11) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `currencyId` double DEFAULT NULL,
  `dc` smallint(6) DEFAULT NULL,
  `detail` smallint(6) DEFAULT NULL,
  `finsysId` double DEFAULT NULL,
  `groupId` double DEFAULT NULL,
  `itemClassId` double DEFAULT NULL,
  `levels` int(11) DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `refId` double DEFAULT NULL,
  `rootId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_subject`
--

LOCK TABLES `fs_subject` WRITE;
/*!40000 ALTER TABLE `fs_subject` DISABLE KEYS */;
INSERT INTO `fs_subject` VALUES (1,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,1,'1001',1,1,1,2,101,0,1,'库存现金',1000,1000),(2,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,2,'1002.01',1,1,1,2,101,0,2,'建设银行人民币',1001,1090),(3,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1012',1,1,1,2,101,0,1,'其他货币资金',1002,1002),(4,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1101',1,1,1,2,101,0,1,'交易性金融资产',1003,1003),(5,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1121',1,1,1,2,101,0,1,'应收票据',1004,1004),(6,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1122',1,1,1,2,101,0,1,'应收账款',1005,1005),(7,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1123',1,1,1,2,101,0,1,'预付账款',1006,1006),(8,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1131',1,1,1,2,101,0,1,'应收股利',1007,1007),(9,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1132',1,1,1,2,101,0,1,'应收利息',1008,1008),(10,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1221',1,1,1,2,101,0,1,'其他应收款',1009,1009),(11,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1231',1,0,1,2,101,0,1,'坏账准备',1010,1010),(12,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1321',1,1,1,2,101,0,1,'代理业务资产',1011,1011),(13,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1401',1,1,1,2,101,0,1,'材料采购',1012,1012),(14,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1402',1,1,1,2,101,0,1,'在途物资',1013,1013),(15,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1403',1,1,1,2,101,0,1,'原材料',1014,1014),(16,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1404',1,1,1,2,101,0,1,'材料成本差异',1015,1015),(17,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1405',1,1,1,2,101,0,1,'库存商品',1016,1016),(18,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1406',1,1,1,2,101,0,1,'发出商品',1017,1017),(19,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1407',1,1,1,2,101,0,1,'商品进销差价',1018,1018),(20,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1408',1,1,1,2,101,0,1,'委托加工物资',1019,1019),(21,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1471',1,0,1,2,101,0,1,'存货跌价准备',1020,1020),(22,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1501',1,1,1,2,101,0,1,'持有至到期投资',1021,1021),(23,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1502',1,0,1,2,101,0,1,'持有至到期投资减值准备',1022,1022),(24,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1503',1,1,1,2,101,0,1,'可供出售金融资产',1023,1023),(25,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1511',1,1,1,2,102,0,1,'长期股权投资',1024,1024),(26,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1512',1,0,1,2,102,0,1,'长期股权投资减值准备',1025,1025),(27,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1521',1,1,1,2,101,0,1,'投资性房地产',1026,1026),(28,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1531',1,1,1,2,102,0,1,'长期应收款',1027,1027),(29,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1532',1,1,1,2,102,0,1,'未实现融资收益',1028,1028),(30,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1601',1,1,1,2,102,0,1,'固定资产',1029,1029),(31,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1602',1,0,1,2,102,0,1,'累计折旧',1030,1030),(32,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1603',1,0,1,2,102,0,1,'固定资产减值准备',1031,1031),(33,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1604',1,1,1,2,102,0,1,'在建工程',1032,1032),(34,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1605',1,1,1,2,102,0,1,'工程物资',1033,1033),(35,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1606',1,1,1,2,102,0,1,'固定资产清理',1034,1034),(36,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1701',1,1,1,2,102,0,1,'无形资产',1035,1035),(37,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1702',1,0,1,2,102,0,1,'累计摊销',1036,1036),(38,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1703',1,0,1,2,102,0,1,'无形资产减值准备',1037,1037),(39,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1711',1,1,1,2,102,0,1,'商誉',1038,1038),(40,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1801',1,1,1,2,102,0,1,'长期待摊费用',1039,1039),(41,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1811',1,1,1,2,102,0,1,'递延所得税资产',1040,1040),(42,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1901',1,1,1,2,101,0,1,'待处理财产损溢',1041,1041),(43,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2001',1,0,1,2,201,0,1,'短期借款',1042,1042),(44,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2101',1,0,1,2,201,0,1,'交易性金融负债',1043,1043),(45,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2201',1,0,1,2,201,0,1,'应付票据',1044,1044),(46,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2202',1,0,1,2,201,0,1,'应付账款',1045,1045),(47,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2203',1,0,1,2,201,0,1,'预收账款',1046,1046),(48,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2211',1,0,1,2,201,0,1,'应付职工薪酬',1047,1047),(49,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2221',1,0,1,2,201,0,1,'应交税费',1048,1048),(50,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2231',1,0,1,2,201,0,1,'利息收入',1049,1049),(51,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2232',1,0,1,2,201,0,1,'应付股利',1050,1050),(52,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2241',1,0,1,2,201,0,1,'其他应付款',1051,1051),(53,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2314',1,0,1,2,201,0,1,'代理业务负债',1052,1052),(54,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2401',1,0,1,2,201,0,1,'递延收益',1053,1053),(55,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2501',1,0,1,2,202,0,1,'长期借款',1054,1054),(56,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2502',1,0,1,2,202,0,1,'长期债券',1055,1055),(57,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2701',1,0,1,2,202,0,1,'长期应付款',1056,1056),(58,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2702',1,0,1,2,202,0,1,'未确认融资费用',1057,1057),(59,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2711',1,0,1,2,202,0,1,'专项应付款',1058,1058),(60,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2801',1,0,1,2,201,0,1,'预计负债',1059,1059),(61,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2901',1,0,1,2,202,0,1,'递延所得税负债',1060,1060),(62,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'3101',1,1,1,2,700,0,1,'衍生工具',1061,1061),(63,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'3201',1,1,1,2,700,0,1,'套期工具',1062,1062),(64,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'3202',1,1,1,2,700,0,1,'被套期项目',1063,1063),(65,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'4001',1,0,1,2,300,0,1,'实收资本',1064,1064),(66,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'4002',1,0,1,2,300,0,1,'资本公积',1065,1065),(67,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'4101',1,0,1,2,300,0,1,'盈余公积',1066,1066),(68,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'4103',1,0,1,2,300,0,1,'本年利润',1067,1067),(69,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'4104',1,0,1,2,300,0,1,'利润分配',1068,1068),(70,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'4201',1,0,1,2,300,0,1,'库存股',1069,1069),(71,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'5001',1,1,1,2,400,0,1,'生产成本',1070,1070),(72,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'5101',1,1,1,2,400,0,1,'制造费用',1071,1071),(73,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'5201',1,1,1,2,400,0,1,'劳务成本',1072,1072),(74,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'5301',1,1,1,2,400,0,1,'研发支出',1073,1073),(75,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6001',1,0,1,2,501,0,1,'主营业务收入',1074,1074),(76,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6051',1,0,1,2,504,0,1,'其他业务收入',1075,1075),(77,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6101',1,0,1,2,504,0,1,'公允价值变动损益',1076,1076),(78,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6111',1,0,1,2,504,0,1,'投资收益',1077,1077),(79,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6301',1,0,1,2,504,0,1,'营业外收入',1078,1078),(80,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6401',1,1,1,2,502,0,1,'主营业务成本',1079,1079),(81,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6402',1,1,1,2,505,0,1,'其他业务支出',1080,1080),(82,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6405',1,1,1,2,502,0,1,'营业税金及附加',1081,1081),(83,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6601',1,1,1,2,503,0,1,'销售费用',1082,1082),(84,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6602',1,1,1,2,503,0,1,'管理费用',1083,1083),(85,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6603',1,1,1,2,503,0,1,'财务费用',1084,1084),(86,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6604',1,1,1,2,503,0,1,'勘探费用',1085,1085),(87,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6701',1,1,1,2,505,0,1,'资产减值损失',1086,1086),(88,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6711',1,1,1,2,505,0,1,'营业外支出',1087,1087),(89,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6801',1,1,1,2,507,0,1,'所得税',1088,1088),(90,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6901',1,0,1,2,506,0,1,'以前年度损益调整',1089,1089),(91,'2013-04-07 02:39:58',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,2,'1002',1000,1,0,2,101,0,1,'银行存款',1090,1090),(92,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'2002',1,1,1,2,201,0,1,'贷款',1091,1091),(93,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6302',1,1,1,2,504,0,1,'手续费',1092,1092),(94,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6303',1,1,1,2,504,0,1,'放款手续费',1094,1094),(95,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1133',1,0,1,2,101,0,1,'应收手续费',1095,1095),(96,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1134',1,0,1,2,101,0,1,'放款手续费',1096,1096),(97,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6304',1,1,1,2,504,0,1,'管理费收入',1097,1097),(98,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1135',1,0,1,2,101,0,1,'应收管理费',1098,1098),(99,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6305',1,0,1,2,504,0,1,'提前还款手续费',1099,1099),(100,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'6306',1,0,1,2,504,0,1,'罚息与滞纳金',1100,1100),(101,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'200201',1,1,1,2,201,0,1,'呆滞贷款',1101,1101),(102,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'20020101',1,1,1,2,201,0,1,'余额',1102,1102),(103,'2013-04-15 08:35:19',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'123101',1,1,1,2,101,0,1,'专项准备',1103,1103),(104,'2013-04-15 09:25:28',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1136',1,1,1,2,101,0,1,'应收提前还款手续费',1104,1104),(105,'2013-04-15 10:13:44',45,8,NULL,1,NULL,'2014-02-21 07:31:09',2,NULL,0,'1137',1,1,1,2,101,0,1,'应收罚息及滞纳金',1105,1105),(106,'2013-07-24 02:43:22',2,8,NULL,1,NULL,'2014-02-21 07:31:09',-1,NULL,1,'1002.02',1,1,1,2,101,0,2,'工商银行人民币账户',1106,1090),(107,'2013-07-24 04:24:18',2,8,NULL,1,NULL,'2014-02-21 07:31:09',-1,NULL,1,'1002',1,0,0,2,101,0,1,'银行存款',1107,1107),(108,'2013-07-24 04:24:18',2,8,NULL,1,NULL,'2014-02-21 07:31:09',-1,NULL,1,'1002.02',1,0,1,2,101,2024,2,'银行存款(工商)',1108,1107),(109,'2013-07-24 04:24:18',2,8,NULL,1,NULL,'2014-02-21 07:31:09',-1,NULL,1,'1002.03',1,1,1,2,101,2024,2,'银行存款(中国银行)',1109,1107),(110,'2013-07-24 13:16:43',2,8,NULL,1,NULL,'2014-02-21 07:31:09',-1,NULL,0,'2002.02',1,1,1,2,201,1,2,'长期贷款',1110,1091),(111,'2013-07-24 13:16:43',2,8,NULL,1,NULL,'2014-02-21 07:31:09',-1,NULL,0,'2002.01',1,1,1,2,201,1,2,'短期贷款',1111,1091),(112,'2013-07-25 09:08:41',2,8,NULL,1,NULL,'2014-02-21 07:31:09',-1,NULL,0,'2802',1,0,1,2,201,1,1,'贷款本金收回',1112,1112),(113,'2013-07-25 09:12:33',2,8,NULL,1,NULL,'2014-02-21 07:31:09',-1,NULL,1,'1002.04',1,1,1,2,101,2024,2,'银行存款(公司帐户)',1113,1107),(114,'2013-07-27 04:25:17',2,8,NULL,1,NULL,'2014-02-21 07:31:09',-1,NULL,0,'6307',1,1,1,2,504,1,1,'放款手续费',1114,1114),(115,'2013-09-01 08:35:33',2,8,NULL,1,NULL,'2014-02-21 07:31:09',-1,NULL,0,'6308',1,0,1,2,503,1,1,'逾期罚息与滞纳金',1115,1115),(116,'2013-09-01 08:35:33',2,8,NULL,1,NULL,'2014-02-21 07:31:09',-1,NULL,0,'6309',1,0,1,2,504,1,1,'罚息与滞纳金收入',1116,1116);
/*!40000 ALTER TABLE `fs_subject` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_usermapping`
--

DROP TABLE IF EXISTS `fs_usermapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_usermapping` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `finsysId` double DEFAULT NULL,
  `forgcode` varchar(30) DEFAULT NULL,
  `fsman` varchar(50) DEFAULT NULL,
  `fuserName` varchar(50) DEFAULT NULL,
  `refId` double DEFAULT NULL,
  `userId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_usermapping`
--

LOCK TABLES `fs_usermapping` WRITE;
/*!40000 ALTER TABLE `fs_usermapping` DISABLE KEYS */;
INSERT INTO `fs_usermapping` VALUES (15,'2013-10-17 08:04:54',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'*','*','Users',0,0),(16,'2013-10-17 08:04:54',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'*','*','Administrators',1,0),(17,'2013-10-17 08:04:54',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'*','*','Cashiers',11,0),(18,'2013-10-17 08:04:54',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'*','*','ReportUsers',12,0),(19,'2013-10-17 08:04:54',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'*','*','Guest',16384,0),(20,'2013-10-17 08:04:54',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'*','*','Morningstar',16393,0),(21,'2013-10-17 08:04:54',6,26,NULL,1,2,'2013-10-18 04:18:53',23,NULL,2,'*','*','Administrator',16394,2),(22,'2013-10-17 08:04:54',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'*','*','HROpenUser',16395,0),(23,'2013-10-17 08:04:54',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'*','*','System',16396,0),(24,'2013-10-17 08:04:54',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'*','*','Workflow',16397,0),(25,'2013-10-17 08:04:54',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'*','*','cmw',16398,0),(26,'2013-10-17 08:04:54',6,26,NULL,1,2,'2013-11-06 04:40:07',23,NULL,2,'C10002','彭登浩','彭登浩',16399,10),(27,'2013-10-17 08:04:54',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'C10001','程明卫','程明卫',16400,0),(28,'2013-10-17 08:11:36',6,26,NULL,1,NULL,'2014-02-21 07:31:09',23,NULL,2,'*','*','NONE',-1,0),(29,'2013-10-17 08:11:36',6,26,NULL,1,6,'2013-10-17 08:11:51',23,NULL,2,'C10002','彭登浩','李万全',16401,6);
/*!40000 ALTER TABLE `fs_usermapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_vouchergroup`
--

DROP TABLE IF EXISTS `fs_vouchergroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_vouchergroup` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `finsysId` double DEFAULT NULL,
  `name` varchar(60) DEFAULT NULL,
  `refId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_vouchergroup`
--

LOCK TABLES `fs_vouchergroup` WRITE;
/*!40000 ALTER TABLE `fs_vouchergroup` DISABLE KEYS */;
INSERT INTO `fs_vouchergroup` VALUES (1,'2013-04-07 07:37:34',45,8,NULL,1,NULL,'2014-02-21 07:31:10',2,NULL,2,'借',1);
/*!40000 ALTER TABLE `fs_vouchergroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_voucheroplog`
--

DROP TABLE IF EXISTS `fs_voucheroplog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_voucheroplog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `amountLogId` double DEFAULT NULL,
  `errCode` int(11) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  `vtempId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_voucheroplog`
--

LOCK TABLES `fs_voucheroplog` WRITE;
/*!40000 ALTER TABLE `fs_voucheroplog` DISABLE KEYS */;
INSERT INTO `fs_voucheroplog` VALUES (32,'2014-01-24 03:05:18',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,47,6,NULL,1,3,10),(33,'2014-01-24 03:13:29',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,48,6,NULL,1,3,13),(34,'2014-01-24 03:37:16',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,50,6,NULL,1,3,10),(35,'2014-01-27 10:16:21',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,52,6,NULL,1,3,10),(36,'2014-01-27 10:27:37',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,53,6,NULL,1,3,13),(37,'2014-01-27 10:29:57',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,54,6,NULL,1,3,13),(38,'2014-01-27 10:30:21',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,55,6,NULL,1,3,13),(39,'2014-01-27 10:33:42',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,56,6,NULL,1,3,13),(40,'2014-01-27 11:01:03',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,57,6,NULL,1,3,13),(41,'2014-01-27 11:02:25',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,58,6,NULL,1,3,13),(42,'2014-01-27 11:13:39',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,59,6,NULL,1,3,13),(43,'2014-01-27 12:28:34',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,61,6,NULL,1,3,10),(44,'2014-01-27 12:29:30',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,62,6,NULL,1,3,10),(45,'2014-01-27 12:32:18',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,64,6,NULL,1,3,13),(46,'2014-01-27 12:36:03',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,65,6,NULL,1,3,13),(47,'2014-02-06 03:45:53',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,66,6,NULL,1,3,10),(48,'2014-02-06 09:51:36',2,8,NULL,1,NULL,'2014-02-21 07:31:10',-1,NULL,70,6,NULL,1,3,13),(49,'2014-02-06 09:52:33',2,8,NULL,1,NULL,'2014-02-21 07:31:10',-1,NULL,71,6,NULL,1,3,13),(50,'2014-02-06 09:59:15',2,8,NULL,1,NULL,'2014-02-21 07:31:10',-1,NULL,72,6,NULL,1,3,13),(51,'2014-02-07 04:19:22',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,77,6,NULL,1,3,13),(52,'2014-02-07 04:20:44',13,4,NULL,1,NULL,'2014-02-21 07:31:10',1,NULL,78,6,NULL,1,3,13),(53,'2014-03-14 07:14:44',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,86,3,NULL,1,3,0),(54,'2014-03-17 04:38:06',13,4,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,101,3,NULL,1,3,0),(55,'2014-03-18 02:10:53',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,119,3,NULL,1,3,0),(56,'2014-03-18 03:28:21',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,120,3,NULL,1,3,0),(57,'2014-03-18 00:02:19',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,127,3,NULL,1,3,0);
/*!40000 ALTER TABLE `fs_voucheroplog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fs_vouchertemp`
--

DROP TABLE IF EXISTS `fs_vouchertemp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fs_vouchertemp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `code` varchar(30) DEFAULT NULL,
  `currencyId` varchar(50) DEFAULT NULL,
  `entry` int(11) DEFAULT NULL,
  `finsysId` double DEFAULT NULL,
  `groupId` varchar(50) DEFAULT NULL,
  `maxcount` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `tactics` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fs_vouchertemp`
--

LOCK TABLES `fs_vouchertemp` WRITE;
/*!40000 ALTER TABLE `fs_vouchertemp` DISABLE KEYS */;
INSERT INTO `fs_vouchertemp` VALUES (3,'2013-04-08 16:00:00',45,8,-1,-1,NULL,'2014-02-21 07:31:10',2,NULL,'V2013040906052500','1',1,2,'1',500,'xxx',1),(4,'2013-04-08 16:00:00',45,8,-1,-1,NULL,'2014-02-21 07:31:10',2,NULL,'V2013040906052500','1',1,2,'1',500,'xxx',1),(5,'2013-04-08 16:00:00',45,8,-1,-1,NULL,'2014-02-21 07:31:10',2,NULL,'V2013040906052500','1',1,2,'1',500,'xxx',1),(6,'2013-04-08 16:00:00',45,8,NULL,0,45,'2013-04-08 16:00:00',2,NULL,'V00001','1',1,2,'1',500,'利息计提(凭证模板)',1),(7,'2013-04-14 16:00:00',45,8,NULL,0,45,'2013-04-14 16:00:00',2,NULL,'V00002','1',1,2,'1',500,'管理费计提(凭证模板)',1),(8,'2013-04-14 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00003','1',1,2,'1',500,'提前还款手续费计提',1),(9,'2013-04-14 16:00:00',45,8,NULL,0,45,'2013-04-14 16:00:00',2,NULL,'V00004','1',1,2,'1',500,'罚息及滞纳金计提(凭证模板)',1),(10,'2013-04-14 16:00:00',45,8,NULL,1,2,'2013-07-23 16:00:00',2,NULL,'V00005','1',1,2,'1',500,'放款(凭证模板)',1),(11,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-26 16:00:00',2,NULL,'V00006','1',2,2,'1',500,'放款手续费收取(凭证模板)',1),(12,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00007','1',1,2,'1',500,'放款手续费豁免(凭证模板)',1),(13,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-07-24 16:00:00',2,NULL,'V00008','1',2,2,'1',500,'正常扣收(凭证模板)',1),(14,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00009','1',2,2,'1',500,'预收账款(凭证模板)',1),(15,'2013-04-19 16:00:00',45,8,NULL,0,2,'2013-10-04 16:00:00',2,NULL,'V00010','1',1,2,'1',500,'正常扣收豁免(凭证模板)',1),(16,'2013-04-19 16:00:00',45,8,NULL,0,2,'2013-10-04 16:00:00',2,NULL,'V00011','1',1,2,'1',500,'提前还款豁免凭证模板)',1),(17,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:10',2,NULL,'V00012','1',2,2,'1',500,'提前还款(凭证模板)',1),(18,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00013','1',1,2,'1',500,'还款豁免利息(凭证模板)',1),(19,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00014','1',1,2,'1',500,'还款豁免管理费',1),(20,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00015','1',1,2,'1',500,'息费返还利息（红字）',1),(21,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00016','1',3,2,'1',500,'预收账款核销',1),(22,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-31 16:00:00',2,NULL,'V00017','1',2,2,'1',500,'逾期扣收',1),(23,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00018','1',1,2,'1',500,'息费返还管理费（红字）',1),(24,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00019','1',1,2,'1',500,'息费返还罚息及滞纳金（红字）',1),(25,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00020','1',1,2,'1',500,'息费返还',1),(26,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00021','1',1,2,'1',500,'放款手续费计提',1),(27,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00022','1',3,2,'1',500,'表外逾期客户利息管理费计提',1),(28,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00023','1',3,2,'1',500,'表内转表外-呆滞贷款',1),(29,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00024','1',1,2,'1',500,'表内转表外-应收利息',1),(30,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00025','1',1,2,'1',500,'表内转表外-管理费',1),(31,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00026','1',2,2,'1',500,'表内转表外-备查登记类借方余额',1),(32,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00027','1',3,2,'1',500,'表外转表内-呆滞贷款',1),(33,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00028','1',1,2,'1',500,'表外转表内-备查登记类借方余额',1),(34,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00029','1',1,2,'1',500,'表外转表内-应收利息',1),(35,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00030','1',1,2,'1',500,'表外转表内-管理费',1),(36,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00031','1',3,2,'1',500,'贷款核销-呆滞贷款',1),(37,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00032','1',2,2,'1',500,'贷款核销—贷款损失准备',1),(38,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00033','1',2,2,'1',500,'贷款核销-备查登记类借方余额',1),(39,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00034','1',3,2,'1',500,'贷款核销-应收利息',1),(40,'2013-04-19 16:00:00',45,8,-1,0,NULL,'2014-02-21 07:31:10',2,NULL,'V00035','1',3,2,'1',500,'贷款核销-管理费',1),(41,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-31 16:00:00',2,NULL,'V00036','1',1,2,'1',500,'表外逾期扣收-备查登记类账户余额',1),(42,'2013-04-19 16:00:00',45,8,NULL,1,2,'2013-08-31 16:00:00',2,NULL,'V00037','1',2,2,'1',500,'表外逾期扣收',1);
/*!40000 ALTER TABLE `fs_vouchertemp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_accordion`
--

DROP TABLE IF EXISTS `ts_accordion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_accordion` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` bigint(20) DEFAULT NULL,
  `deptId` bigint(20) DEFAULT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` bigint(20) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `iconCls` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `sysid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_accordion`
--

LOCK TABLES `ts_accordion` WRITE;
/*!40000 ALTER TABLE `ts_accordion` DISABLE KEYS */;
INSERT INTO `ts_accordion` VALUES (12,'2012-01-24 16:03:00',1,1,NULL,-1,NULL,'2014-02-21 07:31:10',9999,NULL,'A2012032500',NULL,'客户管理',1,0,'./sysMenu_list.action',0),(13,'2012-01-01 16:04:00',1,1,NULL,-1,NULL,'2014-02-21 07:31:10',9999,NULL,'A2012040224',NULL,'客户管理xxyy',1,0,'./sysMenu_list.action',0),(14,'2012-01-01 16:04:00',1,1,NULL,0,1,'2012-01-04 16:04:00',9999,NULL,'A2012040224','admin-office','小贷综合业务管理',2,0,'./sysMenu_list.action',0),(15,'2012-01-04 16:04:00',1,1,NULL,0,1,'2012-01-04 16:04:00',9999,NULL,'A2012040528','man-office','客户管理',1,0,'./sysMenu_list.action',0),(16,'2012-01-04 16:04:00',1,1,NULL,0,NULL,'2014-02-21 07:31:10',9999,NULL,'A2012040530','admin-office','统计分析',3,0,'./sysMenu_list.action',0),(17,'2012-01-04 16:04:00',1,1,NULL,0,NULL,'2014-02-21 07:31:10',9999,NULL,'A2012040532','resource-office','流程管理',4,0,'./sysMenu_list.action',0),(18,'2012-01-04 16:04:00',1,1,NULL,1,NULL,'2014-02-21 07:31:10',9999,NULL,'A2012040534','crm-office','系统管理',5,0,'./sysMenu_list.action',0),(19,'2011-12-31 16:11:00',-1,-1,NULL,1,45,'2013-03-12 16:00:00',0,NULL,'A2012110100235936','tools-office','系统功能列表',1,0,'./sysMenu_sysmenus.action',1),(20,'2011-12-31 16:11:00',-1,-1,NULL,1,NULL,'2014-02-21 07:31:10',0,NULL,'A2012110101381838',NULL,'我的待办',1,0,NULL,2),(21,'2012-01-05 16:12:00',-1,-1,NULL,1,2,'2013-09-22 16:00:00',0,NULL,'FORM_20121206',NULL,'流程业务表单',21,0,'./sysMenu_list.action?action=0',3),(22,'2012-01-09 16:12:00',-1,-1,NULL,1,-1,'2012-01-27 16:12:00',0,NULL,'A2012121011443642','man-office','客户资料',1,0,'./sysMenu_list.action?action=0',3),(23,'2012-01-09 16:12:00',-1,-1,NULL,1,-1,'2012-01-27 16:12:00',0,NULL,'A2012121011481644','admin-office','贷款业务办理',2,0,'./sysMenu_list.action?action=0',3),(24,'2012-01-09 16:12:00',-1,-1,NULL,1,-1,'2012-01-27 16:12:00',0,NULL,'A2012121011493846','resource-office','贷款审批管理',3,0,'./sysMenu_list.action?action=0',3),(25,'2012-01-09 16:12:00',-1,-1,NULL,1,45,'2013-03-10 16:00:00',0,NULL,'A2012121011540448','crm-office','贷款资金收付管理',4,0,'./sysMenu_list.action?action=0',3),(26,'2012-01-09 16:12:00',-1,-1,NULL,1,2,'2013-09-22 16:00:00',0,'xxxx','A2012121011561450','inv-office','系统初始化',20,0,'./sysMenu_list.action?action=0',3),(27,'2012-01-13 16:12:00',-1,-1,-1,1,NULL,'2014-02-21 07:31:10',0,NULL,'A2012121409190352',NULL,'xxx',1,0,NULL,4),(28,'2013-03-27 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:10',2,NULL,'A2013032804200754','tools-office','财务配置列表',1,0,'./sysMenu_sysmenus.action',6),(29,'2013-05-20 16:00:00',45,8,NULL,1,2,'2013-09-22 16:00:00',2,NULL,'A2013052102510556','search-office','业务查询及统计',19,0,'./sysMenu_list.action?action=0',3),(30,'2013-09-22 16:00:00',2,8,NULL,1,2,'2013-09-22 16:00:00',-1,NULL,'SUBFORM_20130923',NULL,'子业务流程表单',22,0,'./sysMenu_list.action?action=0',3),(31,'2014-03-05 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,'A2014030609595160','crm-office','资金管理',7,0,'./sysMenu_list.action?action=0',3);
/*!40000 ALTER TABLE `ts_accordion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_account`
--

DROP TABLE IF EXISTS `ts_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `account` varchar(30) DEFAULT NULL,
  `atype` int(11) DEFAULT NULL,
  `bankName` varchar(150) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `fsbankAccountId` double DEFAULT NULL,
  `isIncome` int(11) DEFAULT NULL,
  `isPay` int(11) DEFAULT NULL,
  `refId` varchar(255) DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_account`
--

LOCK TABLES `ts_account` WRITE;
/*!40000 ALTER TABLE `ts_account` DISABLE KEYS */;
INSERT INTO `ts_account` VALUES (1,'2013-12-15 16:00:00',2,8,NULL,-1,2,'2013-12-18 16:00:00',-1,NULL,'4400 1581 3010 5029 5751',0,'建行天河支行','A2013121606303500',NULL,1,1,NULL,3),(2,'2013-12-15 16:00:00',2,8,NULL,-1,2,'2013-12-15 16:00:00',-1,NULL,'3602 0001 0900 0090 759',1,'工行广州市第一支行','A2013121606311102',NULL,1,1,NULL,3),(3,'2013-12-15 16:00:00',2,8,NULL,-1,2,'2013-12-15 16:00:00',-1,NULL,'3602 0001 0900 0090 759',2,'工行广州市第一支行','A2013121606320004',NULL,1,1,NULL,3),(4,'2013-12-15 16:00:00',2,8,NULL,-1,2,'2013-12-15 16:00:00',-1,NULL,'4411 6251 8018 0010 5458 9',3,'交行广州白云支行','A2013121606323106',NULL,1,1,NULL,3),(5,'2013-12-15 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:11',-1,NULL,'4405 8301 0400 0419 7',4,'农行广州市体育西路支行','A2013121606325808',NULL,1,1,NULL,3),(6,'2013-12-25 16:00:00',23,2,NULL,1,22,'2014-01-13 16:00:00',1,NULL,'802101000000196710',0,'佛山高明顺银村镇银行','A2013122610265410',NULL,1,1,NULL,3),(7,'2013-12-25 16:00:00',23,2,-1,-1,NULL,'2014-02-21 07:31:11',1,NULL,'2013028119200076882',0,'工商银行佛山高明支行营业部','A2013122610364312',NULL,1,1,NULL,3),(8,'2013-12-25 16:00:00',23,2,-1,1,NULL,'2014-02-21 07:31:11',1,NULL,'2013028119200076909',0,'工商银行佛山高明支行营业部','A2013122611522914',NULL,1,1,NULL,3),(9,'2013-12-25 16:00:00',23,2,-1,1,NULL,'2014-02-21 07:31:11',1,NULL,'44453101040016220',0,'农业银行佛山高明支行','A2013122611561816',NULL,1,1,NULL,3),(10,'2013-12-25 16:00:00',23,2,-1,1,NULL,'2014-02-21 07:31:11',1,NULL,'44001667450053008898',0,'建设银行佛山高明支行','A2013122611573118',NULL,1,1,NULL,3),(11,'2013-12-25 16:00:00',23,2,-1,1,NULL,'2014-02-21 07:31:11',1,NULL,'650961278864',0,'中国银行佛山高明支行营业部','A2013122600021120',NULL,1,1,NULL,3),(12,'2013-12-25 16:00:00',23,2,-1,1,NULL,'2014-02-21 07:31:11',1,NULL,'104201516010001881',0,'广发银行佛山高明支行营业部','A2013122600025022',NULL,1,1,NULL,3),(13,'2013-12-25 16:00:00',23,2,-1,1,NULL,'2014-02-21 07:31:11',1,NULL,'393060100100119527',0,'兴业银行佛山高明支行','A2013122600035924',NULL,1,1,NULL,3),(14,'2013-12-25 16:00:00',23,2,-1,1,NULL,'2014-02-21 07:31:11',1,NULL,'11014516836003',0,'平安银行佛山高明支行','A2013122600050426',NULL,1,1,NULL,3),(15,'2013-12-25 16:00:00',23,2,-1,1,NULL,'2014-02-21 07:31:11',1,NULL,'89030000000003186',0,'高明农村商业银行营业部','A2013122600060428',NULL,1,1,NULL,3),(16,'2013-12-25 16:00:00',23,2,NULL,1,23,'2013-12-25 16:00:00',1,NULL,'2013028219200062382',0,'工商银行佛山高明文华支行','A2013122600073230',NULL,1,1,NULL,3);
/*!40000 ALTER TABLE `ts_account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_attachment`
--

DROP TABLE IF EXISTS `ts_attachment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `atype` int(11) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `filePath` varchar(255) DEFAULT NULL,
  `fileSize` double DEFAULT NULL,
  `formId` varchar(50) DEFAULT NULL,
  `formId2` varchar(255) DEFAULT NULL,
  `formType` int(11) DEFAULT NULL,
  `swfPath` varchar(255) DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_attachment`
--

LOCK TABLES `ts_attachment` WRITE;
/*!40000 ALTER TABLE `ts_attachment` DISABLE KEYS */;
INSERT INTO `ts_attachment` VALUES (118,'2013-12-24 05:04:42',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2013/12/1893035926649224.zip',4848,'5',NULL,1,NULL,3),(119,'2013-12-25 04:04:27',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2013/12/1975820346119472.zip',4848,'6',NULL,1,NULL,3),(120,'2013-12-25 04:07:32',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2013/12/1976005258129717.zip',4848,'7',NULL,1,NULL,3),(121,'2013-12-25 04:11:18',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2013/12/1976231066135105.zip',4848,'8',NULL,1,NULL,3),(122,'2013-12-25 04:13:03',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2013/12/1976336555078076.zip',4848,'9',NULL,1,NULL,3),(123,'2013-12-25 04:15:07',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2013/12/1976459858968473.zip',4848,'10',NULL,1,NULL,3),(124,'2014-01-13 01:58:52',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2014/1/3609905599374460.zip',5938,'10',NULL,1,NULL,3),(125,'2014-01-13 02:00:52',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2014/1/3610025838669140.zip',5938,'9',NULL,1,NULL,3),(126,'2014-01-13 02:03:05',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2014/1/3610158457288799.zip',5938,'8',NULL,1,NULL,3),(127,'2014-01-13 02:04:56',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2014/1/3610270053493461.zip',5938,'7',NULL,1,NULL,3),(128,'2014-01-13 02:06:22',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2014/1/3610355515733220.zip',5938,'6',NULL,1,NULL,3),(129,'2014-01-13 02:08:15',2,8,NULL,1,NULL,'2014-02-21 07:31:11',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2014/1/3610468355337030.zip',5938,'5',NULL,1,NULL,3),(130,'2014-02-25 03:00:12',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,2,'QQ图片20140225093012.jpg','WEB-INF/attachments/customerinfo_dir/2014/2/7999452462899.jpg',125689,'2',NULL,18,NULL,3),(131,'2014-02-25 03:00:38',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,2,'QQ图片20140225093012.jpg','WEB-INF/attachments/customerinfo_dir/2014/2/8025102678787.jpg',125689,'1',NULL,28,NULL,3),(132,'2014-02-25 03:04:53',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,2,'QQ图片20140224200841.jpg','WEB-INF/attachments/ecustomerinfo_dir/2014/2/8279909250181.jpg',119639,'1393297285851',NULL,-1,NULL,3),(133,'2014-03-06 03:40:25',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2014/3/8222375439238.zip',4848,'11',NULL,1,NULL,3),(134,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,3,'bingxin.zip','WEB-INF/attachments/process_dir/2014/3/8474592277076.zip',43718,'10',NULL,1,NULL,3),(135,'2014-03-10 14:07:08',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,3,'History.zip','WEB-INF/attachments/process_dir/2014/3/1394460427815987000.zip',4848,'11',NULL,1,NULL,3),(136,'2014-03-15 03:22:33',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,1,'计算表.xlsx','WEB-INF/attachments/mort_dir/2014/3/5962512326828.xlsx',273958,'9',NULL,8,NULL,3);
/*!40000 ALTER TABLE `ts_attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_auditrecords`
--

DROP TABLE IF EXISTS `ts_auditrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_auditrecords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `approval` varchar(255) DEFAULT NULL,
  `bussNodeId` double DEFAULT NULL,
  `notifyStartor` int(11) DEFAULT NULL,
  `notifys` varchar(10) DEFAULT NULL,
  `procId` varchar(20) DEFAULT NULL,
  `recordType` int(11) DEFAULT NULL,
  `result` varchar(60) DEFAULT NULL,
  `tiid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=527 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_auditrecords`
--

LOCK TABLES `ts_auditrecords` WRITE;
/*!40000 ALTER TABLE `ts_auditrecords` DISABLE KEYS */;
INSERT INTO `ts_auditrecords` VALUES (502,'2014-03-12 10:36:31',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'同意',232,2,NULL,'201',0,'贷款结束','204'),(503,'2014-03-12 10:49:40',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'通过',1,2,NULL,'206',0,'提交风控审批',NULL),(504,'2014-03-12 13:06:49',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'11',1,2,NULL,'301',0,'提交风控审批',NULL),(505,'2014-03-14 03:17:53',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'11',1,2,NULL,'401',0,'提交风控审批',NULL),(506,'2014-03-14 05:50:14',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'111',1,2,NULL,'501',0,'提交风控审批',NULL),(507,'2014-03-14 05:52:03',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'通过',232,2,NULL,'501',0,'贷款结束','504'),(508,'2014-03-14 07:03:55',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'同意',1,2,NULL,'506',0,'提交风控审批',NULL),(509,'2014-03-14 07:13:37',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'11',232,2,NULL,'506',0,'贷款结束','509'),(510,'2014-03-15 03:21:07',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'通过',1,2,NULL,'601',0,'提交风控审批',NULL),(511,'2014-03-17 02:19:35',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'通过',1,2,NULL,'701',0,'提交风控审批',NULL),(512,'2014-03-17 03:11:24',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'通过',232,2,NULL,'701',0,'贷款结束','704'),(513,'2014-03-17 03:15:19',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'11',1,2,NULL,'706',0,'提交风控审批',NULL),(514,'2014-03-17 03:17:06',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'同意',232,2,NULL,'706',0,'贷款结束','709'),(515,'2014-03-17 04:34:06',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'333',1,2,NULL,'801',0,'提交风控审批',NULL),(516,'2014-03-17 04:35:57',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'通过',232,2,NULL,'801',0,'贷款结束','804'),(517,'2014-03-17 09:56:20',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'3',1,2,NULL,'901',0,'提交风控审批',NULL),(518,'2014-03-17 10:26:48',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'通过',1,2,NULL,'905',0,'提交风控审批',NULL),(519,'2014-03-17 10:30:35',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'同意',232,2,NULL,'905',0,'贷款结束','908'),(520,'2014-03-17 12:17:34',9,3,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'11',1,2,NULL,'1001',0,'提交风控审批',NULL),(521,'2014-04-02 08:10:29',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'ok',1,2,NULL,'1101',0,'提交风控审批',NULL),(522,'2014-04-02 08:18:00',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'ok',1,2,NULL,'1105',0,'提交风控审批',NULL),(523,'2014-04-02 08:21:31',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'ok',1,2,NULL,'1109',0,'提交风控审批',NULL),(524,'2014-04-02 08:27:14',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'ok',232,2,NULL,'1109',0,'贷款结束','1112'),(525,'2014-04-02 08:48:55',24,1,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'ok',1,2,NULL,'1114',0,'提交风控审批',NULL),(526,'2014-04-02 08:51:14',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'ok',232,2,NULL,'1114',0,'贷款结束','1117');
/*!40000 ALTER TABLE `ts_auditrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_busscode`
--

DROP TABLE IF EXISTS `ts_busscode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_busscode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `express` varchar(255) DEFAULT NULL,
  `funnames` varchar(200) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `recode` varchar(50) DEFAULT NULL,
  `sysid` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_busscode`
--

LOCK TABLES `ts_busscode` WRITE;
/*!40000 ALTER TABLE `ts_busscode` DISABLE KEYS */;
/*!40000 ALTER TABLE `ts_busscode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_bussnode`
--

DROP TABLE IF EXISTS `ts_bussnode`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_bussnode` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `formId` double DEFAULT NULL,
  `inType` int(11) DEFAULT NULL,
  `isCfg` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `nodeId` varchar(50) DEFAULT NULL,
  `nodeType` varchar(50) DEFAULT NULL,
  `pdid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=234 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_bussnode`
--

LOCK TABLES `ts_bussnode` WRITE;
/*!40000 ALTER TABLE `ts_bussnode` DISABLE KEYS */;
INSERT INTO `ts_bussnode` VALUES (1,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'Start','startevent1','startEvent','process1:1:4'),(2,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'风险控制部','usertask1','userTask','process1:1:4'),(3,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'总经理助理','usertask2','userTask','process1:1:4'),(4,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'总经理审批','usertask3','userTask','process1:1:4'),(5,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'董事长审批','usertask6','userTask','process1:1:4'),(6,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'出纳放款','usertask8','userTask','process1:1:4'),(7,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'贷后监控','usertask9','userTask','process1:1:4'),(8,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'客户经理','usertask11','userTask','process1:1:4'),(9,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'客户经理签合同','usertask12','userTask','process1:1:4'),(10,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'会计检查','usertask14','userTask','process1:1:4'),(11,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'流程终止','servicetask1','serviceTask','process1:1:4'),(12,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'财务经理审批','usertask15','userTask','process1:1:4'),(13,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'End','endevent1','endEvent','process1:1:4'),(14,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'Start','startevent1','startEvent','process1:2:8'),(15,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'风险控制部','usertask1','userTask','process1:2:8'),(16,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'评审会','usertask3','userTask','process1:2:8'),(17,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'董事长审批','usertask6','userTask','process1:2:8'),(18,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'出纳放款','usertask8','userTask','process1:2:8'),(19,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'贷后监控','usertask9','userTask','process1:2:8'),(20,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'客户经理','usertask11','userTask','process1:2:8'),(21,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'客户经理签合同','usertask12','userTask','process1:2:8'),(22,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'财务经理审批','usertask13','userTask','process1:2:8'),(23,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'会计检查','usertask14','userTask','process1:2:8'),(24,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'流程终止','servicetask1','serviceTask','process1:2:8'),(25,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'End','endevent1','endEvent','process1:2:8'),(26,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'Start','startevent1','startEvent','process1:3:12'),(27,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'风险控制部','usertask1','userTask','process1:3:12'),(28,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'总经理助理','usertask2','userTask','process1:3:12'),(29,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'总经理审批','usertask3','userTask','process1:3:12'),(30,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'董事长审批','usertask6','userTask','process1:3:12'),(31,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'出纳放款','usertask8','userTask','process1:3:12'),(32,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'贷后监控','usertask9','userTask','process1:3:12'),(33,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'客户经理','usertask11','userTask','process1:3:12'),(34,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'客户经理签合同','usertask12','userTask','process1:3:12'),(35,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'会计检查','usertask14','userTask','process1:3:12'),(36,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'流程终止','servicetask1','serviceTask','process1:3:12'),(37,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'财务经理审批','usertask15','userTask','process1:3:12'),(38,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,1,1,'End','endevent1','endEvent','process1:3:12'),(39,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'Start','startevent1','startEvent','process1:4:16'),(40,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'风险控制部','usertask1','userTask','process1:4:16'),(41,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'评审会','usertask3','userTask','process1:4:16'),(42,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'董事长审批','usertask6','userTask','process1:4:16'),(43,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'出纳放款','usertask8','userTask','process1:4:16'),(44,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'贷后监控','usertask9','userTask','process1:4:16'),(45,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'客户经理','usertask11','userTask','process1:4:16'),(46,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'客户经理签合同','usertask12','userTask','process1:4:16'),(47,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'财务经理审批','usertask13','userTask','process1:4:16'),(48,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'会计检查','usertask14','userTask','process1:4:16'),(49,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'流程终止','servicetask1','serviceTask','process1:4:16'),(50,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,1,1,'End','endevent1','endEvent','process1:4:16'),(51,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'Start','startevent1','startEvent','gaoming_binxing:1:20'),(52,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'Parallel Gateway','parallelgateway1','parallelGateway','gaoming_binxing:1:20'),(53,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'风险控制部','usertask1','userTask','gaoming_binxing:1:20'),(54,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'财务部审批','usertask2','userTask','gaoming_binxing:1:20'),(55,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'总经理助理','usertask3','userTask','gaoming_binxing:1:20'),(56,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'副总经理审批','usertask4','userTask','gaoming_binxing:1:20'),(57,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'Parallel Gateway','parallelgateway2','parallelGateway','gaoming_binxing:1:20'),(58,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'总经理审批','usertask5','userTask','gaoming_binxing:1:20'),(59,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'董事长审批','usertask6','userTask','gaoming_binxing:1:20'),(60,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'贷后监控','usertask7','userTask','gaoming_binxing:1:20'),(61,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'财务放款','usertask9','userTask','gaoming_binxing:1:20'),(62,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'客户经理签合同','usertask10','userTask','gaoming_binxing:1:20'),(63,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'终止流程','servicetask1','serviceTask','gaoming_binxing:1:20'),(64,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'Parallel Gateway','parallelgateway3','parallelGateway','gaoming_binxing:1:20'),(65,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'客户经理','usertask11','userTask','gaoming_binxing:1:20'),(66,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'终止流程','servicetask2','serviceTask','gaoming_binxing:1:20'),(67,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'客户经理','usertask12','userTask','gaoming_binxing:1:20'),(68,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'End','endevent1','endEvent','gaoming_binxing:1:20'),(69,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'End','endevent2','endEvent','gaoming_binxing:1:20'),(70,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'Start','startevent1','startEvent','gaoming_binxing:2:36'),(71,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'Parallel Gateway','parallelgateway1','parallelGateway','gaoming_binxing:2:36'),(72,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'风险控制部','usertask1','userTask','gaoming_binxing:2:36'),(73,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'财务部审批','usertask2','userTask','gaoming_binxing:2:36'),(74,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'总经理助理','usertask3','userTask','gaoming_binxing:2:36'),(75,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'副总经理审批【财务】','usertask4','userTask','gaoming_binxing:2:36'),(76,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'总经理审批','usertask5','userTask','gaoming_binxing:2:36'),(77,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'董事长审批','usertask6','userTask','gaoming_binxing:2:36'),(78,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'贷后监控','usertask7','userTask','gaoming_binxing:2:36'),(79,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'财务放款','usertask9','userTask','gaoming_binxing:2:36'),(80,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'客户经理签合同','usertask10','userTask','gaoming_binxing:2:36'),(81,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'终止流程','servicetask1','serviceTask','gaoming_binxing:2:36'),(82,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'客户经理','usertask11','userTask','gaoming_binxing:2:36'),(83,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'终止流程','servicetask2','serviceTask','gaoming_binxing:2:36'),(84,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'财务经理审批','usertask12','userTask','gaoming_binxing:2:36'),(85,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'会计查账','usertask13','userTask','gaoming_binxing:2:36'),(86,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'End','endevent1','endEvent','gaoming_binxing:2:36'),(87,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'End','endevent2','endEvent','gaoming_binxing:2:36'),(88,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'Start','startevent1','startEvent','gaoming_binxing:3:104'),(89,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'Parallel Gateway','parallelgateway1','parallelGateway','gaoming_binxing:3:104'),(90,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'风险控制部','usertask1','userTask','gaoming_binxing:3:104'),(91,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'财务部审批','usertask2','userTask','gaoming_binxing:3:104'),(92,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'总经理助理','usertask3','userTask','gaoming_binxing:3:104'),(93,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'副总经理审批【财务】','usertask4','userTask','gaoming_binxing:3:104'),(94,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'总经理审批','usertask5','userTask','gaoming_binxing:3:104'),(95,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'董事长审批','usertask6','userTask','gaoming_binxing:3:104'),(96,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'贷后监控','usertask7','userTask','gaoming_binxing:3:104'),(97,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'财务放款','usertask9','userTask','gaoming_binxing:3:104'),(98,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'客户经理签合同','usertask10','userTask','gaoming_binxing:3:104'),(99,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'终止流程','servicetask1','serviceTask','gaoming_binxing:3:104'),(100,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'客户经理','usertask11','userTask','gaoming_binxing:3:104'),(101,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'终止流程','servicetask2','serviceTask','gaoming_binxing:3:104'),(102,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'财务经理审批','usertask12','userTask','gaoming_binxing:3:104'),(103,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'会计查账','usertask13','userTask','gaoming_binxing:3:104'),(104,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'End','endevent1','endEvent','gaoming_binxing:3:104'),(105,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,3,1,1,'End','endevent2','endEvent','gaoming_binxing:3:104'),(106,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'Start','startevent1','startEvent','process1:5:113'),(107,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'风控审核','usertask1','userTask','process1:5:113'),(108,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'总经理助理','usertask2','userTask','process1:5:113'),(109,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'总经理','usertask3','userTask','process1:5:113'),(110,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'董事长','usertask4','userTask','process1:5:113'),(111,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'客户经理签协议','usertask5','userTask','process1:5:113'),(112,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'流程结束','servicetask1','serviceTask','process1:5:113'),(113,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'客户经理','usertask6','userTask','process1:5:113'),(114,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'End','endevent1','endEvent','process1:5:113'),(115,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'Start','startevent1','startEvent','minghui_extension_process1:1:117'),(116,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'风控审核','usertask1','userTask','minghui_extension_process1:1:117'),(117,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'贷审会审批','usertask3','userTask','minghui_extension_process1:1:117'),(118,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'董事长','usertask4','userTask','minghui_extension_process1:1:117'),(119,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'客户经理签协议','usertask5','userTask','minghui_extension_process1:1:117'),(120,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'终止流程','servicetask1','serviceTask','minghui_extension_process1:1:117'),(121,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'客户经理','usertask6','userTask','minghui_extension_process1:1:117'),(122,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,2,2,1,'End','endevent1','endEvent','minghui_extension_process1:1:117'),(123,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,2,1,'Start','startevent1','startEvent','process1:6:121'),(124,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,2,1,'风控审核','usertask1','userTask','process1:6:121'),(125,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,2,1,'总经理助理','usertask2','userTask','process1:6:121'),(126,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,2,1,'总经理','usertask3','userTask','process1:6:121'),(127,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,2,1,'董事长','usertask4','userTask','process1:6:121'),(128,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,2,1,'客户经理签协议','usertask5','userTask','process1:6:121'),(129,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,2,1,'流程结束','servicetask1','serviceTask','process1:6:121'),(130,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,2,1,'客户经理','usertask6','userTask','process1:6:121'),(131,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,1,2,1,'End','endevent1','endEvent','process1:6:121'),(132,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'Start','startevent1','startEvent','gaoming_binxing:4:1534'),(133,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'Parallel Gateway','parallelgateway1','parallelGateway','gaoming_binxing:4:1534'),(134,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'风险控制部','usertask1','userTask','gaoming_binxing:4:1534'),(135,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'财务部审批','usertask2','userTask','gaoming_binxing:4:1534'),(136,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'总经理助理','usertask3','userTask','gaoming_binxing:4:1534'),(137,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'副总经理审批【财务】','usertask4','userTask','gaoming_binxing:4:1534'),(138,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'总经理审批','usertask5','userTask','gaoming_binxing:4:1534'),(139,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'董事长审批','usertask6','userTask','gaoming_binxing:4:1534'),(140,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'贷后监控','usertask7','userTask','gaoming_binxing:4:1534'),(141,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'财务放款','usertask9','userTask','gaoming_binxing:4:1534'),(142,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'客户经理签合同','usertask10','userTask','gaoming_binxing:4:1534'),(143,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'终止流程','servicetask1','serviceTask','gaoming_binxing:4:1534'),(144,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'客户经理','usertask11','userTask','gaoming_binxing:4:1534'),(145,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'终止流程','servicetask2','serviceTask','gaoming_binxing:4:1534'),(146,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'财务经理审批','usertask12','userTask','gaoming_binxing:4:1534'),(147,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'会计查账','usertask13','userTask','gaoming_binxing:4:1534'),(148,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'End','endevent1','endEvent','gaoming_binxing:4:1534'),(149,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'End','endevent2','endEvent','gaoming_binxing:4:1534'),(150,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'Start','startevent1','startEvent','gaoming_binxing:5:1538'),(151,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'Parallel Gateway','parallelgateway1','parallelGateway','gaoming_binxing:5:1538'),(152,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'风险控制部','usertask1','userTask','gaoming_binxing:5:1538'),(153,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'财务部审批','usertask2','userTask','gaoming_binxing:5:1538'),(154,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'总经理助理','usertask3','userTask','gaoming_binxing:5:1538'),(155,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'副总经理审批【财务】','usertask4','userTask','gaoming_binxing:5:1538'),(156,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'总经理审批','usertask5','userTask','gaoming_binxing:5:1538'),(157,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'董事长审批','usertask6','userTask','gaoming_binxing:5:1538'),(158,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'贷后监控','usertask7','userTask','gaoming_binxing:5:1538'),(159,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'财务放款','usertask9','userTask','gaoming_binxing:5:1538'),(160,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'客户经理签合同','usertask10','userTask','gaoming_binxing:5:1538'),(161,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'终止流程','servicetask1','serviceTask','gaoming_binxing:5:1538'),(162,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'客户经理','usertask11','userTask','gaoming_binxing:5:1538'),(163,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'终止流程','servicetask2','serviceTask','gaoming_binxing:5:1538'),(164,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'财务经理审批','usertask12','userTask','gaoming_binxing:5:1538'),(165,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'会计查账','usertask13','userTask','gaoming_binxing:5:1538'),(166,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'End','endevent1','endEvent','gaoming_binxing:5:1538'),(167,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,4,1,1,'End','endevent2','endEvent','gaoming_binxing:5:1538'),(168,'2013-12-24 05:04:42',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,5,1,1,'Start','startevent1','startEvent','process1:7:3104'),(169,'2013-12-24 05:04:42',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,5,1,1,'放款','usertask1','userTask','process1:7:3104'),(170,'2013-12-24 05:04:42',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,5,1,1,'End','endevent1','endEvent','process1:7:3104'),(171,'2013-12-25 04:04:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,6,1,1,'Start','startevent1','startEvent','process1:8:3108'),(172,'2013-12-25 04:04:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,6,1,1,'放款','usertask1','userTask','process1:8:3108'),(173,'2013-12-25 04:04:27',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,6,1,1,'End','endevent1','endEvent','process1:8:3108'),(174,'2013-12-25 04:07:32',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,7,1,1,'Start','startevent1','startEvent','process1:9:3112'),(175,'2013-12-25 04:07:32',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,7,1,1,'放款','usertask1','userTask','process1:9:3112'),(176,'2013-12-25 04:07:32',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,7,1,1,'End','endevent1','endEvent','process1:9:3112'),(177,'2013-12-25 04:11:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,8,1,1,'Start','startevent1','startEvent','process1:10:3116'),(178,'2013-12-25 04:11:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,8,1,1,'放款','usertask1','userTask','process1:10:3116'),(179,'2013-12-25 04:11:18',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,8,1,1,'End','endevent1','endEvent','process1:10:3116'),(180,'2013-12-25 04:13:03',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,9,1,1,'Start','startevent1','startEvent','process1:11:3120'),(181,'2013-12-25 04:13:03',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,9,1,1,'放款','usertask1','userTask','process1:11:3120'),(182,'2013-12-25 04:13:03',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,9,1,1,'End','endevent1','endEvent','process1:11:3120'),(183,'2013-12-25 04:15:07',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,10,1,1,'Start','startevent1','startEvent','process1:12:3124'),(184,'2013-12-25 04:15:07',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,10,1,1,'放款','usertask1','userTask','process1:12:3124'),(185,'2013-12-25 04:15:07',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,10,1,1,'End','endevent1','endEvent','process1:12:3124'),(186,'2014-01-13 01:58:52',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,10,1,1,'Start','startevent1','startEvent','process1:13:3304'),(187,'2014-01-13 01:58:52',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,10,1,1,'财务经理审批','usertask1','userTask','process1:13:3304'),(188,'2014-01-13 01:58:52',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,10,1,1,'放款','usertask2','userTask','process1:13:3304'),(189,'2014-01-13 01:58:52',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,10,1,1,'End','endevent1','endEvent','process1:13:3304'),(190,'2014-01-13 02:00:52',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,9,1,1,'Start','startevent1','startEvent','process1:14:3308'),(191,'2014-01-13 02:00:52',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,9,1,1,'财务经理审批','usertask1','userTask','process1:14:3308'),(192,'2014-01-13 02:00:52',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,9,1,1,'放款','usertask2','userTask','process1:14:3308'),(193,'2014-01-13 02:00:52',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,9,1,1,'End','endevent1','endEvent','process1:14:3308'),(194,'2014-01-13 02:03:05',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,8,1,1,'Start','startevent1','startEvent','process1:15:3312'),(195,'2014-01-13 02:03:05',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,8,1,1,'财务经理审批','usertask1','userTask','process1:15:3312'),(196,'2014-01-13 02:03:05',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,8,1,1,'放款','usertask2','userTask','process1:15:3312'),(197,'2014-01-13 02:03:05',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,8,1,1,'End','endevent1','endEvent','process1:15:3312'),(198,'2014-01-13 02:04:56',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,7,1,1,'Start','startevent1','startEvent','process1:16:3316'),(199,'2014-01-13 02:04:56',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,7,1,1,'财务经理审批','usertask1','userTask','process1:16:3316'),(200,'2014-01-13 02:04:56',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,7,1,1,'放款','usertask2','userTask','process1:16:3316'),(201,'2014-01-13 02:04:56',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,7,1,1,'End','endevent1','endEvent','process1:16:3316'),(202,'2014-01-13 02:06:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,6,1,1,'Start','startevent1','startEvent','process1:17:3320'),(203,'2014-01-13 02:06:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,6,1,1,'财务经理审批','usertask1','userTask','process1:17:3320'),(204,'2014-01-13 02:06:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,6,1,1,'放款','usertask2','userTask','process1:17:3320'),(205,'2014-01-13 02:06:22',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,6,1,1,'End','endevent1','endEvent','process1:17:3320'),(206,'2014-01-13 02:08:15',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,5,1,1,'Start','startevent1','startEvent','process1:18:3324'),(207,'2014-01-13 02:08:15',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,5,1,1,'财务经理审批','usertask1','userTask','process1:18:3324'),(208,'2014-01-13 02:08:15',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,5,1,1,'放款','usertask2','userTask','process1:18:3324'),(209,'2014-01-13 02:08:15',2,8,NULL,1,NULL,'2014-02-21 07:31:12',-1,NULL,5,1,1,'End','endevent1','endEvent','process1:18:3324'),(210,'2014-03-06 03:40:25',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,11,1,1,'Start','startevent1','startEvent','process1:1:4'),(211,'2014-03-06 03:40:25',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,11,1,1,'放款','usertask1','userTask','process1:1:4'),(212,'2014-03-06 03:40:25',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,11,1,1,'End','endevent1','endEvent','process1:1:4'),(213,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'Start','startevent1','startEvent','gaoming_binxing:1:8'),(214,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'Parallel Gateway','parallelgateway1','parallelGateway','gaoming_binxing:1:8'),(215,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'风险控制部','usertask1','userTask','gaoming_binxing:1:8'),(216,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'财务部审批','usertask2','userTask','gaoming_binxing:1:8'),(217,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'总经理助理','usertask3','userTask','gaoming_binxing:1:8'),(218,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'副总经理审批【财务】','usertask4','userTask','gaoming_binxing:1:8'),(219,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'总经理审批','usertask5','userTask','gaoming_binxing:1:8'),(220,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'董事长审批','usertask6','userTask','gaoming_binxing:1:8'),(221,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'贷后监控','usertask7','userTask','gaoming_binxing:1:8'),(222,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'财务放款','usertask9','userTask','gaoming_binxing:1:8'),(223,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'客户经理签合同','usertask10','userTask','gaoming_binxing:1:8'),(224,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'终止流程','servicetask1','serviceTask','gaoming_binxing:1:8'),(225,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'客户经理','usertask11','userTask','gaoming_binxing:1:8'),(226,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'终止流程','servicetask2','serviceTask','gaoming_binxing:1:8'),(227,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'财务经理审批','usertask12','userTask','gaoming_binxing:1:8'),(228,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'会计查账','usertask13','userTask','gaoming_binxing:1:8'),(229,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'End','endevent1','endEvent','gaoming_binxing:1:8'),(230,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,10,1,1,'End','endevent2','endEvent','gaoming_binxing:1:8'),(231,'2014-03-10 14:07:08',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,11,1,1,'Start','startevent1','startEvent','process1:1:4'),(232,'2014-03-10 14:07:08',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,11,1,1,'放款','usertask1','userTask','process1:1:4'),(233,'2014-03-10 14:07:08',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,11,1,1,'End','endevent1','endEvent','process1:1:4');
/*!40000 ALTER TABLE `ts_bussnode` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_bussprocc`
--

DROP TABLE IF EXISTS `ts_bussprocc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_bussprocc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `bussType` int(11) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `companyIds` varchar(80) DEFAULT NULL,
  `companyNames` varchar(200) DEFAULT NULL,
  `formUrl` varchar(150) DEFAULT NULL,
  `icon` varchar(150) DEFAULT NULL,
  `menuId` double DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `pdid` varchar(50) DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  `txtPath` varchar(200) DEFAULT NULL,
  `useorg` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_bussprocc`
--

LOCK TABLES `ts_bussprocc` WRITE;
/*!40000 ALTER TABLE `ts_bussprocc` DISABLE KEYS */;
INSERT INTO `ts_bussprocc` VALUES (1,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 11:58:27',-1,NULL,0,'B2013121607533800',NULL,NULL,'pages/app/finance/bloan/extension/ExtensionApply.js','images/big_icons/48x48/apps/loan.png',195,'普通展期流程','process1:6:121',3,NULL,0),(2,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-16 11:58:08',-1,NULL,0,'B2013121607543902',NULL,NULL,'pages/app/finance/bloan/extension/ExtensionApply.js','images/big_icons/48x48/apps/launchpad.png',195,'500万展期流程','minghui_extension_process1:1:117',3,NULL,0);
/*!40000 ALTER TABLE `ts_bussprocc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_busstrans`
--

DROP TABLE IF EXISTS `ts_busstrans`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_busstrans` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `bnodeId` double DEFAULT NULL,
  `enodeId` double DEFAULT NULL,
  `exparams` varchar(200) DEFAULT NULL,
  `express` varchar(200) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=339 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_busstrans`
--

LOCK TABLES `ts_busstrans` WRITE;
/*!40000 ALTER TABLE `ts_busstrans` DISABLE KEYS */;
INSERT INTO `ts_busstrans` VALUES (1,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,1,2,NULL,NULL,'提交风控审批'),(2,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,2,3,NULL,NULL,'总助审批'),(3,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,2,8,NULL,NULL,'退回客户经理'),(4,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,2,11,NULL,NULL,'审批不通过，终止流程'),(5,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,3,4,NULL,NULL,'总经理审批'),(6,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,3,2,NULL,NULL,'退回'),(7,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,4,5,NULL,NULL,'董事长签字'),(8,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,4,11,NULL,NULL,'项目终止'),(9,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,4,8,NULL,NULL,'退回客户经理'),(10,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,4,3,NULL,NULL,'退回'),(11,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,5,4,NULL,NULL,'退回'),(12,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,5,11,NULL,NULL,'项目终止'),(13,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,5,9,NULL,NULL,'客户经理签合同'),(14,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,6,10,NULL,NULL,'会计查账'),(15,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,7,13,NULL,NULL,'贷款结束'),(16,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,8,2,NULL,NULL,'重新提交风控审批'),(17,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,8,11,NULL,NULL,'审批不通过，终止流程'),(18,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,9,12,NULL,NULL,'提交财务'),(19,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,10,7,NULL,NULL,'贷后监控'),(20,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,11,13,NULL,NULL,'审批不通过，流程结束'),(21,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,12,6,NULL,NULL,'放款'),(22,'2013-12-16 07:31:53',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,12,9,NULL,NULL,'退回客户经理'),(23,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,14,15,NULL,NULL,'提交风控审批'),(24,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,15,20,NULL,NULL,'退回客户经理'),(25,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,15,24,NULL,NULL,'审批不通过，终止流程'),(26,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,15,16,NULL,NULL,'上会'),(27,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,16,17,NULL,NULL,'董事长签字'),(28,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,16,24,NULL,NULL,'项目终止'),(29,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,16,20,NULL,NULL,'退回客户经理'),(30,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,16,15,NULL,NULL,'退回风险部'),(31,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,17,16,NULL,NULL,'退回'),(32,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,17,24,NULL,NULL,'项目终止'),(33,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,17,21,NULL,NULL,'客户经理签合同'),(34,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,18,23,NULL,NULL,'会计查账'),(35,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,19,25,NULL,NULL,'贷款结束'),(36,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,20,15,NULL,NULL,'重新提交风控审批'),(37,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,20,24,NULL,NULL,'审批不通过，终止流程'),(38,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,21,22,NULL,NULL,'提交财务'),(39,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,22,18,NULL,NULL,'放款'),(40,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,22,21,NULL,NULL,'退回客户经理'),(41,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,23,19,NULL,NULL,'贷后监控'),(42,'2013-12-16 07:32:11',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,24,25,NULL,NULL,'审批不通过，流程结束'),(43,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,26,27,NULL,NULL,'提交风控审批'),(44,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,27,28,NULL,NULL,'总助审批'),(45,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,27,33,NULL,NULL,'退回客户经理'),(46,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,27,36,NULL,NULL,'审批不通过，终止流程'),(47,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,28,29,NULL,NULL,'总经理审批'),(48,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,28,27,NULL,NULL,'退回'),(49,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,29,30,NULL,NULL,'董事长签字'),(50,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,29,36,NULL,NULL,'项目终止'),(51,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,29,33,NULL,NULL,'退回客户经理'),(52,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,29,28,NULL,NULL,'退回总助'),(53,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,30,29,NULL,NULL,'退回总经理'),(54,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,30,36,NULL,NULL,'项目终止'),(55,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,30,34,NULL,NULL,'客户经理签合同'),(56,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,31,35,NULL,NULL,'会计查账'),(57,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,32,38,NULL,NULL,'贷款结束'),(58,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,33,27,NULL,NULL,'重新提交风控审批'),(59,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,33,36,NULL,NULL,'审批不通过，终止流程'),(60,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,34,37,NULL,NULL,'提交财务'),(61,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,35,32,NULL,NULL,'贷后监控'),(62,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,36,38,NULL,NULL,'审批不通过，流程结束'),(63,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,37,31,NULL,NULL,'放款'),(64,'2013-12-16 08:20:40',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,37,34,NULL,NULL,'退回客户经理'),(65,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,39,40,NULL,NULL,'提交风控审批'),(66,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,40,45,NULL,NULL,'退回客户经理'),(67,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,40,49,NULL,NULL,'审批不通过，终止流程'),(68,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,40,41,NULL,NULL,'上会'),(69,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,41,42,NULL,NULL,'董事长签字'),(70,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,41,49,NULL,NULL,'项目终止'),(71,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,41,45,NULL,NULL,'退回客户经理'),(72,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,41,40,NULL,NULL,'退回风险部'),(73,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,42,41,NULL,NULL,'退回'),(74,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,42,49,NULL,NULL,'项目终止'),(75,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,42,46,NULL,NULL,'客户经理签合同'),(76,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,43,48,NULL,NULL,'会计查账'),(77,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,44,50,NULL,NULL,'贷款结束'),(78,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,45,40,NULL,NULL,'重新提交风控审批'),(79,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,45,49,NULL,NULL,'审批不通过，终止流程'),(80,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,46,47,NULL,NULL,'提交财务'),(81,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,47,43,NULL,NULL,'放款'),(82,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,47,46,NULL,NULL,'退回客户经理'),(83,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,48,44,NULL,NULL,'贷后监控'),(84,'2013-12-16 08:21:09',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,49,50,NULL,NULL,'审批不通过，流程结束'),(85,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,51,52,NULL,NULL,'提交贷款申请'),(86,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,52,53,NULL,NULL,'风控审批'),(87,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,52,54,NULL,NULL,'财务审批'),(88,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,53,55,NULL,NULL,'提交助理'),(89,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,53,65,NULL,NULL,'退回客户经理'),(90,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,54,56,NULL,NULL,'提交副总'),(91,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,54,65,NULL,NULL,'退回客户经理'),(92,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,55,57,NULL,NULL,'同意'),(93,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,55,53,NULL,NULL,'退回'),(94,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,56,57,NULL,NULL,'同意'),(95,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,56,54,NULL,NULL,'退回'),(96,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,57,58,NULL,NULL,'提交总经理'),(97,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,58,59,NULL,NULL,'提交董事长'),(98,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,58,64,NULL,NULL,'退回'),(99,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,58,67,NULL,NULL,'退回客户经理'),(100,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,59,62,NULL,NULL,'签合同'),(101,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,59,58,NULL,NULL,'退回'),(102,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,59,67,NULL,NULL,'退回客户经理'),(103,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,60,68,NULL,NULL,'审批通过，流程结束'),(104,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,61,60,NULL,NULL,'进入贷后'),(105,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,61,67,NULL,NULL,'退回客户经理'),(106,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,62,61,NULL,NULL,'提交财务放款'),(107,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,63,68,NULL,NULL,'审批不通过结束'),(108,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,64,55,NULL,NULL,'退回助理'),(109,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,64,56,NULL,NULL,'退回副总'),(110,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,65,66,NULL,NULL,'审批不通过，终止流程'),(111,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,65,53,NULL,NULL,'重新提交风控'),(112,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,65,54,NULL,NULL,'重新提交财务'),(113,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,66,69,NULL,NULL,'审批不通过结束'),(114,'2013-12-16 09:14:16',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,67,63,NULL,NULL,'审批不通过，终止流程'),(115,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,70,71,NULL,NULL,'提交贷款申请'),(116,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,71,72,NULL,NULL,'风控审批'),(117,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,71,73,NULL,NULL,'财务审批'),(118,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,72,74,NULL,NULL,'提交助理'),(119,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,72,82,NULL,NULL,'退回客户经理'),(120,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,73,75,NULL,NULL,'提交副总'),(121,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,73,82,NULL,NULL,'退回客户经理'),(122,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,74,72,NULL,NULL,'重新提交风控审批'),(123,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,74,76,NULL,NULL,'同意'),(124,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,75,73,NULL,NULL,'退回'),(125,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,75,76,NULL,NULL,'同意'),(126,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,76,77,NULL,NULL,'提交董事长'),(127,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,76,75,NULL,NULL,'退回财务副总经理'),(128,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,76,74,NULL,NULL,'退回总经理助理'),(129,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,76,81,NULL,NULL,''),(130,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,77,80,NULL,NULL,'签合同'),(131,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,77,76,NULL,NULL,'退回总经理'),(132,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,77,81,NULL,NULL,''),(133,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,78,86,NULL,NULL,'审批通过，流程结束'),(134,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,79,85,NULL,NULL,'会计查账'),(135,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,80,84,NULL,NULL,'提交财务经理审批'),(136,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,81,86,NULL,NULL,'审批不通过结束'),(137,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,82,83,NULL,NULL,'审批不通过，终止流程'),(138,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,82,72,NULL,NULL,'重新提交风控'),(139,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,82,73,NULL,NULL,'重新提交财务'),(140,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,83,87,NULL,NULL,'审批不通过结束'),(141,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,84,79,NULL,NULL,'放款'),(142,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,84,80,NULL,NULL,'退回客户经理'),(143,'2013-12-16 09:23:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,85,78,NULL,NULL,'货后监控'),(144,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,88,89,NULL,NULL,'提交贷款申请'),(145,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,89,90,NULL,NULL,'风控审批'),(146,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,89,91,NULL,NULL,'财务审批'),(147,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,90,92,NULL,NULL,'提交助理'),(148,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,90,100,NULL,NULL,'退回客户经理'),(149,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,91,93,NULL,NULL,'提交副总'),(150,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,91,100,NULL,NULL,'退回客户经理'),(151,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,92,90,NULL,NULL,'重新提交风控审批'),(152,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,92,94,NULL,NULL,'同意'),(153,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,93,91,NULL,NULL,'退回'),(154,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,93,94,NULL,NULL,'同意'),(155,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,94,95,NULL,NULL,'提交董事长'),(156,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,94,93,NULL,NULL,'退回财务副总经理'),(157,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,94,92,NULL,NULL,'退回总经理助理'),(158,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,94,99,NULL,NULL,'项目终止'),(159,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,95,98,NULL,NULL,'签合同'),(160,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,95,94,NULL,NULL,'退回总经理'),(161,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,95,99,NULL,NULL,'项目终止'),(162,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,96,104,NULL,NULL,'审批通过，流程结束'),(163,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,97,103,NULL,NULL,'会计查账'),(164,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,98,102,NULL,NULL,'提交财务经理审批'),(165,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,99,104,NULL,NULL,'审批不通过结束'),(166,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,100,101,NULL,NULL,'审批不通过，终止流程'),(167,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,100,90,NULL,NULL,'重新提交风控'),(168,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,100,91,NULL,NULL,'重新提交财务'),(169,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,101,105,NULL,NULL,'审批不通过结束'),(170,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,102,97,NULL,NULL,'放款'),(171,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,102,98,NULL,NULL,'退回客户经理'),(172,'2013-12-16 10:07:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,103,96,NULL,NULL,'货后监控'),(173,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,106,107,NULL,NULL,'提交风控审核'),(174,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,107,108,NULL,NULL,'总助审核'),(175,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,107,112,NULL,NULL,'审批不同意，流程结束'),(176,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,107,113,NULL,NULL,'退回客户经理'),(177,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,108,109,NULL,NULL,'总经理审核'),(178,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,108,107,NULL,NULL,'退回风控'),(179,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,108,112,NULL,NULL,'审批不同意，流程结束'),(180,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,109,110,NULL,NULL,'董事长签字'),(181,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,109,112,NULL,NULL,'审批不同意，流程结束'),(182,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,109,113,NULL,NULL,'退回客户经理'),(183,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,110,111,NULL,NULL,'客户展期协议签字'),(184,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,110,112,NULL,NULL,'审批不同意，流程结束'),(185,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,110,109,NULL,NULL,'退回总经理'),(186,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,111,114,NULL,NULL,'审批通过流程结束'),(187,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,112,114,NULL,NULL,'审批不同意，流程结束'),(188,'2013-12-16 11:57:04',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,113,107,NULL,NULL,'提交风控审核'),(189,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,115,116,NULL,NULL,'提交风控审核'),(190,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,116,120,NULL,NULL,'审批不同意，流程结束'),(191,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,116,121,NULL,NULL,'退回客户经理'),(192,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,116,117,NULL,NULL,'上会'),(193,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,117,118,NULL,NULL,'董事长签字'),(194,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,117,120,NULL,NULL,'审批不同意，流程结束'),(195,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,117,121,NULL,NULL,'退回客户经理'),(196,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,118,119,NULL,NULL,'客户展期协议签字'),(197,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,118,120,NULL,NULL,'审批不同意，流程结束'),(198,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,118,117,NULL,NULL,'退回总经理'),(199,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,119,122,NULL,NULL,'审批通过流程结束'),(200,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,120,122,NULL,NULL,'审批不同意，流程结束'),(201,'2013-12-16 11:58:08',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,121,116,NULL,NULL,'提交风控审核'),(202,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,123,124,NULL,NULL,'提交风控审核'),(203,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,124,125,NULL,NULL,'总助审核'),(204,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,124,129,NULL,NULL,'审批不同意，流程结束'),(205,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,124,130,NULL,NULL,'退回客户经理'),(206,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,125,126,NULL,NULL,'总经理审核'),(207,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,125,124,NULL,NULL,'退回风控'),(208,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,125,129,NULL,NULL,'审批不同意，流程结束'),(209,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,126,127,NULL,NULL,'董事长签字'),(210,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,126,129,NULL,NULL,'审批不同意，流程结束'),(211,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,126,130,NULL,NULL,'退回客户经理'),(212,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,127,128,NULL,NULL,'客户展期协议签字'),(213,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,127,129,NULL,NULL,'审批不同意，流程结束'),(214,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,127,126,NULL,NULL,'退回总经理'),(215,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,128,131,NULL,NULL,'审批通过流程结束'),(216,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,129,131,NULL,NULL,'审批不同意，流程结束'),(217,'2013-12-16 11:58:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,130,124,NULL,NULL,'提交风控审核'),(218,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,132,133,NULL,NULL,'提交贷款申请'),(219,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,133,134,NULL,NULL,'风控审批'),(220,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,133,135,NULL,NULL,'财务审批'),(221,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,134,136,NULL,NULL,'提交助理'),(222,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,134,144,NULL,NULL,'退回客户经理'),(223,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,135,137,NULL,NULL,'提交副总'),(224,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,135,144,NULL,NULL,'退回客户经理'),(225,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,136,134,NULL,NULL,'重新提交风控审批'),(226,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,136,138,NULL,NULL,'同意'),(227,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,137,135,NULL,NULL,'退回'),(228,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,137,138,NULL,NULL,'同意'),(229,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,138,139,NULL,NULL,'提交董事长'),(230,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,138,137,NULL,NULL,'退回财务副总经理'),(231,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,138,136,NULL,NULL,'退回总经理助理'),(232,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,138,143,NULL,NULL,'项目终止'),(233,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,139,142,NULL,NULL,'签合同'),(234,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,139,138,NULL,NULL,'退回总经理'),(235,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,139,143,NULL,NULL,'项目终止'),(236,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,140,148,NULL,NULL,'审批通过，流程结束'),(237,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,141,147,NULL,NULL,'会计查账'),(238,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,142,146,NULL,NULL,'提交财务经理审批'),(239,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,143,148,NULL,NULL,'审批不通过结束'),(240,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,144,145,NULL,NULL,'审批不通过，终止流程'),(241,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,144,134,NULL,NULL,'重新提交风控'),(242,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,144,135,NULL,NULL,'重新提交财务'),(243,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,145,149,NULL,NULL,'审批不通过结束'),(244,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,146,141,NULL,NULL,'放款'),(245,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,146,142,NULL,NULL,'退回客户经理'),(246,'2013-12-18 03:12:06',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,147,140,NULL,NULL,'货后监控'),(247,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,150,151,NULL,NULL,'提交贷款申请'),(248,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,151,152,NULL,NULL,'风控审批'),(249,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,151,153,NULL,NULL,'财务审批'),(250,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,152,154,NULL,NULL,'提交助理'),(251,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,152,162,NULL,NULL,'退回客户经理'),(252,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,153,155,NULL,NULL,'提交副总'),(253,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,153,162,NULL,NULL,'退回客户经理'),(254,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,154,152,NULL,NULL,'重新提交风控审批'),(255,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,154,156,NULL,NULL,'同意'),(256,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,155,153,NULL,NULL,'退回'),(257,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,155,156,NULL,NULL,'同意'),(258,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,156,157,NULL,NULL,'提交董事长'),(259,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,156,155,NULL,NULL,'退回财务副总经理'),(260,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,156,154,NULL,NULL,'退回总经理助理'),(261,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,156,161,NULL,NULL,'项目终止'),(262,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,157,160,NULL,NULL,'签合同'),(263,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,157,156,NULL,NULL,'退回总经理'),(264,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,157,161,NULL,NULL,'项目终止'),(265,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,158,166,NULL,NULL,'审批通过，流程结束'),(266,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,159,165,NULL,NULL,'会计查账'),(267,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,160,164,NULL,NULL,'提交财务经理审批'),(268,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,161,166,NULL,NULL,'审批不通过结束'),(269,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,162,163,NULL,NULL,'审批不通过，终止流程'),(270,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,162,152,NULL,NULL,'重新提交风控'),(271,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,162,153,NULL,NULL,'重新提交财务'),(272,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,163,167,NULL,NULL,'审批不通过结束'),(273,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,164,159,NULL,NULL,'放款'),(274,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,164,160,NULL,NULL,'退回客户经理'),(275,'2013-12-18 03:13:33',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,165,158,NULL,NULL,'货后监控'),(276,'2013-12-24 05:04:42',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,168,169,NULL,NULL,'放款'),(277,'2013-12-24 05:04:42',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,169,170,NULL,NULL,'贷款结束'),(278,'2013-12-25 04:04:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,171,172,NULL,NULL,'放款'),(279,'2013-12-25 04:04:27',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,172,173,NULL,NULL,'贷款结束'),(280,'2013-12-25 04:07:32',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,174,175,NULL,NULL,'放款'),(281,'2013-12-25 04:07:32',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,175,176,NULL,NULL,'贷款结束'),(282,'2013-12-25 04:11:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,177,178,NULL,NULL,'放款'),(283,'2013-12-25 04:11:18',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,178,179,NULL,NULL,'贷款结束'),(284,'2013-12-25 04:13:03',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,180,181,NULL,NULL,'放款'),(285,'2013-12-25 04:13:03',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,181,182,NULL,NULL,'贷款结束'),(286,'2013-12-25 04:15:07',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,183,184,NULL,NULL,'放款'),(287,'2013-12-25 04:15:07',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,184,185,NULL,NULL,'贷款结束'),(288,'2014-01-13 01:58:52',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,186,187,NULL,NULL,'财务经理审批'),(289,'2014-01-13 01:58:52',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,187,188,NULL,NULL,'出纳放款'),(290,'2014-01-13 01:58:52',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,188,189,NULL,NULL,'贷款结束'),(291,'2014-01-13 02:00:52',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,190,191,NULL,NULL,'财务经理审批'),(292,'2014-01-13 02:00:52',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,191,192,NULL,NULL,'出纳放款'),(293,'2014-01-13 02:00:52',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,192,193,NULL,NULL,'贷款结束'),(294,'2014-01-13 02:03:05',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,194,195,NULL,NULL,'财务经理审批'),(295,'2014-01-13 02:03:05',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,195,196,NULL,NULL,'出纳放款'),(296,'2014-01-13 02:03:05',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,196,197,NULL,NULL,'贷款结束'),(297,'2014-01-13 02:04:56',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,198,199,NULL,NULL,'财务经理审批'),(298,'2014-01-13 02:04:56',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,199,200,NULL,NULL,'出纳放款'),(299,'2014-01-13 02:04:56',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,200,201,NULL,NULL,'贷款结束'),(300,'2014-01-13 02:06:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,202,203,NULL,NULL,'财务经理审批'),(301,'2014-01-13 02:06:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,203,204,NULL,NULL,'出纳放款'),(302,'2014-01-13 02:06:22',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,204,205,NULL,NULL,'贷款结束'),(303,'2014-01-13 02:08:15',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,206,207,NULL,NULL,'财务经理审批'),(304,'2014-01-13 02:08:15',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,207,208,NULL,NULL,'出纳放款'),(305,'2014-01-13 02:08:15',2,8,NULL,1,NULL,'2014-02-21 07:31:13',-1,NULL,208,209,NULL,NULL,'贷款结束'),(306,'2014-03-06 03:40:25',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,210,211,NULL,NULL,'放款'),(307,'2014-03-06 03:40:25',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,211,212,NULL,NULL,'贷款结束'),(308,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,213,214,NULL,NULL,'提交贷款申请'),(309,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,214,215,NULL,NULL,'风控审批'),(310,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,214,216,NULL,NULL,'财务审批'),(311,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,215,217,NULL,NULL,'提交助理'),(312,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,215,225,NULL,NULL,'退回客户经理'),(313,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,216,218,NULL,NULL,'提交副总'),(314,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,216,225,NULL,NULL,'退回客户经理'),(315,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,217,215,NULL,NULL,'重新提交风控审批'),(316,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,217,219,NULL,NULL,'同意'),(317,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,218,216,NULL,NULL,'退回'),(318,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,218,219,NULL,NULL,'同意'),(319,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,219,220,NULL,NULL,'提交董事长'),(320,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,219,218,NULL,NULL,'退回财务副总经理'),(321,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,219,217,NULL,NULL,'退回总经理助理'),(322,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,219,224,NULL,NULL,'项目终止'),(323,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,220,223,NULL,NULL,'签合同'),(324,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,220,219,NULL,NULL,'退回总经理'),(325,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,220,224,NULL,NULL,'项目终止'),(326,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,221,229,NULL,NULL,'审批通过，流程结束'),(327,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,222,228,NULL,NULL,'会计查账'),(328,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,223,227,NULL,NULL,'提交财务经理审批'),(329,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,224,229,NULL,NULL,'审批不通过结束'),(330,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,225,226,NULL,NULL,'审批不通过，终止流程'),(331,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,225,215,NULL,NULL,'重新提交风控'),(332,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,225,216,NULL,NULL,'重新提交财务'),(333,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,226,230,NULL,NULL,'审批不通过结束'),(334,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,227,222,NULL,NULL,'放款'),(335,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,227,223,NULL,NULL,'退回客户经理'),(336,'2014-03-06 03:44:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,228,221,NULL,NULL,'货后监控'),(337,'2014-03-10 14:07:08',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,231,232,NULL,NULL,'放款'),(338,'2014-03-10 14:07:08',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,232,233,NULL,NULL,'贷款结束');
/*!40000 ALTER TABLE `ts_busstrans` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_city`
--

DROP TABLE IF EXISTS `ts_city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_city` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `ename` varchar(50) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `isdefault` int(11) DEFAULT NULL,
  `jname` varchar(50) DEFAULT NULL,
  `koname` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `provinceId` double DEFAULT NULL,
  `twname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_city`
--

LOCK TABLES `ts_city` WRITE;
/*!40000 ALTER TABLE `ts_city` DISABLE KEYS */;
INSERT INTO `ts_city` VALUES (1,'2014-04-09 03:19:55',2,8,NULL,1,24,'2014-04-08 16:00:00',-1,NULL,'C2013121603462200',NULL,NULL,0,NULL,NULL,'深圳市',1,NULL),(2,'2013-12-15 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:13',-1,NULL,'C2013121603551602',NULL,NULL,0,NULL,NULL,'广州市',1,NULL);
/*!40000 ALTER TABLE `ts_city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_company`
--

DROP TABLE IF EXISTS `ts_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `affiliation` int(11) DEFAULT NULL,
  `builddate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `code` varchar(20) DEFAULT NULL,
  `contactor` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `legal` varchar(50) DEFAULT NULL,
  `mnemonic` varchar(100) DEFAULT NULL,
  `name` varchar(150) DEFAULT NULL,
  `offnum` varchar(50) DEFAULT NULL,
  `orgcode` varchar(20) DEFAULT NULL,
  `poid` double DEFAULT NULL,
  `potype` int(11) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `zipcode` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_company`
--

LOCK TABLES `ts_company` WRITE;
/*!40000 ALTER TABLE `ts_company` DISABLE KEYS */;
INSERT INTO `ts_company` VALUES (1,'2014-04-09 01:52:21',2,8,NULL,1,24,'2014-04-08 16:00:00',-1,NULL,NULL,0,'2013-11-27 16:00:00','C2013112804154800',NULL,NULL,NULL,NULL,'深圳市南江小额贷款有限公司',NULL,NULL,0,0,NULL,NULL,NULL);
/*!40000 ALTER TABLE `ts_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_countersign`
--

DROP TABLE IF EXISTS `ts_countersign`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_countersign` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `auditUser` double DEFAULT NULL,
  `bussNodeId` double DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `procId` varchar(50) DEFAULT NULL,
  `recordId` double DEFAULT NULL,
  `result` varchar(60) DEFAULT NULL,
  `tiid` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_countersign`
--

LOCK TABLES `ts_countersign` WRITE;
/*!40000 ALTER TABLE `ts_countersign` DISABLE KEYS */;
/*!40000 ALTER TABLE `ts_countersign` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_countersigncfg`
--

DROP TABLE IF EXISTS `ts_countersigncfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_countersigncfg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `acway` int(11) DEFAULT NULL,
  `ctype` int(11) DEFAULT NULL,
  `eqlogic` int(11) DEFAULT NULL,
  `eqval` double DEFAULT NULL,
  `nodeId` double DEFAULT NULL,
  `pdtype` int(11) DEFAULT NULL,
  `transId` double DEFAULT NULL,
  `transType` int(11) DEFAULT NULL,
  `untransId` double DEFAULT NULL,
  `voteType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_countersigncfg`
--

LOCK TABLES `ts_countersigncfg` WRITE;
/*!40000 ALTER TABLE `ts_countersigncfg` DISABLE KEYS */;
INSERT INTO `ts_countersigncfg` VALUES (1,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 08:36:29',-1,NULL,1,1,3,3,20,1,69,1,71,1),(2,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:14',-1,NULL,1,1,3,3,56,1,193,1,195,1);
/*!40000 ALTER TABLE `ts_countersigncfg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_country`
--

DROP TABLE IF EXISTS `ts_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_country` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `ename` varchar(50) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `isdefault` int(11) DEFAULT NULL,
  `jname` varchar(50) DEFAULT NULL,
  `koname` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `restypeId` double DEFAULT NULL,
  `twname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_country`
--

LOCK TABLES `ts_country` WRITE;
/*!40000 ALTER TABLE `ts_country` DISABLE KEYS */;
INSERT INTO `ts_country` VALUES (1,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:14',-1,NULL,'C2013121603452100',NULL,NULL,0,NULL,NULL,'中国',18,NULL);
/*!40000 ALTER TABLE `ts_country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_dataaccess`
--

DROP TABLE IF EXISTS `ts_dataaccess`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_dataaccess` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accessIds` varchar(255) DEFAULT NULL,
  `accessNames` varchar(255) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `menuId` double DEFAULT NULL,
  `userId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_dataaccess`
--

LOCK TABLES `ts_dataaccess` WRITE;
/*!40000 ALTER TABLE `ts_dataaccess` DISABLE KEYS */;
/*!40000 ALTER TABLE `ts_dataaccess` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_department`
--

DROP TABLE IF EXISTS `ts_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `builddate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `code` varchar(20) DEFAULT NULL,
  `contactor` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `mnemonic` varchar(100) DEFAULT NULL,
  `mperson` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `poid` double DEFAULT NULL,
  `potype` int(11) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `url` varchar(150) DEFAULT NULL,
  `zipcode` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_department`
--

LOCK TABLES `ts_department` WRITE;
/*!40000 ALTER TABLE `ts_department` DISABLE KEYS */;
INSERT INTO `ts_department` VALUES (1,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:14',-1,NULL,NULL,'2013-11-27 16:00:00','D2013112804160900',NULL,NULL,NULL,NULL,'董事长室',1,1,NULL,NULL,NULL),(2,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:14',-1,NULL,NULL,'2013-11-27 16:00:00','D2013112804163802',NULL,NULL,NULL,NULL,'总经理室',1,1,NULL,NULL,NULL),(3,'2014-04-09 01:53:14',2,8,NULL,1,24,'2014-04-08 16:00:00',-1,NULL,NULL,'2013-11-27 16:00:00','D2013112804164504',NULL,NULL,NULL,NULL,'信贷部',1,1,NULL,NULL,NULL),(4,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:14',-1,NULL,NULL,'2013-11-27 16:00:00','D2013112804165606',NULL,NULL,NULL,NULL,'资金财务部',1,1,NULL,NULL,NULL),(5,'2014-04-09 01:54:02',2,8,NULL,1,24,'2014-04-08 16:00:00',-1,NULL,NULL,'2013-11-27 16:00:00','D2013112804171508',NULL,NULL,NULL,NULL,'资产保全部',1,1,NULL,NULL,NULL),(6,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:14',-1,NULL,NULL,'2013-11-27 16:00:00','D2013112804172610',NULL,NULL,NULL,NULL,'风控部',1,1,NULL,NULL,NULL),(7,'2014-04-08 16:00:00',24,1,-1,1,NULL,'0000-00-00 00:00:00',1,NULL,NULL,'2014-04-08 16:00:00','D2014040909542212',NULL,NULL,NULL,NULL,'副总室',1,1,NULL,NULL,NULL);
/*!40000 ALTER TABLE `ts_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_deskmod`
--

DROP TABLE IF EXISTS `ts_deskmod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_deskmod` (
  `id` decimal(19,0) NOT NULL,
  `createTime` datetime NOT NULL,
  `creator` decimal(19,0) NOT NULL,
  `deptId` decimal(19,0) NOT NULL,
  `empId` decimal(19,0) DEFAULT NULL,
  `isenabled` smallint(6) NOT NULL,
  `modifier` decimal(19,0) DEFAULT NULL,
  `modifytime` datetime DEFAULT NULL,
  `orgid` decimal(19,0) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `cls` varchar(20) DEFAULT NULL,
  `code` varchar(20) NOT NULL,
  `dataCode` text,
  `dataTemp` text,
  `dispType` int(11) NOT NULL,
  `dispcmns` varchar(80) DEFAULT NULL,
  `isdefault` int(11) NOT NULL,
  `ismore` int(11) NOT NULL,
  `loadType` int(11) NOT NULL,
  `moreUrl` varchar(100) DEFAULT NULL,
  `msgCount` int(11) NOT NULL,
  `orderNo` int(11) NOT NULL,
  `sysId` decimal(19,0) NOT NULL,
  `title` varchar(50) NOT NULL,
  `url` varchar(50) DEFAULT NULL,
  `urlparams` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_deskmod`
--

LOCK TABLES `ts_deskmod` WRITE;
/*!40000 ALTER TABLE `ts_deskmod` DISABLE KEYS */;
INSERT INTO `ts_deskmod` VALUES (1,'2013-10-29 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,NULL,'DESK_MOD_FIN_1','getCustApplyDatas','			<tr><td width=\"26\"><img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\"></td><td><a href=\"#\" onclick={GO_TO}>[{code}]{name}</a></td><td width=\"120\" nowrap=\"nowrap\">金额:{appAmount#MONEY}元</td>\n<td width=\"120\" nowrap=\"nowrap\">日期:{appdate}</td></tr>',2,'id,code,name,appAmount,appdate#yyyy-MM-dd',1,1,3,'auditCustApplyMgr_tabid_16',7,1,3,'待审批的个人贷款','flow_auditMainUI_tabid_5','applyId'),(2,'2013-10-29 00:00:00',2,8,-1,1,NULL,NULL,-1,NULL,NULL,'DESK_MOD_FIIN_2','getEntCustApplyDatas','<tr><td width=\"26\"><img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\"></td><td><a href=\"#\" onclick={GO_TO}>[{code}]{name}</a></td><td width=\"120\" nowrap=\"nowrap\">金额:{appAmount#MONEY}元</td>\n<td width=\"120\" nowrap=\"nowrap\">日期:{appdate}</td></tr>',1,'id,code,name,appAmount,appdate#yyyy-MM-dd',1,1,3,'auditCustApplyMgr_tabid_17',7,2,3,'待审批的企业贷款','flow_auditMainUI_tabid_5','applyId'),(3,'2013-10-29 00:00:00',2,8,-1,0,NULL,NULL,-1,NULL,NULL,'DESK_MOD_FIN_3','xxx',NULL,1,NULL,2,1,3,NULL,7,3,3,'超时未审批的个人贷款',NULL,NULL),(4,'2013-10-29 00:00:00',2,8,-1,0,NULL,NULL,-1,NULL,NULL,'DESK_MOD_FIIN_4','xxxx',NULL,1,NULL,2,1,3,NULL,7,4,3,'超时未审批的企业贷款',NULL,NULL),(5,'2013-10-29 00:00:00',2,8,NULL,0,2,'2014-01-17 00:00:00',-1,NULL,NULL,'DESK_MOD_FIN_5','select top 7 A.id,A.xpayDate,ROUND((A.totalAmount-A.ytotalAmount-A.trinterAmount-A.trmgrAmount),2) as ztotalAmount,\n(case B.custType when 0 then \'个人\' else \'企业\' end) custType,\n(case when B.custType=0 then (select name from crm_customerInfo where id = B.customerId) \n when B.custType=1 then (select name from crm_Ecustomer  where id = B.customerId) else \'\' end) as custName,\n Datediff(dd,A.xpayDate,Dateadd(dd,5,getDate())) as payDays \n from fc_Plan A inner join fc_LoanContract B on A.contractId=B.id \n inner join fc_LoanInvoce C on A.contractId = C.contractId\n WHERE A.isenabled=1 and A.status in (0,1) and C.state =1 and C.auditState = 2\n and (A.xpayDate>=getDate() and A.xpayDate< Dateadd(dd,5,getDate()))','<tr><td width=\"26\"><img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\"></td><td><a href=\"#\" onclick={GO_TO}>({custType}){custName}</a></td><td width=\"120\" nowrap=\"nowrap\">应收:{ztotalAmount#MONEY}元</td><td width=\"200\" nowrap=\"nowrap\">还款日:{xpayDate}<span style=\"color:red;font-weight:bold;\">({payDays}天到期)</span></td></tr>',3,'id,xpayDate#yyyy-MM-dd,ztotalAmount,custType,custName,payDays',2,1,1,'nomalDeductMgr_tabid_15',7,5,3,'5天内即将还款的客户','nomalDeductMgr_tabid_15','id'),(6,'2013-10-29 00:00:00',2,8,-1,0,NULL,NULL,-1,NULL,NULL,'DESK_MOD_FIN_6','select top 7 A.totalPharses,ROUND(A.amounts+A.iamounts+A.mamounts+A.pamounts+A.damounts,2) as totalAmounts,(case D.custType when 0 then \'个人\' else \'企业\' end) custType, D.name,A.id from fc_taboutside A inner join fc_LoanContract C on A.contractId = C.id inner join(select X.id as applyId, X.custType,X.paydPhases,X.totalPhases,(case when X.custType=0 then (select name from crm_customerInfo where id = X.customerId) when X.custType=1 then (select name from crm_Ecustomer  where id = X.customerId) else \'\' end) as name from fc_Apply X) D on C.formId = D.applyId where A.isenabled=\'1\'  and A.planId is not null ORDER BY A.inouttype,A.id ','<tr><td width=\"26\"><img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\"></td><td><a href=\"#\" onclick={GO_TO}>({custType}){name}</a></td><td width=\"120\" nowrap=\"nowrap\">逾期:{totalAmounts#MONEY}元</td><td width=\"120\" nowrap=\"nowrap\">愈期:{totalPharses}期</td></tr>',3,'totalPharses,totalAmounts,custType,name,id',2,1,1,'overdueDeductMgr_tabid_15',7,6,3,'逾期客户列表','overdueDeductMgr_tabid_15','id'),(7,'2013-10-29 00:00:00',2,8,-1,0,NULL,NULL,-1,NULL,NULL,'DESK_MOD_FIN_7','    select top 7 A.id, A.code, D.name, A.payAmount,A.payDate\n    from fc_LoanInvoce A inner join fc_LoanContract B on A.contractId=B.id  \n    inner join fc_Apply C  on B.formId = C.id  inner join crm_CustomerInfo D  on C.customerId = D.id  \n    where A.isenabled=1 and C.custType=0 and A.auditState= 2 and A.state = 0   ORDER BY  A.payDate DESC,A.id DESC','<tr><td width=\"26\"><img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\"></td><td><a href=\"#\" onclick={GO_TO}>[{code}]{name}</a></td><td width=\"120\" nowrap=\"nowrap\">金额:{payAmount#MONEY}元</td>\n<td width=\"120\" nowrap=\"nowrap\"><a>日期:{payDate}</a></td></tr>',1,'id,code,name,payAmount,payDate#yyyy-MM-dd',2,1,1,'custLoanMgr_tabid_13',7,7,3,'待放款的个人客户贷款','custLoanMgr_tabid_13','id'),(8,'2013-10-29 00:00:00',2,8,-1,0,NULL,NULL,-1,NULL,NULL,'DESK_MOD_FIN_8','    select top 7 A.id, A.code, D.name, A.payAmount,A.payDate\n    from fc_LoanInvoce A inner join fc_LoanContract B on A.contractId=B.id  \n    inner join fc_Apply C  on B.formId = C.id  inner join crm_ECustomer D  on C.customerId = D.id  \n    where A.isenabled=1 and C.custType=1 and A.auditState= 2 and A.state = 0  \n    ORDER BY  A.payDate DESC,A.id DESC','<tr><td width=\"26\"><img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\"></td><td><a href=\"#\" onclick={GO_TO}>[{code}]{name}</a></td><td width=\"120\" nowrap=\"nowrap\">金额:{payAmount#MONEY}元</td>\n<td width=\"120\" nowrap=\"nowrap\"><a>日期:{payDate}</a></td></tr>',2,'id,code,name,payAmount,payDate#yyyy-MM-dd',2,1,1,'ecustLoanMgr_tabid_14',7,8,3,'待放款的企业客户贷款','ecustLoanMgr_tabid_14','id'),(9,'2013-10-29 00:00:00',2,8,-1,0,NULL,NULL,-1,NULL,NULL,'DESK_MOD_FIN_9','getExtensionTemporary','<tr>\n		<td width=\"26\">\n			<img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\">\n		</td>\n		<td>\n		<a href=\"#\" onclick={GO_TO}>[{code}]{name}</a>\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">\n			金额:{extAmount#MONEY}元\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">日期:{createTime}\n		</td>\n</tr>',2,'id,code,name,extAmount,createTime#yyyy-MM-dd',2,1,3,'tempExtensionApplyMgr_tabid_19',7,9,3,'待提交的展期申请单','tempExtensionApplyMgr_tabid_19','id'),(10,'2013-10-29 00:00:00',2,8,NULL,0,2,'2013-11-13 00:00:00',-1,NULL,NULL,'DESK_MOD_FIN_10','getExemptTemporary','<tr>\n		<td width=\"26\">\n			<img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\">\n		</td>\n		<td>\n		<a href=\"#\" onclick={GO_TO}>[{code}]{custName}</a>\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">\n			合计:{totalAmount#MONEY}元\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">日期:{createTime}\n		</td>\n</tr>',2,'id,code,custName,totalAmount,createTime#yyyy-MM-dd',2,1,3,'tempExemptApplyMgr_tabid_24',7,10,3,'待提交息费豁免申请单','tempExemptApplyMgr_tabid_24','id'),(11,'2013-10-29 00:00:00',2,8,-1,0,NULL,NULL,-1,NULL,NULL,'DESK_MOD_FIN_11','getPrepaymentTemporary','<tr>\n		<td width=\"26\">\n			<img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\">\n		</td>\n		<td>\n		<a href=\"#\" onclick={GO_TO}>[{code}]{custName}</a>\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">\n			合计:{totalAmount#MONEY}元\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">{etype}\n		</td>\n</tr>',2,'id,code,custName,totalAmount,etype',2,1,3,'tempPrepaymentApplyMgrTabId',7,11,3,'待提交提前还款申请单','tempPrepaymentApplyMgrTabId','id'),(12,'2013-10-29 00:00:00',2,8,NULL,0,2,'2014-03-05 00:00:00',-1,NULL,NULL,'DESK_MOD_FIN_12','getExtensionApplyDatas','<tr>\n		<td width=\"26\">\n			<img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\">\n		</td>\n		<td>\n		<a href=\"#\" onclick={GO_TO}>[{code}]{name}</a>\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">\n			金额:{extAmount#MONEY}元\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">日期:{createTime}\n		</td>\n</tr>',2,'id,code,name,extAmount,createTime#yyyy-MM-dd',2,1,3,'AuditExtensionMgr_tabId_26',7,12,3,'待审批的展期申请单','flow_auditMainUI_tabid_20','id,bussProccCode#B0001'),(13,'2013-10-29 00:00:00',2,8,-1,0,NULL,NULL,-1,NULL,NULL,'DESK_MOD_FIN_13','getExemptApplyDatas','<tr>\n		<td width=\"26\">\n			<img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\">\n		</td>\n		<td>\n		<a href=\"#\" onclick={GO_TO}>[{code}]{custName}</a>\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">\n			合计:{totalAmount#MONEY}元\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">日期:{createTime}\n		</td>\n</tr>',2,'id,code,custName,totalAmount,createTime#yyyy-MM-dd',2,1,3,'AuditExemptMgr_tabId_28',7,13,3,'待审批息费豁免申请单','AuditExemptMgr_tabId_28','id'),(14,'2013-10-29 00:00:00',2,8,-1,0,NULL,NULL,-1,NULL,NULL,'DESK_MOD_FIN_14','getPrepaymentApplyDatas','<tr>\n		<td width=\"26\">\n			<img src=\"./extlibs/ext-3.3.0/resources/images/icons/heart.png\">\n		</td>\n		<td>\n		<a href=\"#\" onclick={GO_TO}>[{code}]{custName}</a>\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">\n			合计:{totalAmount#MONEY}元\n		</td>\n		<td width=\"120\" nowrap=\"nowrap\">{etype}\n		</td>\n</tr>',2,'id,code,custName,totalAmount,etype',2,1,3,'AuditPrepaymentMgr_tabId_27',7,14,3,'待审批提前还款申请单','AuditPrepaymentMgr_tabId_27','id');
/*!40000 ALTER TABLE `ts_deskmod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_fieldcustom`
--

DROP TABLE IF EXISTS `ts_fieldcustom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_fieldcustom` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `col` int(11) DEFAULT NULL,
  `controlType` int(11) DEFAULT NULL,
  `datasource` varchar(200) DEFAULT NULL,
  `dispName` varchar(50) DEFAULT NULL,
  `dval` varchar(50) DEFAULT NULL,
  `ename` varchar(50) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `formdiyId` double DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `isRequired` int(11) DEFAULT NULL,
  `jname` varchar(50) DEFAULT NULL,
  `koname` varchar(50) DEFAULT NULL,
  `maxlength` int(11) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `row` int(11) DEFAULT NULL,
  `twname` varchar(50) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_fieldcustom`
--

LOCK TABLES `ts_fieldcustom` WRITE;
/*!40000 ALTER TABLE `ts_fieldcustom` DISABLE KEYS */;
INSERT INTO `ts_fieldcustom` VALUES (1,'2013-10-29 16:00:00',2,8,NULL,1,2,'2013-11-02 16:00:00',-1,NULL,1,0,NULL,'产权人名称',NULL,NULL,NULL,1,NULL,1,NULL,NULL,50,'equityer',1,NULL,125),(2,'2013-10-29 16:00:00',2,8,NULL,1,2,'2013-11-02 16:00:00',-1,NULL,2,5,'身份证,营业执照,组织机构代码证','证件类型',NULL,'cardTypes',NULL,1,NULL,1,NULL,NULL,30,'cardTypes',1,NULL,125),(3,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,5,'房地证,房权证,国土证','房屋产权证',NULL,'certificatesProof',NULL,1,NULL,1,NULL,NULL,30,'certificatesProof',2,NULL,125),(4,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,0,NULL,'证件编号',NULL,'cartNumer',NULL,1,NULL,0,NULL,NULL,20,'cartNumer',2,NULL,125),(5,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,2,NULL,'面积(<span style=\'color:red\'>m2</span>)',NULL,'hourseArea',NULL,1,NULL,0,NULL,NULL,15,'hourseArea',3,NULL,125),(6,'2013-10-29 16:00:00',2,8,NULL,1,2,'2013-12-03 16:00:00',-1,NULL,2,0,'购买,交易','土地取得方式',NULL,'landgetType',NULL,1,NULL,0,NULL,NULL,10,'landgetType',3,NULL,125),(7,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'合同编号',NULL,'contractNumer',NULL,1,NULL,1,NULL,NULL,20,'contractNumer',4,NULL,125),(8,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,0,NULL,'抵押期限',NULL,'pledgeLimit',NULL,1,NULL,1,NULL,NULL,10,'pledgeLimit',4,NULL,125),(9,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'所有人名称',NULL,'ownerName',NULL,2,NULL,0,NULL,NULL,30,'ownerName',1,NULL,125),(10,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,5,'身份证,组织机构代码证','证件品种',NULL,'cardBreed',NULL,2,NULL,0,NULL,NULL,10,'cardBreed',1,NULL,125),(11,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'证件编号',NULL,'cartNumer',NULL,2,NULL,0,NULL,NULL,20,'cartNumer',2,NULL,125),(12,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,0,NULL,'机动车车牌号',NULL,NULL,NULL,2,NULL,0,NULL,NULL,30,'licenseNumber',2,NULL,125),(13,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'机动车品牌',NULL,'motorBrand',NULL,2,NULL,0,NULL,NULL,30,'motorBrand',3,NULL,125),(14,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,0,NULL,'车辆车架号',NULL,'chassisNumber',NULL,2,NULL,0,NULL,NULL,20,'chassisNumber',3,NULL,125),(15,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'所属车管所',NULL,'vehicleAdministration',NULL,2,NULL,0,NULL,NULL,150,'vehicleAdministration',5,NULL,300),(16,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'合同编号',NULL,'contractNumer',NULL,2,NULL,1,NULL,NULL,30,'contractNumer',4,NULL,125),(17,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,0,NULL,'抵押期限',NULL,'pledgeLimit',NULL,2,NULL,0,NULL,NULL,20,'pledgeLimit',4,NULL,125),(18,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'所在企业名称',NULL,'companyName',NULL,3,NULL,0,NULL,NULL,150,'companyName',1,NULL,125),(19,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,0,NULL,'企业营业执照编号',NULL,'businessLicenseNum',NULL,3,NULL,0,NULL,NULL,30,'businessLicenseNum',1,NULL,125),(20,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'股东名称',NULL,'shareHolder',NULL,3,NULL,0,NULL,NULL,30,'shareHolder',2,NULL,125),(21,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,0,NULL,'证件品种',NULL,'cardBreed',NULL,3,NULL,0,NULL,NULL,20,'cardBreed',2,NULL,125),(22,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'证件编号',NULL,'cardNumber',NULL,3,NULL,0,NULL,NULL,20,'cardNumber',3,NULL,125),(23,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,2,NULL,'股东出资比例(%)',NULL,'fundedRatio',NULL,3,NULL,0,NULL,NULL,10,'fundedRatio',3,NULL,125),(24,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,3,NULL,'出资金额',NULL,NULL,NULL,3,NULL,0,NULL,NULL,15,'amountOfContribution',4,NULL,125),(25,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,6,'./sysGvlist_cbodatas.action?restypeId=100013','出资方式',NULL,'BreakDown',NULL,3,NULL,0,NULL,NULL,10,'breakDown',4,NULL,125),(26,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'合同编号',NULL,'contractNumber',NULL,3,NULL,0,NULL,NULL,20,'contractNumber',5,NULL,125),(27,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,0,NULL,'抵押期限',NULL,'pledgeLimit',NULL,3,NULL,1,NULL,NULL,10,'pledgeLimit',5,NULL,125),(28,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'抵押期限',NULL,'pledgeLimit',NULL,4,NULL,0,NULL,NULL,10,'pledgeLimit',2,NULL,125),(29,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'合同编号',NULL,'contractNumber',NULL,4,NULL,0,NULL,NULL,20,'contractNumber',2,NULL,125),(30,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'营业执照注册地址',NULL,'registerAddress',NULL,4,NULL,0,NULL,NULL,150,'registerAddress',3,NULL,350),(31,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,2,0,NULL,'营业执照编号',NULL,'businessLicenseNum',NULL,4,NULL,0,NULL,NULL,20,'businessLicenseNum',1,NULL,125),(32,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'企业名称',NULL,'companyName',NULL,4,NULL,0,NULL,NULL,150,'companyName',1,NULL,125),(33,'2013-12-02 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'xxx',NULL,NULL,NULL,5,NULL,0,NULL,NULL,50,'xxx',1,NULL,139),(34,'2013-12-02 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'www',NULL,NULL,NULL,6,NULL,0,NULL,NULL,50,'www',1,NULL,139),(35,'2013-12-02 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'ssss',NULL,NULL,NULL,14,NULL,0,NULL,NULL,50,'xxxs',1,NULL,139),(36,'2013-12-02 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'ssss',NULL,NULL,NULL,5,NULL,0,NULL,NULL,50,'xxx',1,NULL,139),(37,'2013-12-02 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:15',-1,NULL,1,1,NULL,'xxxx',NULL,NULL,NULL,14,NULL,0,NULL,NULL,50,'sss',1,NULL,139),(38,'2013-12-03 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'协办经理',NULL,NULL,NULL,22,NULL,0,NULL,NULL,50,'comanager',1,NULL,125),(39,'2013-12-03 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'个人信用度',NULL,NULL,NULL,5,NULL,0,NULL,NULL,20,'xinyongdu',1,NULL,139),(40,'2013-12-03 16:00:00',2,8,NULL,1,2,'2013-12-04 16:00:00',-1,NULL,1,5,'小型企业,中型企业,大型企业','企业规模',NULL,NULL,NULL,14,NULL,0,NULL,NULL,20,'ecustomScale',1,NULL,139),(41,'2013-12-03 16:00:00',2,8,-1,0,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'xxxx',NULL,NULL,NULL,6,NULL,0,NULL,NULL,50,'xxx',1,NULL,125),(42,'2013-12-03 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'qqq',NULL,NULL,NULL,7,NULL,0,NULL,NULL,50,'qqq',1,NULL,125),(43,'2013-12-14 16:00:00',2,8,-1,0,NULL,'2014-02-21 07:31:15',-1,NULL,1,0,NULL,'ssss',NULL,NULL,NULL,23,NULL,0,NULL,NULL,50,'xxx',1,NULL,125),(44,'2013-12-14 16:00:00',2,8,-1,0,NULL,'2014-02-21 07:31:15',-1,NULL,2,0,NULL,'xxx',NULL,NULL,NULL,23,NULL,0,NULL,NULL,50,'sssxxx',1,NULL,125),(45,'2013-12-14 16:00:00',2,8,-1,0,NULL,'2014-02-21 07:31:15',-1,NULL,2,0,NULL,'www',NULL,NULL,NULL,23,NULL,0,NULL,NULL,50,'www',1,NULL,125),(46,'2013-12-14 16:00:00',2,8,NULL,1,2,'2013-12-14 16:00:00',-1,NULL,1,1,NULL,'预收利息月数',NULL,NULL,NULL,24,NULL,0,NULL,NULL,50,'ysRatMonth',1,NULL,125),(47,'2013-12-14 16:00:00',2,8,NULL,1,2,'2013-12-14 16:00:00',-1,NULL,2,3,NULL,'预收利息金额',NULL,NULL,NULL,24,NULL,0,NULL,NULL,50,'ysRat',1,NULL,125),(48,'2013-12-14 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,1,NULL,'预收管理费月数',NULL,NULL,NULL,24,NULL,0,NULL,NULL,50,'ysMatMonth',2,NULL,125),(49,'2013-12-14 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,1,3,NULL,'预收管理费金额',NULL,NULL,NULL,24,NULL,0,NULL,NULL,50,'ysMat',2,NULL,125),(50,'2014-01-13 16:00:00',2,8,NULL,1,2,'2014-01-13 16:00:00',-1,NULL,1,0,NULL,'管理费银行',NULL,NULL,NULL,24,NULL,0,NULL,NULL,50,'mrateBank',3,NULL,125);
/*!40000 ALTER TABLE `ts_fieldcustom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_fieldprop`
--

DROP TABLE IF EXISTS `ts_fieldprop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_fieldprop` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `dispName` varchar(50) DEFAULT NULL,
  `ename` varchar(50) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `formdiyId` double DEFAULT NULL,
  `isRequired` int(11) DEFAULT NULL,
  `jname` varchar(50) DEFAULT NULL,
  `koname` varchar(50) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `standName` varchar(30) DEFAULT NULL,
  `twname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_fieldprop`
--

LOCK TABLES `ts_fieldprop` WRITE;
/*!40000 ALTER TABLE `ts_fieldprop` DISABLE KEYS */;
INSERT INTO `ts_fieldprop` VALUES (1,'2013-12-02 16:00:00',2,8,NULL,1,2,'2013-12-02 16:00:00',-1,NULL,'证件到期日期',NULL,NULL,5,2,NULL,NULL,'cendDate','证件到期日期',NULL),(2,'2013-12-02 16:00:00',2,8,NULL,1,2,'2013-12-02 16:00:00',-1,NULL,'年龄',NULL,NULL,5,2,NULL,NULL,'age','年龄',NULL),(3,'2013-12-22 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,'婚姻状况',NULL,NULL,5,2,NULL,NULL,'maristal','婚姻状况',NULL),(4,'2013-12-22 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,'籍贯',NULL,NULL,5,2,NULL,NULL,'hometown','籍贯',NULL),(5,'2013-12-22 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,'民族',NULL,NULL,5,2,NULL,NULL,'nation','民族',NULL),(6,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,'成立时间',NULL,NULL,14,2,NULL,NULL,'comeTime','成立时间',NULL),(7,'2014-01-13 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,'民族',NULL,NULL,15,2,NULL,NULL,'nation','民族',NULL),(8,'2014-01-13 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,'籍贯',NULL,NULL,15,2,NULL,NULL,'hometown','籍贯',NULL),(9,'2014-01-13 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,'婚姻状况',NULL,NULL,15,2,NULL,NULL,'maristal','婚姻状况',NULL),(10,'2014-01-13 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,'年龄',NULL,NULL,15,2,NULL,NULL,'age','年龄',NULL),(11,'2014-01-13 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:15',-1,NULL,'证件到期日期',NULL,NULL,15,2,NULL,NULL,'cendDate','证件到期日期',NULL);
/*!40000 ALTER TABLE `ts_fieldprop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_fieldval`
--

DROP TABLE IF EXISTS `ts_fieldval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_fieldval` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `fieldName` varchar(60) DEFAULT NULL,
  `formId` double DEFAULT NULL,
  `formdiyId` double DEFAULT NULL,
  `val` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=403 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_fieldval`
--

LOCK TABLES `ts_fieldval` WRITE;
/*!40000 ALTER TABLE `ts_fieldval` DISABLE KEYS */;
INSERT INTO `ts_fieldval` VALUES (42,'businessLicenseNum',6,3,'xx'),(43,'breakDown',6,3,'47'),(44,'fundedRatio',6,3,'111'),(45,'cardBreed',6,3,'x'),(46,'shareHolder',6,3,'xx'),(47,'pledgeLimit',6,3,'x'),(48,'companyName',6,3,'xx'),(49,'contractNumber',6,3,'xxx'),(50,'amountOfContribution',6,3,'1,110.00'),(51,'cardNumber',6,3,'xxx'),(56,'xxx',22,23,'111'),(57,'www',22,23,'111'),(58,'sssxxx',22,23,'111'),(66,'xxx',23,23,'xc'),(67,'www',23,23,'方法'),(68,'sssxxx',23,23,'方法'),(73,'xxx',24,23,'111'),(74,'www',24,23,'11'),(75,'sssxxx',24,23,'11'),(91,'businessLicenseNum',8,3,'11'),(92,'breakDown',8,3,'47'),(93,'fundedRatio',8,3,'11'),(94,'cardBreed',8,3,'11'),(95,'shareHolder',8,3,'11'),(96,'pledgeLimit',8,3,'11'),(97,'companyName',8,3,'11'),(98,'contractNumber',8,3,'11'),(99,'amountOfContribution',8,3,'11.00'),(100,'cardNumber',8,3,'11'),(117,'cartNumer',7,1,'11'),(118,'cardTypes',7,1,'身份证'),(119,'contractNumer',7,1,'1'),(120,'equityer',7,1,'1'),(121,'pledgeLimit',7,1,'11'),(122,'landgetType',7,1,'11'),(123,'certificatesProof',7,1,'房权证'),(128,'pledgeLimit',16,3,'11'),(129,'amountOfContribution',16,3,'0.00'),(139,'cardTypes',14,1,'营业执照'),(140,'contractNumer',14,1,'11'),(141,'equityer',14,1,'1'),(142,'pledgeLimit',14,1,'1'),(143,'certificatesProof',14,1,'国土证'),(154,'cardTypes',13,1,'营业执照'),(155,'contractNumer',13,1,'11'),(156,'equityer',13,1,'1'),(157,'pledgeLimit',13,1,'1'),(158,'certificatesProof',13,1,'国土证'),(159,'pledgeLimit',20,3,'11'),(160,'amountOfContribution',20,3,'0.00'),(161,'hourseArea',18,1,'1'),(162,'cardTypes',18,1,'营业执照'),(163,'contractNumer',18,1,'1321'),(164,'equityer',18,1,'1312'),(165,'pledgeLimit',18,1,'312'),(166,'certificatesProof',18,1,'房权证'),(167,'cardTypes',19,1,'营业执照'),(168,'contractNumer',19,1,'312'),(169,'equityer',19,1,'31'),(170,'pledgeLimit',19,1,'321'),(171,'certificatesProof',19,1,'房权证'),(172,'cardTypes',20,1,'营业执照'),(173,'contractNumer',20,1,'312'),(174,'equityer',20,1,'31'),(175,'pledgeLimit',20,1,'321'),(176,'certificatesProof',20,1,'房权证'),(181,'cardTypes',22,1,'身份证'),(182,'contractNumer',22,1,'13232'),(183,'equityer',22,1,'1'),(184,'pledgeLimit',22,1,'232'),(185,'certificatesProof',22,1,'房权证'),(186,'cardTypes',21,1,'身份证'),(187,'contractNumer',21,1,'13232'),(188,'equityer',21,1,'1'),(189,'pledgeLimit',21,1,'232'),(190,'certificatesProof',21,1,'房权证'),(194,'xxx',37,23,'11'),(195,'www',37,23,'11'),(196,'sssxxx',37,23,'11'),(209,'comanager',NULL,22,'xxxxxx'),(210,'comanager',37,22,'xxxxxx'),(211,'cardTypes',29,1,'组织机构代码证'),(212,'contractNumer',29,1,'11'),(213,'equityer',29,1,'1'),(214,'pledgeLimit',29,1,'11'),(215,'certificatesProof',29,1,'房权证'),(221,'cardTypes',30,1,'组织机构代码证'),(222,'contractNumer',30,1,'11'),(223,'equityer',30,1,'1'),(224,'pledgeLimit',30,1,'11'),(225,'certificatesProof',30,1,'房权证'),(228,'comanager',43,22,'xxxxxxxxx'),(233,'hourseArea',42,1,'111'),(234,'cartNumer',42,1,'11'),(235,'cardTypes',42,1,'身份证'),(236,'contractNumer',42,1,'xx'),(237,'equityer',42,1,'1'),(238,'pledgeLimit',42,1,'xxxx'),(239,'landgetType',42,1,'11'),(240,'certificatesProof',42,1,'房权证'),(241,'businessLicenseNum',26,3,'xx'),(242,'breakDown',26,3,'47'),(243,'fundedRatio',26,3,'111'),(244,'cardBreed',26,3,'xxx'),(245,'shareHolder',26,3,'x'),(246,'pledgeLimit',26,3,'xxxx'),(247,'companyName',26,3,'x'),(248,'contractNumber',26,3,'x'),(249,'amountOfContribution',26,3,'1,110.00'),(250,'cardNumber',26,3,'xx'),(251,'hourseArea',43,1,'1111'),(252,'cartNumer',43,1,'1111'),(253,'cardTypes',43,1,'身份证'),(254,'contractNumer',43,1,'111'),(255,'equityer',43,1,'zzzz'),(256,'pledgeLimit',43,1,'111'),(257,'landgetType',43,1,'11'),(258,'certificatesProof',43,1,'房地证'),(276,'xxx',5,23,'11'),(277,'www',5,23,'22'),(278,'sssxxx',5,23,'33'),(289,'xxx',8,23,'xxx'),(290,'www',8,23,'x'),(291,'sssxxx',8,23,'xx'),(313,'comanager',5,22,'王五'),(334,'ecustomScale',2,14,'大型企业'),(340,'ecustomScale',8,14,'小型企业'),(344,'ecustomScale',1,14,'小型企业'),(349,'ecustomScale',19,14,'小型企业'),(358,'ysRat',NULL,24,'0.00'),(359,'ysMat',NULL,24,'0.00'),(369,'comanager',1,22,'黄海年'),(373,'ysRat',1,24,'90,000.00'),(374,'ysMat',1,24,'0.00'),(375,'ysRatMonth',1,24,'1'),(376,'comanager',2,22,'黄海年'),(379,'ysRat',2,24,'0.00'),(380,'ysMat',2,24,'0.00'),(381,'comanager',3,22,'黄海年'),(386,'ysRat',3,24,'45,000.00'),(387,'ysMat',3,24,'160,000.00'),(388,'ysRatMonth',3,24,'1'),(389,'ysMatMonth',3,24,'1'),(390,'comanager',4,22,'黄海年'),(395,'ysRat',4,24,'45,000.00'),(396,'ysMat',4,24,'160,000.00'),(397,'ysRatMonth',4,24,'1'),(398,'ysMatMonth',4,24,'1'),(399,'ysRat',6,24,'1,000.00'),(400,'ysMat',6,24,'1,000.00'),(401,'ysRatMonth',6,24,'1'),(402,'ysMatMonth',6,24,'1');
/*!40000 ALTER TABLE `ts_fieldval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_formcfg`
--

DROP TABLE IF EXISTS `ts_formcfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_formcfg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `custFormId` double DEFAULT NULL,
  `funRights` varchar(100) DEFAULT NULL,
  `nodeId` double DEFAULT NULL,
  `opType` int(11) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=259 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_formcfg`
--

LOCK TABLES `ts_formcfg` WRITE;
/*!40000 ALTER TABLE `ts_formcfg` DISABLE KEYS */;
INSERT INTO `ts_formcfg` VALUES (1,'2013-12-16 08:03:26',2,8,NULL,1,NULL,'2014-02-21 07:31:15',-1,NULL,87,'',1,2,0),(2,'2013-12-16 08:03:26',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',1,2,0),(3,'2013-12-16 08:03:26',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',1,2,0),(4,'2013-12-16 08:04:04',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',2,2,0),(5,'2013-12-16 08:04:04',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',2,2,0),(6,'2013-12-16 08:04:04',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',2,2,0),(7,'2013-12-16 08:04:29',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',3,2,0),(8,'2013-12-16 08:04:29',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',3,2,0),(9,'2013-12-16 08:04:29',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',3,2,0),(10,'2013-12-16 08:09:23',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',4,2,0),(11,'2013-12-16 08:09:23',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',4,2,0),(12,'2013-12-16 08:09:23',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',4,2,0),(22,'2013-12-16 08:24:59',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',8,2,0),(23,'2013-12-16 08:24:59',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',8,2,0),(24,'2013-12-16 08:24:59',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',8,2,0),(25,'2013-12-16 08:26:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',9,2,0),(26,'2013-12-16 08:26:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',9,2,0),(27,'2013-12-16 08:26:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',9,2,0),(28,'2013-12-16 08:26:59',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',10,2,0),(29,'2013-12-16 08:26:59',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',10,2,0),(30,'2013-12-16 08:26:59',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',10,2,0),(37,'2013-12-16 08:30:28',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',17,2,0),(38,'2013-12-16 08:30:28',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',17,2,0),(39,'2013-12-16 08:30:28',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',17,2,0),(40,'2013-12-16 08:31:37',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',18,2,0),(41,'2013-12-16 08:31:37',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',18,2,0),(42,'2013-12-16 08:31:37',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',18,2,0),(43,'2013-12-16 08:32:33',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',19,2,0),(44,'2013-12-16 08:32:33',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',19,2,0),(45,'2013-12-16 08:32:33',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',19,2,0),(46,'2013-12-16 08:36:30',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',20,2,0),(47,'2013-12-16 08:36:30',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',20,2,0),(48,'2013-12-16 08:36:30',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',20,2,0),(49,'2013-12-16 08:36:39',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',21,2,0),(50,'2013-12-16 08:36:39',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',21,2,0),(51,'2013-12-16 08:36:39',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',21,2,0),(52,'2013-12-16 08:37:24',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'',22,1,0),(53,'2013-12-16 08:37:24',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'',22,1,0),(54,'2013-12-16 08:37:24',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'',22,2,0),(55,'2013-12-16 08:37:24',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'',22,2,0),(56,'2013-12-16 08:37:24',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'',22,2,0),(61,'2013-12-16 10:10:00',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',32,2,0),(62,'2013-12-16 10:10:00',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',32,2,0),(63,'2013-12-16 10:10:00',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',32,2,0),(64,'2013-12-16 10:10:00',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',32,2,0),(65,'2013-12-16 12:07:04',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,202,'',52,1,0),(66,'2013-12-16 12:17:00',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,202,'',59,1,0),(74,'2013-12-18 06:29:46',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',5,2,0),(75,'2013-12-18 06:29:46',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',5,2,0),(76,'2013-12-18 06:29:46',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',5,2,0),(77,'2013-12-18 06:29:46',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',5,2,0),(78,'2013-12-18 06:29:52',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',6,2,0),(79,'2013-12-18 06:29:52',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',6,2,0),(80,'2013-12-18 06:29:52',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',6,2,0),(81,'2013-12-18 06:29:52',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',6,2,0),(82,'2013-12-18 06:29:59',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',7,2,0),(83,'2013-12-18 06:29:59',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'',7,2,0),(84,'2013-12-18 06:29:59',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'',7,2,0),(85,'2013-12-18 06:29:59',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'',7,2,0),(86,'2020-12-18 04:01:18',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'',11,1,0),(87,'2020-12-18 04:01:18',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'',11,1,0),(88,'2020-12-18 04:01:18',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',11,2,0),(89,'2020-12-18 04:01:18',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'',11,2,0),(90,'2020-12-18 04:01:18',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'',11,2,0),(91,'2020-12-18 04:01:18',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'',11,2,0),(92,'2014-03-15 08:52:56',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',35,2,0),(93,'2014-03-15 08:52:56',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',35,2,0),(94,'2014-03-15 08:52:56',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',35,2,0),(95,'2014-03-15 08:52:56',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',35,2,0),(96,'2014-03-15 08:53:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',34,2,0),(97,'2014-03-15 08:53:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',34,2,0),(98,'2014-03-15 08:53:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',34,2,0),(99,'2014-03-15 08:53:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',34,2,0),(100,'2014-03-15 08:53:22',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',36,2,0),(101,'2014-03-15 08:53:22',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',36,2,0),(102,'2014-03-15 08:53:22',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',36,2,0),(103,'2014-03-15 08:53:22',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',36,2,0),(104,'2014-03-15 08:53:55',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',60,2,0),(105,'2014-03-15 08:53:55',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',60,2,0),(106,'2014-03-15 08:53:55',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',60,2,0),(107,'2014-03-15 08:53:55',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',60,2,0),(108,'2014-03-15 08:54:05',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',61,2,0),(109,'2014-03-15 08:54:05',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',61,2,0),(110,'2014-03-15 08:54:05',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',61,2,0),(111,'2014-03-15 08:54:05',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',61,2,0),(112,'2014-03-15 08:54:15',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',62,2,0),(113,'2014-03-15 08:54:15',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',62,2,0),(114,'2014-03-15 08:54:15',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',62,2,0),(115,'2014-03-15 08:54:15',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',62,2,0),(116,'2013-12-20 10:32:38',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',40,1,0),(117,'2013-12-20 10:32:38',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',40,1,0),(119,'2013-12-20 12:15:07',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',12,1,0),(120,'2013-12-20 12:16:21',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',23,1,0),(122,'2013-12-20 13:16:30',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',41,1,0),(123,'2013-12-24 05:06:10',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',64,1,0),(124,'2013-12-24 05:06:10',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',64,1,0),(125,'2013-12-24 05:06:10',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',64,2,0),(126,'2013-12-24 05:06:10',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',64,2,0),(127,'2013-12-24 05:06:10',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',64,2,0),(128,'2013-12-24 05:06:10',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',64,2,0),(129,'2013-12-24 05:06:10',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',64,2,0),(130,'2013-12-24 05:06:10',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',64,2,0),(131,'2013-12-24 05:06:10',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',64,2,0),(132,'2013-12-24 05:06:40',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',65,1,0),(133,'2013-12-25 04:06:43',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',66,1,0),(134,'2013-12-25 04:06:43',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',66,1,0),(135,'2013-12-25 04:06:43',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',66,2,0),(136,'2013-12-25 04:06:43',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',66,2,0),(137,'2013-12-25 04:06:43',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',66,2,0),(138,'2013-12-25 04:06:43',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',66,2,0),(139,'2013-12-25 04:06:43',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',66,2,0),(140,'2013-12-25 04:06:43',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',66,2,0),(141,'2013-12-25 04:06:43',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',66,2,0),(142,'2013-12-25 04:07:10',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',67,1,0),(143,'2013-12-25 04:08:42',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',68,1,0),(144,'2013-12-25 04:08:42',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',68,1,0),(145,'2013-12-25 04:08:42',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',68,2,0),(146,'2013-12-25 04:08:42',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',68,2,0),(147,'2013-12-25 04:08:42',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',68,2,0),(148,'2013-12-25 04:08:42',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',68,2,0),(149,'2013-12-25 04:08:42',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',68,2,0),(150,'2013-12-25 04:08:42',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',68,2,0),(151,'2013-12-25 04:08:42',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',68,2,0),(161,'2013-12-25 04:10:57',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',69,1,0),(162,'2013-12-25 04:12:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',70,1,0),(163,'2013-12-25 04:12:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',70,1,0),(164,'2013-12-25 04:12:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',70,2,0),(165,'2013-12-25 04:12:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',70,2,0),(166,'2013-12-25 04:12:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',70,2,0),(167,'2013-12-25 04:12:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',70,2,0),(168,'2013-12-25 04:12:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',70,2,0),(169,'2013-12-25 04:12:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',70,2,0),(170,'2013-12-25 04:12:13',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',70,2,0),(171,'2013-12-25 04:12:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',71,1,0),(172,'2013-12-25 04:14:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',72,1,0),(173,'2013-12-25 04:14:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',72,1,0),(174,'2013-12-25 04:14:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',72,2,0),(175,'2013-12-25 04:14:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',72,2,0),(176,'2013-12-25 04:14:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',72,2,0),(177,'2013-12-25 04:14:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',72,2,0),(178,'2013-12-25 04:14:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',72,2,0),(179,'2013-12-25 04:14:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',72,2,0),(180,'2013-12-25 04:14:11',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',72,2,0),(181,'2013-12-25 04:14:46',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',73,1,0),(182,'2013-12-25 04:16:20',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',74,1,0),(183,'2013-12-25 04:16:20',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',74,1,0),(184,'2013-12-25 04:16:20',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',74,2,0),(185,'2013-12-25 04:16:20',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',74,2,0),(186,'2013-12-25 04:16:20',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',74,2,0),(187,'2013-12-25 04:16:20',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',74,2,0),(188,'2013-12-25 04:16:20',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',74,2,0),(189,'2013-12-25 04:16:20',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',74,2,0),(190,'2013-12-25 04:16:20',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',74,2,0),(191,'2013-12-25 04:16:38',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',75,1,0),(192,'2014-01-13 02:00:03',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',76,1,0),(193,'2014-01-13 02:00:03',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',76,1,0),(194,'2014-01-13 02:00:03',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',76,2,0),(195,'2014-01-13 02:00:03',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',76,2,0),(196,'2014-01-13 02:00:03',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',76,2,0),(197,'2014-01-13 02:00:03',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',76,2,0),(198,'2014-01-13 02:00:03',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',76,2,0),(199,'2014-01-13 02:00:03',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',76,2,0),(200,'2014-01-13 02:00:03',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',76,2,0),(201,'2014-01-13 02:00:30',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',77,1,0),(202,'2014-01-13 02:01:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',79,1,0),(203,'2014-01-13 02:01:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',79,1,0),(204,'2014-01-13 02:01:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',79,2,0),(205,'2014-01-13 02:01:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',79,2,0),(206,'2014-01-13 02:01:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',79,2,0),(207,'2014-01-13 02:01:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',79,2,0),(208,'2014-01-13 02:01:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',79,2,0),(209,'2014-01-13 02:01:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',79,2,0),(210,'2014-01-13 02:01:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',79,2,0),(211,'2014-01-13 02:02:14',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',80,1,0),(212,'2014-01-13 02:04:02',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',82,1,0),(213,'2014-01-13 02:04:02',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',82,1,0),(214,'2014-01-13 02:04:02',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',82,2,0),(215,'2014-01-13 02:04:02',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',82,2,0),(216,'2014-01-13 02:04:02',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',82,2,0),(217,'2014-01-13 02:04:02',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',82,2,0),(218,'2014-01-13 02:04:02',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',82,2,0),(219,'2014-01-13 02:04:02',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',82,2,0),(220,'2014-01-13 02:04:02',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',82,2,0),(221,'2014-01-13 02:04:32',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',83,1,0),(222,'2014-01-13 02:05:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',85,1,0),(223,'2014-01-13 02:05:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',85,1,0),(224,'2014-01-13 02:05:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',85,2,0),(225,'2014-01-13 02:05:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',85,2,0),(226,'2014-01-13 02:05:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',85,2,0),(227,'2014-01-13 02:05:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',85,2,0),(228,'2014-01-13 02:05:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',85,2,0),(229,'2014-01-13 02:05:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',85,2,0),(230,'2014-01-13 02:05:44',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',85,2,0),(231,'2014-01-13 02:06:02',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',86,1,0),(232,'2014-01-13 02:07:12',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',88,1,0),(233,'2014-01-13 02:07:12',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',88,1,0),(234,'2014-01-13 02:07:12',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',88,2,0),(235,'2014-01-13 02:07:12',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',88,2,0),(236,'2014-01-13 02:07:12',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',88,2,0),(237,'2014-01-13 02:07:12',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',88,2,0),(238,'2014-01-13 02:07:12',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',88,2,0),(239,'2014-01-13 02:07:12',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',88,2,0),(240,'2014-01-13 02:07:12',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',88,2,0),(241,'2014-01-13 02:07:56',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',89,1,0),(242,'2014-01-13 02:09:08',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',91,1,0),(243,'2014-01-13 02:09:08',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',91,1,0),(244,'2014-01-13 02:09:08',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,111,'添加质押合同,编辑质押合同,删除质押合同,打印质押合同,质押合同模板下载',91,2,0),(245,'2014-01-13 02:09:08',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,108,'添加保证合同,编辑保证合同,删除保证合同,生成保证合同,打印保证合同,保证合同模板下载',91,2,0),(246,'2014-01-13 02:09:08',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,107,'添加抵押合同,编辑抵押合同,删除抵押合同,查询抵押合同,打印抵押合同,抵押合同模板下载',91,2,0),(247,'2014-01-13 02:09:08',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,87,'添加,编辑,删除',91,2,0),(248,'2014-01-13 02:09:08',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,86,'添加,编辑,删除',91,2,0),(249,'2014-01-13 02:09:08',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,64,'',91,2,0),(250,'2014-01-13 02:09:08',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,62,'编辑,保存',91,2,0),(251,'2014-01-13 02:09:30',2,8,NULL,1,NULL,'2014-02-21 07:31:16',-1,NULL,104,'同意放款,不同意放款',92,1,0),(252,'2014-03-06 03:42:17',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,104,'同意放款,不同意放款',95,1,0),(253,'2014-03-06 03:42:17',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',95,1,0),(254,'2014-03-06 03:42:17',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',95,1,0),(255,'2014-03-06 04:08:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,86,'添加,编辑,删除',94,2,0),(256,'2014-03-10 14:08:30',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,104,'同意放款,不同意放款',98,1,0),(257,'2014-03-10 14:08:30',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,100,'添加放款单,编辑放款单,删除放款单,提交放款单,打印放款单',98,1,0),(258,'2014-03-10 14:08:30',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,97,'添加借款合同,编辑借款合同,删除借款合同,生成借款合同,打印借款合同,借款合同模板下载',98,1,0);
/*!40000 ALTER TABLE `ts_formcfg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_formdiy`
--

DROP TABLE IF EXISTS `ts_formdiy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_formdiy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `frecode` varchar(200) DEFAULT NULL,
  `funcs` varchar(200) DEFAULT NULL,
  `isRestype` int(11) DEFAULT NULL,
  `maxCmncount` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `recode` varchar(50) DEFAULT NULL,
  `sysid` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_formdiy`
--

LOCK TABLES `ts_formdiy` WRITE;
/*!40000 ALTER TABLE `ts_formdiy` DISABLE KEYS */;
INSERT INTO `ts_formdiy` VALUES (1,'2013-10-28 16:00:00',2,8,NULL,1,2,'2013-12-16 16:00:00',-1,NULL,NULL,NULL,1,2,'房屋','53',3),(2,'2013-10-29 16:00:00',2,8,NULL,0,2,'2013-12-16 16:00:00',-1,NULL,NULL,NULL,1,2,'汽车','54',3),(3,'2013-10-29 16:00:00',2,8,NULL,1,2,'2013-12-16 16:00:00',-1,NULL,NULL,NULL,1,2,'股票','57',3),(4,'2013-10-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,1,2,'机械设备','93',3),(5,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'个人客户信息','FORMDIY_CUSTOMER_INFO_90',3),(6,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'配偶信息','FORMDIY_CONSORT_91',3),(7,'2013-12-02 16:00:00',2,8,NULL,1,2,'2013-12-02 16:00:00',-1,NULL,NULL,NULL,0,3,'客户住宅信息','FORMDIY_ADDRESS_92',3),(8,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'房产物业信息','FORMDIY_ESTATE_93',3),(9,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'职业信息','FORMDIY_WORK_94',3),(10,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'个人旗下/企业关联公司信息','FORMDIY_CUSTCOMPANY_95',3),(11,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'联系人资料','FORMDIY_CONTACTOR_96',3),(12,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'个人信用资料','FORMDIY_CREDITINFO_97',3),(13,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'个人其它信息','FORMDIY_CUSROMER_OTHERINFO_98',3),(14,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'企业客户详情','FORMDIY_ECUSTOMER_INFO_99',3),(15,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'企业法人信息','FORMDIY_LEGAL_108',3),(16,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'银行借款情况','FORMDIY_EBANKBORR_102',3),(17,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'所有者借款情况','FORMDIY_EOWNERBORR_103',3),(18,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'股权结构','FORMDIY_EEQSTRUCT_104',3),(19,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'企业领导班子','FORMDIY_ECLASS_105',3),(20,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'企业担保','FORMDIY_EASSURE_106',3),(21,'2013-12-02 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'企业其它信息','FORMDIY_ECUSROMER_OTHERINFO_107',3),(22,'2013-12-03 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'贷款申请','FROMDIY_APPLY_109',3),(23,'2013-12-14 16:00:00',2,8,NULL,1,2,'2013-12-14 16:00:00',-1,NULL,NULL,NULL,0,3,'借款合同','FORMDIY_LOAN_86',3),(24,'2013-12-14 16:00:00',2,8,NULL,1,2,'2013-12-14 16:00:00',-1,NULL,NULL,NULL,0,2,'放款单','FORMDIY_LOANINVOCE_87',3),(25,'2014-01-13 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:16',-1,NULL,NULL,NULL,0,3,'第三方担保人','FORMDIY_CUST_90',3);
/*!40000 ALTER TABLE `ts_formdiy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_formrecords`
--

DROP TABLE IF EXISTS `ts_formrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_formrecords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `bussCode` varchar(255) DEFAULT NULL,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `custFormId` double DEFAULT NULL,
  `formId` varchar(255) DEFAULT NULL,
  `nodeId` double DEFAULT NULL,
  `userId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=272 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_formrecords`
--

LOCK TABLES `ts_formrecords` WRITE;
/*!40000 ALTER TABLE `ts_formrecords` DISABLE KEYS */;
INSERT INTO `ts_formrecords` VALUES (1,'B9999','2013-12-15 16:00:00',97,'1',11,11),(2,'B9999','2013-12-15 16:00:00',100,'1',11,11),(3,'B9999','2013-12-15 16:00:00',104,'1',12,23),(4,'B9999','2013-12-15 16:00:00',97,'2',11,8),(5,'B9999','2013-12-16 16:00:00',62,'4',5,2),(6,'B9999','2013-12-16 16:00:00',97,'4',11,2),(7,'B9999','2013-12-16 16:00:00',87,'5',32,9),(8,'B9999','2013-12-16 16:00:00',86,'5',32,9),(9,'B9999','2013-12-16 16:00:00',62,'5',32,9),(11,'B9999','2013-12-16 16:00:00',97,'6',11,9),(12,'B9999','2013-12-16 16:00:00',100,'6',11,9),(13,'B9999','2013-12-16 16:00:00',87,'6',5,12),(14,'B9999','2013-12-16 16:00:00',97,'9',11,9),(15,'B9999','2013-12-16 16:00:00',100,'9',11,9),(16,'B9999','2013-12-16 16:00:00',86,'10',5,10),(17,'B9999','2013-12-16 16:00:00',87,'10',5,10),(18,'B9999','2013-12-16 16:00:00',97,'3',11,10),(19,'B9999','2013-12-16 16:00:00',100,'3',11,10),(20,'B9999','2013-12-16 16:00:00',97,'7',11,10),(21,'B9999','2013-12-16 16:00:00',100,'7',11,10),(22,'B9999','2013-12-16 16:00:00',97,'16',11,9),(23,'B9999','2013-12-16 16:00:00',100,'16',11,9),(24,'B9999','2013-12-16 16:00:00',87,'17',32,9),(25,'B9999','2013-12-16 16:00:00',86,'17',32,9),(26,'B9999','2013-12-16 16:00:00',62,'17',32,9),(27,'B9999','2013-12-16 16:00:00',108,'2',11,8),(28,'B9999','2013-12-16 16:00:00',86,'18',5,9),(29,'B9999','2013-12-16 16:00:00',87,'18',5,9),(30,'B9999','2013-12-16 16:00:00',97,'10',11,10),(31,'B9999','2013-12-16 16:00:00',100,'10',11,10),(32,'B9999','2014-03-11 16:00:00',62,'20',8,7),(34,'B9999','2014-03-11 16:00:00',87,'20',10,3),(35,'B9999','2014-03-11 16:00:00',97,'20',11,8),(36,'B9999','2014-03-11 16:00:00',100,'20',11,8),(37,'B9999','2013-12-16 16:00:00',104,'20',12,23),(38,'B9999','2013-12-16 16:00:00',86,'21',5,10),(39,'B9999','2013-12-16 16:00:00',86,'21',5,10),(40,'B0001','2013-12-16 16:00:00',202,'1',52,2),(41,'B9999','2013-12-16 16:00:00',87,'21',6,22),(42,'B9999','2013-12-16 16:00:00',86,'21',9,4),(43,'B9999','2013-12-16 16:00:00',86,'21',10,3),(44,'B9999','2013-12-16 16:00:00',86,'21',10,3),(45,'B9999','2013-12-16 16:00:00',97,'21',11,10),(46,'B9999','2013-12-16 16:00:00',108,'21',11,10),(47,'B9999','2013-12-16 16:00:00',107,'21',11,10),(48,'B9999','2013-12-16 16:00:00',100,'21',11,10),(49,'B9999','2013-12-16 16:00:00',87,'25',32,10),(50,'B9999','2013-12-16 16:00:00',62,'18',8,7),(51,'B9999','2013-12-16 16:00:00',86,'29',32,10),(52,'B9999','2013-12-16 16:00:00',86,'29',32,10),(53,'B9999','2013-12-16 16:00:00',86,'13',5,10),(55,'B9999','2013-12-16 16:00:00',97,'28',11,10),(56,'B9999','2013-12-16 16:00:00',100,'28',11,10),(57,'B9999','2013-12-17 16:00:00',97,'30',35,22),(58,'B9999','2013-12-17 16:00:00',97,'14',62,4),(59,'B9999','2013-12-17 16:00:00',100,'14',62,4),(60,'B9999','2013-12-17 16:00:00',97,'13',11,10),(61,'B9999','2013-12-17 16:00:00',100,'13',11,10),(62,'B9999','2013-12-17 16:00:00',97,'31',11,11),(63,'B9999','2013-12-17 16:00:00',100,'31',11,11),(64,'B9999','2013-12-17 16:00:00',97,'25',35,22),(65,'B9999','2013-12-17 16:00:00',100,'25',35,22),(66,'B9999','2013-12-17 16:00:00',86,'33',32,8),(67,'B9999','2013-12-17 16:00:00',86,'33',32,8),(69,'B9999','2013-12-17 16:00:00',86,'34',32,8),(70,'B9999','2013-12-17 16:00:00',87,'33',32,8),(71,'B9999','2013-12-17 16:00:00',97,'27',11,10),(72,'B9999','2013-12-17 16:00:00',100,'27',11,10),(73,'B9999','2013-12-17 16:00:00',62,'11',5,2),(74,'B9999','2013-12-17 16:00:00',86,'42',5,2),(75,'B9999','2013-12-17 16:00:00',87,'42',5,2),(76,'B9999','2013-12-17 16:00:00',86,'11',5,2),(80,'B9999','2013-12-17 16:00:00',86,'44',5,10),(81,'B9999','2013-12-17 16:00:00',97,'44',11,10),(82,'B9999','2013-12-17 16:00:00',100,'44',11,10),(83,'B9999','2013-12-18 16:00:00',64,'41',5,2),(84,'B9999','2013-12-18 16:00:00',62,'41',5,2),(85,'B9999','2013-12-18 16:00:00',62,'23',6,22),(86,'B9999','2013-12-18 16:00:00',62,'24',6,22),(87,'B0001','2013-12-18 16:00:00',202,'2',52,2),(88,'B0001','2013-12-18 16:00:00',202,'3',52,2),(89,'B9999','2013-12-18 16:00:00',97,'18',11,9),(90,'B9999','2013-12-18 16:00:00',62,'1',9,4),(91,'B9999','2013-12-18 16:00:00',86,'1',9,4),(92,'B9999','2013-12-18 16:00:00',87,'1',9,4),(93,'B9999','2013-12-18 16:00:00',97,'1',11,10),(94,'B9999','2013-12-18 16:00:00',100,'1',11,10),(95,'B9999','2013-12-18 16:00:00',97,'2',35,22),(96,'B9999','2013-12-18 16:00:00',86,'3',5,2),(97,'B9999','2013-12-18 16:00:00',97,'3',11,2),(98,'B9999','2013-12-18 16:00:00',100,'3',11,2),(99,'B9999','2013-12-18 16:00:00',97,'4',11,11),(100,'B9999','2013-12-18 16:00:00',100,'4',11,11),(101,'B9999','2013-12-18 16:00:00',100,'2',35,22),(102,'B9999','2013-12-18 16:00:00',97,'5',11,11),(103,'B9999','2013-12-18 16:00:00',100,'5',11,11),(104,'B9999','2013-12-18 16:00:00',97,'6',11,8),(105,'B9999','2013-12-18 16:00:00',100,'6',11,8),(106,'B9999','2014-03-13 16:00:00',97,'7',11,2),(107,'B9999','2014-03-13 16:00:00',100,'7',11,2),(108,'B9999','2014-03-13 16:00:00',104,'7',12,23),(109,'B9999','2014-03-13 16:00:00',97,'8',11,2),(110,'B9999','2014-03-13 16:00:00',100,'8',11,2),(111,'B9999','2013-12-18 16:00:00',97,'9',11,10),(112,'B9999','2013-12-18 16:00:00',100,'9',11,10),(113,'B9999','2013-12-18 16:00:00',104,'9',12,23),(114,'B0001','2013-12-19 16:00:00',202,'1',52,10),(115,'B9999','2013-12-19 16:00:00',97,'11',11,10),(116,'B9999','2013-12-19 16:00:00',100,'11',11,10),(117,'B9999','2013-12-19 16:00:00',108,'11',11,10),(118,'B9999','2013-12-19 16:00:00',97,'12',11,11),(119,'B9999','2013-12-19 16:00:00',100,'12',11,11),(120,'B9999','2013-12-19 16:00:00',104,'11',12,23),(124,'B9999','2013-12-19 16:00:00',108,'13',11,10),(125,'B9999','2013-12-19 16:00:00',97,'2',11,10),(126,'B9999','2013-12-19 16:00:00',108,'2',11,10),(127,'B9999','2013-12-19 16:00:00',97,'4',11,10),(128,'B9999','2013-12-19 16:00:00',100,'4',11,10),(129,'B9999','2013-12-19 16:00:00',100,'2',11,10),(130,'B9999','2013-12-19 16:00:00',97,'5',11,10),(131,'B9999','2013-12-19 16:00:00',100,'5',11,10),(132,'B9999','2013-12-19 16:00:00',104,'2',12,23),(133,'B9999','2013-12-19 16:00:00',97,'7',40,10),(134,'B9999','2013-12-19 16:00:00',100,'7',40,10),(135,'B9999','2013-12-19 16:00:00',104,'7',41,23),(136,'B9999','2013-12-25 16:00:00',97,'1',72,2),(137,'B9999','2013-12-25 16:00:00',100,'1',72,2),(138,'B9999','2013-12-25 16:00:00',97,'1',72,10),(139,'B9999','2013-12-25 16:00:00',100,'1',72,10),(140,'B9999','2013-12-25 16:00:00',104,'1',73,23),(141,'B9999','2013-12-30 16:00:00',97,'2',66,10),(142,'B9999','2013-12-30 16:00:00',100,'2',66,10),(143,'B9999','2013-12-30 16:00:00',104,'2',67,23),(144,'B9999','2013-12-30 16:00:00',97,'3',72,10),(145,'B9999','2013-12-30 16:00:00',100,'3',72,10),(146,'B9999','2013-12-30 16:00:00',104,'3',73,23),(147,'B9999','2014-01-13 16:00:00',97,'1',76,10),(148,'B9999','2014-01-13 16:00:00',100,'1',76,10),(149,'B9999','2014-01-13 16:00:00',104,'1',77,23),(150,'B9999','2014-01-13 16:00:00',97,'2',91,10),(151,'B9999','2014-01-13 16:00:00',100,'2',91,10),(152,'B9999','2014-01-13 16:00:00',104,'2',92,23),(153,'B9999','2014-01-16 16:00:00',97,'3',76,10),(154,'B9999','2014-01-16 16:00:00',100,'3',76,10),(155,'B9999','2014-01-16 16:00:00',104,'3',77,23),(156,'B9999','2014-01-16 16:00:00',97,'4',82,10),(157,'B9999','2014-01-16 16:00:00',100,'4',82,10),(158,'B9999','2014-01-16 16:00:00',104,'4',83,23),(159,'B9999','2014-01-18 16:00:00',97,'5',91,2),(160,'B9999','2014-01-18 16:00:00',100,'5',91,2),(161,'B9999','2014-01-18 16:00:00',104,'5',92,23),(162,'B9999','2014-01-18 16:00:00',97,'6',91,2),(163,'B9999','2014-01-18 16:00:00',100,'6',91,2),(164,'B9999','2014-01-18 16:00:00',104,'6',92,23),(165,'B9999','2014-01-20 16:00:00',97,'7',91,2),(166,'B9999','2014-01-20 16:00:00',100,'7',91,2),(167,'B9999','2014-01-20 16:00:00',104,'7',92,23),(168,'B9999','2014-01-21 16:00:00',97,'8',91,2),(169,'B9999','2014-01-21 16:00:00',100,'8',91,2),(170,'B9999','2014-01-21 16:00:00',104,'8',92,23),(171,'B9999','2014-01-22 16:00:00',97,'9',85,10),(172,'B9999','2014-01-22 16:00:00',100,'9',85,10),(173,'B9999','2014-01-22 16:00:00',104,'9',86,23),(174,'B9999','2014-01-22 16:00:00',97,'10',79,10),(175,'B9999','2014-01-22 16:00:00',100,'10',79,10),(176,'B9999','2014-01-22 16:00:00',104,'10',80,23),(177,'B9999','2014-01-22 16:00:00',97,'11',79,10),(178,'B9999','2014-01-22 16:00:00',100,'11',79,10),(179,'B9999','2014-01-22 16:00:00',104,'11',80,23),(180,'B9999','2014-01-22 16:00:00',97,'2',91,2),(181,'B9999','2014-01-22 16:00:00',100,'2',91,2),(182,'B9999','2014-01-22 16:00:00',97,'1',91,2),(183,'B9999','2014-01-22 16:00:00',100,'1',91,2),(184,'B9999','2014-01-22 16:00:00',97,'3',76,2),(185,'B9999','2014-01-22 16:00:00',100,'3',76,2),(186,'B9999','2014-01-22 16:00:00',104,'1',92,23),(187,'B9999','2014-01-22 16:00:00',97,'5',88,10),(188,'B9999','2014-01-22 16:00:00',100,'5',88,10),(189,'B9999','2014-01-22 16:00:00',97,'7',91,10),(190,'B9999','2014-01-22 16:00:00',100,'7',91,10),(191,'B9999','2014-01-22 16:00:00',97,'6',82,10),(192,'B9999','2014-01-22 16:00:00',100,'6',82,10),(193,'B9999','2014-01-22 16:00:00',97,'12',91,10),(194,'B9999','2014-01-22 16:00:00',100,'12',91,10),(195,'B9999','2014-01-22 16:00:00',97,'10',88,10),(196,'B9999','2014-01-22 16:00:00',100,'10',88,10),(197,'B9999','2014-01-22 16:00:00',97,'9',76,10),(198,'B9999','2014-01-22 16:00:00',100,'9',76,10),(199,'B9999','2014-01-22 16:00:00',97,'8',82,10),(200,'B9999','2014-01-22 16:00:00',100,'8',82,10),(201,'B9999','2014-01-22 16:00:00',104,'5',89,23),(202,'B9999','2014-01-22 16:00:00',104,'6',83,23),(203,'B9999','2014-01-22 16:00:00',104,'8',83,23),(204,'B9999','2014-01-22 16:00:00',104,'9',77,23),(205,'B9999','2014-01-22 16:00:00',104,'10',89,23),(206,'B9999','2014-01-22 16:00:00',104,'12',92,23),(207,'B9999','2014-01-23 16:00:00',97,'3',79,10),(208,'B9999','2014-01-23 16:00:00',100,'3',79,10),(209,'B9999','2014-01-23 16:00:00',97,'1',91,10),(210,'B9999','2014-01-23 16:00:00',100,'1',91,10),(211,'B9999','2014-01-23 16:00:00',97,'4',76,10),(212,'B9999','2014-01-23 16:00:00',100,'4',76,10),(213,'B9999','2014-01-23 16:00:00',104,'3',80,23),(214,'B9999','2014-01-23 16:00:00',104,'4',77,23),(215,'B9999','2014-02-05 16:00:00',97,'6',91,10),(216,'B9999','2014-02-05 16:00:00',100,'6',91,10),(217,'B9999','2014-02-05 16:00:00',97,'8',91,10),(218,'B9999','2014-02-05 16:00:00',100,'8',91,10),(219,'B9999','2014-02-05 16:00:00',97,'9',91,10),(220,'B9999','2014-02-05 16:00:00',100,'9',91,10),(221,'B9999','2014-02-05 16:00:00',104,'9',92,23),(222,'B9999','2014-02-07 16:00:00',97,'10',91,10),(223,'B9999','2014-02-07 16:00:00',100,'10',91,10),(224,'B9999','2014-02-07 16:00:00',104,'10',92,23),(225,'B9999','2014-02-24 16:00:00',97,'15',85,2),(226,'B9999','2014-02-24 16:00:00',100,'15',85,2),(227,'B9999','2014-03-05 16:00:00',97,'16',95,22),(228,'B9999','2014-03-05 16:00:00',100,'16',95,22),(229,'B9999','2014-03-05 16:00:00',104,'16',95,22),(230,'B9999','2014-03-10 16:00:00',97,'3',98,22),(231,'B9999','2014-03-11 16:00:00',97,'4',98,22),(232,'B9999','2014-03-11 16:00:00',100,'4',98,22),(233,'B9999','2014-03-11 16:00:00',104,'4',98,22),(234,'B9999','2014-03-11 16:00:00',97,'5',98,22),(235,'B9999','2014-03-11 16:00:00',100,'5',98,22),(236,'B9999','2014-03-11 16:00:00',104,'5',98,22),(237,'B9999','2014-03-11 16:00:00',97,'6',98,22),(238,'B9999','2014-03-11 16:00:00',100,'6',98,22),(239,'B9999','2014-03-11 16:00:00',104,'6',98,22),(240,'B9999','2014-03-13 16:00:00',97,'7',98,22),(241,'B9999','2014-03-13 16:00:00',100,'7',98,22),(242,'B9999','2014-03-13 16:00:00',104,'7',98,22),(243,'B9999','2014-03-13 16:00:00',97,'8',98,22),(244,'B9999','2014-03-13 16:00:00',100,'8',98,22),(245,'B9999','2014-03-13 16:00:00',104,'8',98,22),(246,'B9999','2014-03-13 16:00:00',97,'9',98,22),(247,'B9999','2014-03-13 16:00:00',100,'9',98,22),(248,'B9999','2014-03-13 16:00:00',104,'9',98,22),(249,'B9999','2014-03-14 16:00:00',97,'10',98,22),(250,'B9999','2014-03-14 16:00:00',100,'10',98,22),(251,'B9999','2014-03-16 16:00:00',97,'11',98,22),(252,'B9999','2014-03-16 16:00:00',100,'11',98,22),(253,'B9999','2014-03-16 16:00:00',104,'11',98,22),(254,'B9999','2014-03-16 16:00:00',97,'12',98,22),(255,'B9999','2014-03-16 16:00:00',100,'12',98,22),(256,'B9999','2014-03-16 16:00:00',104,'12',98,22),(257,'B9999','2014-03-16 16:00:00',97,'13',98,22),(258,'B9999','2014-03-16 16:00:00',100,'13',98,22),(259,'B9999','2014-03-16 16:00:00',104,'13',98,22),(260,'B9999','2014-03-16 16:00:00',97,'14',98,22),(261,'B9999','2014-03-16 16:00:00',104,'14',98,22),(262,'B9999','2014-03-16 16:00:00',97,'15',98,22),(263,'B9999','2014-03-16 16:00:00',100,'15',98,22),(264,'B9999','2014-03-16 16:00:00',104,'15',98,22),(265,'B9999','2014-03-16 16:00:00',97,'16',98,22),(266,'B9999','2014-04-01 16:00:00',97,'19',98,22),(267,'B9999','2014-04-01 16:00:00',100,'19',98,22),(268,'B9999','2014-04-01 16:00:00',104,'19',98,22),(269,'B9999','2014-04-01 16:00:00',97,'20',98,22),(270,'B9999','2014-04-01 16:00:00',100,'20',98,22),(271,'B9999','2014-04-01 16:00:00',104,'20',98,22);
/*!40000 ALTER TABLE `ts_formrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_formula`
--

DROP TABLE IF EXISTS `ts_formula`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_formula` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` bigint(20) DEFAULT NULL,
  `deptId` bigint(20) DEFAULT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` bigint(20) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `dispExpress` varchar(255) DEFAULT NULL,
  `express` varchar(255) DEFAULT NULL,
  `fieldIds` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=284 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_formula`
--

LOCK TABLES `ts_formula` WRITE;
/*!40000 ALTER TABLE `ts_formula` DISABLE KEYS */;
INSERT INTO `ts_formula` VALUES (4,'2013-04-13 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041406175500','1*{客户核算项ID})','1*refId)','2'),(5,'2013-04-13 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041406241308','1*{客户核算项ID}','1*refId','2'),(6,'2013-04-13 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041406253610','1*{客户核算项ID}','1*refId','2'),(7,'2013-04-13 16:00:00',45,8,NULL,1,45,'2013-04-13 16:00:00',2,NULL,'F2013041406282012','1*{客户核算项ID}>={客户编号}+1','1*refId>=code+1','2,3'),(8,'2013-04-13 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041408582314','1+2*{客户核算项ID}','1+2*refId','2'),(9,'2013-04-13 16:00:00',45,8,NULL,1,45,'2013-04-13 16:00:00',2,NULL,'F2013041409192116','1*2+{客户核算项ID}>{客户编号}+1','1*2+refId>code+1','2,3'),(10,'2013-04-13 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041409292318','1+2*{客户核算项ID}','1+2*refId','2'),(11,'2013-04-13 16:00:00',45,8,NULL,1,45,'2013-04-13 16:00:00',2,NULL,'F2013041409295520','1+2+{客户核算项ID}+1','1+2+refId+1','2'),(12,'2013-04-13 16:00:00',45,8,NULL,1,45,'2013-04-14 16:00:00',2,NULL,'F2013041409452022','1+3!={客户核算项ID}+2/{客户编号}','1+3!=refId+2/code','2,3'),(13,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041510180024','{客户核算项ID}!=0','refId!=0','2'),(14,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041500283126','1+{客户核算项ID}*2','1+refId*2','2'),(15,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041500291128','1*2+3','1*2+3',NULL),(16,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041500362330','1+2','1+2',NULL),(17,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041500363632','3*4','3*4',NULL),(18,'2013-04-14 16:00:00',45,8,NULL,1,45,'2013-04-14 16:00:00',2,NULL,'F2013041500393834','1+2*3-3+4/1','1+2*3-3+4/1',NULL),(19,'2013-04-14 16:00:00',45,8,NULL,1,45,'2013-04-14 16:00:00',2,NULL,'F2013041500395336','3+4*0/1','3+4*0/1',NULL),(20,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041504582538','{利息}','ramount','5'),(21,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041504591640','{利息}!=0','ramount!=0','5'),(22,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041505010842','{利息}','ramount','5'),(23,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041505013744','{利息}!=0','ramount!=0','5'),(24,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041505101446','{管理费}','mamount','6'),(25,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041505103448','{管理费}!=0','mamount!=0','6'),(26,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041505113350','{管理费}','mamount','6'),(27,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041505120152','{管理费}!=0','mamount!=0','6'),(28,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041505262554','{提前还款手续费}','bamount','10'),(29,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041505264956','{提前还款手续费}!=0','bamount!=0','10'),(30,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041505280458','{提前还款手续费}','bamount','10'),(31,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041505282160','{提前还款手续费}!=0','bamount!=0','10'),(32,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041506155262','({罚息金额}+{滞纳金})!=0','(pamount+oamount)!=0','7,8'),(33,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041506163264','({罚息金额}+{滞纳金})!=0','(pamount+oamount)!=0','7,8'),(34,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041506190666','{罚息金额}+{滞纳金}','pamount+oamount','7,8'),(35,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041506193268','({罚息金额}+{滞纳金})!=0','(pamount+oamount)!=0','7,8'),(36,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041506251570','{本金}','amount','4'),(37,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041506260572','{本金}!=0','amount!=0','4'),(38,'2013-04-14 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013041506272474','{本金}','amount','4'),(39,'2013-04-19 16:00:00',45,8,NULL,1,45,'2013-04-19 16:00:00',2,NULL,'F2013042002510076','{手续费}','famount','9'),(40,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013042002522678','{手续费}!=0','famount!=0','9'),(41,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013042002540980','{手续费}','famount','9'),(42,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013042002542782','{手续费}!=0','famount!=0','9'),(43,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013042003015984','-{手续费}','-famount','9'),(44,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013042003022786','{手续费}!=0','famount!=0','9'),(45,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013042003030988','-{手续费}','-famount','9'),(46,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013042003033190','{手续费}!=0','famount!=0','9'),(47,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013042003133292','{实收合计}','sumamount','11'),(48,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013042003135294','{实收合计}!=0','sumamount!=0','11'),(49,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013042003183896','{本金}','amount','4'),(50,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F2013042003212598','{本金}!=0','amount!=0','4'),(51,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420032639100','{利息}','ramount','5'),(52,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420032658102','{利息}!=0','ramount!=0','5'),(53,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420032843104','{管理费}','mamount','6'),(54,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420032913106','{管理费}!=0','mamount!=0','6'),(55,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420033321108','{本金}','amount','4'),(56,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420033351110','{本金}!=0','amount!=0','4'),(57,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420033828112','{本金}','amount','4'),(58,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420033854114','{本金}!=0','amount!=0','4'),(59,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420034823116','-{利息}','-ramount','5'),(60,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420034906118','{利息}!=0','ramount!=0','5'),(61,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420035048120','{利息}','ramount','5'),(62,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420035101122','{利息}!=0','ramount!=0','5'),(63,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420035742124','-{管理费}','-mamount','6'),(64,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420035800126','{管理费}!=0','mamount!=0','6'),(65,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420035906128','-{管理费}','-mamount','6'),(66,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420035920130','{管理费}!=0','mamount!=0','6'),(67,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420040459132','{实收合计}','sumamount','11'),(68,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420040527134','{实收合计}!=0','sumamount!=0','11'),(69,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420040951136','{本金}','amount','4'),(70,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420041013138','{本金}!=0','amount!=0','4'),(71,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420051035140','({贷款期限[年]}>=1||{贷款期限[月]}>=12)&&{本金}!=0','(yearLoan>=1||monthLoan>=12)&&amount!=0','4,14,15'),(72,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420051344142','{罚息金额}+{滞纳金}','pamount+oamount','7,8'),(73,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420051414144','({罚息金额}+{滞纳金})!=0','(pamount+oamount)!=0','7,8'),(74,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420051607146','{提前还款手续费}','bamount','10'),(75,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420051630148','{提前还款手续费}!=0','bamount!=0','10'),(76,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420051858150','{管理费}','mamount','6'),(77,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420051920152','{管理费}!=0','mamount!=0','6'),(78,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420052041154','{利息}','ramount','5'),(79,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420052055156','{利息}!=0','ramount!=0','5'),(80,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420052240158','{本金}','amount','4'),(81,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420052546160','({贷款期限[年]}<1&&{贷款期限[月]}<=12)&&{本金}!=0','(yearLoan<1&&monthLoan<=12)&&amount!=0','4,14,15'),(82,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420052956162','-{利息}','-ramount','5'),(83,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:16',2,NULL,'F20130420053010164','{利息}!=0','ramount!=0','5'),(84,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420053102166','-{利息}','-ramount','5'),(85,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420053118168','{利息}!=0','ramount!=0','5'),(86,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420053337170','-{管理费}','-mamount','6'),(87,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420053348172','{管理费}!=0','mamount!=0','6'),(88,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420053447174','-{管理费}','-mamount','6'),(89,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420053500176','{管理费}!=0','mamount!=0','6'),(90,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420053917178','-{利息}','-ramount','5'),(91,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420053937180','{利息}!=0','ramount!=0','5'),(92,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420054529182','-{利息}','-ramount','5'),(93,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420054548184','{利息}!=0','ramount!=0','5'),(94,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420054919186','{实收合计}','sumamount','11'),(95,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420054956188','{实收合计}!=0','sumamount!=0','11'),(96,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420055104190','{本金}','amount','4'),(97,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420055407192','({贷款期限[年]}*12+{贷款期限[月]})>12&&{本金}!=0','(yearLoan*12+monthLoan)>12&&amount!=0','4,14,15'),(98,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420055542194','{管理费}','mamount','6'),(99,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420055559196','{管理费}!=0','mamount!=0','6'),(100,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420055647198','{利息}','ramount','5'),(101,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420055704200','{利息}!=0','ramount!=0','5'),(102,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420055800202','{本金}','amount','4'),(103,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420055914204','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{本金}!=0','(yearLoan*12+monthLoan)<=12&&amount!=0','4,14,15'),(104,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420060401206','{实收合计}','sumamount','11'),(105,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420060416208','{实收合计}!=0','sumamount!=0','11'),(106,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420060519210','{本金}','amount','4'),(107,'2013-04-19 16:00:00',45,8,NULL,1,2,'2014-07-29 16:00:00',2,NULL,'F20130420060610212','({贷款期限[年]}*12+{贷款期限[月]})>12&&{实收本金}!=0','(yearLoan*12+monthLoan)>12&&cat!=0','4,14,15,21'),(108,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420060723214','{罚息金额}+{滞纳金}','pamount+oamount','7,8'),(109,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420060748216','{罚息金额}+{滞纳金}!=0','pamount+oamount!=0','7,8'),(110,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420060845218','{管理费}','mamount','6'),(111,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420060901220','{管理费}!=0','mamount!=0','6'),(112,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420061025222','{利息}','ramount','5'),(113,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420061041224','{利息}!=0','ramount!=0','5'),(114,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420061140226','{本金}','amount','4'),(115,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420061238228','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{本金}!=0','(yearLoan*12+monthLoan)<=12&&amount!=0','4,14,15'),(116,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420061430230','-{管理费}','-mamount','6'),(117,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420061450232','{管理费}!=0','mamount!=0','6'),(118,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420061558234','-{管理费}','-mamount','6'),(119,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420061611236','{管理费}!=0','mamount!=0','6'),(120,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420061923238','-{罚息金额}-{滞纳金}','-pamount-oamount','7,8'),(121,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420061951240','({罚息金额}+{滞纳金})!=0','(pamount+oamount)!=0','7,8'),(122,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420062107242','-{罚息金额}-{滞纳金}','-pamount-oamount','7,8'),(123,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420062133244','({罚息金额}+{滞纳金})!=0','(pamount+oamount)!=0','7,8'),(124,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420062446246','{罚息金额}+{滞纳金}','pamount+oamount','7,8'),(125,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420062518248','({罚息金额}+{滞纳金})!=0','(pamount+oamount)!=0','7,8'),(126,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420062602250','{管理费}','mamount','6'),(127,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420062618252','{管理费}!=0','mamount!=0','6'),(128,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420062651254','{利息}','ramount','5'),(129,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420062705256','{利息}!=0','ramount!=0','5'),(130,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420062844258','{实收合计}','sumamount','11'),(131,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420062859260','{实收合计}!=0','sumamount!=0','11'),(132,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420063109262','{手续费}','famount','9'),(133,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420063123264','{手续费}!=0','famount!=0','9'),(134,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420063209266','{手续费}','famount','9'),(135,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420063224268','{手续费}!=0','famount!=0','9'),(136,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420075235270','{利息}+{管理费}','ramount+mamount','5,6'),(137,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420075331272','{利息}!=0||{管理费}!=0','ramount!=0||mamount!=0','5,6'),(138,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420075441274','{管理费}','mamount','6'),(139,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420075454276','{管理费}!=0','mamount!=0','6'),(140,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420075542278','{利息}','ramount','5'),(141,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420075558280','{利息}!=0','ramount!=0','5'),(142,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420075931282','{本金}','amount','4'),(143,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420080005284','({贷款期限[年]}*12+{贷款期限[月]})>12','(yearLoan*12+monthLoan)>12','14,15'),(144,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420080119286','{本金}','amount','4'),(145,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420080151288','({贷款期限[年]}*12+{贷款期限[月]})<=12','(yearLoan*12+monthLoan)<=12','14,15'),(146,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420080423290','{本金}','amount','4'),(147,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420080455292','({贷款期限[年]}*12+{贷款期限[月]})>12','(yearLoan*12+monthLoan)>12','14,15'),(148,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420080555294','{本金}','amount','4'),(149,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420080620296','({贷款期限[年]}*12+{贷款期限[月]})<=12','(yearLoan*12+monthLoan)<=12','14,15'),(150,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420081101298','-{利息}','-ramount','5'),(151,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420081121300','{利息}!=0','ramount!=0','5'),(152,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420081209302','-{利息}','-ramount','5'),(153,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420081223304','{利息}!=0','ramount!=0','5'),(154,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420081507306','-{管理费}','-mamount','6'),(155,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420081523308','{管理费}!=0','mamount!=0','6'),(156,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420081642310','-{管理费}','-mamount','6'),(157,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420081707312','{管理费}!=0','mamount!=0','6'),(158,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420081859314','{管理费}+{利息}','mamount+ramount','5,6'),(159,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420082132316','{利息}+{管理费}!=0','ramount+mamount!=0','5,6'),(160,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420082233318','{管理费}','mamount','6'),(161,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420082248320','{管理费}!=0','mamount!=0','6'),(162,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420082350322','{利息}','ramount','5'),(163,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420082402324','{利息}!=0','ramount!=0','5'),(164,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420082559326','{本金}','amount','4'),(165,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420082644328','({贷款期限[年]}*12+{贷款期限[月]})>12&&{本金}!=0','(yearLoan*12+monthLoan)>12&&amount!=0','4,14,15'),(166,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420082718330','{本金}','amount','4'),(167,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420082803332','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{本金}!=0','(yearLoan*12+monthLoan)<=12&&amount!=0','4,14,15'),(168,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420083011334','{本金}','amount','4'),(169,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420083052336','({贷款期限[年]}*12+{贷款期限[月]})>12&&{本金}!=0','(yearLoan*12+monthLoan)>12&&amount!=0','4,14,15'),(170,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420083156338','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{本金}!=0','(yearLoan*12+monthLoan)<=12&&amount!=0','4,14,15'),(171,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420083221340','{本金}','amount','4'),(172,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420083300342','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{本金}!=0','(yearLoan*12+monthLoan)<=12&&amount!=0','4,14,15'),(173,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420083600344','{管理费}','mamount','6'),(174,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420083614346','{管理费}!=0','mamount!=0','6'),(175,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420083946348','{利息}','ramount','5'),(176,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084001350','{利息}!=0','ramount!=0','5'),(177,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084108352','{利息}+{管理费}','ramount+mamount','5,6'),(178,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084130354','{利息}+{管理费}!=0','ramount+mamount!=0','5,6'),(179,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084337356','{利息}','ramount','5'),(180,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084353358','{利息}!=0','ramount!=0','5'),(181,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084450360','{利息}','ramount','5'),(182,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084506362','{利息}!=0','ramount!=0','5'),(183,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084636364','{管理费}','mamount','6'),(184,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084655366','{管理费}!=0','mamount!=0','6'),(185,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084746368','{管理费}','mamount','6'),(186,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084801370','{管理费}!=0','mamount!=0','6'),(187,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420084935372','{本金}','amount','4'),(188,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420085636374','({贷款期限[年]}*12+{贷款期限[月]})>12&&{本金}!=0','(yearLoan*12+monthLoan)>12&&amount!=0','4,14,15'),(189,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420085636374','({贷款期限[年]}*12+{贷款期限[月]})>12&&{本金}!=0','(yearLoan*12+monthLoan)>12&&amount!=0','4,14,15'),(190,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420085737378','{本金}','amount','4'),(191,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420085819380','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{本金}!=0','(yearLoan*12+monthLoan)<=12&&amount!=0','4,14,15'),(192,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420085921382','{本金}','amount','4'),(193,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420090014384','({贷款期限[年]}*12+{贷款期限[月]})>12&&{本金}!=0','(yearLoan*12+monthLoan)>12&&amount!=0','4,14,15'),(194,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420090058386','{本金}','amount','4'),(195,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420090140388','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{本金}!=0','(yearLoan*12+monthLoan)<=12&&amount!=0','4,14,15'),(196,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420090328390','{本金}','amount','4'),(197,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420090342392','{本金}!=0','amount!=0','4'),(198,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420090438394','{本金}','amount','4'),(199,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420090520396','({贷款期限[年]}*12+{贷款期限[月]})>12&&{本金}!=0','(yearLoan*12+monthLoan)>12&&amount!=0','4,14,15'),(200,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420090618398','{本金}','amount','4'),(201,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420090710400','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{本金}!=0','(yearLoan*12+monthLoan)<=12&&amount!=0','4,14,15'),(202,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420091130402','{本金}','amount','4'),(203,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420091145404','{本金}!=0','amount!=0','4'),(204,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420091229406','{本金}','amount','4'),(205,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420091243408','{本金}!=0','amount!=0','4'),(206,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420091617410','{利息}','ramount','5'),(207,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420091629412','{利息}!=0','ramount!=0','5'),(208,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420091728414','{利息}','ramount','5'),(209,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420091746416','{利息}!=0','ramount!=0','5'),(210,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420091927418','{管理费}','mamount','6'),(211,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420091943420','{管理费}!=0','mamount!=0','6'),(212,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420092100422','{管理费}','mamount','6'),(213,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420092115424','{管理费}!=0','mamount!=0','6'),(214,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420092336426','{管理费}','mamount','6'),(215,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420092352428','{管理费}!=0','mamount!=0','6'),(216,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420092458430','{利息}','ramount','5'),(217,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420092512432','{利息}!=0','ramount!=0','5'),(218,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420092614434','{利息}+{管理费}','ramount+mamount','5,6'),(219,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420092636436','{利息}+{管理费}!=0','ramount+mamount!=0','5,6'),(220,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420092911438','{实收合计}','sumamount','11'),(221,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420092927440','{实收合计}!=0','sumamount!=0','11'),(222,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420093129442','{罚息金额}+{滞纳金}','pamount+oamount','7,8'),(223,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420093146444','{罚息金额}+{滞纳金}!=0','pamount+oamount!=0','7,8'),(224,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420093244446','{管理费}','mamount','6'),(225,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420093302448','{管理费}!=0','mamount!=0','6'),(226,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420093407450','{利息}','ramount','5'),(227,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420093431452','{利息}!=0','ramount!=0','5'),(228,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420093523454','{本金}','amount','4'),(229,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420093559456','({贷款期限[年]}*12+{贷款期限[月]})>12&&{本金}!=0','(yearLoan*12+monthLoan)>12&&amount!=0','4,14,15'),(230,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420093641458','{本金}','amount','4'),(231,'2013-04-19 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130420093727460','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{本金}!=0','(yearLoan*12+monthLoan)<=12&&amount!=0','4,14,15'),(232,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130521052243462','{放款金额}','payAmount','19'),(233,'2013-05-20 16:00:00',45,8,NULL,1,2,'2013-07-22 16:00:00',2,NULL,'F20130521052915464','({贷款期限[年]}<=0&&({贷款期限[月]}*30+{贷款期限[日]})<360)&&{放款金额}!=0','(yearLoan<=0&&(monthLoan*30+dayLoan)<360)&&payAmount!=0','14,15,16,19'),(234,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130521053123466','{放款金额}','payAmount','19'),(235,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130521053316468','({贷款期限[年]}<=0&&{贷款期限[月]}>12&&{贷款期限[日]}<=0)&&{放款金额}!=0','(yearLoan<=0&&monthLoan>12&&dayLoan<=0)&&payAmount!=0','14,15,16,19'),(236,'2013-05-20 16:00:00',45,8,-1,1,NULL,'2014-02-21 07:31:17',2,NULL,'F20130521092237470','{实收合计}','sumamount','11'),(237,'2013-07-22 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130723041442472','({贷款期限[年]}*12+{贷款期限[月]})>=12&&{放款金额}!=0','(yearLoan*12+monthLoan)>=12&&payAmount!=0','14,15,19'),(238,'2013-07-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130725085350474','{实收管理费}','mat','23'),(239,'2013-07-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130725085428476','{实收合计}','sumamount','11'),(240,'2013-07-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130725085454478','{实收利息}','rat','22'),(241,'2013-07-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130725085513480','{实收本金}','cat','21'),(242,'2013-07-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130725090901482','{实收管理费}>0','mat>0','23'),(243,'2013-07-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130725090926484','{实收利息}>0','rat>0','22'),(244,'2013-07-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130725090944486','{实收本金}>0','cat>0','21'),(245,'2013-07-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130727100039488','{实收手续费金额}','amount','20'),(246,'2013-07-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130727100101490','{实收手续费金额}!=0','amount!=0','20'),(247,'2013-07-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130727100416492','{手续费}','famount','9'),(248,'2013-07-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130727115103494','{手续费}','famount','9'),(249,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730114446496','{实收本金}','cat','21'),(250,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730114655498','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{实收本金}!=0','(yearLoan*12+monthLoan)<=12&&cat!=0','14,15,21'),(251,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730114908500','{实收利息}','rat','22'),(252,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730115003502','{实收利息}','rat','22'),(253,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730115030504','{实收利息}!=0','rat!=0','22'),(254,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730115054506','{实收管理费}','mat','23'),(255,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730115110508','{实收管理费}!=0','mat!=0','23'),(256,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730115204510','{实收罚息}+{实收滞纳金}','pat+dat','24,25'),(257,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730115236512','({实收罚息}+{实收滞纳金})!=0','(pat+dat)!=0','24,25'),(258,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730115342514','{实收本金}','cat','21'),(259,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730115845516','{实收合计}','sumamount','11'),(260,'2014-07-29 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20140730115904518','{实收合计}!=0','sumamount!=0','11'),(261,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901045907520','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{实收本金}!=0','(yearLoan*12+monthLoan)<=12&&cat!=0','14,15,21'),(262,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901045933522','{实收本金}','cat','21'),(263,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050011524','{实收本金}','cat','21'),(264,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050058526','({贷款期限[年]}*12+{贷款期限[月]})>12&&{实收本金}!=0','(yearLoan*12+monthLoan)>12&&cat!=0','14,15,21'),(265,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050219528','{实收利息}!=0','rat!=0','22'),(266,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050303530','{实收管理费}!=0','mat!=0','23'),(267,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050339532','{实收管理费}','mat','23'),(268,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050352534','{实收管理费}!=0','mat!=0','23'),(269,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050439536','({实收罚息}+{实收滞纳金})!=0','(pat+dat)!=0','24,25'),(270,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050457538','{实收罚息}+{实收滞纳金}','pat+dat','24,25'),(271,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050516540','({实收罚息}+{实收滞纳金})!=0','(pat+dat)!=0','24,25'),(272,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050559542','{实收合计}','sumamount','11'),(273,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050627544','{实收合计}!=0','sumamount!=0','11'),(274,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050926546','{利息}+{管理费}','ramount+mamount','5,6'),(275,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901050945548','{利息}+{管理费}!=0','ramount+mamount!=0','5,6'),(276,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901051358550','{实收利息}','rat','22'),(277,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901051428552','{实收利息}!=0','rat!=0','22'),(278,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901051533554','{实收管理费}','mat','23'),(279,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901051545556','{实收管理费}!=0','mat!=0','23'),(280,'2013-08-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130901054607558','{实收利息}','rat','22'),(281,'2013-09-01 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20130902100309560','{实收利息}!=0','rat!=0','22'),(282,'2013-10-04 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20131005003751562','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{实收本金}!=0','(yearLoan*12+monthLoan)<=12&&cat!=0','14,15,21'),(283,'2013-10-04 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,'F20131005003859564','({贷款期限[年]}*12+{贷款期限[月]})<=12&&{实收本金}!=0','(yearLoan*12+monthLoan)<=12&&cat!=0','14,15,21');
/*!40000 ALTER TABLE `ts_formula` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_gvlist`
--

DROP TABLE IF EXISTS `ts_gvlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_gvlist` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `biconCls` varchar(200) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `ename` varchar(50) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `jname` varchar(50) DEFAULT NULL,
  `koname` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `restypeId` double DEFAULT NULL,
  `twname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=127 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_gvlist`
--

LOCK TABLES `ts_gvlist` WRITE;
/*!40000 ALTER TABLE `ts_gvlist` DISABLE KEYS */;
INSERT INTO `ts_gvlist` VALUES (1,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/CaRMetal.png','G2013121603142200',NULL,NULL,NULL,NULL,'的士广告',1,NULL),(2,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/actions/news-subscribe-2.png','G2013121603143402',NULL,NULL,NULL,NULL,'报纸',1,NULL),(3,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/leafpad-2.png','G2013121603173404',NULL,NULL,NULL,NULL,'宣传单',1,NULL),(4,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/others/people-girl-anime_with_silver_hair.png','G2013121603174106',NULL,NULL,NULL,NULL,'朋友介绍',1,NULL),(5,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/status/network-wireless-none.png','G2013121603174808',NULL,NULL,NULL,NULL,'网络',1,NULL),(6,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/taxbird.png','G2013121603175510',NULL,NULL,NULL,NULL,'其他',1,NULL),(7,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/actions/identity-2.png','G2013121603181012',NULL,NULL,NULL,NULL,'身份证',2,NULL),(8,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/devices/drive-removable-media-5.png','G2013121603181814',NULL,NULL,NULL,NULL,'驾驶证',2,NULL),(9,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/actions/identity.png','G2013121603182416',NULL,NULL,NULL,NULL,'出国证',2,NULL),(10,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/maxima-2.png','G2013121603251918',NULL,NULL,NULL,NULL,'未婚',3,NULL),(11,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/getjar.png','G2013121603252720',NULL,NULL,NULL,NULL,'已婚',3,NULL),(12,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603253422',NULL,NULL,NULL,NULL,'离婚/分',3,NULL),(13,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603254724',NULL,NULL,NULL,NULL,'丧偶/夫',3,NULL),(14,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603255826',NULL,NULL,NULL,NULL,'广东省',4,NULL),(15,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603260428',NULL,NULL,NULL,NULL,'湖北省',4,NULL),(16,'2013-12-15 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603262230',NULL,NULL,NULL,NULL,'汉',5,NULL),(17,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603262932',NULL,NULL,NULL,NULL,'中学',6,NULL),(18,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603263634',NULL,NULL,NULL,NULL,'高中',6,NULL),(19,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603264336',NULL,NULL,NULL,NULL,'大专',6,NULL),(20,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603264838',NULL,NULL,NULL,NULL,'本科',6,NULL),(21,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603265840',NULL,NULL,NULL,NULL,'自购',7,NULL),(22,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603270542',NULL,NULL,NULL,NULL,'家族拥有',7,NULL),(23,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603271144',NULL,NULL,NULL,NULL,'宿舍',7,NULL),(24,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603271746',NULL,NULL,NULL,NULL,'租用',7,NULL),(25,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603273148',NULL,NULL,NULL,NULL,'独居',8,NULL),(26,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603273850',NULL,NULL,NULL,NULL,'父母同居',8,NULL),(27,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603274552',NULL,NULL,NULL,NULL,'兄弟/姐妹',8,NULL),(28,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603275154',NULL,NULL,NULL,NULL,'配偶',8,NULL),(29,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603275756',NULL,NULL,NULL,NULL,'子女',8,NULL),(30,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603280458',NULL,NULL,NULL,NULL,'同事',8,NULL),(31,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603281260',NULL,NULL,NULL,NULL,'其它',8,NULL),(32,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/animations/go-home-6.png','G2013121603282662',NULL,NULL,NULL,NULL,'政府机构',9,NULL),(33,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/orbital-eunuchs-sniper.png','G2013121603283464',NULL,NULL,NULL,NULL,'机关事业',9,NULL),(34,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/prism-icon4.png','G2013121603284066',NULL,NULL,NULL,NULL,'私营',9,NULL),(35,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603284868',NULL,NULL,NULL,NULL,'三资企业',9,NULL),(36,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603285570',NULL,NULL,NULL,NULL,'国有企业',9,NULL),(37,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603290172',NULL,NULL,NULL,NULL,'个体',9,NULL),(38,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603291274',NULL,NULL,NULL,NULL,'其它',9,NULL),(39,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/moneys.png','G2013121603292976',NULL,NULL,NULL,NULL,'抵押贷款',10,NULL),(40,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/monodevelop.png','G2013121603293678',NULL,NULL,NULL,NULL,'无抵押贷款',10,NULL),(41,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/actions/identity-2.png','G2013121603294380',NULL,NULL,NULL,NULL,'信用卡',10,NULL),(42,'2013-12-15 16:00:00',2,8,NULL,-1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/others/heart-3.png','G2013121603295282',NULL,NULL,NULL,NULL,'医疗',11,NULL),(43,'2013-12-15 16:00:00',2,8,NULL,-1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/electrem.png','G2013121603295784',NULL,NULL,NULL,NULL,'电子',11,NULL),(44,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603300486',NULL,NULL,NULL,NULL,'法人',12,NULL),(45,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603301088',NULL,NULL,NULL,NULL,'股东',12,NULL),(46,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G2013121603301590',NULL,NULL,NULL,NULL,'其他',12,NULL),(47,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/money.png','G2013121603302492',NULL,NULL,NULL,NULL,'货币',13,NULL),(48,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/actions/project-open.png','G2013121603303094',NULL,NULL,NULL,NULL,'知识产权',13,NULL),(49,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/actions/project-open-2.png','G2013121603303496',NULL,NULL,NULL,NULL,'土地使用权',13,NULL),(50,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/money.png','G2013121603305698',NULL,NULL,NULL,NULL,'人民币',14,NULL),(51,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/moneys.png','G20131216033104100',NULL,NULL,NULL,NULL,'美元',14,NULL),(52,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/others/money.png','G20131216033109102',NULL,NULL,NULL,NULL,'港币',14,NULL),(53,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/house.png','G20131216033124104',NULL,NULL,NULL,NULL,'房屋',16,NULL),(54,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/card.png','G20131216033134106',NULL,NULL,NULL,NULL,'汽车',16,NULL),(55,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/house.png','G20131216033141108',NULL,NULL,NULL,NULL,'厂房',16,NULL),(56,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/homebank-2.png','G20131216033147110',NULL,NULL,NULL,NULL,'地产',16,NULL),(57,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/stock.png','G20131216033204112',NULL,NULL,NULL,NULL,'股票',17,NULL),(58,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/stock3.png','G20131216033211114',NULL,NULL,NULL,NULL,'期权',17,NULL),(59,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033227116',NULL,NULL,NULL,NULL,'三农',18,NULL),(60,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033242118',NULL,NULL,NULL,NULL,'小型企业',18,NULL),(61,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033253120',NULL,NULL,NULL,NULL,'中型企业',18,NULL),(62,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033535122',NULL,NULL,NULL,NULL,'个体工商户',18,NULL),(63,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033542124',NULL,NULL,NULL,NULL,'自然人',18,NULL),(64,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033548126',NULL,NULL,NULL,NULL,'农户贷款',18,NULL),(65,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033600128',NULL,NULL,NULL,NULL,'农业',19,NULL),(66,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033608130',NULL,NULL,NULL,NULL,'工业',19,NULL),(67,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033620132',NULL,NULL,NULL,NULL,'服务业',19,NULL),(68,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033638134',NULL,NULL,NULL,NULL,'消费',19,NULL),(69,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033644136',NULL,NULL,NULL,NULL,'其他',19,NULL),(70,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033652138',NULL,NULL,NULL,NULL,'信用',20,NULL),(71,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033659140',NULL,NULL,NULL,NULL,'保证',20,NULL),(72,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033706142',NULL,NULL,NULL,NULL,'抵押',20,NULL),(73,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033711144',NULL,NULL,NULL,NULL,'质押',20,NULL),(74,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033720146',NULL,NULL,NULL,NULL,'留置',20,NULL),(75,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033726148',NULL,NULL,NULL,NULL,'定金',20,NULL),(76,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216033747150',NULL,NULL,NULL,NULL,'其他',20,NULL),(77,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/loan.png','G20131216033916152',NULL,NULL,NULL,NULL,'短期贷款',21,NULL),(78,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/terraer.png','G20131216033923154',NULL,NULL,NULL,NULL,'中期贷款',21,NULL),(79,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/terraer.png','G20131216033929156',NULL,NULL,NULL,NULL,'长期贷款',21,NULL),(80,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/actions/select.png','G20131216033936158',NULL,NULL,NULL,NULL,'自接',22,NULL),(81,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/bank_account.png','G20131216033947160',NULL,NULL,NULL,NULL,'银行介绍',22,NULL),(82,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/frozen_bubble.png','G20131216033953162',NULL,NULL,NULL,NULL,'朋友介绍',22,NULL),(83,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'images/big_icons/48x48/apps/frets_on_fire.png','G20131216034002164',NULL,NULL,NULL,NULL,'客户介绍',22,NULL),(84,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034008166',NULL,NULL,NULL,NULL,'其他',22,NULL),(85,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034016168',NULL,NULL,NULL,NULL,'7',23,NULL),(86,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034021170',NULL,NULL,NULL,NULL,'15',23,NULL),(87,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034027172',NULL,NULL,NULL,NULL,'30',23,NULL),(88,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034031174',NULL,NULL,NULL,NULL,'45',23,NULL),(89,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034041176',NULL,NULL,NULL,NULL,'60',23,NULL),(90,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034048178',NULL,NULL,NULL,NULL,'75',23,NULL),(91,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034055180',NULL,NULL,NULL,NULL,'90',23,NULL),(92,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034142182',NULL,NULL,NULL,NULL,'水费',24,NULL),(93,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034148184',NULL,NULL,NULL,NULL,'电费',24,NULL),(94,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034154186',NULL,NULL,NULL,NULL,'通信补贴费',24,NULL),(95,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131216034200188',NULL,NULL,NULL,NULL,'其他',24,NULL),(96,'2013-12-18 16:00:00',2,8,-1,0,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131219041214190',NULL,NULL,NULL,NULL,'11',24,NULL),(97,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226110824192',NULL,NULL,NULL,NULL,'制造业',11,NULL),(98,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226110857194',NULL,NULL,NULL,NULL,'建筑业',11,NULL),(99,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226110906196',NULL,NULL,NULL,NULL,'零售及批发业',11,NULL),(100,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111321198',NULL,NULL,NULL,NULL,'农、林、牧、渔业',11,NULL),(101,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111336200',NULL,NULL,NULL,NULL,'交通运输、仓储和邮政业',11,NULL),(102,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111349202',NULL,NULL,NULL,NULL,'电力、燃气及水的生产和供应业',11,NULL),(103,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111406204',NULL,NULL,NULL,NULL,'住宿和餐饮业',11,NULL),(104,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111424206',NULL,NULL,NULL,NULL,'金融业',11,NULL),(105,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111447208',NULL,NULL,NULL,NULL,'房地产业',11,NULL),(106,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111504210',NULL,NULL,NULL,NULL,'租赁和商务服务业',11,NULL),(107,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111517212',NULL,NULL,NULL,NULL,'水利、环境和公共设施管理业',11,NULL),(108,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111530214',NULL,NULL,NULL,NULL,'居民服务和其他服务业',11,NULL),(109,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111559216',NULL,NULL,NULL,NULL,'教育',11,NULL),(110,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111611218',NULL,NULL,NULL,NULL,'文化、体育和娱乐业',11,NULL),(111,'2013-12-25 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131226111623220',NULL,NULL,NULL,NULL,'公共管理和社会组织',11,NULL),(112,'2013-12-25 16:00:00',3,1,-1,1,NULL,'2014-02-21 07:31:17',1,NULL,NULL,'G20131226115944222',NULL,NULL,NULL,NULL,'广西省',4,NULL),(113,'2013-12-25 16:00:00',3,1,-1,1,NULL,'2014-02-21 07:31:17',1,NULL,NULL,'G20131226000001224',NULL,NULL,NULL,NULL,'江西省',4,NULL),(114,'2013-12-25 16:00:00',3,1,-1,1,NULL,'2014-02-21 07:31:17',1,NULL,NULL,'G20131226000214226',NULL,NULL,NULL,NULL,'湖南省',4,NULL),(115,'2013-12-25 16:00:00',3,1,-1,1,NULL,'2014-02-21 07:31:17',1,NULL,NULL,'G20131226000232228',NULL,NULL,NULL,NULL,'重庆市',4,NULL),(116,'2013-12-25 16:00:00',3,1,-1,1,NULL,'2014-02-21 07:31:17',1,NULL,NULL,'G20131226000242230',NULL,NULL,NULL,NULL,'淅江省',4,NULL),(117,'2013-12-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131227033504232',NULL,NULL,NULL,NULL,'贵州省',4,NULL),(118,'2013-12-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131227033524234',NULL,NULL,NULL,NULL,'福建省',4,NULL),(119,'2013-12-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131227033538236',NULL,NULL,NULL,NULL,'河北省',4,NULL),(120,'2013-12-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131227033549238',NULL,NULL,NULL,NULL,'河南省',4,NULL),(121,'2013-12-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131227033607240',NULL,NULL,NULL,NULL,'江苏省',4,NULL),(122,'2013-12-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131227033714242',NULL,NULL,NULL,NULL,'云南省',4,NULL),(123,'2013-12-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131227033842244',NULL,NULL,NULL,NULL,'吉林省',4,NULL),(124,'2013-12-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131227035812246',NULL,NULL,NULL,NULL,'汉',5,NULL),(125,'2013-12-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131227035819248',NULL,NULL,NULL,NULL,'壮',5,NULL),(126,'2013-12-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:17',-1,NULL,NULL,'G20131227035833250',NULL,NULL,NULL,NULL,'苗',5,NULL);
/*!40000 ALTER TABLE `ts_gvlist` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_holidays`
--

DROP TABLE IF EXISTS `ts_holidays`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_holidays` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `edate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `name` varchar(100) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `sdate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_holidays`
--

LOCK TABLES `ts_holidays` WRITE;
/*!40000 ALTER TABLE `ts_holidays` DISABLE KEYS */;
INSERT INTO `ts_holidays` VALUES (1,'2014-04-08 16:00:00',24,1,-1,1,NULL,'0000-00-00 00:00:00',1,NULL,'2014-05-02 16:00:00','劳动节',1,'2014-04-30 16:00:00');
/*!40000 ALTER TABLE `ts_holidays` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_icon`
--

DROP TABLE IF EXISTS `ts_icon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_icon` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `fileName` varchar(100) DEFAULT NULL,
  `filePath` varchar(225) DEFAULT NULL,
  `fileSize` double DEFAULT NULL,
  `iconType` int(11) DEFAULT NULL,
  `lastmod` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6807 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_icon`
--

LOCK TABLES `ts_icon` WRITE;
/*!40000 ALTER TABLE `ts_icon` DISABLE KEYS */;
INSERT INTO `ts_icon` VALUES (5867,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-hippo.png','images/big_icons/48x48/others/animals-hippo.png',5007,1,'2012-10-28 09:06:44'),(5868,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-hornet.png','images/big_icons/48x48/others/animals-hornet.png',4683,1,'2012-10-28 09:06:44'),(5869,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-horse_flame_outline.png','images/big_icons/48x48/others/animals-horse_flame_outline.png',3805,1,'2012-10-28 09:06:44'),(5870,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-hummingbird.png','images/big_icons/48x48/others/animals-hummingbird.png',3032,1,'2012-10-28 09:06:44'),(5871,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-kitten_cute_toon_character_t.png','images/big_icons/48x48/others/animals-kitten_cute_toon_character_t.png',4779,1,'2012-10-28 09:06:44'),(5872,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-ladybug.png','images/big_icons/48x48/others/animals-ladybug.png',4444,1,'2012-10-28 09:06:44'),(5873,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-lion.png','images/big_icons/48x48/others/animals-lion.png',4660,1,'2012-10-28 09:06:44'),(5874,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-lion_rampant.png','images/big_icons/48x48/others/animals-lion_rampant.png',5860,1,'2012-10-28 09:06:44'),(5875,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-lizard-eft.png','images/big_icons/48x48/others/animals-lizard-eft.png',3977,1,'2012-10-28 09:06:44'),(5876,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-mouse.png','images/big_icons/48x48/others/animals-mouse.png',2335,1,'2012-10-28 09:06:44'),(5877,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-mouse_on_top_of_a_cheese.png','images/big_icons/48x48/others/animals-mouse_on_top_of_a_cheese.png',3764,1,'2012-10-28 09:06:44'),(5878,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-octopus.png','images/big_icons/48x48/others/animals-octopus.png',5072,1,'2012-10-28 09:06:44'),(5879,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-owl-txr.png','images/big_icons/48x48/others/animals-owl-txr.png',5044,1,'2012-10-28 09:06:44'),(5880,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-penguin.png','images/big_icons/48x48/others/animals-penguin.png',4892,1,'2012-10-28 09:06:44'),(5881,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-rabbit.png','images/big_icons/48x48/others/animals-rabbit.png',4855,1,'2012-10-28 09:06:44'),(5882,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-rabbit_face.png','images/big_icons/48x48/others/animals-rabbit_face.png',3721,1,'2012-10-28 09:06:44'),(5883,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-ram-dall_sheep_ram_stylized.png','images/big_icons/48x48/others/animals-ram-dall_sheep_ram_stylized.png',2248,1,'2012-10-28 09:06:44'),(5884,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-scarabe.png','images/big_icons/48x48/others/animals-scarabe.png',3760,1,'2012-10-28 09:06:44'),(5885,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-sheep.png','images/big_icons/48x48/others/animals-sheep.png',4547,1,'2012-10-28 09:06:44'),(5886,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-whale.png','images/big_icons/48x48/others/animals-whale.png',1573,1,'2012-10-28 09:06:44'),(5887,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'animals-zebra.png','images/big_icons/48x48/others/animals-zebra.png',2589,1,'2012-10-28 09:06:44'),(5888,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'backpack.png','images/big_icons/48x48/others/backpack.png',5070,1,'2012-10-28 09:06:44'),(5889,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'bbq_gril.png','images/big_icons/48x48/others/bbq_gril.png',3103,1,'2012-10-28 09:06:44'),(5890,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'book.png','images/big_icons/48x48/others/book.png',1958,1,'2012-10-28 09:06:44'),(5891,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'books.png','images/big_icons/48x48/others/books.png',4222,1,'2012-10-28 09:06:44'),(5892,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'button-blue.png','images/big_icons/48x48/others/button-blue.png',3404,1,'2012-10-28 09:06:44'),(5893,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'button-green.png','images/big_icons/48x48/others/button-green.png',3354,1,'2012-10-28 09:06:44'),(5894,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'button-purple.png','images/big_icons/48x48/others/button-purple.png',3366,1,'2012-10-28 09:06:44'),(5895,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'button-red.png','images/big_icons/48x48/others/button-red.png',3286,1,'2012-10-28 09:06:44'),(5896,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'button-seagreen.png','images/big_icons/48x48/others/button-seagreen.png',3364,1,'2012-10-28 09:06:44'),(5897,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'button-yellow.png','images/big_icons/48x48/others/button-yellow.png',3374,1,'2012-10-28 09:06:44'),(5898,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'cat.png','images/big_icons/48x48/others/cat.png',5163,1,'2012-10-28 09:06:44'),(5899,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'circle_blue.png','images/big_icons/48x48/others/circle_blue.png',4032,1,'2012-10-28 09:06:44'),(5900,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'circle_green.png','images/big_icons/48x48/others/circle_green.png',3887,1,'2012-10-28 09:06:44'),(5901,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'circle_grey.png','images/big_icons/48x48/others/circle_grey.png',4049,1,'2012-10-28 09:06:44'),(5902,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'circle_orange.png','images/big_icons/48x48/others/circle_orange.png',3959,1,'2012-10-28 09:06:44'),(5903,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'circle_purple.png','images/big_icons/48x48/others/circle_purple.png',3901,1,'2012-10-28 09:06:44'),(5904,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'circle_red.png','images/big_icons/48x48/others/circle_red.png',3947,1,'2012-10-28 09:06:44'),(5905,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'circle_tan.png','images/big_icons/48x48/others/circle_tan.png',4012,1,'2012-10-28 09:06:44'),(5906,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'circle_yellow.png','images/big_icons/48x48/others/circle_yellow.png',4022,1,'2012-10-28 09:06:44'),(5907,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'crystal_smiley.png','images/big_icons/48x48/others/crystal_smiley.png',4892,1,'2012-10-28 09:06:44'),(5908,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'devil_bat.png','images/big_icons/48x48/others/devil_bat.png',6457,1,'2012-10-28 09:06:44'),(5909,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'diamond_blue.png','images/big_icons/48x48/others/diamond_blue.png',2849,1,'2012-10-28 09:06:44'),(5910,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'diamond_green.png','images/big_icons/48x48/others/diamond_green.png',2868,1,'2012-10-28 09:06:44'),(5911,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'diamond_orange.png','images/big_icons/48x48/others/diamond_orange.png',2975,1,'2012-10-28 09:06:44'),(5912,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'diamond_red.png','images/big_icons/48x48/others/diamond_red.png',2905,1,'2012-10-28 09:06:44'),(5913,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'eye.png.png','images/big_icons/48x48/others/eye.png.png',2903,1,'2012-10-28 09:06:44'),(5914,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'food-apple_worm.png','images/big_icons/48x48/others/food-apple_worm.png',4054,1,'2012-10-28 09:06:44'),(5915,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'food-beer.png','images/big_icons/48x48/others/food-beer.png',5276,1,'2012-10-28 09:06:44'),(5916,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'food-blackberry.png','images/big_icons/48x48/others/food-blackberry.png',5254,1,'2012-10-28 09:06:44'),(5917,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'food-cupcake_iced_with_cherry.png','images/big_icons/48x48/others/food-cupcake_iced_with_cherry.png',3531,1,'2012-10-28 09:06:44'),(5918,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'food-egg_blue.png','images/big_icons/48x48/others/food-egg_blue.png',3786,1,'2012-10-28 09:06:44'),(5919,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'food-fried_egg_sunny.png','images/big_icons/48x48/others/food-fried_egg_sunny.png',3291,1,'2012-10-28 09:06:44'),(5920,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'food-grapes.png','images/big_icons/48x48/others/food-grapes.png',4328,1,'2012-10-28 09:06:44'),(5921,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'food-kiwi.png','images/big_icons/48x48/others/food-kiwi.png',3913,1,'2012-10-28 09:06:44'),(5922,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'food-peper-cayenne_red_chili_pepper.png','images/big_icons/48x48/others/food-peper-cayenne_red_chili_pepper.png',3355,1,'2012-10-28 09:06:44'),(5923,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'food-strawberry_with_light_shadow.png','images/big_icons/48x48/others/food-strawberry_with_light_shadow.png',4442,1,'2012-10-28 09:06:44'),(5924,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'food-worm_in_apple.png','images/big_icons/48x48/others/food-worm_in_apple.png',4143,1,'2012-10-28 09:06:44'),(5925,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'ghost-blue.png','images/big_icons/48x48/others/ghost-blue.png',3879,1,'2012-10-28 09:06:44'),(5926,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'ghost-game_basic_guy.png','images/big_icons/48x48/others/ghost-game_basic_guy.png',3622,1,'2012-10-28 09:06:44'),(5927,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'ghost.png','images/big_icons/48x48/others/ghost.png',4423,1,'2012-10-28 09:06:44'),(5928,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'heart-3.png','images/big_icons/48x48/others/heart-3.png',2971,1,'2012-10-28 09:06:44'),(5929,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'hourglass-2.png','images/big_icons/48x48/others/hourglass-2.png',3512,1,'2012-10-28 09:06:44'),(5930,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'human_skull.png','images/big_icons/48x48/others/human_skull.png',4849,1,'2012-10-28 09:06:44'),(5931,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'jigsaw_piece_t.png','images/big_icons/48x48/others/jigsaw_piece_t.png',2960,1,'2012-10-28 09:06:44'),(5932,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'lifejacket.png','images/big_icons/48x48/others/lifejacket.png',4985,1,'2012-10-28 09:06:44'),(5933,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'light_bulb.png','images/big_icons/48x48/others/light_bulb.png',2994,1,'2012-10-28 09:06:44'),(5934,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'magic-tophat.png','images/big_icons/48x48/others/magic-tophat.png',4672,1,'2012-10-28 09:06:44'),(5935,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'map-compass.png','images/big_icons/48x48/others/map-compass.png',5070,1,'2012-10-28 09:06:44'),(5936,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'molicule-sphalerite_unit.png','images/big_icons/48x48/others/molicule-sphalerite_unit.png',6338,1,'2012-10-28 09:06:44'),(5937,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'molicule-stilleite_zinc_selenide_unit.png','images/big_icons/48x48/others/molicule-stilleite_zinc_selenide_unit.png',6671,1,'2012-10-28 09:06:44'),(5938,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'money.png','images/big_icons/48x48/others/money.png',2943,1,'2012-10-28 09:06:44'),(5939,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'orc.png','images/big_icons/48x48/others/orc.png',4504,1,'2012-10-28 09:06:44'),(5940,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'oval_blue.png','images/big_icons/48x48/others/oval_blue.png',2811,1,'2012-10-28 09:06:44'),(5941,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'oval_green.png','images/big_icons/48x48/others/oval_green.png',2828,1,'2012-10-28 09:06:44'),(5942,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'oval_grey.png','images/big_icons/48x48/others/oval_grey.png',2870,1,'2012-10-28 09:06:44'),(5943,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'oval_orange.png','images/big_icons/48x48/others/oval_orange.png',2797,1,'2012-10-28 09:06:44'),(5944,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'oval_purple.png','images/big_icons/48x48/others/oval_purple.png',2785,1,'2012-10-28 09:06:44'),(5945,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'oval_red.png','images/big_icons/48x48/others/oval_red.png',2876,1,'2012-10-28 09:06:44'),(5946,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'oval_tan.png','images/big_icons/48x48/others/oval_tan.png',2888,1,'2012-10-28 09:06:44'),(5947,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'oval_yellow.png','images/big_icons/48x48/others/oval_yellow.png',2963,1,'2012-10-28 09:06:44'),(5948,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'people-block_figure-fireman.png','images/big_icons/48x48/others/people-block_figure-fireman.png',3409,1,'2012-10-28 09:06:44'),(5949,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'people-boy.png','images/big_icons/48x48/others/people-boy.png',6754,1,'2012-10-28 09:06:44'),(5950,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'people-delivery.png','images/big_icons/48x48/others/people-delivery.png',3332,1,'2012-10-28 09:06:44'),(5951,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'people-girl-anime_with_silver_hair.png','images/big_icons/48x48/others/people-girl-anime_with_silver_hair.png',3151,1,'2012-10-28 09:06:44'),(5952,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'people-girl-blowing_dandelion_seeds.png','images/big_icons/48x48/others/people-girl-blowing_dandelion_seeds.png',4770,1,'2012-10-28 09:06:44'),(5953,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'people-kid_head.png','images/big_icons/48x48/others/people-kid_head.png',5003,1,'2012-10-28 09:06:44'),(5954,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'people-melting.png','images/big_icons/48x48/others/people-melting.png',2955,1,'2012-10-28 09:06:44'),(5955,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'people-xbill.png','images/big_icons/48x48/others/people-xbill.png',3593,1,'2012-10-28 09:06:44'),(5956,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'plant-acorn.png','images/big_icons/48x48/others/plant-acorn.png',2793,1,'2012-10-28 09:06:44'),(5957,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'plant-flower-apple_blossom.png','images/big_icons/48x48/others/plant-flower-apple_blossom.png',4860,1,'2012-10-28 09:06:44'),(5958,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'plant-leaf.png','images/big_icons/48x48/others/plant-leaf.png',2800,1,'2012-10-28 09:06:44'),(5959,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'plant-mushroom.png','images/big_icons/48x48/others/plant-mushroom.png',2739,1,'2012-10-28 09:06:44'),(5960,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'radio-old.png','images/big_icons/48x48/others/radio-old.png',5046,1,'2012-10-28 09:06:44'),(5961,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'rectagle_blue.png','images/big_icons/48x48/others/rectagle_blue.png',1913,1,'2012-10-28 09:06:44'),(5962,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'rectagle_green.png','images/big_icons/48x48/others/rectagle_green.png',2161,1,'2012-10-28 09:06:44'),(5963,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'rectagle_grey.png','images/big_icons/48x48/others/rectagle_grey.png',2202,1,'2012-10-28 09:06:44'),(5964,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'rectagle_orange.png','images/big_icons/48x48/others/rectagle_orange.png',2001,1,'2012-10-28 09:06:44'),(5965,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'rectagle_purple.png','images/big_icons/48x48/others/rectagle_purple.png',1965,1,'2012-10-28 09:06:44'),(5966,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'rectagle_red.png','images/big_icons/48x48/others/rectagle_red.png',1951,1,'2012-10-28 09:06:44'),(5967,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'rectagle_tan.png','images/big_icons/48x48/others/rectagle_tan.png',2200,1,'2012-10-28 09:06:44'),(5968,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'rectagle_yellow.png','images/big_icons/48x48/others/rectagle_yellow.png',2198,1,'2012-10-28 09:06:44'),(5969,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'ribbon-blue.png','images/big_icons/48x48/others/ribbon-blue.png',3856,1,'2012-10-28 09:06:44'),(5970,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'scroll-blank.png','images/big_icons/48x48/others/scroll-blank.png',3301,1,'2012-10-28 09:06:44'),(5971,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'sea_mine.png','images/big_icons/48x48/others/sea_mine.png',4550,1,'2012-10-28 09:06:44'),(5972,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'shield-2.png','images/big_icons/48x48/others/shield-2.png',4581,1,'2012-10-28 09:06:44'),(5973,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'sign-crossroads.png','images/big_icons/48x48/others/sign-crossroads.png',3673,1,'2012-10-28 09:06:44'),(5974,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'suitcase.png','images/big_icons/48x48/others/suitcase.png',3596,1,'2012-10-28 09:06:44'),(5975,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'sun-wearing_shades.png','images/big_icons/48x48/others/sun-wearing_shades.png',6285,1,'2012-10-28 09:06:44'),(5976,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'tools-anvil.png','images/big_icons/48x48/others/tools-anvil.png',1938,1,'2012-10-28 09:06:44'),(5977,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'tools-hammer_and_nails.png','images/big_icons/48x48/others/tools-hammer_and_nails.png',4295,1,'2012-10-28 09:06:44'),(5978,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'transportation-hummer_01.png','images/big_icons/48x48/others/transportation-hummer_01.png',2147,1,'2012-10-28 09:06:44'),(5979,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'transportation-motorcycle.png','images/big_icons/48x48/others/transportation-motorcycle.png',5496,1,'2012-10-28 09:06:44'),(5980,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'transportation-plane-business_jet.png','images/big_icons/48x48/others/transportation-plane-business_jet.png',2072,1,'2012-10-28 09:06:44'),(5981,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'transportation-plane-cessna.png','images/big_icons/48x48/others/transportation-plane-cessna.png',4117,1,'2012-10-28 09:06:44'),(5982,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'umbrella-black.png.png','images/big_icons/48x48/others/umbrella-black.png.png',3642,1,'2012-10-28 09:06:44'),(5983,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'water_drop_1.png','images/big_icons/48x48/others/water_drop_1.png',2850,1,'2012-10-28 09:06:44'),(5984,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-3.png','images/big_icons/48x48/places/folder-3.png',3415,1,'2012-10-28 09:06:44'),(5985,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-remote-2.png','images/big_icons/48x48/places/folder-remote-2.png',3957,1,'2012-10-28 09:06:44'),(5986,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-remote-ftp.png','images/big_icons/48x48/places/folder-remote-ftp.png',3724,1,'2012-10-28 09:06:44'),(5987,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-remote-nfs.png','images/big_icons/48x48/places/folder-remote-nfs.png',3748,1,'2012-10-28 09:06:44'),(5988,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-remote-smb.png','images/big_icons/48x48/places/folder-remote-smb.png',3776,1,'2012-10-28 09:06:44'),(5989,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-remote-ssh.png','images/big_icons/48x48/places/folder-remote-ssh.png',3776,1,'2012-10-28 09:06:44'),(5990,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-remote.png','images/big_icons/48x48/places/folder-remote.png',3743,1,'2012-10-28 09:06:44'),(5991,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-saved-search.png','images/big_icons/48x48/places/folder-saved-search.png',4014,1,'2012-10-28 09:06:44'),(5992,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder.png','images/big_icons/48x48/places/folder.png',2638,1,'2012-10-28 09:06:44'),(5993,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-server.png','images/big_icons/48x48/places/network-server.png',3612,1,'2012-10-28 09:06:46'),(5994,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-workgroup.png','images/big_icons/48x48/places/network-workgroup.png',4570,1,'2012-10-28 09:06:46'),(5995,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-desktop-2.png','images/big_icons/48x48/places/user-desktop-2.png',2650,1,'2012-10-28 09:06:46'),(5996,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-desktop.png','images/big_icons/48x48/places/user-desktop.png',3346,1,'2012-10-28 09:06:46'),(5997,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-home-txr.png','images/big_icons/48x48/places/user-home-txr.png',3600,1,'2012-10-28 09:06:46'),(5998,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash.png','images/big_icons/48x48/places/user-trash.png',2225,1,'2012-10-28 09:06:46'),(5999,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'blockdevice.png','images/big_icons/48x48/places/crystal-style/blockdevice.png',4656,1,'2012-10-28 09:06:44'),(6000,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'chardevice.png','images/big_icons/48x48/places/crystal-style/chardevice.png',3358,1,'2012-10-28 09:06:44'),(6001,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'database.png','images/big_icons/48x48/places/crystal-style/database.png',5795,1,'2012-10-28 09:06:44'),(6002,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'favorites.png','images/big_icons/48x48/places/crystal-style/favorites.png',3480,1,'2012-10-28 09:06:44'),(6003,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-alert.png','images/big_icons/48x48/places/crystal-style/file-alert.png',2425,1,'2012-10-28 09:06:44'),(6004,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-broken.png','images/big_icons/48x48/places/crystal-style/file-broken.png',1674,1,'2012-10-28 09:06:44'),(6005,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-doc.png','images/big_icons/48x48/places/crystal-style/file-doc.png',1576,1,'2012-10-28 09:06:44'),(6006,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-locked.png','images/big_icons/48x48/places/crystal-style/file-locked.png',2288,1,'2012-10-28 09:06:44'),(6007,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-temporary.png','images/big_icons/48x48/places/crystal-style/file-temporary.png',3193,1,'2012-10-28 09:06:44'),(6008,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file.png','images/big_icons/48x48/places/crystal-style/file.png',957,1,'2012-10-28 09:06:44'),(6009,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-blue.png','images/big_icons/48x48/places/crystal-style/folder-blue.png',3085,1,'2012-10-28 09:06:44'),(6010,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-documents.png','images/big_icons/48x48/places/crystal-style/folder-documents.png',3851,1,'2012-10-28 09:06:44'),(6011,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-favorites.png','images/big_icons/48x48/places/crystal-style/folder-favorites.png',3685,1,'2012-10-28 09:06:44'),(6012,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-green.png','images/big_icons/48x48/places/crystal-style/folder-green.png',3178,1,'2012-10-28 09:06:44'),(6013,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-grey.png','images/big_icons/48x48/places/crystal-style/folder-grey.png',1831,1,'2012-10-28 09:06:44'),(6014,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-html.png','images/big_icons/48x48/places/crystal-style/folder-html.png',4713,1,'2012-10-28 09:06:44'),(6015,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-image.png','images/big_icons/48x48/places/crystal-style/folder-image.png',4204,1,'2012-10-28 09:06:44'),(6016,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-lin.png','images/big_icons/48x48/places/crystal-style/folder-lin.png',3741,1,'2012-10-28 09:06:44'),(6017,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-locked.png','images/big_icons/48x48/places/crystal-style/folder-locked.png',3566,1,'2012-10-28 09:06:44'),(6018,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-music.png','images/big_icons/48x48/places/crystal-style/folder-music.png',3654,1,'2012-10-28 09:06:44'),(6019,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-print.png','images/big_icons/48x48/places/crystal-style/folder-print.png',3501,1,'2012-10-28 09:06:44'),(6020,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-public.png','images/big_icons/48x48/places/crystal-style/folder-public.png',3880,1,'2012-10-28 09:06:44'),(6021,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-red.png','images/big_icons/48x48/places/crystal-style/folder-red.png',3098,1,'2012-10-28 09:06:44'),(6022,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-sound.png','images/big_icons/48x48/places/crystal-style/folder-sound.png',3862,1,'2012-10-28 09:06:44'),(6023,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-tar.png','images/big_icons/48x48/places/crystal-style/folder-tar.png',3553,1,'2012-10-28 09:06:44'),(6024,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-video.png','images/big_icons/48x48/places/crystal-style/folder-video.png',3667,1,'2012-10-28 09:06:44'),(6025,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-yellow.png','images/big_icons/48x48/places/crystal-style/folder-yellow.png',3260,1,'2012-10-28 09:06:44'),(6026,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder.png','images/big_icons/48x48/places/crystal-style/folder.png',3052,1,'2012-10-28 09:06:44'),(6027,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'ftp.png','images/big_icons/48x48/places/crystal-style/ftp.png',3022,1,'2012-10-28 09:06:44'),(6028,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'globe.png','images/big_icons/48x48/places/crystal-style/globe.png',5296,1,'2012-10-28 09:06:44'),(6029,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'globe2.png','images/big_icons/48x48/places/crystal-style/globe2.png',5249,1,'2012-10-28 09:06:44'),(6030,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'link.png','images/big_icons/48x48/places/crystal-style/link.png',1048,1,'2012-10-28 09:06:44'),(6031,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'lockoverlay.png','images/big_icons/48x48/places/crystal-style/lockoverlay.png',2130,1,'2012-10-28 09:06:44'),(6032,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'mail-folder-inbox.png','images/big_icons/48x48/places/crystal-style/mail-folder-inbox.png',3451,1,'2012-10-28 09:06:44'),(6033,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'mail-folder-outbox.png','images/big_icons/48x48/places/crystal-style/mail-folder-outbox.png',2661,1,'2012-10-28 09:06:44'),(6034,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'my_computer.png','images/big_icons/48x48/places/crystal-style/my_computer.png',5004,1,'2012-10-28 09:06:44'),(6035,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'my_documents.png','images/big_icons/48x48/places/crystal-style/my_documents.png',4531,1,'2012-10-28 09:06:44'),(6036,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'my_linspire.png','images/big_icons/48x48/places/crystal-style/my_linspire.png',4093,1,'2012-10-28 09:06:44'),(6037,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'my_mac.png','images/big_icons/48x48/places/crystal-style/my_mac.png',3720,1,'2012-10-28 09:06:44'),(6038,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-local.png','images/big_icons/48x48/places/crystal-style/network-local.png',5069,1,'2012-10-28 09:06:44'),(6039,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network.png','images/big_icons/48x48/places/crystal-style/network.png',5155,1,'2012-10-28 09:06:44'),(6040,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'programs.png','images/big_icons/48x48/places/crystal-style/programs.png',3074,1,'2012-10-28 09:06:44'),(6041,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'services.png','images/big_icons/48x48/places/crystal-style/services.png',3480,1,'2012-10-28 09:06:44'),(6042,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'socket.png','images/big_icons/48x48/places/crystal-style/socket.png',5494,1,'2012-10-28 09:06:44'),(6043,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-desktop.png','images/big_icons/48x48/places/crystal-style/user-desktop.png',3647,1,'2012-10-28 09:06:44'),(6044,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-home.png','images/big_icons/48x48/places/crystal-style/user-home.png',4147,1,'2012-10-28 09:06:44'),(6045,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-home2-txr.png','images/big_icons/48x48/places/crystal-style/user-home2-txr.png',4798,1,'2012-10-28 09:06:44'),(6046,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash-full.png','images/big_icons/48x48/places/crystal-style/user-trash-full.png',3708,1,'2012-10-28 09:06:44'),(6047,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash.png','images/big_icons/48x48/places/crystal-style/user-trash.png',3468,1,'2012-10-28 09:06:44'),(6048,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'www.png','images/big_icons/48x48/places/crystal-style/www.png',3150,1,'2012-10-28 09:06:44'),(6049,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'zip.png','images/big_icons/48x48/places/crystal-style/zip.png',1332,1,'2012-10-28 09:06:44'),(6050,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'blockdevice.png','images/big_icons/48x48/places/crystal_clear-style/blockdevice.png',4644,1,'2012-10-28 09:06:44'),(6051,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'chardevice.png','images/big_icons/48x48/places/crystal_clear-style/chardevice.png',3502,1,'2012-10-28 09:06:44'),(6052,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-broken.png','images/big_icons/48x48/places/crystal_clear-style/file-broken.png',1910,1,'2012-10-28 09:06:44'),(6053,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-important.png','images/big_icons/48x48/places/crystal_clear-style/file-important.png',3992,1,'2012-10-28 09:06:44'),(6054,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-temporary.png','images/big_icons/48x48/places/crystal_clear-style/file-temporary.png',3855,1,'2012-10-28 09:06:44'),(6055,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-blue.png','images/big_icons/48x48/places/crystal_clear-style/folder-blue.png',3159,1,'2012-10-28 09:06:44'),(6056,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-desktop.png','images/big_icons/48x48/places/crystal_clear-style/folder-desktop.png',3815,1,'2012-10-28 09:06:44'),(6057,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-favorites.png','images/big_icons/48x48/places/crystal_clear-style/folder-favorites.png',4020,1,'2012-10-28 09:06:44'),(6058,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-green.png','images/big_icons/48x48/places/crystal_clear-style/folder-green.png',3129,1,'2012-10-28 09:06:44'),(6059,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-grey.png','images/big_icons/48x48/places/crystal_clear-style/folder-grey.png',1773,1,'2012-10-28 09:06:44'),(6060,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-grey_open.png','images/big_icons/48x48/places/crystal_clear-style/folder-grey_open.png',2422,1,'2012-10-28 09:06:44'),(6061,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-html.png','images/big_icons/48x48/places/crystal_clear-style/folder-html.png',4558,1,'2012-10-28 09:06:44'),(6062,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-image.png','images/big_icons/48x48/places/crystal_clear-style/folder-image.png',3857,1,'2012-10-28 09:06:44'),(6063,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-lin.png','images/big_icons/48x48/places/crystal_clear-style/folder-lin.png',3994,1,'2012-10-28 09:06:44'),(6064,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-locked.png','images/big_icons/48x48/places/crystal_clear-style/folder-locked.png',3845,1,'2012-10-28 09:06:44'),(6065,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-music.png','images/big_icons/48x48/places/crystal_clear-style/folder-music.png',3606,1,'2012-10-28 09:06:44'),(6066,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-print.png','images/big_icons/48x48/places/crystal_clear-style/folder-print.png',3595,1,'2012-10-28 09:06:44'),(6067,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-public.png','images/big_icons/48x48/places/crystal_clear-style/folder-public.png',4399,1,'2012-10-28 09:06:44'),(6068,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-red.png','images/big_icons/48x48/places/crystal_clear-style/folder-red.png',3149,1,'2012-10-28 09:06:44'),(6069,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-sound.png','images/big_icons/48x48/places/crystal_clear-style/folder-sound.png',4420,1,'2012-10-28 09:06:44'),(6070,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-tar.png','images/big_icons/48x48/places/crystal_clear-style/folder-tar.png',3890,1,'2012-10-28 09:06:44'),(6071,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-txt.png','images/big_icons/48x48/places/crystal_clear-style/folder-txt.png',3914,1,'2012-10-28 09:06:44'),(6072,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-video.png','images/big_icons/48x48/places/crystal_clear-style/folder-video.png',4604,1,'2012-10-28 09:06:44'),(6073,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-yellow.png','images/big_icons/48x48/places/crystal_clear-style/folder-yellow.png',2994,1,'2012-10-28 09:06:44'),(6074,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder.png','images/big_icons/48x48/places/crystal_clear-style/folder.png',2970,1,'2012-10-28 09:06:44'),(6075,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'lockoverlay.png','images/big_icons/48x48/places/crystal_clear-style/lockoverlay.png',1323,1,'2012-10-28 09:06:44'),(6076,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-local.png','images/big_icons/48x48/places/crystal_clear-style/network-local.png',4167,1,'2012-10-28 09:06:44'),(6077,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'pipe.png','images/big_icons/48x48/places/crystal_clear-style/pipe.png',4663,1,'2012-10-28 09:06:44'),(6078,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'socket.png','images/big_icons/48x48/places/crystal_clear-style/socket.png',5477,1,'2012-10-28 09:06:44'),(6079,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-desktop-txr.png','images/big_icons/48x48/places/crystal_clear-style/user-desktop-txr.png',5057,1,'2012-10-28 09:06:44'),(6080,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-home.png','images/big_icons/48x48/places/crystal_clear-style/user-home.png',4094,1,'2012-10-28 09:06:44'),(6081,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-home2.png','images/big_icons/48x48/places/crystal_clear-style/user-home2.png',4089,1,'2012-10-28 09:06:44'),(6082,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash-full.png','images/big_icons/48x48/places/crystal_clear-style/user-trash-full.png',3842,1,'2012-10-28 09:06:44'),(6083,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash.png','images/big_icons/48x48/places/crystal_clear-style/user-trash.png',3361,1,'2012-10-28 09:06:44'),(6084,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-remote.png','images/big_icons/48x48/places/gnome-style/folder-remote.png',2717,1,'2012-10-28 09:06:44'),(6085,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-saved-search.png','images/big_icons/48x48/places/gnome-style/folder-saved-search.png',2698,1,'2012-10-28 09:06:44'),(6086,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder.png','images/big_icons/48x48/places/gnome-style/folder.png',1961,1,'2012-10-28 09:06:44'),(6087,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-server-2.png','images/big_icons/48x48/places/gnome-style/network-server-2.png',3292,1,'2012-10-28 09:06:44'),(6088,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-server.png','images/big_icons/48x48/places/gnome-style/network-server.png',3027,1,'2012-10-28 09:06:44'),(6089,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-workgroup.png','images/big_icons/48x48/places/gnome-style/network-workgroup.png',3640,1,'2012-10-28 09:06:44'),(6090,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'start-here-2.png','images/big_icons/48x48/places/gnome-style/start-here-2.png',4027,1,'2012-10-28 09:06:44'),(6091,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'start-here.png','images/big_icons/48x48/places/gnome-style/start-here.png',3570,1,'2012-10-28 09:06:44'),(6092,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-bookmarks.png','images/big_icons/48x48/places/gnome-style/user-bookmarks.png',3085,1,'2012-10-28 09:06:44'),(6093,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-desktop.png','images/big_icons/48x48/places/gnome-style/user-desktop.png',3457,1,'2012-10-28 09:06:44'),(6094,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-home.png','images/big_icons/48x48/places/gnome-style/user-home.png',2394,1,'2012-10-28 09:06:44'),(6095,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash-2.png','images/big_icons/48x48/places/gnome-style/user-trash-2.png',5386,1,'2012-10-28 09:06:44'),(6096,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash.png','images/big_icons/48x48/places/gnome-style/user-trash.png',2725,1,'2012-10-28 09:06:44'),(6097,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'book.png','images/big_icons/48x48/places/intrigue-style/book.png',1958,1,'2012-10-28 09:06:44'),(6098,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-broken.png','images/big_icons/48x48/places/intrigue-style/file-broken.png',3543,1,'2012-10-28 09:06:44'),(6099,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-bin.png','images/big_icons/48x48/places/intrigue-style/folder-bin.png',3079,1,'2012-10-28 09:06:44'),(6100,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-blender.png','images/big_icons/48x48/places/intrigue-style/folder-blender.png',3821,1,'2012-10-28 09:06:44'),(6101,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-blue.png','images/big_icons/48x48/places/intrigue-style/folder-blue.png',2822,1,'2012-10-28 09:06:44'),(6102,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-coffee.png','images/big_icons/48x48/places/intrigue-style/folder-coffee.png',3604,1,'2012-10-28 09:06:44'),(6103,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-crystal.png','images/big_icons/48x48/places/intrigue-style/folder-crystal.png',3487,1,'2012-10-28 09:06:44'),(6104,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-develop.png','images/big_icons/48x48/places/intrigue-style/folder-develop.png',3750,1,'2012-10-28 09:06:44'),(6105,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-documents.png','images/big_icons/48x48/places/intrigue-style/folder-documents.png',3240,1,'2012-10-28 09:06:44'),(6106,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-favorite.png','images/big_icons/48x48/places/intrigue-style/folder-favorite.png',3323,1,'2012-10-28 09:06:44'),(6107,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-games.png','images/big_icons/48x48/places/intrigue-style/folder-games.png',3391,1,'2012-10-28 09:06:44'),(6108,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-green.png','images/big_icons/48x48/places/intrigue-style/folder-green.png',2827,1,'2012-10-28 09:06:44'),(6109,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-grey.png','images/big_icons/48x48/places/intrigue-style/folder-grey.png',2674,1,'2012-10-28 09:06:44'),(6110,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-html.png','images/big_icons/48x48/places/intrigue-style/folder-html.png',3813,1,'2012-10-28 09:06:44'),(6111,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-ideas.png','images/big_icons/48x48/places/intrigue-style/folder-ideas.png',3450,1,'2012-10-28 09:06:44'),(6112,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-image.png','images/big_icons/48x48/places/intrigue-style/folder-image.png',3406,1,'2012-10-28 09:06:44'),(6113,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-important.png','images/big_icons/48x48/places/intrigue-style/folder-important.png',3073,1,'2012-10-28 09:06:44'),(6114,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-incomplete.png','images/big_icons/48x48/places/intrigue-style/folder-incomplete.png',4753,1,'2012-10-28 09:06:44'),(6115,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-incomplete2.png','images/big_icons/48x48/places/intrigue-style/folder-incomplete2.png',3500,1,'2012-10-28 09:06:44'),(6116,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-iso.png','images/big_icons/48x48/places/intrigue-style/folder-iso.png',3634,1,'2012-10-28 09:06:44'),(6117,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-kde.png','images/big_icons/48x48/places/intrigue-style/folder-kde.png',3545,1,'2012-10-28 09:06:44'),(6118,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-leaf.png','images/big_icons/48x48/places/intrigue-style/folder-leaf.png',3535,1,'2012-10-28 09:06:44'),(6119,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-locked.png','images/big_icons/48x48/places/intrigue-style/folder-locked.png',3186,1,'2012-10-28 09:06:44'),(6120,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-man.png','images/big_icons/48x48/places/intrigue-style/folder-man.png',3485,1,'2012-10-28 09:06:44'),(6121,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-multimedia.png','images/big_icons/48x48/places/intrigue-style/folder-multimedia.png',3880,1,'2012-10-28 09:06:44'),(6122,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-open.png','images/big_icons/48x48/places/intrigue-style/folder-open.png',3105,1,'2012-10-28 09:06:44'),(6123,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-orange.png','images/big_icons/48x48/places/intrigue-style/folder-orange.png',2744,1,'2012-10-28 09:06:44'),(6124,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-packages.png','images/big_icons/48x48/places/intrigue-style/folder-packages.png',3463,1,'2012-10-28 09:06:44'),(6125,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-personal.png','images/big_icons/48x48/places/intrigue-style/folder-personal.png',3441,1,'2012-10-28 09:06:44'),(6126,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-photo.png','images/big_icons/48x48/places/intrigue-style/folder-photo.png',3788,1,'2012-10-28 09:06:44'),(6127,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-qt.png','images/big_icons/48x48/places/intrigue-style/folder-qt.png',3615,1,'2012-10-28 09:06:44'),(6128,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-red.png','images/big_icons/48x48/places/intrigue-style/folder-red.png',2773,1,'2012-10-28 09:06:44'),(6129,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-ruby.png','images/big_icons/48x48/places/intrigue-style/folder-ruby.png',3978,1,'2012-10-28 09:06:44'),(6130,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-searched.png','images/big_icons/48x48/places/intrigue-style/folder-searched.png',3960,1,'2012-10-28 09:06:44'),(6131,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-simplefoundation.png','images/big_icons/48x48/places/intrigue-style/folder-simplefoundation.png',3240,1,'2012-10-28 09:06:44'),(6132,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-sound.png','images/big_icons/48x48/places/intrigue-style/folder-sound.png',3603,1,'2012-10-28 09:06:44'),(6133,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-starred.png','images/big_icons/48x48/places/intrigue-style/folder-starred.png',3432,1,'2012-10-28 09:06:44'),(6134,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-system.png','images/big_icons/48x48/places/intrigue-style/folder-system.png',3285,1,'2012-10-28 09:06:44'),(6135,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-tar.png','images/big_icons/48x48/places/intrigue-style/folder-tar.png',3624,1,'2012-10-28 09:06:44'),(6136,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-tasks.png','images/big_icons/48x48/places/intrigue-style/folder-tasks.png',3111,1,'2012-10-28 09:06:44'),(6137,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-time.png','images/big_icons/48x48/places/intrigue-style/folder-time.png',4075,1,'2012-10-28 09:06:44'),(6138,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-txt.png','images/big_icons/48x48/places/intrigue-style/folder-txt.png',3532,1,'2012-10-28 09:06:44'),(6139,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-video.png','images/big_icons/48x48/places/intrigue-style/folder-video.png',3482,1,'2012-10-28 09:06:44'),(6140,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-violet.png','images/big_icons/48x48/places/intrigue-style/folder-violet.png',2712,1,'2012-10-28 09:06:44'),(6141,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-yellow.png','images/big_icons/48x48/places/intrigue-style/folder-yellow.png',2666,1,'2012-10-28 09:06:44'),(6142,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder.png','images/big_icons/48x48/places/intrigue-style/folder.png',2828,1,'2012-10-28 09:06:44'),(6143,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'lockoverlay.png','images/big_icons/48x48/places/intrigue-style/lockoverlay.png',1120,1,'2012-10-28 09:06:44'),(6144,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'my_computer.png','images/big_icons/48x48/places/intrigue-style/my_computer.png',2561,1,'2012-10-28 09:06:44'),(6145,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-local.png','images/big_icons/48x48/places/intrigue-style/network-local.png',3735,1,'2012-10-28 09:06:44'),(6146,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network.png','images/big_icons/48x48/places/intrigue-style/network.png',4767,1,'2012-10-28 09:06:44'),(6147,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-desktop.png','images/big_icons/48x48/places/intrigue-style/user-desktop.png',3520,1,'2012-10-28 09:06:46'),(6148,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-home.png','images/big_icons/48x48/places/intrigue-style/user-home.png',3469,1,'2012-10-28 09:06:46'),(6149,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash-full.png','images/big_icons/48x48/places/intrigue-style/user-trash-full.png',3164,1,'2012-10-28 09:06:46'),(6150,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash.png','images/big_icons/48x48/places/intrigue-style/user-trash.png',2092,1,'2012-10-28 09:06:46'),(6151,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'www.png','images/big_icons/48x48/places/intrigue-style/www.png',3292,1,'2012-10-28 09:06:46'),(6152,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'chardevice.png','images/big_icons/48x48/places/nuvola-style/chardevice.png',3700,1,'2012-10-28 09:06:46'),(6153,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-broken.png','images/big_icons/48x48/places/nuvola-style/file-broken.png',3437,1,'2012-10-28 09:06:46'),(6154,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-important.png','images/big_icons/48x48/places/nuvola-style/file-important.png',2995,1,'2012-10-28 09:06:46'),(6155,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-locked.png','images/big_icons/48x48/places/nuvola-style/file-locked.png',2686,1,'2012-10-28 09:06:46'),(6156,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'file-temporary.png','images/big_icons/48x48/places/nuvola-style/file-temporary.png',3598,1,'2012-10-28 09:06:46'),(6157,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-blue.png','images/big_icons/48x48/places/nuvola-style/folder-blue.png',4394,1,'2012-10-28 09:06:46'),(6158,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-blue_open.png','images/big_icons/48x48/places/nuvola-style/folder-blue_open.png',3461,1,'2012-10-28 09:06:46'),(6159,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-cool.png','images/big_icons/48x48/places/nuvola-style/folder-cool.png',4783,1,'2012-10-28 09:06:46'),(6160,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-cyan.png','images/big_icons/48x48/places/nuvola-style/folder-cyan.png',4564,1,'2012-10-28 09:06:46'),(6161,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-cyan_open.png','images/big_icons/48x48/places/nuvola-style/folder-cyan_open.png',3699,1,'2012-10-28 09:06:46'),(6162,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-download.png','images/big_icons/48x48/places/nuvola-style/folder-download.png',4592,1,'2012-10-28 09:06:46'),(6163,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-favorite.png','images/big_icons/48x48/places/nuvola-style/folder-favorite.png',4616,1,'2012-10-28 09:06:46'),(6164,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-font.png','images/big_icons/48x48/places/nuvola-style/folder-font.png',4978,1,'2012-10-28 09:06:46'),(6165,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-games.png','images/big_icons/48x48/places/nuvola-style/folder-games.png',4902,1,'2012-10-28 09:06:46'),(6166,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-green.png','images/big_icons/48x48/places/nuvola-style/folder-green.png',4608,1,'2012-10-28 09:06:46'),(6167,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-green_open.png','images/big_icons/48x48/places/nuvola-style/folder-green_open.png',3668,1,'2012-10-28 09:06:46'),(6168,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-grey.png','images/big_icons/48x48/places/nuvola-style/folder-grey.png',4501,1,'2012-10-28 09:06:46'),(6169,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-grey_open.png','images/big_icons/48x48/places/nuvola-style/folder-grey_open.png',3595,1,'2012-10-28 09:06:46'),(6170,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-html.png','images/big_icons/48x48/places/nuvola-style/folder-html.png',4972,1,'2012-10-28 09:06:46'),(6171,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-image.png','images/big_icons/48x48/places/nuvola-style/folder-image.png',4543,1,'2012-10-28 09:06:46'),(6172,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-important.png','images/big_icons/48x48/places/nuvola-style/folder-important.png',4763,1,'2012-10-28 09:06:46'),(6173,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-locked.png','images/big_icons/48x48/places/nuvola-style/folder-locked.png',4640,1,'2012-10-28 09:06:46'),(6174,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-mail.png','images/big_icons/48x48/places/nuvola-style/folder-mail.png',4348,1,'2012-10-28 09:06:46'),(6175,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-man.png','images/big_icons/48x48/places/nuvola-style/folder-man.png',5008,1,'2012-10-28 09:06:46'),(6176,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-midi.png','images/big_icons/48x48/places/nuvola-style/folder-midi.png',4372,1,'2012-10-28 09:06:46'),(6177,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-open.png','images/big_icons/48x48/places/nuvola-style/folder-open.png',3461,1,'2012-10-28 09:06:46'),(6178,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-orange.png','images/big_icons/48x48/places/nuvola-style/folder-orange.png',4392,1,'2012-10-28 09:06:46'),(6179,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-orange_open.png','images/big_icons/48x48/places/nuvola-style/folder-orange_open.png',3272,1,'2012-10-28 09:06:46'),(6180,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-photo.png','images/big_icons/48x48/places/nuvola-style/folder-photo.png',4533,1,'2012-10-28 09:06:46'),(6181,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-print.png','images/big_icons/48x48/places/nuvola-style/folder-print.png',4712,1,'2012-10-28 09:06:46'),(6182,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-red.png','images/big_icons/48x48/places/nuvola-style/folder-red.png',4491,1,'2012-10-28 09:06:46'),(6183,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-red_open.png','images/big_icons/48x48/places/nuvola-style/folder-red_open.png',3393,1,'2012-10-28 09:06:46'),(6184,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-sound.png','images/big_icons/48x48/places/nuvola-style/folder-sound.png',4937,1,'2012-10-28 09:06:46'),(6185,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-tar.png','images/big_icons/48x48/places/nuvola-style/folder-tar.png',4631,1,'2012-10-28 09:06:46'),(6186,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-txt.png','images/big_icons/48x48/places/nuvola-style/folder-txt.png',4957,1,'2012-10-28 09:06:46'),(6187,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-video.png','images/big_icons/48x48/places/nuvola-style/folder-video.png',4503,1,'2012-10-28 09:06:46'),(6188,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-violet.png','images/big_icons/48x48/places/nuvola-style/folder-violet.png',4563,1,'2012-10-28 09:06:46'),(6189,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-violet_open.png','images/big_icons/48x48/places/nuvola-style/folder-violet_open.png',3692,1,'2012-10-28 09:06:46'),(6190,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-yellow.png','images/big_icons/48x48/places/nuvola-style/folder-yellow.png',4355,1,'2012-10-28 09:06:46'),(6191,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-yellow_open.png','images/big_icons/48x48/places/nuvola-style/folder-yellow_open.png',3279,1,'2012-10-28 09:06:46'),(6192,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder.png','images/big_icons/48x48/places/nuvola-style/folder.png',4394,1,'2012-10-28 09:06:46'),(6193,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'ftp.png','images/big_icons/48x48/places/nuvola-style/ftp.png',5048,1,'2012-10-28 09:06:46'),(6194,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'link.png','images/big_icons/48x48/places/nuvola-style/link.png',1145,1,'2012-10-28 09:06:46'),(6195,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'lockoverlay.png','images/big_icons/48x48/places/nuvola-style/lockoverlay.png',1569,1,'2012-10-28 09:06:46'),(6196,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'my_computer.png','images/big_icons/48x48/places/nuvola-style/my_computer.png',4551,1,'2012-10-28 09:06:46'),(6197,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-local.png','images/big_icons/48x48/places/nuvola-style/network-local.png',3079,1,'2012-10-28 09:06:46'),(6198,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-server.png','images/big_icons/48x48/places/nuvola-style/network-server.png',3076,1,'2012-10-28 09:06:46'),(6199,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network.png','images/big_icons/48x48/places/nuvola-style/network.png',4332,1,'2012-10-28 09:06:46'),(6200,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'pipe.png','images/big_icons/48x48/places/nuvola-style/pipe.png',2558,1,'2012-10-28 09:06:46'),(6201,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'services.png','images/big_icons/48x48/places/nuvola-style/services.png',3171,1,'2012-10-28 09:06:46'),(6202,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'socket.png','images/big_icons/48x48/places/nuvola-style/socket.png',4800,1,'2012-10-28 09:06:46'),(6203,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-desktop.png','images/big_icons/48x48/places/nuvola-style/user-desktop.png',4465,1,'2012-10-28 09:06:46'),(6204,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-home.png','images/big_icons/48x48/places/nuvola-style/user-home.png',4414,1,'2012-10-28 09:06:46'),(6205,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash-full.png','images/big_icons/48x48/places/nuvola-style/user-trash-full.png',4277,1,'2012-10-28 09:06:46'),(6206,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash.png','images/big_icons/48x48/places/nuvola-style/user-trash.png',3243,1,'2012-10-28 09:06:46'),(6207,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'www.png','images/big_icons/48x48/places/nuvola-style/www.png',5045,1,'2012-10-28 09:06:46'),(6208,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'zip.png','images/big_icons/48x48/places/nuvola-style/zip.png',1048,1,'2012-10-28 09:06:46'),(6209,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'bookmarks.png','images/big_icons/48x48/places/oxygen-style/bookmarks.png',3529,1,'2012-10-28 09:06:46'),(6210,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'document-multiple.png','images/big_icons/48x48/places/oxygen-style/document-multiple.png',2049,1,'2012-10-28 09:06:46'),(6211,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'favorites.png','images/big_icons/48x48/places/oxygen-style/favorites.png',3529,1,'2012-10-28 09:06:46'),(6212,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-blue.png','images/big_icons/48x48/places/oxygen-style/folder-blue.png',1468,1,'2012-10-28 09:06:46'),(6213,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-bookmark.png','images/big_icons/48x48/places/oxygen-style/folder-bookmark.png',2228,1,'2012-10-28 09:06:46'),(6214,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-brown.png','images/big_icons/48x48/places/oxygen-style/folder-brown.png',1447,1,'2012-10-28 09:06:46'),(6215,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-cyan.png','images/big_icons/48x48/places/oxygen-style/folder-cyan.png',1451,1,'2012-10-28 09:06:46'),(6216,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-development.png','images/big_icons/48x48/places/oxygen-style/folder-development.png',2777,1,'2012-10-28 09:06:46'),(6217,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-documents.png','images/big_icons/48x48/places/oxygen-style/folder-documents.png',2112,1,'2012-10-28 09:06:46'),(6218,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-downloads.png','images/big_icons/48x48/places/oxygen-style/folder-downloads.png',2660,1,'2012-10-28 09:06:46'),(6219,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-favorites.png','images/big_icons/48x48/places/oxygen-style/folder-favorites.png',3201,1,'2012-10-28 09:06:46'),(6220,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-green.png','images/big_icons/48x48/places/oxygen-style/folder-green.png',1483,1,'2012-10-28 09:06:46'),(6221,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-grey.png','images/big_icons/48x48/places/oxygen-style/folder-grey.png',1041,1,'2012-10-28 09:06:46'),(6222,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-html.png','images/big_icons/48x48/places/oxygen-style/folder-html.png',3010,1,'2012-10-28 09:06:46'),(6223,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-image.png','images/big_icons/48x48/places/oxygen-style/folder-image.png',2971,1,'2012-10-28 09:06:46'),(6224,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-important.png','images/big_icons/48x48/places/oxygen-style/folder-important.png',2915,1,'2012-10-28 09:06:46'),(6225,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-locked.png','images/big_icons/48x48/places/oxygen-style/folder-locked.png',2474,1,'2012-10-28 09:06:46'),(6226,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-orange.png','images/big_icons/48x48/places/oxygen-style/folder-orange.png',1461,1,'2012-10-28 09:06:46'),(6227,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-print.png','images/big_icons/48x48/places/oxygen-style/folder-print.png',2776,1,'2012-10-28 09:06:46'),(6228,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-red.png','images/big_icons/48x48/places/oxygen-style/folder-red.png',1445,1,'2012-10-28 09:06:46'),(6229,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-remote.png','images/big_icons/48x48/places/oxygen-style/folder-remote.png',4400,1,'2012-10-28 09:06:46'),(6230,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-sound.png','images/big_icons/48x48/places/oxygen-style/folder-sound.png',2359,1,'2012-10-28 09:06:46'),(6231,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-tar.png','images/big_icons/48x48/places/oxygen-style/folder-tar.png',2802,1,'2012-10-28 09:06:46'),(6232,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-txt.png','images/big_icons/48x48/places/oxygen-style/folder-txt.png',2788,1,'2012-10-28 09:06:46'),(6233,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-video.png','images/big_icons/48x48/places/oxygen-style/folder-video.png',3298,1,'2012-10-28 09:06:46'),(6234,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-violet.png','images/big_icons/48x48/places/oxygen-style/folder-violet.png',1476,1,'2012-10-28 09:06:46'),(6235,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-yellow.png','images/big_icons/48x48/places/oxygen-style/folder-yellow.png',1371,1,'2012-10-28 09:06:46'),(6236,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder.png','images/big_icons/48x48/places/oxygen-style/folder.png',1602,1,'2012-10-28 09:06:46'),(6237,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'mail-folder-inbox.png','images/big_icons/48x48/places/oxygen-style/mail-folder-inbox.png',2435,1,'2012-10-28 09:06:46'),(6238,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'mail-folder-outbox.png','images/big_icons/48x48/places/oxygen-style/mail-folder-outbox.png',3420,1,'2012-10-28 09:06:46'),(6239,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'mail-folder-sent.png','images/big_icons/48x48/places/oxygen-style/mail-folder-sent.png',2915,1,'2012-10-28 09:06:46'),(6240,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'mail-message.png','images/big_icons/48x48/places/oxygen-style/mail-message.png',1773,1,'2012-10-28 09:06:46'),(6241,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-server-database.png','images/big_icons/48x48/places/oxygen-style/network-server-database.png',3772,1,'2012-10-28 09:06:46'),(6242,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-server.png','images/big_icons/48x48/places/oxygen-style/network-server.png',2643,1,'2012-10-28 09:06:46'),(6243,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'network-workgroup.png','images/big_icons/48x48/places/oxygen-style/network-workgroup.png',5002,1,'2012-10-28 09:06:46'),(6244,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'repository.png','images/big_icons/48x48/places/oxygen-style/repository.png',5423,1,'2012-10-28 09:06:46'),(6245,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'server-database.png','images/big_icons/48x48/places/oxygen-style/server-database.png',2854,1,'2012-10-28 09:06:46'),(6246,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'start-here-kde.png','images/big_icons/48x48/places/oxygen-style/start-here-kde.png',5040,1,'2012-10-28 09:06:46'),(6247,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'start-here.png','images/big_icons/48x48/places/oxygen-style/start-here.png',5516,1,'2012-10-28 09:06:46'),(6248,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-desktop.png','images/big_icons/48x48/places/oxygen-style/user-desktop.png',3319,1,'2012-10-28 09:06:46'),(6249,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-home.png','images/big_icons/48x48/places/oxygen-style/user-home.png',2234,1,'2012-10-28 09:06:46'),(6250,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'user-trash.png','images/big_icons/48x48/places/oxygen-style/user-trash.png',3562,1,'2012-10-28 09:06:46'),(6251,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-accessibility.png','images/big_icons/48x48/places/tango-style/folder-accessibility.png',3238,1,'2012-10-28 09:06:46'),(6252,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-development.png','images/big_icons/48x48/places/tango-style/folder-development.png',3711,1,'2012-10-28 09:06:46'),(6253,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-document.png','images/big_icons/48x48/places/tango-style/folder-document.png',2833,1,'2012-10-28 09:06:46'),(6254,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-downloads.png','images/big_icons/48x48/places/tango-style/folder-downloads.png',3255,1,'2012-10-28 09:06:46'),(6255,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-forbidden.png','images/big_icons/48x48/places/tango-style/folder-forbidden.png',3719,1,'2012-10-28 09:06:46'),(6256,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-games.png','images/big_icons/48x48/places/tango-style/folder-games.png',3628,1,'2012-10-28 09:06:46'),(6257,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-html.png','images/big_icons/48x48/places/tango-style/folder-html.png',3917,1,'2012-10-28 09:06:46'),(6258,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-image.png','images/big_icons/48x48/places/tango-style/folder-image.png',2978,1,'2012-10-28 09:06:46'),(6259,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-important.png','images/big_icons/48x48/places/tango-style/folder-important.png',3527,1,'2012-10-28 09:06:46'),(6260,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-locked.png','images/big_icons/48x48/places/tango-style/folder-locked.png',3301,1,'2012-10-28 09:06:46'),(6261,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-photo.png','images/big_icons/48x48/places/tango-style/folder-photo.png',3360,1,'2012-10-28 09:06:46'),(6262,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-photo2.png','images/big_icons/48x48/places/tango-style/folder-photo2.png',3326,1,'2012-10-28 09:06:46'),(6263,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-print.png','images/big_icons/48x48/places/tango-style/folder-print.png',2888,1,'2012-10-28 09:06:46'),(6264,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-remote-smb.png','images/big_icons/48x48/places/tango-style/folder-remote-smb.png',3613,1,'2012-10-28 09:06:46'),(6265,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-remote-ssh.png','images/big_icons/48x48/places/tango-style/folder-remote-ssh.png',3639,1,'2012-10-28 09:06:46'),(6266,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-remote.png','images/big_icons/48x48/places/tango-style/folder-remote.png',3503,1,'2012-10-28 09:06:46'),(6267,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-saved-search.png','images/big_icons/48x48/places/tango-style/folder-saved-search.png',3547,1,'2012-10-28 09:06:46'),(6268,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-sound.png','images/big_icons/48x48/places/tango-style/folder-sound.png',3450,1,'2012-10-28 09:06:46'),(6269,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-tar.png','images/big_icons/48x48/places/tango-style/folder-tar.png',3085,1,'2012-10-28 09:06:46'),(6270,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-txt.png','images/big_icons/48x48/places/tango-style/folder-txt.png',3597,1,'2012-10-28 09:06:46'),(6271,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-user.png','images/big_icons/48x48/places/tango-style/folder-user.png',3605,1,'2012-10-28 09:06:46'),(6272,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:26',-1,NULL,'folder-video.png','images/big_icons/48x48/places/tango-style/folder-video.png',3601,1,'2012-10-28 09:06:46'),(6273,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder.png','images/big_icons/48x48/places/tango-style/folder.png',2704,1,'2012-10-28 09:06:46'),(6274,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-server.png','images/big_icons/48x48/places/tango-style/network-server.png',1628,1,'2012-10-28 09:06:46'),(6275,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-workgroup.png','images/big_icons/48x48/places/tango-style/network-workgroup.png',2991,1,'2012-10-28 09:06:46'),(6276,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'start-here.png','images/big_icons/48x48/places/tango-style/start-here.png',4027,1,'2012-10-28 09:06:46'),(6277,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-desktop.png','images/big_icons/48x48/places/tango-style/user-desktop.png',2774,1,'2012-10-28 09:06:46'),(6278,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-home.png','images/big_icons/48x48/places/tango-style/user-home.png',3341,1,'2012-10-28 09:06:46'),(6279,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-trash.png','images/big_icons/48x48/places/tango-style/user-trash.png',4757,1,'2012-10-28 09:06:46'),(6280,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_1.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_1.png',2318,1,'2012-10-28 09:06:46'),(6281,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_10.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_10.png',3074,1,'2012-10-28 09:06:46'),(6282,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_11.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_11.png',3233,1,'2012-10-28 09:06:46'),(6283,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_12.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_12.png',2458,1,'2012-10-28 09:06:46'),(6284,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_13.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_13.png',3020,1,'2012-10-28 09:06:46'),(6285,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_14.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_14.png',2886,1,'2012-10-28 09:06:46'),(6286,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_15.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_15.png',2400,1,'2012-10-28 09:06:46'),(6287,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_2.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_2.png',3031,1,'2012-10-28 09:06:46'),(6288,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_3.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_3.png',3019,1,'2012-10-28 09:06:46'),(6289,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_4.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_4.png',3021,1,'2012-10-28 09:06:46'),(6290,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_5.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_5.png',2651,1,'2012-10-28 09:06:46'),(6291,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_6.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_6.png',2415,1,'2012-10-28 09:06:46'),(6292,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_7.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_7.png',2537,1,'2012-10-28 09:06:46'),(6293,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_8.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_8.png',3015,1,'2012-10-28 09:06:46'),(6294,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'anonymous_simple_weather_symbols_9.png','images/big_icons/48x48/status/anonymous_simple_weather_symbols_9.png',3531,1,'2012-10-28 09:06:46'),(6295,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'appointment-missed.png','images/big_icons/48x48/status/appointment-missed.png',5415,1,'2012-10-28 09:06:46'),(6296,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'appointment-soon.png','images/big_icons/48x48/status/appointment-soon.png',5176,1,'2012-10-28 09:06:46'),(6297,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-high-2.png','images/big_icons/48x48/status/audio-volume-high-2.png',5942,1,'2012-10-28 09:06:46'),(6298,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-high-3.png','images/big_icons/48x48/status/audio-volume-high-3.png',2794,1,'2012-10-28 09:06:46'),(6299,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-high-4.png','images/big_icons/48x48/status/audio-volume-high-4.png',3095,1,'2012-10-28 09:06:46'),(6300,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-high-5.png','images/big_icons/48x48/status/audio-volume-high-5.png',1135,1,'2012-10-28 09:06:46'),(6301,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-high.png','images/big_icons/48x48/status/audio-volume-high.png',4275,1,'2012-10-28 09:06:46'),(6302,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-low-2.png','images/big_icons/48x48/status/audio-volume-low-2.png',5172,1,'2012-10-28 09:06:46'),(6303,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-low-3.png','images/big_icons/48x48/status/audio-volume-low-3.png',2544,1,'2012-10-28 09:06:46'),(6304,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-low-4.png','images/big_icons/48x48/status/audio-volume-low-4.png',2292,1,'2012-10-28 09:06:46'),(6305,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-low-5.png','images/big_icons/48x48/status/audio-volume-low-5.png',851,1,'2012-10-28 09:06:46'),(6306,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-low.png','images/big_icons/48x48/status/audio-volume-low.png',3782,1,'2012-10-28 09:06:46'),(6307,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-medium-2.png','images/big_icons/48x48/status/audio-volume-medium-2.png',5571,1,'2012-10-28 09:06:46'),(6308,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-medium-3.png','images/big_icons/48x48/status/audio-volume-medium-3.png',2674,1,'2012-10-28 09:06:46'),(6309,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-medium-4.png','images/big_icons/48x48/status/audio-volume-medium-4.png',2639,1,'2012-10-28 09:06:46'),(6310,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-medium-5.png','images/big_icons/48x48/status/audio-volume-medium-5.png',962,1,'2012-10-28 09:06:46'),(6311,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-medium.png','images/big_icons/48x48/status/audio-volume-medium.png',4017,1,'2012-10-28 09:06:46'),(6312,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-muted-2.png','images/big_icons/48x48/status/audio-volume-muted-2.png',5414,1,'2012-10-28 09:06:46'),(6313,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-muted-3.png','images/big_icons/48x48/status/audio-volume-muted-3.png',2744,1,'2012-10-28 09:06:46'),(6314,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-muted-4.png','images/big_icons/48x48/status/audio-volume-muted-4.png',3361,1,'2012-10-28 09:06:46'),(6315,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-muted-5.png','images/big_icons/48x48/status/audio-volume-muted-5.png',1761,1,'2012-10-28 09:06:46'),(6316,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-muted.png','images/big_icons/48x48/status/audio-volume-muted.png',4057,1,'2012-10-28 09:06:46'),(6317,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'audio-volume-off.png','images/big_icons/48x48/status/audio-volume-off.png',2456,1,'2012-10-28 09:06:46'),(6318,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-000.png','images/big_icons/48x48/status/battery-000.png',2121,1,'2012-10-28 09:06:46'),(6319,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-010.png','images/big_icons/48x48/status/battery-010.png',2198,1,'2012-10-28 09:06:46'),(6320,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-020.png','images/big_icons/48x48/status/battery-020.png',2219,1,'2012-10-28 09:06:46'),(6321,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-030.png','images/big_icons/48x48/status/battery-030.png',1536,1,'2012-10-28 09:06:46'),(6322,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-040-2.png','images/big_icons/48x48/status/battery-040-2.png',2424,1,'2012-10-28 09:06:46'),(6323,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-040.png','images/big_icons/48x48/status/battery-040.png',1531,1,'2012-10-28 09:06:46'),(6324,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-050.png','images/big_icons/48x48/status/battery-050.png',1512,1,'2012-10-28 09:06:46'),(6325,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-060-2.png','images/big_icons/48x48/status/battery-060-2.png',2537,1,'2012-10-28 09:06:46'),(6326,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-060.png','images/big_icons/48x48/status/battery-060.png',1518,1,'2012-10-28 09:06:46'),(6327,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-070.png','images/big_icons/48x48/status/battery-070.png',1513,1,'2012-10-28 09:06:46'),(6328,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-080-2.png','images/big_icons/48x48/status/battery-080-2.png',2617,1,'2012-10-28 09:06:46'),(6329,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-080.png','images/big_icons/48x48/status/battery-080.png',1493,1,'2012-10-28 09:06:46'),(6330,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-090.png','images/big_icons/48x48/status/battery-090.png',1496,1,'2012-10-28 09:06:46'),(6331,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-100-2.png','images/big_icons/48x48/status/battery-100-2.png',2603,1,'2012-10-28 09:06:46'),(6332,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-100.png','images/big_icons/48x48/status/battery-100.png',1453,1,'2012-10-28 09:06:46'),(6333,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-caution-2.png','images/big_icons/48x48/status/battery-caution-2.png',4976,1,'2012-10-28 09:06:46'),(6334,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-caution-3.png','images/big_icons/48x48/status/battery-caution-3.png',2338,1,'2012-10-28 09:06:46'),(6335,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-caution-charging.png','images/big_icons/48x48/status/battery-caution-charging.png',4056,1,'2012-10-28 09:06:46'),(6336,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-caution.png','images/big_icons/48x48/status/battery-caution.png',3288,1,'2012-10-28 09:06:46'),(6337,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charged.png','images/big_icons/48x48/status/battery-charged.png',4508,1,'2012-10-28 09:06:46'),(6338,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-000.png','images/big_icons/48x48/status/battery-charging-000.png',1786,1,'2012-10-28 09:06:46'),(6339,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-010.png','images/big_icons/48x48/status/battery-charging-010.png',1820,1,'2012-10-28 09:06:46'),(6340,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-020.png','images/big_icons/48x48/status/battery-charging-020.png',1826,1,'2012-10-28 09:06:46'),(6341,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-030.png','images/big_icons/48x48/status/battery-charging-030.png',1817,1,'2012-10-28 09:06:46'),(6342,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-040-2.png','images/big_icons/48x48/status/battery-charging-040-2.png',3738,1,'2012-10-28 09:06:46'),(6343,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-040.png','images/big_icons/48x48/status/battery-charging-040.png',1817,1,'2012-10-28 09:06:46'),(6344,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-050.png','images/big_icons/48x48/status/battery-charging-050.png',1781,1,'2012-10-28 09:06:46'),(6345,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-060-2.png','images/big_icons/48x48/status/battery-charging-060-2.png',3940,1,'2012-10-28 09:06:46'),(6346,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-060.png','images/big_icons/48x48/status/battery-charging-060.png',1751,1,'2012-10-28 09:06:46'),(6347,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-070.png','images/big_icons/48x48/status/battery-charging-070.png',1741,1,'2012-10-28 09:06:46'),(6348,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-080-2.png','images/big_icons/48x48/status/battery-charging-080-2.png',3991,1,'2012-10-28 09:06:46'),(6349,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-080.png','images/big_icons/48x48/status/battery-charging-080.png',1717,1,'2012-10-28 09:06:46'),(6350,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-090.png','images/big_icons/48x48/status/battery-charging-090.png',1673,1,'2012-10-28 09:06:46'),(6351,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-100.png','images/big_icons/48x48/status/battery-charging-100.png',1631,1,'2012-10-28 09:06:46'),(6352,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-caution.png','images/big_icons/48x48/status/battery-charging-caution.png',3708,1,'2012-10-28 09:06:46'),(6353,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging-low.png','images/big_icons/48x48/status/battery-charging-low.png',3747,1,'2012-10-28 09:06:46'),(6354,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-charging.png','images/big_icons/48x48/status/battery-charging.png',4013,1,'2012-10-28 09:06:46'),(6355,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-empty-2.png','images/big_icons/48x48/status/battery-empty-2.png',2121,1,'2012-10-28 09:06:46'),(6356,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-empty-charging.png','images/big_icons/48x48/status/battery-empty-charging.png',4052,1,'2012-10-28 09:06:46'),(6357,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-empty.png','images/big_icons/48x48/status/battery-empty.png',3292,1,'2012-10-28 09:06:46'),(6358,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-full-charging.png','images/big_icons/48x48/status/battery-full-charging.png',4508,1,'2012-10-28 09:06:46'),(6359,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-full.png','images/big_icons/48x48/status/battery-full.png',3690,1,'2012-10-28 09:06:46'),(6360,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-high-charging.png','images/big_icons/48x48/status/battery-high-charging.png',4482,1,'2012-10-28 09:06:46'),(6361,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-high.png','images/big_icons/48x48/status/battery-high.png',3686,1,'2012-10-28 09:06:46'),(6362,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-low-2.png','images/big_icons/48x48/status/battery-low-2.png',2219,1,'2012-10-28 09:06:46'),(6363,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-low-3.png','images/big_icons/48x48/status/battery-low-3.png',2334,1,'2012-10-28 09:06:46'),(6364,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-low-charging.png','images/big_icons/48x48/status/battery-low-charging.png',4052,1,'2012-10-28 09:06:46'),(6365,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-low.png','images/big_icons/48x48/status/battery-low.png',2645,1,'2012-10-28 09:06:46'),(6366,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-medium-charging.png','images/big_icons/48x48/status/battery-medium-charging.png',4513,1,'2012-10-28 09:06:46'),(6367,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-medium.png','images/big_icons/48x48/status/battery-medium.png',3595,1,'2012-10-28 09:06:46'),(6368,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'battery-missing.png','images/big_icons/48x48/status/battery-missing.png',3691,1,'2012-10-28 09:06:46'),(6369,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-clean.png','images/big_icons/48x48/status/dialog-clean.png',3444,1,'2012-10-28 09:06:46'),(6370,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-declare.png','images/big_icons/48x48/status/dialog-declare.png',3759,1,'2012-10-28 09:06:46'),(6371,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-error-2.png','images/big_icons/48x48/status/dialog-error-2.png',3255,1,'2012-10-28 09:06:46'),(6372,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-error-3.png','images/big_icons/48x48/status/dialog-error-3.png',4596,1,'2012-10-28 09:06:46'),(6373,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-error-4.png','images/big_icons/48x48/status/dialog-error-4.png',3650,1,'2012-10-28 09:06:46'),(6374,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-error-5.png','images/big_icons/48x48/status/dialog-error-5.png',3503,1,'2012-10-28 09:06:46'),(6375,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-error-7.png','images/big_icons/48x48/status/dialog-error-7.png',2929,1,'2012-10-28 09:06:46'),(6376,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-error.png','images/big_icons/48x48/status/dialog-error.png',3671,1,'2012-10-28 09:06:46'),(6377,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-important-2.png','images/big_icons/48x48/status/dialog-important-2.png',3492,1,'2012-10-28 09:06:46'),(6378,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-important.png','images/big_icons/48x48/status/dialog-important.png',3676,1,'2012-10-28 09:06:46'),(6379,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-information-2.png','images/big_icons/48x48/status/dialog-information-2.png',5910,1,'2012-10-28 09:06:46'),(6380,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-information-3.png','images/big_icons/48x48/status/dialog-information-3.png',4197,1,'2012-10-28 09:06:46'),(6381,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-information-4.png','images/big_icons/48x48/status/dialog-information-4.png',3988,1,'2012-10-28 09:06:46'),(6382,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-information.png','images/big_icons/48x48/status/dialog-information.png',3572,1,'2012-10-28 09:06:46'),(6383,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-password-2.png','images/big_icons/48x48/status/dialog-password-2.png',3634,1,'2012-10-28 09:06:46'),(6384,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-password.png','images/big_icons/48x48/status/dialog-password.png',4192,1,'2012-10-28 09:06:46'),(6385,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-question-2.png','images/big_icons/48x48/status/dialog-question-2.png',4435,1,'2012-10-28 09:06:46'),(6386,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-question.png','images/big_icons/48x48/status/dialog-question.png',3778,1,'2012-10-28 09:06:46'),(6387,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-warning-2.png','images/big_icons/48x48/status/dialog-warning-2.png',4039,1,'2012-10-28 09:06:46'),(6388,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-warning-3.png','images/big_icons/48x48/status/dialog-warning-3.png',3492,1,'2012-10-28 09:06:46'),(6389,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-warning-4.png','images/big_icons/48x48/status/dialog-warning-4.png',3194,1,'2012-10-28 09:06:46'),(6390,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-warning-5.png','images/big_icons/48x48/status/dialog-warning-5.png',2416,1,'2012-10-28 09:06:46'),(6391,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-warning-panel.png','images/big_icons/48x48/status/dialog-warning-panel.png',3492,1,'2012-10-28 09:06:46'),(6392,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'dialog-warning.png','images/big_icons/48x48/status/dialog-warning.png',3248,1,'2012-10-28 09:06:46'),(6393,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-00.png','images/big_icons/48x48/status/disc-00.png',4802,1,'2012-10-28 09:06:46'),(6394,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-05.png','images/big_icons/48x48/status/disc-05.png',4846,1,'2012-10-28 09:06:46'),(6395,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-10.png','images/big_icons/48x48/status/disc-10.png',4866,1,'2012-10-28 09:06:46'),(6396,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-100.png','images/big_icons/48x48/status/disc-100.png',4707,1,'2012-10-28 09:06:46'),(6397,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-15.png','images/big_icons/48x48/status/disc-15.png',4853,1,'2012-10-28 09:06:46'),(6398,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-20.png','images/big_icons/48x48/status/disc-20.png',4872,1,'2012-10-28 09:06:46'),(6399,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-25.png','images/big_icons/48x48/status/disc-25.png',4864,1,'2012-10-28 09:06:46'),(6400,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-30.png','images/big_icons/48x48/status/disc-30.png',4858,1,'2012-10-28 09:06:46'),(6401,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-35.png','images/big_icons/48x48/status/disc-35.png',4848,1,'2012-10-28 09:06:46'),(6402,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-40.png','images/big_icons/48x48/status/disc-40.png',4833,1,'2012-10-28 09:06:46'),(6403,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-45.png','images/big_icons/48x48/status/disc-45.png',4828,1,'2012-10-28 09:06:46'),(6404,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-50.png','images/big_icons/48x48/status/disc-50.png',4809,1,'2012-10-28 09:06:46'),(6405,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-55.png','images/big_icons/48x48/status/disc-55.png',4795,1,'2012-10-28 09:06:46'),(6406,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-60.png','images/big_icons/48x48/status/disc-60.png',4795,1,'2012-10-28 09:06:46'),(6407,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-65.png','images/big_icons/48x48/status/disc-65.png',4782,1,'2012-10-28 09:06:46'),(6408,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-70.png','images/big_icons/48x48/status/disc-70.png',4793,1,'2012-10-28 09:06:46'),(6409,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-75.png','images/big_icons/48x48/status/disc-75.png',4793,1,'2012-10-28 09:06:46'),(6410,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-80.png','images/big_icons/48x48/status/disc-80.png',4776,1,'2012-10-28 09:06:46'),(6411,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-85.png','images/big_icons/48x48/status/disc-85.png',4778,1,'2012-10-28 09:06:46'),(6412,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-90.png','images/big_icons/48x48/status/disc-90.png',4793,1,'2012-10-28 09:06:46'),(6413,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'disc-95.png','images/big_icons/48x48/status/disc-95.png',4778,1,'2012-10-28 09:06:46'),(6414,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'display-brightness-full.png','images/big_icons/48x48/status/display-brightness-full.png',2755,1,'2012-10-28 09:06:46'),(6415,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'display-brightness-high.png','images/big_icons/48x48/status/display-brightness-high.png',2699,1,'2012-10-28 09:06:46'),(6416,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'display-brightness-low.png','images/big_icons/48x48/status/display-brightness-low.png',2630,1,'2012-10-28 09:06:46'),(6417,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'display-brightness-medium.png','images/big_icons/48x48/status/display-brightness-medium.png',2686,1,'2012-10-28 09:06:46'),(6418,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'display-brightness-off.png','images/big_icons/48x48/status/display-brightness-off.png',2480,1,'2012-10-28 09:06:46'),(6419,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-drag-accept-2.png','images/big_icons/48x48/status/folder-drag-accept-2.png',1988,1,'2012-10-28 09:06:46'),(6420,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-drag-accept-3.png','images/big_icons/48x48/status/folder-drag-accept-3.png',3089,1,'2012-10-28 09:06:46'),(6421,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-drag-accept.png','images/big_icons/48x48/status/folder-drag-accept.png',3121,1,'2012-10-28 09:06:46'),(6422,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-open-2.png','images/big_icons/48x48/status/folder-open-2.png',2092,1,'2012-10-28 09:06:46'),(6423,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-open-3.png','images/big_icons/48x48/status/folder-open-3.png',3174,1,'2012-10-28 09:06:46'),(6424,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-open-4.png','images/big_icons/48x48/status/folder-open-4.png',1609,1,'2012-10-28 09:06:46'),(6425,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-open-5.png','images/big_icons/48x48/status/folder-open-5.png',3316,1,'2012-10-28 09:06:46'),(6426,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-open.png','images/big_icons/48x48/status/folder-open.png',3093,1,'2012-10-28 09:06:46'),(6427,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-visiting-2.png','images/big_icons/48x48/status/folder-visiting-2.png',2982,1,'2012-10-28 09:06:46'),(6428,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-visiting-3.png','images/big_icons/48x48/status/folder-visiting-3.png',1668,1,'2012-10-28 09:06:46'),(6429,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-visiting-4.png','images/big_icons/48x48/status/folder-visiting-4.png',2035,1,'2012-10-28 09:06:46'),(6430,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-visiting-5.png','images/big_icons/48x48/status/folder-visiting-5.png',3267,1,'2012-10-28 09:06:46'),(6431,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'folder-visiting.png','images/big_icons/48x48/status/folder-visiting.png',3709,1,'2012-10-28 09:06:46'),(6432,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'image-loading-2.png','images/big_icons/48x48/status/image-loading-2.png',3385,1,'2012-10-28 09:06:46'),(6433,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'image-loading-3.png','images/big_icons/48x48/status/image-loading-3.png',3445,1,'2012-10-28 09:06:46'),(6434,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'image-loading-4.png','images/big_icons/48x48/status/image-loading-4.png',14244,1,'2012-10-28 09:06:46'),(6435,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'image-loading-5.png','images/big_icons/48x48/status/image-loading-5.png',3221,1,'2012-10-28 09:06:46'),(6436,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'image-loading.png','images/big_icons/48x48/status/image-loading.png',3431,1,'2012-10-28 09:06:46'),(6437,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'image-missing-2.png','images/big_icons/48x48/status/image-missing-2.png',2760,1,'2012-10-28 09:06:46'),(6438,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'image-missing-3.png','images/big_icons/48x48/status/image-missing-3.png',3503,1,'2012-10-28 09:06:46'),(6439,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'image-missing.png','images/big_icons/48x48/status/image-missing.png',2757,1,'2012-10-28 09:06:46'),(6440,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'keyboard-brightness-full.png','images/big_icons/48x48/status/keyboard-brightness-full.png',2194,1,'2012-10-28 09:06:46'),(6441,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'keyboard-brightness-high.png','images/big_icons/48x48/status/keyboard-brightness-high.png',2182,1,'2012-10-28 09:06:46'),(6442,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'keyboard-brightness-low.png','images/big_icons/48x48/status/keyboard-brightness-low.png',2136,1,'2012-10-28 09:06:46'),(6443,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'keyboard-brightness-medium.png','images/big_icons/48x48/status/keyboard-brightness-medium.png',2148,1,'2012-10-28 09:06:46'),(6444,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'keyboard-brightness-off.png','images/big_icons/48x48/status/keyboard-brightness-off.png',1998,1,'2012-10-28 09:06:46'),(6445,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-attachment-2.png','images/big_icons/48x48/status/mail-attachment-2.png',4960,1,'2012-10-28 09:06:46'),(6446,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-attachment-3.png','images/big_icons/48x48/status/mail-attachment-3.png',2232,1,'2012-10-28 09:06:46'),(6447,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-attachment.png','images/big_icons/48x48/status/mail-attachment.png',3116,1,'2012-10-28 09:06:46'),(6448,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-queued.png','images/big_icons/48x48/status/mail-queued.png',1868,1,'2012-10-28 09:06:46'),(6449,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-read-2.png','images/big_icons/48x48/status/mail-read-2.png',3066,1,'2012-10-28 09:06:46'),(6450,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-read.png','images/big_icons/48x48/status/mail-read.png',2988,1,'2012-10-28 09:06:46'),(6451,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-replied-2.png','images/big_icons/48x48/status/mail-replied-2.png',11108,1,'2012-10-28 09:06:46'),(6452,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-replied.png','images/big_icons/48x48/status/mail-replied.png',4117,1,'2012-10-28 09:06:46'),(6453,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-signed-verified.png','images/big_icons/48x48/status/mail-signed-verified.png',4876,1,'2012-10-28 09:06:46'),(6454,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-signed.png','images/big_icons/48x48/status/mail-signed.png',4412,1,'2012-10-28 09:06:46'),(6455,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-unread-2.png','images/big_icons/48x48/status/mail-unread-2.png',1223,1,'2012-10-28 09:06:46'),(6456,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-unread-3.png','images/big_icons/48x48/status/mail-unread-3.png',1968,1,'2012-10-28 09:06:46'),(6457,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-unread-new.png','images/big_icons/48x48/status/mail-unread-new.png',3703,1,'2012-10-28 09:06:46'),(6458,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'mail-unread.png','images/big_icons/48x48/status/mail-unread.png',3323,1,'2012-10-28 09:06:46'),(6459,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'media-playlist-repeat-2.png','images/big_icons/48x48/status/media-playlist-repeat-2.png',2136,1,'2012-10-28 09:06:46'),(6460,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'media-playlist-repeat-3.png','images/big_icons/48x48/status/media-playlist-repeat-3.png',3401,1,'2012-10-28 09:06:46'),(6461,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'media-playlist-repeat.png','images/big_icons/48x48/status/media-playlist-repeat.png',2706,1,'2012-10-28 09:06:46'),(6462,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'media-playlist-shuffle-2.png','images/big_icons/48x48/status/media-playlist-shuffle-2.png',2860,1,'2012-10-28 09:06:46'),(6463,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'media-playlist-shuffle-3.png','images/big_icons/48x48/status/media-playlist-shuffle-3.png',2124,1,'2012-10-28 09:06:46'),(6464,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'media-playlist-shuffle.png','images/big_icons/48x48/status/media-playlist-shuffle.png',3077,1,'2012-10-28 09:06:46'),(6465,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-disconnected.png','images/big_icons/48x48/status/network-disconnected.png',3276,1,'2012-10-28 09:06:46'),(6466,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-error-2.png','images/big_icons/48x48/status/network-error-2.png',5892,1,'2012-10-28 09:06:46'),(6467,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-error.png','images/big_icons/48x48/status/network-error.png',4309,1,'2012-10-28 09:06:46'),(6468,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-ethernet-connected.png','images/big_icons/48x48/status/network-ethernet-connected.png',1517,1,'2012-10-28 09:06:46'),(6469,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-ethernet-disconnected.png','images/big_icons/48x48/status/network-ethernet-disconnected.png',3293,1,'2012-10-28 09:06:46'),(6470,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-3g-full.png','images/big_icons/48x48/status/network-gsm-3g-full.png',3306,1,'2012-10-28 09:06:46'),(6471,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-3g-high.png','images/big_icons/48x48/status/network-gsm-3g-high.png',3301,1,'2012-10-28 09:06:46'),(6472,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-3g-low.png','images/big_icons/48x48/status/network-gsm-3g-low.png',3284,1,'2012-10-28 09:06:46'),(6473,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-3g-medium.png','images/big_icons/48x48/status/network-gsm-3g-medium.png',3301,1,'2012-10-28 09:06:46'),(6474,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-3g-none.png','images/big_icons/48x48/status/network-gsm-3g-none.png',3209,1,'2012-10-28 09:06:46'),(6475,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-disconnected.png','images/big_icons/48x48/status/network-gsm-disconnected.png',3276,1,'2012-10-28 09:06:46'),(6476,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-edge-full.png','images/big_icons/48x48/status/network-gsm-edge-full.png',1809,1,'2012-10-28 09:06:46'),(6477,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-edge-high.png','images/big_icons/48x48/status/network-gsm-edge-high.png',1813,1,'2012-10-28 09:06:46'),(6478,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-edge-low.png','images/big_icons/48x48/status/network-gsm-edge-low.png',1747,1,'2012-10-28 09:06:46'),(6479,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-edge-medium.png','images/big_icons/48x48/status/network-gsm-edge-medium.png',1784,1,'2012-10-28 09:06:46'),(6480,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-edge-none.png','images/big_icons/48x48/status/network-gsm-edge-none.png',1707,1,'2012-10-28 09:06:48'),(6481,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-full.png','images/big_icons/48x48/status/network-gsm-full.png',1586,1,'2012-10-28 09:06:48'),(6482,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-h-full.png','images/big_icons/48x48/status/network-gsm-h-full.png',1848,1,'2012-10-28 09:06:48'),(6483,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-h-high.png','images/big_icons/48x48/status/network-gsm-h-high.png',1857,1,'2012-10-28 09:06:48'),(6484,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-h-low.png','images/big_icons/48x48/status/network-gsm-h-low.png',1810,1,'2012-10-28 09:06:48'),(6485,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-h-medium.png','images/big_icons/48x48/status/network-gsm-h-medium.png',1836,1,'2012-10-28 09:06:48'),(6486,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-h-none.png','images/big_icons/48x48/status/network-gsm-h-none.png',1754,1,'2012-10-28 09:06:48'),(6487,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-high.png','images/big_icons/48x48/status/network-gsm-high.png',1575,1,'2012-10-28 09:06:48'),(6488,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-low.png','images/big_icons/48x48/status/network-gsm-low.png',1484,1,'2012-10-28 09:06:48'),(6489,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-medium.png','images/big_icons/48x48/status/network-gsm-medium.png',1529,1,'2012-10-28 09:06:48'),(6490,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-gsm-none.png','images/big_icons/48x48/status/network-gsm-none.png',1437,1,'2012-10-28 09:06:48'),(6491,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-idle-2.png','images/big_icons/48x48/status/network-idle-2.png',5689,1,'2012-10-28 09:06:48'),(6492,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-idle.png','images/big_icons/48x48/status/network-idle.png',3731,1,'2012-10-28 09:06:48'),(6493,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-offline-2.png','images/big_icons/48x48/status/network-offline-2.png',4861,1,'2012-10-28 09:06:48'),(6494,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-offline.png','images/big_icons/48x48/status/network-offline.png',4146,1,'2012-10-28 09:06:48'),(6495,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-receive-2.png','images/big_icons/48x48/status/network-receive-2.png',5611,1,'2012-10-28 09:06:48'),(6496,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-receive.png','images/big_icons/48x48/status/network-receive.png',3808,1,'2012-10-28 09:06:48'),(6497,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-transmit-2.png','images/big_icons/48x48/status/network-transmit-2.png',5580,1,'2012-10-28 09:06:48'),(6498,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-transmit-receive-2.png','images/big_icons/48x48/status/network-transmit-receive-2.png',5499,1,'2012-10-28 09:06:48'),(6499,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-transmit-receive.png','images/big_icons/48x48/status/network-transmit-receive.png',3820,1,'2012-10-28 09:06:48'),(6500,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-transmit.png','images/big_icons/48x48/status/network-transmit.png',3806,1,'2012-10-28 09:06:48'),(6501,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-wireless-disconnected.png','images/big_icons/48x48/status/network-wireless-disconnected.png',3276,1,'2012-10-28 09:06:48'),(6502,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-wireless-encrypted-2.png','images/big_icons/48x48/status/network-wireless-encrypted-2.png',5967,1,'2012-10-28 09:06:48'),(6503,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-wireless-encrypted-3.png','images/big_icons/48x48/status/network-wireless-encrypted-3.png',4416,1,'2012-10-28 09:06:48'),(6504,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-wireless-full.png','images/big_icons/48x48/status/network-wireless-full.png',2221,1,'2012-10-28 09:06:50'),(6505,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-wireless-high.png','images/big_icons/48x48/status/network-wireless-high.png',2253,1,'2012-10-28 09:06:50'),(6506,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-wireless-low.png','images/big_icons/48x48/status/network-wireless-low.png',2176,1,'2012-10-28 09:06:50'),(6507,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-wireless-medium.png','images/big_icons/48x48/status/network-wireless-medium.png',2232,1,'2012-10-28 09:06:50'),(6508,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'network-wireless-none.png','images/big_icons/48x48/status/network-wireless-none.png',2104,1,'2012-10-28 09:06:50'),(6509,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'object-locked-2.png','images/big_icons/48x48/status/object-locked-2.png',2402,1,'2012-10-28 09:06:50'),(6510,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'object-locked.png','images/big_icons/48x48/status/object-locked.png',3856,1,'2012-10-28 09:06:50'),(6511,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'object-unlocked-2.png','images/big_icons/48x48/status/object-unlocked-2.png',10437,1,'2012-10-28 09:06:50'),(6512,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'object-unlocked.png','images/big_icons/48x48/status/object-unlocked.png',4011,1,'2012-10-28 09:06:50'),(6513,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'power-disconnected.png','images/big_icons/48x48/status/power-disconnected.png',3333,1,'2012-10-28 09:06:50'),(6514,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'printer-error-2.png','images/big_icons/48x48/status/printer-error-2.png',3651,1,'2012-10-28 09:06:50'),(6515,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'printer-error-3.png','images/big_icons/48x48/status/printer-error-3.png',3367,1,'2012-10-28 09:06:50'),(6516,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'printer-error-4.png','images/big_icons/48x48/status/printer-error-4.png',3443,1,'2012-10-28 09:06:50'),(6517,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'printer-error.png','images/big_icons/48x48/status/printer-error.png',3485,1,'2012-10-28 09:06:50'),(6518,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'printer-printing-2.png','images/big_icons/48x48/status/printer-printing-2.png',3341,1,'2012-10-28 09:06:50'),(6519,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'printer-printing-3.png','images/big_icons/48x48/status/printer-printing-3.png',12366,1,'2012-10-28 09:06:50'),(6520,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'printer-printing.png','images/big_icons/48x48/status/printer-printing.png',3411,1,'2012-10-28 09:06:50'),(6521,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'printer.png','images/big_icons/48x48/status/printer.png',2625,1,'2012-10-28 09:06:50'),(6522,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'script-error.png','images/big_icons/48x48/status/script-error.png',3817,1,'2012-10-28 09:06:50'),(6523,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'security-high-2.png','images/big_icons/48x48/status/security-high-2.png',3512,1,'2012-10-28 09:06:50'),(6524,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'security-high.png','images/big_icons/48x48/status/security-high.png',4995,1,'2012-10-28 09:06:50'),(6525,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'security-low-2.png','images/big_icons/48x48/status/security-low-2.png',3644,1,'2012-10-28 09:06:50'),(6526,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'security-low.png','images/big_icons/48x48/status/security-low.png',3882,1,'2012-10-28 09:06:50'),(6527,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'security-medium-2.png','images/big_icons/48x48/status/security-medium-2.png',3415,1,'2012-10-28 09:06:50'),(6528,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'security-medium.png','images/big_icons/48x48/status/security-medium.png',3921,1,'2012-10-28 09:06:50'),(6529,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'software-update-available-2.png','images/big_icons/48x48/status/software-update-available-2.png',5231,1,'2012-10-28 09:06:50'),(6530,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'software-update-available-3.png','images/big_icons/48x48/status/software-update-available-3.png',3981,1,'2012-10-28 09:06:50'),(6531,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'software-update-available.png','images/big_icons/48x48/status/software-update-available.png',3963,1,'2012-10-28 09:06:50'),(6532,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'software-update-current.png','images/big_icons/48x48/status/software-update-current.png',5911,1,'2012-10-28 09:06:50'),(6533,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'software-update-download.png','images/big_icons/48x48/status/software-update-download.png',3800,1,'2012-10-28 09:06:50'),(6534,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'software-update-error.png','images/big_icons/48x48/status/software-update-error.png',3779,1,'2012-10-28 09:06:50'),(6535,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'software-update-inactive.png','images/big_icons/48x48/status/software-update-inactive.png',2416,1,'2012-10-28 09:06:50'),(6536,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'software-update-unavailable.png','images/big_icons/48x48/status/software-update-unavailable.png',3819,1,'2012-10-28 09:06:50'),(6537,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'software-update-urgent-2.png','images/big_icons/48x48/status/software-update-urgent-2.png',3414,1,'2012-10-28 09:06:50'),(6538,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'software-update-urgent-3.png','images/big_icons/48x48/status/software-update-urgent-3.png',3879,1,'2012-10-28 09:06:50'),(6539,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'software-update-urgent.png','images/big_icons/48x48/status/software-update-urgent.png',3301,1,'2012-10-28 09:06:50'),(6540,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'task-due.png','images/big_icons/48x48/status/task-due.png',2643,1,'2012-10-28 09:06:50'),(6541,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'task-past-due.png','images/big_icons/48x48/status/task-past-due.png',2617,1,'2012-10-28 09:06:52'),(6542,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-caution-charging.png','images/big_icons/48x48/status/ups-caution-charging.png',3905,1,'2012-10-28 09:06:52'),(6543,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-caution.png','images/big_icons/48x48/status/ups-caution.png',3928,1,'2012-10-28 09:06:52'),(6544,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-charged.png','images/big_icons/48x48/status/ups-charged.png',4257,1,'2012-10-28 09:06:52'),(6545,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-empty-charging.png','images/big_icons/48x48/status/ups-empty-charging.png',4061,1,'2012-10-28 09:06:52'),(6546,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-empty.png','images/big_icons/48x48/status/ups-empty.png',3991,1,'2012-10-28 09:06:52'),(6547,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-full-charging.png','images/big_icons/48x48/status/ups-full-charging.png',4257,1,'2012-10-28 09:06:52'),(6548,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-full.png','images/big_icons/48x48/status/ups-full.png',4118,1,'2012-10-28 09:06:52'),(6549,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-high-charging.png','images/big_icons/48x48/status/ups-high-charging.png',4232,1,'2012-10-28 09:06:52'),(6550,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-high.png','images/big_icons/48x48/status/ups-high.png',4238,1,'2012-10-28 09:06:52'),(6551,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-low-charging.png','images/big_icons/48x48/status/ups-low-charging.png',4027,1,'2012-10-28 09:06:52'),(6552,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-low.png','images/big_icons/48x48/status/ups-low.png',3928,1,'2012-10-28 09:06:52'),(6553,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-medium-charging.png','images/big_icons/48x48/status/ups-medium-charging.png',4045,1,'2012-10-28 09:06:52'),(6554,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ups-medium.png','images/big_icons/48x48/status/ups-medium.png',4032,1,'2012-10-28 09:06:52'),(6555,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-available.png','images/big_icons/48x48/status/user-available.png',4528,1,'2012-10-28 09:06:52'),(6556,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-away-2.png','images/big_icons/48x48/status/user-away-2.png',13060,1,'2012-10-28 09:06:52'),(6557,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-away-extended.png','images/big_icons/48x48/status/user-away-extended.png',14032,1,'2012-10-28 09:06:52'),(6558,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-away.png','images/big_icons/48x48/status/user-away.png',4947,1,'2012-10-28 09:06:52'),(6559,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-busy-2.png','images/big_icons/48x48/status/user-busy-2.png',12582,1,'2012-10-28 09:06:52'),(6560,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-busy.png','images/big_icons/48x48/status/user-busy.png',4067,1,'2012-10-28 09:06:52'),(6561,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-extended-away.png','images/big_icons/48x48/status/user-extended-away.png',4600,1,'2012-10-28 09:06:52'),(6562,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-idle.png','images/big_icons/48x48/status/user-idle.png',3025,1,'2012-10-28 09:06:52'),(6563,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-invisible-2.png','images/big_icons/48x48/status/user-invisible-2.png',13765,1,'2012-10-28 09:06:52'),(6564,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-invisible.png','images/big_icons/48x48/status/user-invisible.png',4094,1,'2012-10-28 09:06:52'),(6565,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-offline-2.png','images/big_icons/48x48/status/user-offline-2.png',6738,1,'2012-10-28 09:06:52'),(6566,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-offline.png','images/big_icons/48x48/status/user-offline.png',5170,1,'2012-10-28 09:06:52'),(6567,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-online-2.png','images/big_icons/48x48/status/user-online-2.png',12794,1,'2012-10-28 09:06:52'),(6568,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-online.png','images/big_icons/48x48/status/user-online.png',4528,1,'2012-10-28 09:06:52'),(6569,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-trash-full-2.png','images/big_icons/48x48/status/user-trash-full-2.png',6480,1,'2012-10-28 09:06:52'),(6570,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-trash-full-3.png','images/big_icons/48x48/status/user-trash-full-3.png',5053,1,'2012-10-28 09:06:52'),(6571,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-trash-full-4.png','images/big_icons/48x48/status/user-trash-full-4.png',4072,1,'2012-10-28 09:06:52'),(6572,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'user-trash-full.png','images/big_icons/48x48/status/user-trash-full.png',3725,1,'2012-10-28 09:06:52'),(6573,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'virus-detected.png','images/big_icons/48x48/status/virus-detected.png',3808,1,'2012-10-28 09:06:52'),(6574,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'volume-0.png','images/big_icons/48x48/status/volume-0.png',752,1,'2012-10-28 09:06:52'),(6575,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'wallet-closed.png','images/big_icons/48x48/status/wallet-closed.png',2056,1,'2012-10-28 09:06:52'),(6576,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'wallet-open.png','images/big_icons/48x48/status/wallet-open.png',3207,1,'2012-10-28 09:06:52'),(6577,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-clear-2.png','images/big_icons/48x48/status/weather-clear-2.png',5514,1,'2012-10-28 09:06:52'),(6578,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-clear-3.png','images/big_icons/48x48/status/weather-clear-3.png',5409,1,'2012-10-28 09:06:52'),(6579,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-clear-night-2.png','images/big_icons/48x48/status/weather-clear-night-2.png',3754,1,'2012-10-28 09:06:52'),(6580,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-clear-night-3.png','images/big_icons/48x48/status/weather-clear-night-3.png',2153,1,'2012-10-28 09:06:52'),(6581,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-clear-night.png','images/big_icons/48x48/status/weather-clear-night.png',3767,1,'2012-10-28 09:06:52'),(6582,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-clear.png','images/big_icons/48x48/status/weather-clear.png',4582,1,'2012-10-28 09:06:52'),(6583,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-clouds-night.png','images/big_icons/48x48/status/weather-clouds-night.png',3742,1,'2012-10-28 09:06:52'),(6584,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-clouds.png','images/big_icons/48x48/status/weather-clouds.png',4513,1,'2012-10-28 09:06:52'),(6585,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-few-clouds-2.png','images/big_icons/48x48/status/weather-few-clouds-2.png',6201,1,'2012-10-28 09:06:52'),(6586,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-few-clouds-3.png','images/big_icons/48x48/status/weather-few-clouds-3.png',4310,1,'2012-10-28 09:06:52'),(6587,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-few-clouds-night-2.png','images/big_icons/48x48/status/weather-few-clouds-night-2.png',4714,1,'2012-10-28 09:06:52'),(6588,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-few-clouds-night-3.png','images/big_icons/48x48/status/weather-few-clouds-night-3.png',2073,1,'2012-10-28 09:06:52'),(6589,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-few-clouds-night.png','images/big_icons/48x48/status/weather-few-clouds-night.png',4461,1,'2012-10-28 09:06:52'),(6590,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-few-clouds.png','images/big_icons/48x48/status/weather-few-clouds.png',4501,1,'2012-10-28 09:06:52'),(6591,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-fog-2.png','images/big_icons/48x48/status/weather-fog-2.png',3008,1,'2012-10-28 09:06:52'),(6592,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-fog.png','images/big_icons/48x48/status/weather-fog.png',581,1,'2012-10-28 09:06:52'),(6593,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-freezing-rain.png','images/big_icons/48x48/status/weather-freezing-rain.png',4155,1,'2012-10-28 09:06:52'),(6594,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-hail.png','images/big_icons/48x48/status/weather-hail.png',4504,1,'2012-10-28 09:06:52'),(6595,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-many-clouds.png','images/big_icons/48x48/status/weather-many-clouds.png',3498,1,'2012-10-28 09:06:52'),(6596,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-mist.png','images/big_icons/48x48/status/weather-mist.png',1566,1,'2012-10-28 09:06:52'),(6597,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-none-available.png','images/big_icons/48x48/status/weather-none-available.png',1804,1,'2012-10-28 09:06:52'),(6598,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-overcast-2.png','images/big_icons/48x48/status/weather-overcast-2.png',4291,1,'2012-10-28 09:06:52'),(6599,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-overcast.png','images/big_icons/48x48/status/weather-overcast.png',2873,1,'2012-10-28 09:06:52'),(6600,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-severe-alert-2.png','images/big_icons/48x48/status/weather-severe-alert-2.png',5737,1,'2012-10-28 09:06:52'),(6601,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-severe-alert.png','images/big_icons/48x48/status/weather-severe-alert.png',3690,1,'2012-10-28 09:06:52'),(6602,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-showers-2.png','images/big_icons/48x48/status/weather-showers-2.png',5487,1,'2012-10-28 09:06:52'),(6603,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-showers-3.png','images/big_icons/48x48/status/weather-showers-3.png',4714,1,'2012-10-28 09:06:52'),(6604,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-showers-day.png','images/big_icons/48x48/status/weather-showers-day.png',5178,1,'2012-10-28 09:06:52'),(6605,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-showers-night.png','images/big_icons/48x48/status/weather-showers-night.png',5115,1,'2012-10-28 09:06:52'),(6606,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-showers-scattered-2.png','images/big_icons/48x48/status/weather-showers-scattered-2.png',5758,1,'2012-10-28 09:06:52'),(6607,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-showers-scattered-3.png','images/big_icons/48x48/status/weather-showers-scattered-3.png',4587,1,'2012-10-28 09:06:52'),(6608,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-showers-scattered-day.png','images/big_icons/48x48/status/weather-showers-scattered-day.png',4721,1,'2012-10-28 09:06:52'),(6609,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-showers-scattered-night.png','images/big_icons/48x48/status/weather-showers-scattered-night.png',4344,1,'2012-10-28 09:06:52'),(6610,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-showers-scattered.png','images/big_icons/48x48/status/weather-showers-scattered.png',4149,1,'2012-10-28 09:06:52'),(6611,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-showers.png','images/big_icons/48x48/status/weather-showers.png',5049,1,'2012-10-28 09:06:52'),(6612,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-snow-2.png','images/big_icons/48x48/status/weather-snow-2.png',5336,1,'2012-10-28 09:06:52'),(6613,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-snow-3.png','images/big_icons/48x48/status/weather-snow-3.png',4966,1,'2012-10-28 09:06:52'),(6614,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-snow-rain.png','images/big_icons/48x48/status/weather-snow-rain.png',4532,1,'2012-10-28 09:06:52'),(6615,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-snow-scattered-day.png','images/big_icons/48x48/status/weather-snow-scattered-day.png',5439,1,'2012-10-28 09:06:52'),(6616,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-snow-scattered-night.png','images/big_icons/48x48/status/weather-snow-scattered-night.png',4877,1,'2012-10-28 09:06:52'),(6617,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-snow-scattered.png','images/big_icons/48x48/status/weather-snow-scattered.png',4325,1,'2012-10-28 09:06:52'),(6618,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-snow.png','images/big_icons/48x48/status/weather-snow.png',3669,1,'2012-10-28 09:06:52'),(6619,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-storm-2.png','images/big_icons/48x48/status/weather-storm-2.png',5699,1,'2012-10-28 09:06:52'),(6620,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-storm-3.png','images/big_icons/48x48/status/weather-storm-3.png',5831,1,'2012-10-28 09:06:52'),(6621,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-storm-day.png','images/big_icons/48x48/status/weather-storm-day.png',5562,1,'2012-10-28 09:06:52'),(6622,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-storm-night.png','images/big_icons/48x48/status/weather-storm-night.png',5680,1,'2012-10-28 09:06:52'),(6623,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'weather-storm.png','images/big_icons/48x48/status/weather-storm.png',3956,1,'2012-10-28 09:06:52'),(6624,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'window-suppressed.png','images/big_icons/48x48/status/window-suppressed.png',3015,1,'2012-10-28 09:06:52'),(6625,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Acrobat Reader.png','images/flat_icons/Adobe Acrobat Reader.png',1353,2,'2013-09-24 03:35:10'),(6626,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe After Effects.png','images/flat_icons/Adobe After Effects.png',1098,2,'2013-09-24 03:35:10'),(6627,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Audition.png','images/flat_icons/Adobe Audition.png',908,2,'2013-09-24 03:35:10'),(6628,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Bridge.png','images/flat_icons/Adobe Bridge.png',806,2,'2013-09-24 03:35:10'),(6629,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Contribute.png','images/flat_icons/Adobe Contribute.png',881,2,'2013-09-24 03:35:10'),(6630,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Creative Cloud.png','images/flat_icons/Adobe Creative Cloud.png',1533,2,'2013-09-24 03:35:10'),(6631,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Dreamweaver.png','images/flat_icons/Adobe Dreamweaver.png',1279,2,'2013-09-24 03:35:10'),(6632,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Encore.png','images/flat_icons/Adobe Encore.png',491,2,'2013-09-24 03:35:10'),(6633,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Fireworks.png','images/flat_icons/Adobe Fireworks.png',988,2,'2013-09-24 03:35:11'),(6634,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Flash Builder.png','images/flat_icons/Adobe Flash Builder.png',685,2,'2013-09-24 03:35:11'),(6635,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Flash Catalyst.png','images/flat_icons/Adobe Flash Catalyst.png',646,2,'2013-09-24 03:35:10'),(6636,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Flash.png','images/flat_icons/Adobe Flash.png',326,2,'2013-09-24 03:35:10'),(6637,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Illustrator.png','images/flat_icons/Adobe Illustrator.png',828,2,'2013-09-24 03:35:10'),(6638,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe InDesign.png','images/flat_icons/Adobe InDesign.png',617,2,'2013-09-24 03:35:10'),(6639,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Lightroom.png','images/flat_icons/Adobe Lightroom.png',400,2,'2013-09-24 03:35:10'),(6640,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe OnLocation.png','images/flat_icons/Adobe OnLocation.png',868,2,'2013-09-24 03:35:10'),(6641,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Photoshop.png','images/flat_icons/Adobe Photoshop.png',951,2,'2013-09-24 03:35:10'),(6642,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Premiere Pro.png','images/flat_icons/Adobe Premiere Pro.png',610,2,'2013-09-24 03:35:10'),(6643,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Soundbooth.png','images/flat_icons/Adobe Soundbooth.png',1070,2,'2013-09-24 03:35:10'),(6644,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Adobe Story.png','images/flat_icons/Adobe Story.png',985,2,'2013-09-24 03:35:10'),(6645,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Advanced Systemcare.png','images/flat_icons/Advanced Systemcare.png',1476,2,'2013-09-24 03:35:10'),(6646,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Aero WinFlip 3D.png','images/flat_icons/Aero WinFlip 3D.png',373,2,'2013-09-24 03:35:10'),(6647,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'AIM.png','images/flat_icons/AIM.png',1025,2,'2013-09-24 03:35:10'),(6648,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Aimp.png','images/flat_icons/Aimp.png',951,2,'2013-09-24 03:35:10'),(6649,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Angry Birds.png','images/flat_icons/Angry Birds.png',1420,2,'2013-09-24 03:35:11'),(6650,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Audacity.png','images/flat_icons/Audacity.png',1632,2,'2013-09-24 03:35:10'),(6651,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'AutoDesk 3DS Max.png','images/flat_icons/AutoDesk 3DS Max.png',1743,2,'2013-09-24 03:35:10'),(6652,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Autodesk.png','images/flat_icons/Autodesk.png',865,2,'2013-09-24 03:35:10'),(6653,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Avast Antivirus.png','images/flat_icons/Avast Antivirus.png',1352,2,'2013-09-24 03:35:10'),(6654,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Bejeweled 2.png','images/flat_icons/Bejeweled 2.png',1351,2,'2013-09-24 03:35:10'),(6655,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Bejeweled 3.png','images/flat_icons/Bejeweled 3.png',1483,2,'2013-09-24 03:35:10'),(6656,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Bejeweled Twist.png','images/flat_icons/Bejeweled Twist.png',1750,2,'2013-09-24 03:35:10'),(6657,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Bejeweled.png','images/flat_icons/Bejeweled.png',827,2,'2013-09-24 03:35:10'),(6658,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'BitTorrent.png','images/flat_icons/BitTorrent.png',1623,2,'2013-09-24 03:35:11'),(6659,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Blender.png','images/flat_icons/Blender.png',1238,2,'2013-09-24 03:35:10'),(6660,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Bluetooth alt.png','images/flat_icons/Bluetooth alt.png',1178,2,'2013-09-24 03:35:10'),(6661,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Bluetooth.png','images/flat_icons/Bluetooth.png',751,2,'2013-09-24 03:35:10'),(6662,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Calculator alt.png','images/flat_icons/Calculator alt.png',1277,2,'2013-09-24 03:35:10'),(6663,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Calculator.png','images/flat_icons/Calculator.png',971,2,'2013-09-24 03:35:11'),(6664,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Calendar.png','images/flat_icons/Calendar.png',778,2,'2013-09-24 03:35:10'),(6665,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Camstudio.png','images/flat_icons/Camstudio.png',610,2,'2013-09-24 03:35:10'),(6666,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Camtasia Studio.png','images/flat_icons/Camtasia Studio.png',1267,2,'2013-09-24 03:35:11'),(6667,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'CCleaner.png','images/flat_icons/CCleaner.png',1655,2,'2013-09-24 03:35:10'),(6668,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Chuzzle.png','images/flat_icons/Chuzzle.png',1196,2,'2013-09-24 03:35:10'),(6669,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Circle Dock.png','images/flat_icons/Circle Dock.png',984,2,'2013-09-24 03:35:10'),(6670,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Coding App.png','images/flat_icons/Coding App.png',896,2,'2013-09-24 03:35:10'),(6671,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'CrashPlan.png','images/flat_icons/CrashPlan.png',1047,2,'2013-09-24 03:35:10'),(6672,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Digsby.png','images/flat_icons/Digsby.png',1315,2,'2013-09-24 03:35:10'),(6673,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Dropbox.png','images/flat_icons/Dropbox.png',1360,2,'2013-09-24 03:35:10'),(6674,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Eclipse.png','images/flat_icons/Eclipse.png',1057,2,'2013-09-24 03:35:10'),(6675,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Email Chat.png','images/flat_icons/Email Chat.png',979,2,'2013-09-24 03:35:11'),(6676,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Evernote.png','images/flat_icons/Evernote.png',1105,2,'2013-09-24 03:35:10'),(6677,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Expose.png','images/flat_icons/Expose.png',349,2,'2013-09-24 03:35:10'),(6678,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'FileZilla.png','images/flat_icons/FileZilla.png',1428,2,'2013-09-24 03:35:10'),(6679,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'FL Studio.png','images/flat_icons/FL Studio.png',1062,2,'2013-09-24 03:35:11'),(6680,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Flipboard.png','images/flat_icons/Flipboard.png',328,2,'2013-09-24 03:35:11'),(6681,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Foobar.png','images/flat_icons/Foobar.png',1088,2,'2013-09-24 03:35:10'),(6682,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Fraps.png','images/flat_icons/Fraps.png',666,2,'2013-09-24 03:35:10'),(6683,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'GIMP.png','images/flat_icons/GIMP.png',1085,2,'2013-09-24 03:35:10'),(6684,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'GOM Player.png','images/flat_icons/GOM Player.png',1132,2,'2013-09-24 03:35:10'),(6685,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Google Sketchup 2012.png','images/flat_icons/Google Sketchup 2012.png',1390,2,'2013-09-24 03:35:10'),(6686,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'GrooveShark.png','images/flat_icons/GrooveShark.png',1427,2,'2013-09-24 03:35:10'),(6687,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'HTML5.png','images/flat_icons/HTML5.png',1275,2,'2013-09-24 03:35:10'),(6688,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'iCloud alt.png','images/flat_icons/iCloud alt.png',1089,2,'2013-09-24 03:35:10'),(6689,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'iCloud.png','images/flat_icons/iCloud.png',1108,2,'2013-09-24 03:35:10'),(6690,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Inkscape.png','images/flat_icons/Inkscape.png',1066,2,'2013-09-24 03:35:10'),(6691,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Instagram.png','images/flat_icons/Instagram.png',815,2,'2013-09-24 03:35:10'),(6692,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Internet Download Manager.png','images/flat_icons/Internet Download Manager.png',1603,2,'2013-09-24 03:35:11'),(6693,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'iOS Facetime.png','images/flat_icons/iOS Facetime.png',923,2,'2013-09-24 03:35:10'),(6694,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'IrFan View.png','images/flat_icons/IrFan View.png',1258,2,'2013-09-24 03:35:10'),(6695,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'iTunes alt.png','images/flat_icons/iTunes alt.png',815,2,'2013-09-24 03:35:10'),(6696,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'iTunes.png','images/flat_icons/iTunes.png',1629,2,'2013-09-24 03:35:10'),(6697,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Jahshaka.png','images/flat_icons/Jahshaka.png',1231,2,'2013-09-24 03:35:10'),(6698,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'KMPlayer.png','images/flat_icons/KMPlayer.png',1182,2,'2013-09-24 03:35:10'),(6699,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Koding.png','images/flat_icons/Koding.png',819,2,'2013-09-24 03:35:10'),(6700,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Kompozer.png','images/flat_icons/Kompozer.png',2052,2,'2013-09-24 03:35:10'),(6701,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Launchy alt.png','images/flat_icons/Launchy alt.png',1064,2,'2013-09-24 03:35:10'),(6702,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Launchy.png','images/flat_icons/Launchy.png',1139,2,'2013-09-24 03:35:10'),(6703,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Lightwave.png','images/flat_icons/Lightwave.png',2198,2,'2013-09-24 03:35:10'),(6704,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'LightWorks.png','images/flat_icons/LightWorks.png',1377,2,'2013-09-24 03:35:10'),(6705,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Limewire alt.png','images/flat_icons/Limewire alt.png',1627,2,'2013-09-24 03:35:10'),(6706,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Limewire.png','images/flat_icons/Limewire.png',2426,2,'2013-09-24 03:35:10'),(6707,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Live Mail.png','images/flat_icons/Live Mail.png',1507,2,'2013-09-24 03:35:11'),(6708,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Live Messenger alt 1.png','images/flat_icons/Live Messenger alt 1.png',1117,2,'2013-09-24 03:35:10'),(6709,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Live Messenger alt 2.png','images/flat_icons/Live Messenger alt 2.png',1209,2,'2013-09-24 03:35:10'),(6710,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Live Messenger alt 3.png','images/flat_icons/Live Messenger alt 3.png',1011,2,'2013-09-24 03:35:11'),(6711,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Live Messenger.png','images/flat_icons/Live Messenger.png',1011,2,'2013-09-24 03:35:10'),(6712,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Live Sync.png','images/flat_icons/Live Sync.png',746,2,'2013-09-24 03:35:11'),(6713,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Mac App Store alt.png','images/flat_icons/Mac App Store alt.png',1137,2,'2013-09-24 03:35:10'),(6714,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Mac App Store.png','images/flat_icons/Mac App Store.png',1696,2,'2013-09-24 03:35:10'),(6715,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Mac Dashboard.png','images/flat_icons/Mac Dashboard.png',1410,2,'2013-09-24 03:35:10'),(6716,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Mac iCal.png','images/flat_icons/Mac iCal.png',1225,2,'2013-09-24 03:35:10'),(6717,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Mac iChat alt.png','images/flat_icons/Mac iChat alt.png',929,2,'2013-09-24 03:35:10'),(6718,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Mac iChat.png','images/flat_icons/Mac iChat.png',886,2,'2013-09-24 03:35:10'),(6719,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Mac iMovie.png','images/flat_icons/Mac iMovie.png',1658,2,'2013-09-24 03:35:10'),(6720,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Mac Photo Booth.png','images/flat_icons/Mac Photo Booth.png',990,2,'2013-09-24 03:35:10'),(6721,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Mac Spaces.png','images/flat_icons/Mac Spaces.png',996,2,'2013-09-24 03:35:11'),(6722,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Magnifier.png','images/flat_icons/Magnifier.png',856,2,'2013-09-24 03:35:10'),(6723,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'MalwareBytes.png','images/flat_icons/MalwareBytes.png',711,2,'2013-09-24 03:35:10'),(6724,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Messaging alt.png','images/flat_icons/Messaging alt.png',1162,2,'2013-09-24 03:35:10'),(6725,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Messaging.png','images/flat_icons/Messaging.png',806,2,'2013-09-24 03:35:10'),(6726,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Microsoft Security Essentials.png','images/flat_icons/Microsoft Security Essentials.png',836,2,'2013-09-24 03:35:11'),(6727,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Minecraft.png','images/flat_icons/Minecraft.png',411,2,'2013-09-24 03:35:10'),(6728,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Nero.png','images/flat_icons/Nero.png',1783,2,'2013-09-24 03:35:10'),(6729,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'No$GBA.png','images/flat_icons/No$GBA.png',880,2,'2013-09-24 03:35:10'),(6730,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Nokia Suite alt.png','images/flat_icons/Nokia Suite alt.png',1413,2,'2013-09-24 03:35:10'),(6731,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Nokia Suite.png','images/flat_icons/Nokia Suite.png',1054,2,'2013-09-24 03:35:10'),(6732,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Notepad alt.png','images/flat_icons/Notepad alt.png',1030,2,'2013-09-24 03:35:10'),(6733,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Notepad++.png','images/flat_icons/Notepad++.png',1497,2,'2013-09-24 03:35:10'),(6734,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Notepad.png','images/flat_icons/Notepad.png',961,2,'2013-09-24 03:35:10'),(6735,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'ObjectDock.png','images/flat_icons/ObjectDock.png',1705,2,'2013-09-24 03:35:10'),(6736,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Other Antivirus Software.png','images/flat_icons/Other Antivirus Software.png',942,2,'2013-09-24 03:35:11'),(6737,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Paint NET.png','images/flat_icons/Paint NET.png',1087,2,'2013-09-24 03:35:10'),(6738,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Paint.png','images/flat_icons/Paint.png',1605,2,'2013-09-24 03:35:10'),(6739,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Peggle Nights.png','images/flat_icons/Peggle Nights.png',1298,2,'2013-09-24 03:35:10'),(6740,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Picture Viewer.png','images/flat_icons/Picture Viewer.png',801,2,'2013-09-24 03:35:10'),(6741,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Pidgin.png','images/flat_icons/Pidgin.png',1210,2,'2013-09-24 03:35:10'),(6742,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'QuickTime.png','images/flat_icons/QuickTime.png',1172,2,'2013-09-24 03:35:10'),(6743,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Rainmeter.png','images/flat_icons/Rainmeter.png',1053,2,'2013-09-24 03:35:11'),(6744,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'RealPlayer.png','images/flat_icons/RealPlayer.png',1274,2,'2013-09-24 03:35:10'),(6745,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'RKLauncher.png','images/flat_icons/RKLauncher.png',1203,2,'2013-09-24 03:35:10'),(6746,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'RocketDock.png','images/flat_icons/RocketDock.png',1587,2,'2013-09-24 03:35:10'),(6747,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Screenshot.png','images/flat_icons/Screenshot.png',589,2,'2013-09-24 03:35:10'),(6748,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'SkyDrive.png','images/flat_icons/SkyDrive.png',1063,2,'2013-09-24 03:35:10'),(6749,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Skype alt.png','images/flat_icons/Skype alt.png',1892,2,'2013-09-24 03:35:10'),(6750,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Skype.png','images/flat_icons/Skype.png',1530,2,'2013-09-24 03:35:10'),(6751,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Speccy.png','images/flat_icons/Speccy.png',1516,2,'2013-09-24 03:35:10'),(6752,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Spotify alt.png','images/flat_icons/Spotify alt.png',1536,2,'2013-09-24 03:35:10'),(6753,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Spotify.png','images/flat_icons/Spotify.png',1269,2,'2013-09-24 03:35:10'),(6754,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'StarDock Fences.png','images/flat_icons/StarDock Fences.png',1395,2,'2013-09-24 03:35:11'),(6755,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'StarDock Start8.png','images/flat_icons/StarDock Start8.png',1639,2,'2013-09-24 03:35:10'),(6756,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Steam alt.png','images/flat_icons/Steam alt.png',1125,2,'2013-09-24 03:35:10'),(6757,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Steam.png','images/flat_icons/Steam.png',991,2,'2013-09-24 03:35:10'),(6758,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Sublime Text 2.png','images/flat_icons/Sublime Text 2.png',801,2,'2013-09-24 03:35:11'),(6759,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Thunderbird.png','images/flat_icons/Thunderbird.png',1608,2,'2013-09-24 03:35:10'),(6760,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Trillian.png','images/flat_icons/Trillian.png',1270,2,'2013-09-24 03:35:10'),(6761,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Tune-Up Utilities.png','images/flat_icons/Tune-Up Utilities.png',1445,2,'2013-09-24 03:35:10'),(6762,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'UltraISO.png','images/flat_icons/UltraISO.png',1817,2,'2013-09-24 03:35:10'),(6763,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Uninstall.png','images/flat_icons/Uninstall.png',1568,2,'2013-09-24 03:35:10'),(6764,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'uTorrent alt.png','images/flat_icons/uTorrent alt.png',1337,2,'2013-09-24 03:35:11'),(6765,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'uTorrent.png','images/flat_icons/uTorrent.png',801,2,'2013-09-24 03:35:10'),(6766,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'VirtualBox.png','images/flat_icons/VirtualBox.png',1658,2,'2013-09-24 03:35:10'),(6767,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Visual Studio 2012.png','images/flat_icons/Visual Studio 2012.png',806,2,'2013-09-24 03:35:10'),(6768,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Visual Studio alt.png','images/flat_icons/Visual Studio alt.png',1116,2,'2013-09-24 03:35:10'),(6769,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Visual Studio.png','images/flat_icons/Visual Studio.png',954,2,'2013-09-24 03:35:10'),(6770,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'VLC Media Player.png','images/flat_icons/VLC Media Player.png',852,2,'2013-09-24 03:35:10'),(6771,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'VMware.png','images/flat_icons/VMware.png',1047,2,'2013-09-24 03:35:10'),(6772,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Winamp.png','images/flat_icons/Winamp.png',1059,2,'2013-09-24 03:35:10'),(6773,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Windows 8 Maps.png','images/flat_icons/Windows 8 Maps.png',1342,2,'2013-09-24 03:35:10'),(6774,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Windows 8 Messaging.png','images/flat_icons/Windows 8 Messaging.png',809,2,'2013-09-24 03:35:10'),(6775,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Windows 8 News.png','images/flat_icons/Windows 8 News.png',533,2,'2013-09-24 03:35:10'),(6776,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Windows 8 Photos.png','images/flat_icons/Windows 8 Photos.png',602,2,'2013-09-24 03:35:10'),(6777,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Windows 8 Store.png','images/flat_icons/Windows 8 Store.png',977,2,'2013-09-24 03:35:11'),(6778,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Windows Live Mesh.png','images/flat_icons/Windows Live Mesh.png',1595,2,'2013-09-24 03:35:10'),(6779,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Windows Media Player alt.png','images/flat_icons/Windows Media Player alt.png',1396,2,'2013-09-24 03:35:10'),(6780,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Windows Media Player.png','images/flat_icons/Windows Media Player.png',776,2,'2013-09-24 03:35:10'),(6781,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Windows MovieMaker.png','images/flat_icons/Windows MovieMaker.png',1845,2,'2013-09-24 03:35:10'),(6782,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Wordpad.png','images/flat_icons/Wordpad.png',836,2,'2013-09-24 03:35:10'),(6783,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Xunlei.png','images/flat_icons/Xunlei.png',1020,2,'2013-09-24 03:35:10'),(6784,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'XWindows Dock.png','images/flat_icons/XWindows Dock.png',1273,2,'2013-09-24 03:35:10'),(6785,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Y\'z Dock.png','images/flat_icons/Y\'z Dock.png',1013,2,'2013-09-24 03:35:10'),(6786,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Yahoo Messenger alt.png','images/flat_icons/Yahoo Messenger alt.png',1137,2,'2013-09-24 03:35:10'),(6787,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Yahoo Messenger.png','images/flat_icons/Yahoo Messenger.png',1242,2,'2013-09-24 03:35:10'),(6788,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Zune alt.png','images/flat_icons/Zune alt.png',1689,2,'2013-09-24 03:35:10'),(6789,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'Zune.png','images/flat_icons/Zune.png',1784,2,'2013-09-24 03:35:10'),(6790,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'bank_account.png','images/small_icons/bank_account.png',595,3,'2013-04-20 03:38:53'),(6791,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'bussObjectMapping.png','images/small_icons/bussObjectMapping.png',681,3,'2013-03-29 08:24:25'),(6792,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'childFlow_16.png','images/small_icons/childFlow_16.png',585,3,'2013-08-24 03:00:34'),(6793,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'company_account_16.png','images/small_icons/company_account_16.png',589,3,'2013-07-22 02:28:18'),(6794,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'deskstopRight_16.png','images/small_icons/deskstopRight_16.png',447,3,'2013-07-22 02:28:18'),(6795,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'deskstop_16.png','images/small_icons/deskstop_16.png',617,3,'2013-07-22 02:28:18'),(6796,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'financesys.png','images/small_icons/financesys.png',618,3,'2013-03-29 07:27:35'),(6797,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'finCustField.png','images/small_icons/finCustField.png',854,3,'2013-03-30 03:35:45'),(6798,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'gradient.png','images/small_icons/gradient.png',20608,3,'2013-07-09 13:46:21'),(6799,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'settleType.png','images/small_icons/settleType.png',739,3,'2013-03-29 09:50:33'),(6800,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'subject.png','images/small_icons/subject.png',718,3,'2013-03-29 12:33:10'),(6801,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'sysconfig_16.png','images/small_icons/sysconfig_16.png',902,3,'2013-07-16 13:18:29'),(6802,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'sysmapping_16.png','images/small_icons/sysmapping_16.png',619,3,'2013-07-16 13:20:16'),(6803,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'userMapping.png','images/small_icons/userMapping.png',877,3,'2013-03-29 11:30:48'),(6804,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'voucherGroup.png','images/small_icons/voucherGroup.png',681,3,'2013-03-29 09:57:26'),(6805,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'voucherTemp_16.png','images/small_icons/voucherTemp_16.png',427,3,'2013-07-16 13:12:47'),(6806,'2013-12-16 08:05:02',2,8,NULL,1,NULL,'2014-02-21 07:31:27',-1,NULL,'wheel.png','images/small_icons/wheel.png',14349,3,'2013-07-09 13:46:21');
/*!40000 ALTER TABLE `ts_icon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_matparams`
--

DROP TABLE IF EXISTS `ts_matparams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_matparams` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `allowBlank` int(11) DEFAULT NULL,
  `isAttach` int(11) DEFAULT NULL,
  `isRemark` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `subjectId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_matparams`
--

LOCK TABLES `ts_matparams` WRITE;
/*!40000 ALTER TABLE `ts_matparams` DISABLE KEYS */;
INSERT INTO `ts_matparams` VALUES (1,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'营业执照（年检）',1,1),(2,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'组织机构代码证',2,1),(3,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'国、地税税务登记证',3,1),(4,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'法人代表证明书和法人代表授权书',4,1),(5,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'法人代表及委托代理人身份证',5,1),(6,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'公司章程、企业注册地工商局出具的企业机读档案',6,1),(7,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'注册资本验资报告',7,1),(8,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'银行开户许可证',8,1),(9,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'贷款卡及贷款卡回执单复印件',9,1),(10,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'环评报告',10,1),(11,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'企业及实际控制人信用报告，借款、担保合同',11,1),(12,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'《委托担保申请书》及董事会、股东（大）会同意借款的决议',12,1),(13,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'《企业贷款担保申报书》及用款还款计划',13,1),(14,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'高管简历',14,1),(15,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'当期财务报表及近3年的财务报表和经会计师事务所审计的审计报告',15,1),(16,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'与借款用途有关的资料：购销协议、合作协议',16,1),(17,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'公司重大固定资产购置合同公司主要账户近6个月主要银行对账单',17,1),(18,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'公司实际控制人（股东或总经理等）个人近6个月银行对账单',18,1),(19,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'近3个月大额应收账款对账单',19,1),(20,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'近6个月生产用水电发票',20,1),(21,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'项目可行性报告及主管部门批件',21,1),(22,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'生产经营情况、企业获得的各项许可、荣誉等证明',22,1),(23,'2013-10-31 16:00:00',2,8,NULL,1,2,'2013-12-22 16:00:00',-1,NULL,0,1,1,'主要存货、长期投资、固定资产、银行借款明细表，应收账款帐龄分析表和有关负债情况表',23,1),(24,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'其他有关材料',24,1),(25,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:27',-1,NULL,0,1,1,'法人营业执照（年检）',1,2),(26,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'组织机构代码证',2,2),(27,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'法人代表证明书',3,2),(28,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'法人代表授权书',4,2),(29,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'法人代表及委托代理人身份证',5,2),(30,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'注册资本验资报告',6,2),(31,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'贷款卡及贷款卡回执单',7,2),(32,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'企业及实际控制人信用报告，借款、担保合同',8,2),(33,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'公司章程及公司合同',9,2),(34,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'当期财务报表及近3年的财务报表和审计报告',10,2),(35,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'主要存货、长期投资、固定资产、银行借款明细表，应收账款帐龄分析表和或有负债情况表',11,2),(36,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'其他有关材料',12,2),(37,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'抵押物、质物清单',1,3),(38,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'抵押物、质物权力凭证',2,3),(39,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'抵押物、质物评估资料',3,3),(40,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'保险单',4,3),(41,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'股东(大）会、董事会同意抵押、质押的决议',5,3),(42,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'股东(大）会、董事会同意抵押、质押的决议',6,3),(43,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'抵押物、质物为海关监管的，提供海关同意抵押或质押的证明',7,3),(44,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'抵押物、质物为海关监管的，提供海关同意抵押或质押的证明',8,3),(45,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'其他有关材料',9,3),(46,'2013-11-01 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'身份证',1,4),(47,'2013-11-01 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'户口簿',2,4),(48,'2013-11-01 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'婚姻证明',3,4),(49,'2013-11-01 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'收入证明',4,4),(50,'2013-11-01 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'个人银行卡现金流交易明细（最近三个月',5,4),(51,'2013-11-01 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'公司近三个月的财务报表或年度报表',6,4),(52,'2013-11-01 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'个人征信记录（借款人及保证人）',7,4),(53,'2013-11-01 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'抵质押担保物权属证明及共有人同意担保的书面证明',8,4),(54,'2013-11-01 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,0,1,1,'借款用途及还款来源的相关资料',9,4);
/*!40000 ALTER TABLE `ts_matparams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_matresult`
--

DROP TABLE IF EXISTS `ts_matresult`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_matresult` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `formId` double DEFAULT NULL,
  `formId2` varchar(50) DEFAULT NULL,
  `isRemark` int(11) DEFAULT NULL,
  `result` varchar(50) DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_matresult`
--

LOCK TABLES `ts_matresult` WRITE;
/*!40000 ALTER TABLE `ts_matresult` DISABLE KEYS */;
INSERT INTO `ts_matresult` VALUES (6,'2013-12-16 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,'',4,'48',1,'0',3),(9,'2013-12-16 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,'',4,'46',1,'0,1',3),(10,'2013-12-16 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,'',4,'47',1,'0,1',3),(14,'2013-12-16 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:31:28',1,'',5,'2',1,'0,1',3),(15,'2013-12-16 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:31:28',1,'',5,'3',1,'0,1',3),(16,'2013-12-16 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:31:28',1,'',5,'5',1,'0,1',3),(17,'2013-12-16 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:31:28',1,'',5,'12',1,'1',3),(18,'2013-12-16 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:31:28',1,'',5,'24',1,'0',3),(19,'2013-12-16 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:31:28',1,'',17,'47',1,'1',3),(20,'2013-12-16 16:00:00',9,3,-1,1,NULL,'2014-02-21 07:31:28',1,'',17,'48',1,'1',3),(21,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'2',1,'0',3),(22,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'3',1,'1',3),(23,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'5',1,'0',3),(24,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'6',1,'0',3),(25,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'8',1,'0',3),(26,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'10',1,'1',3),(27,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'11',1,'0',3),(28,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'12',1,'1',3),(29,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'13',1,'1',3),(30,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'14',1,'1',3),(31,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'15',1,'1',3),(32,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'16',1,'1',3),(33,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'17',1,'1',3),(34,'2014-03-11 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',20,'18',1,'1',3),(35,'2013-12-16 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',18,'46',1,'1',3),(36,'2013-12-16 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',18,'47',1,'1',3),(37,'2013-12-16 16:00:00',7,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',18,'49',1,'1',3),(40,'2013-12-17 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,'',11,'46',1,'0',3),(41,'2013-12-17 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,'',11,'47',1,'0',3),(42,'2013-12-17 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,'',11,'48',1,'0',3),(43,'2013-12-17 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,'',11,'49',1,'1',3),(44,'2013-12-17 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,'',11,'50',1,'0,1',3),(45,'2013-12-17 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,'',11,'51',1,'1',3),(47,'2013-12-18 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,'',41,'46',1,'1',3),(49,'2013-12-18 16:00:00',22,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',23,'49',1,'1',3),(51,'2013-12-18 16:00:00',22,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',24,'46',1,'1',3),(52,'2013-12-18 16:00:00',22,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',24,'47',1,'1',3),(53,'2013-12-18 16:00:00',22,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',24,'48',1,'1',3),(54,'2013-12-18 16:00:00',22,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',24,'50',1,'1',3),(55,'2013-12-18 16:00:00',4,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',1,'46',1,'0',3),(56,'2013-12-18 16:00:00',4,2,-1,1,NULL,'2014-02-21 07:31:28',1,'',1,'47',1,'1',3);
/*!40000 ALTER TABLE `ts_matresult` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_matsubjec`
--

DROP TABLE IF EXISTS `ts_matsubjec`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_matsubjec` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `tempId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_matsubjec`
--

LOCK TABLES `ts_matsubjec` WRITE;
/*!40000 ALTER TABLE `ts_matsubjec` DISABLE KEYS */;
INSERT INTO `ts_matsubjec` VALUES (1,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,'(一)借款人（担保申请人）应提供的材料应提供的资料',1,1),(2,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,'(二) 反担保第三人应提供的材料：企业客户贷款申请应提供的资料',2,1),(3,'2013-10-31 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,'(三) 反担保方式为抵押或质押应提供的材料关联公司资料企业客户贷款申请应提供的资料',3,1),(4,'2013-11-01 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,'个人贷款资料',1,2);
/*!40000 ALTER TABLE `ts_matsubjec` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_mattemp`
--

DROP TABLE IF EXISTS `ts_mattemp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_mattemp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `breedId` double DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  `custType` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_mattemp`
--

LOCK TABLES `ts_mattemp` WRITE;
/*!40000 ALTER TABLE `ts_mattemp` DISABLE KEYS */;
INSERT INTO `ts_mattemp` VALUES (1,'2013-10-31 16:00:00',2,8,NULL,1,2,'2013-11-21 16:00:00',-1,NULL,3,'企业客户应提供的材料',3,1),(2,'2013-11-01 16:00:00',2,8,NULL,1,2,'2013-11-21 16:00:00',-1,NULL,2,'个人客户应提供的资料',3,0);
/*!40000 ALTER TABLE `ts_mattemp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_menu`
--

DROP TABLE IF EXISTS `ts_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_menu` (
  `menuId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` bigint(20) DEFAULT NULL,
  `deptId` bigint(20) DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` bigint(20) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `iconCls` varchar(200) DEFAULT NULL,
  `jsArray` varchar(200) DEFAULT NULL,
  `link` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isSystem` smallint(6) DEFAULT NULL,
  `accordionId` bigint(20) DEFAULT NULL,
  `leaf` varchar(255) DEFAULT NULL,
  `params` varchar(200) DEFAULT NULL,
  `biconCls` varchar(200) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `loadType` bigint(20) DEFAULT NULL,
  `tabId` varchar(50) DEFAULT NULL,
  `cached` int(11) DEFAULT NULL,
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB AUTO_INCREMENT=239 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_menu`
--

LOCK TABLES `ts_menu` WRITE;
/*!40000 ALTER TABLE `ts_menu` DISABLE KEYS */;
INSERT INTO `ts_menu` VALUES (49,'2011-12-31 16:11:00',-1,-1,1,2,'2013-10-07 16:00:00',0,NULL,'20121101015443',NULL,'pages/app/sys/org/OrgMgr.js',NULL,'组织架构管理',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',1,1,NULL,0),(50,'2011-12-31 16:11:00',-1,-1,1,2,'2013-10-07 16:00:00',0,NULL,'20121101022429',NULL,'pages/app/sys/role/RoleMgr.js',NULL,'角色管理',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/role.png',3,1,NULL,0),(51,'2011-12-31 16:11:00',-1,-1,1,2,'2013-10-07 16:00:00',0,NULL,'20121101092153',NULL,'pages/app/sys/user/UserMgr.js',NULL,'用户管理',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/users.png',4,1,NULL,0),(52,'2012-01-01 16:11:00',1,1,1,2,'2013-10-07 16:00:00',9999,NULL,'20121102083453',NULL,'pages/app/sys/gvlist/GvlistMgr.js',NULL,'基础数据设置',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/database.png',5,1,NULL,0),(53,'2012-01-01 16:11:00',1,1,1,2,'2013-10-07 16:00:00',9999,NULL,'20121102083512',NULL,'pages/app/sys/system/SystemMgr.js',NULL,'系统管理',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/system.png',6,1,NULL,0),(54,'2012-01-01 16:11:00',1,1,1,2,'2013-10-07 16:00:00',9999,NULL,'20121102083641',NULL,'pages/app/sys/accordion/AccordionMgr.js',NULL,'卡片菜单管理',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/cardmenu.png',7,1,NULL,0),(55,'2012-01-01 16:11:00',1,1,1,2,'2013-10-07 16:00:00',9999,NULL,'20121102083642',NULL,'pages/app/sys/menu/MenuMgr.js',NULL,'菜单管理',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/menu.png',8,1,NULL,0),(56,'2012-01-19 16:11:00',-1,-1,1,2,'2013-10-07 16:00:00',0,NULL,'20121120095510',NULL,'pages/app/sys/holidays/HolidaysMgr.js',NULL,'节假日设置',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/holidays.png',8,1,NULL,0),(57,'2012-01-20 16:11:00',-1,-1,0,2,'2013-08-11 16:00:00',0,NULL,'20121121024623',NULL,'pages/app/sys/bcode/BusscodeMgr.js',NULL,'编号规则设置',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/gnome-tetravex.png',0,1,NULL,0),(58,'2012-01-21 16:11:00',-1,-1,1,2,'2013-10-07 16:00:00',0,NULL,'20121122090812',NULL,'pages/app/sys/Individuation/FormDiyMgr.js',NULL,'表单个性化',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/adium.png',9,1,NULL,0),(59,'2012-01-27 16:11:00',-1,-1,1,2,'2013-10-07 16:00:00',0,NULL,'20121128073600',NULL,'pages/app/sys/sysparams/SysparamsMgr.js',NULL,'系统参数',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/devices/audio-card.png',10,1,NULL,0),(60,'2012-01-27 16:11:00',-1,-1,1,2,'2013-10-07 16:00:00',0,NULL,'20121128091502',NULL,'pages/app/sys/variety/VarietyMgr.js',NULL,'业务品种管理',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/devices/blockdevice.png',11,1,NULL,0),(61,'2012-01-05 16:12:00',-1,-1,1,-1,'2012-01-05 16:12:00',0,NULL,'20121206034441',NULL,'',NULL,'客户信息',1,21,NULL,0,21,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(62,'2012-01-05 16:12:00',-1,-1,1,2,'2013-10-08 16:00:00',0,NULL,'20121206034748',NULL,'pages/app/workflow/variety/formUIs/listData/custAttMgr.js',NULL,'附件资料清单',1,21,NULL,0,21,'true',NULL,'images/flat_icons/Calendar.png',0,1,NULL,0),(64,'2012-01-05 16:12:00',-1,-1,1,2,'2013-12-16 16:00:00',0,NULL,'20121206035213',NULL,'pages/app/workflow/variety/formUIs/survey/SurveyMgr.js',NULL,'贷款调查报告',1,21,NULL,0,21,'true',NULL,'images/flat_icons/Windows 8 Store.png',0,2,NULL,0),(65,'2012-01-05 16:12:00',-1,-1,1,2,'2013-08-26 16:00:00',0,NULL,'20121206045850',NULL,'pages/app/finance/fcinit/rate/RateMgr.js',NULL,'自定义利率设置',1,26,NULL,0,26,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(68,'2012-01-09 16:12:00',-1,-1,1,2,'2013-08-24 16:00:00',0,NULL,'20121210011720','images/small_icons/voucherTemp_16.png','pages/app/crm/incustomer/CustomerInfoEdit.js',NULL,'添加个人客户资料',1,22,NULL,0,22,'true',NULL,'images/flat_icons/Aero WinFlip 3D.png',0,1,'customerInfoEdit_tabid_6',0),(69,'2012-01-09 16:12:00',-1,-1,1,2,'2013-08-21 16:00:00',0,NULL,'20121210011852',NULL,'pages/app/crm/incustomer/CustomerInfoMgr.js',NULL,'个人客户资料管理',1,22,NULL,0,22,'true',NULL,NULL,0,1,NULL,0),(70,'2012-01-09 16:12:00',-1,-1,1,2,'2013-08-19 16:00:00',0,NULL,'20121210012448',NULL,'pages/app/crm/ecustomer/EcustomerInfoEdit.js',NULL,'添加企业客户资料',1,22,NULL,0,22,'true',NULL,NULL,0,1,'ecustomerInfoEdit_tabid_10',0),(71,'2012-01-09 16:12:00',-1,-1,1,2,'2013-08-19 16:00:00',0,NULL,'20121210013153',NULL,'pages/app/crm/ecustomer/EcustomerInfoMgr.js',NULL,'企业客户资料管理',1,22,NULL,0,22,'true',NULL,NULL,0,1,NULL,0),(72,'2012-01-09 16:12:00',-1,-1,1,2,'2014-02-07 16:00:00',0,NULL,'20121210013226',NULL,'pages/app/finance/bloan/apply/Apply.js',NULL,'贷款申请',1,23,NULL,0,23,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,2,'apply_form_tabid_3',1),(73,'2012-01-09 16:12:00',-1,-1,1,2,'2013-10-07 16:00:00',0,NULL,'20121210063930',NULL,'pages/app/sys/Homes/HomesMgr.js',NULL,'现居区域',1,19,NULL,0,19,'true',NULL,'/images/big_icons/48x48/categories/applications-internet-4.png',13,1,NULL,0),(74,'2012-01-21 16:12:00',-1,-1,1,NULL,'2014-02-21 07:31:28',0,NULL,'20121222015630',NULL,NULL,NULL,'我的暂存业务',1,23,-1,0,23,'false',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(75,'2012-01-21 16:12:00',-1,-1,1,45,'2013-03-05 16:00:00',0,NULL,'20121222015702',NULL,'pages/app/finance/bloan/temporary/TempCustApplyMgr.js',NULL,'个人客户暂存申请单',2,74,NULL,0,23,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,'tempCustApplyMgr_tabid_4',0),(76,'2012-01-21 16:12:00',-1,-1,1,45,'2013-03-05 16:00:00',0,NULL,'20121222015817',NULL,'pages/app/finance/bloan/temporary/TempEntCustApplyMgr.js',NULL,'企业客户暂存申请单',2,74,NULL,0,23,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,'tempEntCustApplyMgr_tabid_8',0),(77,'2012-01-21 16:12:00',-1,-1,1,2,'2013-08-26 16:00:00',0,NULL,'20121222040056',NULL,'pages/app/finance/fcinit/order/OrderMgr.js',NULL,'扣款优先级设置',1,26,NULL,0,26,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(79,'2012-01-22 16:12:00',-1,-1,1,2,'2013-08-26 16:00:00',0,NULL,'20121223041126',NULL,'pages/app/finance/fcinit/risklevel/RiskLevelMgr.js',NULL,'风险等级设置',1,26,NULL,0,26,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(81,'2012-01-26 16:12:00',-1,-1,1,2,'2013-10-07 16:00:00',0,NULL,'20121227075042',NULL,'pages/app/sys/mat/MatTempMgr.js',NULL,'资料模板',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/development-gtk.png',14,1,NULL,0),(84,'2013-01-05 16:01:00',-1,-1,1,2,'2013-08-13 16:00:00',0,NULL,'20130106083420',NULL,'pages/app/finance/auditloan/approval/AuditCustApplyMgr.js',NULL,'个人客户贷款审批',1,24,NULL,0,24,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,'auditCustApplyMgr_tabid_16',0),(85,'2013-01-05 16:01:00',-1,-1,1,2,'2013-08-13 16:00:00',0,NULL,'20130106083450',NULL,'pages/app/finance/auditloan/approval/AuditEntCustApplyMgr.js',NULL,'企业客户贷款审批',1,24,NULL,0,24,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,'auditCustApplyMgr_tabid_17',0),(86,'2013-01-07 16:01:00',1,-1,1,2,'2013-10-08 16:00:00',-1,NULL,'20130108064641',NULL,'pages/app/workflow/variety/formUIs/mort/MortgageMgr.js',NULL,'抵押物登记',1,21,NULL,0,21,'true',NULL,'images/flat_icons/Mac Spaces.png',0,1,NULL,0),(87,'2013-01-07 16:01:00',1,-1,1,2,'2013-10-08 16:00:00',-1,NULL,'20130108064705',NULL,'pages/app/workflow/variety/formUIs/pledge/PledgeMgr.js',NULL,'质押物登记',1,21,NULL,0,21,'true',NULL,'images/flat_icons/Microsoft Security Essentials.png',0,1,NULL,0),(88,'2013-01-09 16:01:00',1,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,'20130110002742',NULL,NULL,NULL,'贷款审批历史',1,24,-1,0,24,'false',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(89,'2013-01-09 16:01:00',1,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,'20130110002836',NULL,'pages/app/finance/auditloan/apphostorys/AuditCustApplyHistory.js',NULL,'个人贷款审批历史',2,88,-1,0,24,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(90,'2013-01-09 16:01:00',1,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,'20130110002935',NULL,'pages/app/finance/auditloan/apphostorys/AuditEntCustApplyHistory.js',NULL,'企业贷款审批历史',2,88,-1,0,24,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(91,'2013-01-09 16:01:00',1,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,'20130110003005',NULL,NULL,NULL,'客户贷款一览表',1,24,-1,0,24,'false',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(92,'2013-01-09 16:01:00',1,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,'20130110003040',NULL,'pages/app/finance/auditloan/applyalls/AuditCustApplyAll.js',NULL,'个人贷款一览表',2,91,-1,0,24,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(93,'2013-01-09 16:01:00',1,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,'20130110003108',NULL,'pages/app/finance/auditloan/applyalls/AuditEntCustApplyAll.js',NULL,'企业贷款一览表',2,91,-1,0,24,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(97,'2013-01-15 16:01:00',1,-1,1,2,'2013-10-08 16:00:00',-1,NULL,'20130116074622',NULL,'pages/app/workflow/variety/formUIs/loancontract/LoancontractMgr.js',NULL,'借款合同登记',1,21,NULL,0,21,'true',NULL,'images/flat_icons/Wordpad.png',0,1,NULL,0),(99,'2013-01-16 16:01:00',1,9999,1,2,'2013-08-19 16:00:00',9999,NULL,'20130117000353',NULL,'pages/app/finance/deduct/loan/CustLoanMgr.js',NULL,'个人贷款发放',1,25,NULL,0,25,'true',NULL,NULL,0,1,'custLoanMgr_tabid_13',0),(100,'2013-01-16 16:01:00',1,9999,1,2,'2013-10-09 16:00:00',9999,NULL,'20130117065203',NULL,'pages/app/workflow/variety/formUIs/loaninvoce/LoaninvoceMgr.js',NULL,'放款单',1,21,NULL,0,21,'true',NULL,'images/flat_icons/Windows 8 News.png',0,1,NULL,0),(101,'2013-01-18 16:01:00',1,9999,1,2,'2013-10-07 16:00:00',9999,NULL,'20130119105359',NULL,'pages/app/sys/reporttemp/ReportTempMgr.js',NULL,'Excel报表模板管理',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/Excel.png',15,1,NULL,0),(103,'2013-01-22 16:01:00',45,8,1,NULL,'2014-02-21 07:31:28',2,NULL,'20130123072620',NULL,'pages/app/finance/fcinit/payType/PayTypeMgr.js',NULL,'还款方式公式设置',1,26,-1,0,26,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(104,'2013-01-25 16:01:00',45,8,1,2,'2013-10-08 16:00:00',2,NULL,'20130126023138',NULL,'pages/app/workflow/variety/formUIs/loaninvoce/LoanInvoceSubmintMgr.js',NULL,'放款通知书审批',1,21,NULL,0,21,'true',NULL,'images/flat_icons/Notepad.png',0,1,'submintLoanInvoce_tabid_11',0),(107,'2013-01-28 16:01:00',37,-1,1,2,'2013-08-22 16:00:00',-1,NULL,'20130129071021',NULL,'pages/app/workflow/variety/formUIs/mortcontract/MortContractMgr.js',NULL,'抵押合同',1,21,NULL,0,21,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(108,'2013-01-28 16:01:00',37,-1,1,2,'2013-08-22 16:00:00',-1,NULL,'20130129081136',NULL,'pages/app/workflow/variety/formUIs/gua/GuaContractMgr.js',NULL,'保证合同',1,21,NULL,0,21,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(109,'2013-01-29 16:01:00',45,8,1,2,'2013-11-05 16:00:00',2,NULL,'20130130060046',NULL,'pages/app/finance/deduct/nomal/NomalDeductMgr.js',NULL,'正常还款管理',1,25,NULL,0,25,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,'nomalDeductMgr_tabid_15',0),(110,'2013-01-21 16:02:00',37,-1,1,45,'2013-03-09 16:00:00',-1,NULL,'20130222094622',NULL,'pages/app/finance/deduct/eloan/EcustLoanMgr.js',NULL,'企业贷款发放',1,25,NULL,0,25,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,'ecustLoanMgr_tabid_14',0),(111,'2013-01-21 16:02:00',37,-1,1,2,'2013-08-22 16:00:00',-1,NULL,'20130222043554',NULL,'pages/app/workflow/variety/formUIs/plecontract/PleContractMgr.js',NULL,'质押合同',1,21,NULL,0,21,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(112,'2013-01-24 16:02:00',37,-1,1,2,'2013-10-07 16:00:00',-1,NULL,'20130225002510','images/small_icons/deskstop_16.png','pages/app/sys/deskmod/DeskModMgr.js',NULL,'桌面模块配置',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/desktop.png',16,1,NULL,0),(113,'2013-01-25 16:02:00',37,-1,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20130226105531',NULL,'pages/app/finance/deduct/free/FreeMgr.js',NULL,'放款手续费收取',1,25,-1,0,25,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(114,'2013-01-27 16:02:00',45,8,1,NULL,'2014-02-21 07:31:28',2,NULL,'20130228034211',NULL,'pages/app/finance/fcinit/minamount/MinAmountMgr.js',NULL,'最低收费标准管理',1,26,-1,0,26,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(115,'2013-03-02 16:00:00',45,8,1,45,'2013-03-09 16:00:00',2,NULL,'20130303081920',NULL,'pages/app/finance/deduct/overdue/OverdueDeductMgr.js',NULL,'逾期还款管理',1,25,NULL,0,25,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,'overdueDeductMgr_tabid_15',0),(116,'2013-03-08 16:00:00',45,8,1,2,'2013-10-07 16:00:00',2,NULL,'20130309101802','images/small_icons/deskstopRight_16.png','pages/app/sys/deskmod/RoleModMgr.js',NULL,'桌面权限配置',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/tsclient.png',17,1,NULL,0),(119,'2013-03-15 16:00:00',45,8,0,NULL,'2014-02-21 07:31:28',2,NULL,'20130316025311',NULL,'pages/app/workflow/formUIs/mort/MortgageMgr.js',NULL,'抵押物登记',1,26,-1,0,26,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(120,'2013-03-27 16:00:00',45,8,1,2,'2013-08-11 16:00:00',2,NULL,'20130328042415','images/small_icons/sysconfig_16.png','pages/app/fininter/finsyscfg/FinSysCfgMgr.js',NULL,'财务系统配置',1,28,NULL,0,28,'true',NULL,'images/big_icons/48x48/apps/finance_sys.png',0,1,NULL,0),(121,'2013-03-28 16:00:00',45,8,1,2,'2013-08-11 16:00:00',2,NULL,'20130329112834','images/small_icons/sysmapping_16.png','pages/app/fininter/bussfincfg/BussFinCfgMgr.js',NULL,'业务财务映射',1,28,NULL,0,28,'true',NULL,'images/big_icons/48x48/apps/rainbow.png',0,1,NULL,0),(122,'2013-03-28 16:00:00',45,8,1,2,'2013-08-11 16:00:00',2,NULL,'20130329032056','images/small_icons/financesys.png','pages/app/fininter/currency/CurrencyMgr.js',NULL,'币别配置',1,28,NULL,0,28,'true',NULL,'images/big_icons/48x48/apps/moneycfg.png',0,1,NULL,0),(123,'2013-03-28 16:00:00',45,8,1,2,'2013-08-11 16:00:00',2,NULL,'20130329042542','images/small_icons/bussObjectMapping.png','pages/app/fininter/itemclass/ItemClassMgr.js',NULL,'核算项配置',1,28,NULL,0,28,'true',NULL,'images/big_icons/48x48/apps/bussObjectMapping.png',0,1,NULL,0),(124,'2013-03-28 16:00:00',45,8,1,2,'2013-08-11 16:00:00',2,NULL,'20130329054745','images/small_icons/settleType.png','pages/app/fininter/settle/SettleMgr.js',NULL,'结算方式配置',1,28,NULL,0,28,'true',NULL,'images/big_icons/48x48/apps/settleType.png',0,1,NULL,0),(125,'2013-03-28 16:00:00',45,8,1,2,'2013-08-11 16:00:00',2,NULL,'20130329055834','images/small_icons/voucherGroup.png','pages/app/fininter/vouchergroup/VoucherGroupMgr.js',NULL,'凭证字配置',1,28,NULL,0,28,'true',NULL,'images/big_icons/48x48/apps/voucherGroup.png',0,1,NULL,0),(126,'2013-03-28 16:00:00',45,8,1,2,'2013-08-11 16:00:00',2,NULL,'20130329073315','images/small_icons/userMapping.png','pages/app/fininter/usermapping/UserMappingMgr.js',NULL,'用户帐号映射',1,28,NULL,0,28,'true',NULL,'images/big_icons/48x48/apps/userMapping.png',0,1,NULL,0),(127,'2013-03-28 16:00:00',45,8,1,2,'2013-08-11 16:00:00',2,NULL,'20130329083410','images/small_icons/subject.png','pages/app/fininter/subject/SubjectMgr.js',NULL,'科目配置',1,28,NULL,0,28,'true',NULL,'images/big_icons/48x48/apps/subject.png',0,1,NULL,0),(129,'2013-03-29 16:00:00',45,8,1,2,'2013-08-11 16:00:00',2,NULL,'20130330113739','images/small_icons/finCustField.png','pages/app/fininter/fincustfield/FinCustFieldMgr.js',NULL,'自定义字段配置',1,28,NULL,0,28,'true',NULL,'images/big_icons/48x48/apps/finCustField.png',0,1,NULL,0),(130,'2013-04-06 16:00:00',45,8,1,2,'2013-08-11 16:00:00',2,NULL,'20130407051829','images/small_icons/voucherTemp_16.png','pages/app/fininter/vtemp/VoucherTempMgr.js',NULL,'凭证模板配置',1,28,NULL,0,28,'true',NULL,'images/big_icons/48x48/apps/flashkard.png',0,1,NULL,0),(131,'2013-04-19 16:00:00',45,8,1,2,'2013-08-11 16:00:00',2,NULL,'20130420113923','images/small_icons/bank_account.png','pages/app/fininter/bankaccount/BankAccountMgr.js',NULL,'银行账号配置',1,28,NULL,0,28,'true',NULL,'images/big_icons/48x48/apps/bank_account.png',1,1,NULL,0),(132,'2013-05-18 16:00:00',2,8,1,2,'2013-10-07 16:00:00',-1,NULL,'20130519113451','images/small_icons/company_account_16.png','pages/app/sys/account/AccountMgr.js',NULL,'公司帐户',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/company_account_48.png',18,1,NULL,0),(133,'2013-05-20 16:00:00',45,8,1,45,'2013-05-20 16:00:00',2,NULL,'20130521025957',NULL,NULL,NULL,'个人客户查询',1,29,NULL,0,29,'false',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(134,'2013-05-20 16:00:00',45,8,1,45,'2013-05-20 16:00:00',2,NULL,'20130521030133',NULL,NULL,NULL,'企业客户查询',1,29,NULL,0,29,'false',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(135,'2013-05-20 16:00:00',45,8,1,45,'2013-05-22 16:00:00',2,NULL,'20130521030317',NULL,NULL,NULL,'个人客户贷款查询',1,29,NULL,0,29,'false',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(136,'2013-05-20 16:00:00',45,8,1,45,'2013-05-22 16:00:00',2,NULL,'20130521031259',NULL,NULL,NULL,'企业客户贷款查询',1,29,NULL,0,29,'false',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(137,'2013-05-20 16:00:00',45,8,1,2,'2013-08-04 16:00:00',2,NULL,'20130521033209',NULL,'pages/app/finance/reports/customer/CustomerSearchMgr.js',NULL,'个人客户资料查询',2,133,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(138,'2013-05-20 16:00:00',45,8,1,2,'2013-08-04 16:00:00',2,NULL,'20130521033652',NULL,'pages/app/finance/reports/customer/EcustomerSearchMgr.js',NULL,'企业客户资料查询',2,134,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(139,'2013-05-20 16:00:00',45,8,1,2,'2013-08-04 16:00:00',2,NULL,'20130521033956',NULL,'pages/app/finance/reports/apply/AuditCustApplyAll.js',NULL,'个人客户贷款查询',2,135,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(140,'2013-05-20 16:00:00',45,8,1,2,'2013-08-04 16:00:00',2,NULL,'20130521034046',NULL,'pages/app/finance/reports/apply/AuditEntCustApplyAll.js',NULL,'企业客户贷款查询',2,136,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(141,'2013-05-25 16:00:00',45,8,1,NULL,'2014-02-21 07:31:28',2,NULL,'20130526020146',NULL,NULL,NULL,'合同查询',1,29,-1,0,29,'false',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(142,'2013-05-25 16:00:00',45,8,1,2,'2013-08-04 16:00:00',2,NULL,'20130526021316',NULL,'pages/app/finance/reports/contract/LoanSearchMgr.js',NULL,'借款合同查询',2,141,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(143,'2013-05-25 16:00:00',45,8,1,2,'2013-08-04 16:00:00',2,NULL,'20130526021340',NULL,'pages/app/finance/reports/contract/GuaSearchMgr.js',NULL,'保证合同查询',2,141,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(144,'2013-05-25 16:00:00',45,8,1,2,'2013-08-04 16:00:00',2,NULL,'20130526021357',NULL,'pages/app/finance/reports/contract/MortSearchMgr.js',NULL,'抵押合同查询',2,141,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(145,'2013-05-25 16:00:00',45,8,1,2,'2013-08-04 16:00:00',2,NULL,'20130526021409',NULL,'pages/app/finance/reports/contract/PleSearchMgr.js',NULL,'质押合同查询',2,141,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(146,'2013-08-04 16:00:00',2,8,1,2,'2013-08-19 16:00:00',-1,NULL,'20130805051602',NULL,'pages/app/finance/reports/FundReport.js',NULL,'资金头寸分析',1,29,NULL,0,29,'true',NULL,NULL,0,1,NULL,0),(147,'2013-08-04 16:00:00',2,8,0,2,'2013-08-19 16:00:00',-1,NULL,'20130805051823',NULL,NULL,NULL,'放贷汇总表',1,29,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(148,'2013-08-04 16:00:00',2,8,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20130805051836',NULL,NULL,NULL,'个人放贷明细',1,29,-1,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(149,'2013-08-04 16:00:00',2,8,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20130805051855',NULL,NULL,NULL,'企业放贷明细',1,29,-1,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(150,'2013-08-04 16:00:00',2,8,1,2,'2013-08-04 16:00:00',-1,NULL,'20130805051859',NULL,'pages/app/finance/reports/LoanRateReport.js',NULL,'贷款发放利率表',1,29,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(151,'2013-08-04 16:00:00',2,8,1,2,'2013-08-06 16:00:00',-1,NULL,'20130805051915',NULL,'pages/app/finance/reports/LoanSortReport.js',NULL,'贷款发放分类表',1,29,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(152,'2013-08-04 16:00:00',2,8,1,2,'2013-08-16 16:00:00',-1,NULL,'20130805051923',NULL,'pages/app/finance/reports/BalanceReport.js',NULL,'贷款余额汇总表',1,29,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(153,'2013-08-04 16:00:00',2,8,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20130805051931',NULL,NULL,NULL,'营业收入汇总表',1,29,-1,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(154,'2013-08-04 16:00:00',2,8,1,2,'2013-08-09 16:00:00',-1,NULL,'20130805051944',NULL,'pages/app/finance/reports/LoanStructReport.js',NULL,'贷款业务结构表',1,29,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(155,'2013-08-04 16:00:00',2,8,0,2,'2013-08-09 16:00:00',-1,NULL,'20130805051957',NULL,NULL,NULL,'表内信息查询',1,29,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(156,'2013-08-04 16:00:00',2,8,0,2,'2013-08-09 16:00:00',-1,NULL,'20130805052020',NULL,NULL,NULL,'表外信息查询',1,29,NULL,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(157,'2013-08-09 16:00:00',2,8,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20130810102240',NULL,NULL,NULL,'五级分类统计',1,29,-1,0,29,'true',NULL,'images/big_icons/48x48/places/crystal-style/user-home2-txr.png',0,1,NULL,0),(193,'2013-08-12 16:00:00',2,8,1,2,'2013-08-26 16:00:00',-1,NULL,'20130813003356',NULL,'pages/app/finance/fcinit/ownfunds/OwnFundsMgr.js',NULL,'自有资金管理',1,26,NULL,0,26,'true',NULL,NULL,0,1,NULL,0),(194,'2013-05-16 16:00:00',2,8,1,2,'2013-10-07 16:00:00',-1,NULL,'20130517115307','images/small_icons/childFlow_16.png','pages/app/sys/bussprocc/BussProccMgr.js',NULL,'子业务流程管理',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/apps/kedit-3.png',12,1,NULL,0),(195,'2013-08-23 16:00:00',2,8,1,2,'2013-09-09 16:00:00',-1,NULL,'20130824103617',NULL,'pages/app/workflow/bussProcc/BussProccBase.js',NULL,'展期申请',1,23,NULL,0,23,'true',NULL,NULL,0,2,'extensionApply_tabid_18',0),(196,'2013-08-23 16:00:00',2,8,1,2,'2013-09-09 16:00:00',-1,NULL,'20130824103807',NULL,'pages/app/finance/bloan/extension/TempExtensionApplyMgr.js',NULL,'暂存的展期申请',1,23,NULL,0,23,'true',NULL,NULL,0,1,'tempExtensionApplyMgr_tabid_19',0),(197,'2013-08-23 16:00:00',2,8,0,2,'2013-09-12 16:00:00',-1,NULL,'20130824104131',NULL,'pages/app/workflow/bussProcc/BussProccBase.js',NULL,'提前还款申请',1,23,NULL,0,23,'true',NULL,NULL,0,2,'prepaymentApply_tabid_21',0),(198,'2013-08-23 16:00:00',2,8,0,2,'2013-09-12 16:00:00',-1,NULL,'20130824104738',NULL,'pages/app/finance/bloan/prepayment/TempPrepaymentApplyMgr.js',NULL,'暂存的提前还款申请',1,23,NULL,0,23,'true',NULL,NULL,0,1,'tempPrepaymentApplyMgrTabId',0),(199,'2013-08-23 16:00:00',2,8,0,2,'2013-09-20 16:00:00',-1,NULL,'20130824105032',NULL,'pages/app/workflow/bussProcc/BussProccBase.js',NULL,'息费豁免申请',1,23,NULL,0,23,'true',NULL,NULL,0,2,'exemptApply_tabid_23',0),(200,'2013-08-23 16:00:00',2,8,0,2,'2013-09-20 16:00:00',-1,NULL,'20130824105102',NULL,'pages/app/finance/bloan/exempt/TempExemptApplyMgr.js',NULL,'暂存的息费豁免申请',1,23,NULL,0,23,'true',NULL,NULL,0,1,'tempExemptApplyMgr_tabid_24',0),(201,'2013-09-20 16:00:00',2,8,1,2,'2013-09-20 16:00:00',-1,NULL,'20130921101407',NULL,'pages/app/finance/reports/contract/LoanInvoceSearchMgr.js',NULL,'还款计划查询',2,141,NULL,0,29,'true',NULL,NULL,0,1,NULL,0),(202,'2013-09-22 16:00:00',2,8,1,2,'2013-09-25 16:00:00',-1,NULL,'20130923112817',NULL,'pages/app/workflow/bussProcc/formUIs/extcontract/ExtContractMgr.js',NULL,'展期协议书',1,30,NULL,0,30,'true',NULL,'images/flat_icons/Wordpad.png',0,1,NULL,0),(203,'2013-09-22 16:00:00',2,8,0,2,'2013-09-25 16:00:00',-1,NULL,'20130923063803',NULL,'pages/app/workflow/variety/formUIs/mort/MortgageMgr.js',NULL,'提前还款复核',1,30,NULL,0,30,'true',NULL,'images/flat_icons/XWindows Dock.png',0,1,NULL,0),(204,'2013-09-22 16:00:00',2,8,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20130923063854',NULL,NULL,NULL,'息费豁免（财务）',1,30,-1,0,30,'true',NULL,'images/flat_icons/CrashPlan.png',0,1,NULL,0),(205,'2013-09-22 16:00:00',2,8,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20130923064327',NULL,NULL,NULL,'展期单据详情',1,30,-1,0,30,'true',NULL,'images/flat_icons/Notepad.png',0,1,NULL,0),(206,'2013-09-22 16:00:00',2,8,0,2,'2013-09-25 16:00:00',-1,'提前还款申请单详细信息查看','20130923064501',NULL,NULL,NULL,'提前还款详情',1,30,NULL,0,30,'true',NULL,'images/flat_icons/Windows 8 Store.png',0,1,NULL,0),(207,'2013-09-22 16:00:00',2,8,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20130923064619',NULL,NULL,NULL,'息费豁免详情',1,30,-1,0,30,'true',NULL,'images/flat_icons/Sublime Text 2.png',0,1,NULL,0),(208,'2013-09-23 16:00:00',2,8,1,2,'2013-10-24 16:00:00',-1,NULL,'20130924005547',NULL,'pages/app/finance/auditloan/extension/AuditExtensionMgr.js',NULL,'展期审批',1,24,NULL,0,24,'true',NULL,NULL,0,1,'AuditExtensionMgr_tabId_26',0),(209,'2013-09-29 16:00:00',2,8,1,NULL,'2014-02-21 07:31:28',-1,NULL,'20130930002404',NULL,NULL,NULL,'展期查询',1,29,-1,0,29,'false',NULL,NULL,0,1,NULL,0),(210,'2013-09-29 16:00:00',2,8,1,2,'2013-09-29 16:00:00',-1,NULL,'20130930002434',NULL,'pages/app/finance/reports/extcontract/ExtContractSearchMgr.js',NULL,'展期还款计划查询',2,209,NULL,0,29,'true',NULL,NULL,0,1,NULL,0),(211,'2013-10-03 16:00:00',2,8,0,2,'2013-10-24 16:00:00',-1,NULL,'20131004001520',NULL,'pages/app/finance/auditloan/prepayment/AuditPrepaymentMgr.js',NULL,'提前还款审批',1,24,NULL,0,24,'true',NULL,NULL,0,1,'AuditPrepaymentMgr_tabId_27',0),(212,'2013-10-03 16:00:00',2,8,0,2,'2013-10-24 16:00:00',-1,NULL,'20131004001555',NULL,'pages/app/finance/auditloan/exempt/AuditExemptMgr.js',NULL,'息费豁免审批',1,24,NULL,0,24,'true',NULL,NULL,0,1,'AuditExemptMgr_tabId_28',0),(213,'2013-10-06 16:00:00',2,8,1,2,'2013-10-07 16:00:00',-1,NULL,'20131007061806','images/small_icons/company_account_16.png','pages/app/sys/post/PostMgr.js',NULL,'职位管理',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/intl/round-icons/flag-zw.png',2,1,NULL,0),(214,'2013-10-15 16:00:00',2,8,1,2,'2013-10-15 16:00:00',-1,NULL,'20131016061500',NULL,'pages/app/sys/voulog/VoucherOplogMgr.js',NULL,'财务凭证记录',1,19,NULL,0,19,'true',NULL,'images/big_icons/48x48/mimetypes/oxygen-style/text-x-yacc.png',18,1,NULL,0),(215,'2013-10-15 16:00:00',2,8,1,2,'2013-10-15 16:00:00',-1,'参数格式与普通URL链接后带参数格式相同，\n例如：\n    hasTopSys=false&sysid=5','20131016082917',NULL,'pages/app/sys/voulog/VoucherOplogMgr.js',NULL,'凭证日志',1,25,NULL,0,25,'true','hasTopSys=false',NULL,0,1,NULL,0),(216,'2013-10-25 16:00:00',2,-1,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20131026104029',NULL,'pages/app/crm/ecustomer/testImg.js',NULL,'测试',1,22,-1,0,22,'true',NULL,NULL,0,1,NULL,0),(217,'2013-10-25 16:00:00',2,-1,1,2,'2013-10-25 16:00:00',-1,NULL,'20131026044411',NULL,'pages/app/finance/auditloan/extension/AuditExtensionHistory.js',NULL,'展期审批历史',1,24,NULL,0,24,'true',NULL,NULL,0,1,NULL,0),(218,'2013-10-25 16:00:00',2,-1,0,2,'2013-10-25 16:00:00',-1,NULL,'20131026044535',NULL,'pages/app/finance/auditloan/prepayment/AuditPrepaymentHistory.js',NULL,'提前还款审批历史',1,24,NULL,0,24,'true',NULL,NULL,0,1,NULL,0),(219,'2013-10-25 16:00:00',2,-1,0,2,'2013-10-25 16:00:00',-1,NULL,'20131026044557',NULL,'pages/app/finance/auditloan/exempt/AuditExemptHistory.js',NULL,'息费豁免审批历史',1,24,NULL,0,24,'true',NULL,NULL,0,1,NULL,0),(220,'2013-10-25 16:00:00',2,-1,1,NULL,'2014-02-21 07:31:28',-1,NULL,'20131026061652',NULL,'pages/app/finance/auditloan/extension/AuditExtensionAll.js',NULL,'展期审批一览表',1,24,-1,0,24,'true',NULL,NULL,0,1,NULL,0),(221,'2013-10-25 16:00:00',2,-1,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20131026061818',NULL,'pages/app/finance/auditloan/prepayment/AuditPrepaymentAll.js',NULL,'提前还款一览表',1,24,-1,0,24,'true',NULL,NULL,0,1,NULL,0),(222,'2013-10-25 16:00:00',2,-1,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20131026061855',NULL,'pages/app/finance/auditloan/exempt/AuditExemptAll.js',NULL,'息费豁免审批一览表',1,24,-1,0,24,'true',NULL,NULL,0,1,NULL,0),(223,'2013-11-08 16:00:00',2,8,0,2,'2013-11-08 16:00:00',-1,NULL,'20131109004956',NULL,'pages/app/finance/reports/LedgerReport.js',NULL,'客户贷款台账明细资料',1,29,NULL,0,29,'true',NULL,NULL,0,1,NULL,0),(224,'2013-11-11 16:00:00',2,8,0,2,'2013-11-14 16:00:00',-1,NULL,'20131112044230',NULL,'pages/app/finance/reports/RecordRateReport.js',NULL,'应收及已收利息报表',1,29,NULL,0,29,'true',NULL,NULL,0,1,NULL,0),(225,'2013-12-04 16:00:00',2,8,1,2,'2013-12-04 16:00:00',-1,NULL,'20131205042729',NULL,'pages/app/finance/reports/ledger/LedgerDeatilReport.js',NULL,'民汇客户台账明细',1,29,NULL,0,29,'true',NULL,NULL,0,1,NULL,0),(226,'2013-12-04 16:00:00',2,8,1,NULL,'2014-02-21 07:31:28',-1,NULL,'20131205080223',NULL,'pages/app/finance/reports/LoanBusiness.js',NULL,'贷款业务统计',1,29,-1,0,29,'true',NULL,NULL,0,1,NULL,0),(227,'2013-12-05 16:00:00',2,8,0,NULL,'2014-02-21 07:31:28',-1,NULL,'20131206075212',NULL,'pages/app/finance/reports/LoanBusiness.js',NULL,'业务统计报表',1,29,-1,0,29,'true',NULL,NULL,0,1,NULL,0),(228,'2013-12-15 16:00:00',2,8,1,2,'2013-12-15 16:00:00',-1,NULL,'20131216094712',NULL,'pages/app/finance/fcinit/contract/ContractTemplateMgr.js',NULL,'合同模板配置',1,26,NULL,0,26,'true',NULL,NULL,0,1,NULL,0),(229,'2013-12-15 16:00:00',2,8,1,NULL,'2014-02-21 07:31:28',-1,'日常收支管理的菜单','20131216100213',NULL,'pages/app/finance/fcinit/dailManagement/DailManagementMgr.js',NULL,'日常收支管理',1,26,-1,0,26,'true',NULL,NULL,0,1,NULL,0),(230,'2013-12-15 16:00:00',2,8,1,2,'2020-12-17 16:00:00',-1,'针对贷款业务的流水 的 记录','20131216100348',NULL,'pages/app/finance/deduct/turnover/loanover/LoanOverMgr.js',NULL,'个人贷款发放流水',1,25,NULL,0,25,'true',NULL,NULL,0,1,NULL,0),(231,'2013-12-15 16:00:00',2,8,0,2,'2020-12-17 16:00:00',-1,NULL,'20131216100439',NULL,'pages/app/finance/deduct/turnover/freeOver/FreeOverMgr.js',NULL,'放款手续费收取流水',1,25,NULL,0,25,'true',NULL,NULL,0,1,NULL,0),(232,'2013-12-15 16:00:00',2,8,1,2,'2020-12-17 16:00:00',-1,NULL,'20131216100504',NULL,'pages/app/finance/deduct/turnover/nomalover/NomalOverMgr.js',NULL,'正常还款流水',1,25,NULL,0,25,'true',NULL,NULL,0,1,NULL,0),(233,'2013-12-15 16:00:00',2,8,0,2,'2020-12-17 16:00:00',-1,NULL,'20131216100526',NULL,'pages/app/finance/deduct/turnover/overdueover/OverdueOverMgr.js',NULL,'逾期还款流水',1,25,NULL,0,25,'true',NULL,NULL,0,1,NULL,0),(234,'2013-12-17 16:00:00',2,8,1,2,'2020-12-17 16:00:00',-1,NULL,'20131218045326',NULL,'pages/app/finance/deduct/turnover/eloanover/EloanOverMgr.js',NULL,'企业贷款发放流水',1,25,NULL,0,25,'true',NULL,NULL,0,1,NULL,0),(235,'2014-01-13 16:00:00',2,8,1,NULL,'2014-02-21 07:31:28',-1,NULL,'20140114112621',NULL,'pages/app/finance/deduct/current/CurrentMgr.js',NULL,'随借随还管理',1,25,-1,0,25,'true',NULL,NULL,0,1,NULL,0),(236,'2014-01-20 16:00:00',2,8,1,2,'2014-01-20 16:00:00',-1,NULL,'20140121094451',NULL,'pages/app/finance/deduct/receipts/ReceiptsMgr.js',NULL,'预收息费管理',1,25,NULL,0,25,'true',NULL,NULL,0,1,NULL,0),(237,'2014-03-17 12:27:52',2,8,1,2,'2014-03-16 16:00:00',-1,NULL,'20140306100054',NULL,'pages/app/finance/financing/OutFundsMgr.js',NULL,'融资管理',1,31,NULL,0,31,'true',NULL,NULL,0,1,NULL,0),(238,'2014-03-13 16:00:00',2,8,1,NULL,'0000-00-00 00:00:00',-1,NULL,'20140314051336',NULL,'pages/app/finance/deduct/advance/AdvanceAmountMgr.js',NULL,'预收账款管理',1,25,-1,0,25,'true',NULL,NULL,0,1,NULL,0);
/*!40000 ALTER TABLE `ts_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_module`
--

DROP TABLE IF EXISTS `ts_module`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `iconCls` varchar(30) DEFAULT NULL,
  `menuId` double DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=524 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_module`
--

LOCK TABLES `ts_module` WRITE;
/*!40000 ALTER TABLE `ts_module` DISABLE KEYS */;
INSERT INTO `ts_module` VALUES (1,'2013-12-16 06:27:44',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274300','page_add',49,'添加公司'),(2,'2013-12-16 06:27:44',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274402','page_edit',49,'编辑公司'),(3,'2013-12-16 06:27:44',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274404','page_delete',49,'删除公司'),(4,'2013-12-16 06:27:44',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274406','page_add',49,'添加部门'),(5,'2013-12-16 06:27:44',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274408','page_edit',49,'编辑部门'),(6,'2013-12-16 06:27:44',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274410','page_delete',49,'删除部门'),(7,'2013-12-16 06:27:46',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274612','page_query',213,'查询'),(8,'2013-12-16 06:27:46',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274614','page_reset',213,'重置'),(9,'2013-12-16 06:27:46',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274616','page_add',213,'添加'),(10,'2013-12-16 06:27:46',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274618','page_edit',213,'编辑'),(11,'2013-12-16 06:27:46',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274620','page_enabled',213,'启用'),(12,'2013-12-16 06:27:46',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274622','page_disabled',213,'禁用'),(13,'2013-12-16 06:27:46',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274624','page_delete',213,'删除'),(14,'2013-12-16 06:27:48',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274826','page_add_role',50,'添加角色'),(15,'2013-12-16 06:27:48',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274828','page_edit_role',50,'编辑角色'),(16,'2013-12-16 06:27:48',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274830','page_copy',50,'复制角色'),(17,'2013-12-16 06:27:48',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274832','page_save',50,'保存角色'),(18,'2013-12-16 06:27:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274934','page_del_role',50,'取消权限'),(19,'2013-12-16 06:27:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274936','page_enabled',50,'启用角色'),(20,'2013-12-16 06:27:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274938','page_disabled',50,'禁用角色'),(21,'2013-12-16 06:27:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602274940','page_del_role',50,'删除角色'),(22,'2013-12-16 06:27:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602275242','page_query',51,'查询'),(23,'2013-12-16 06:27:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602275244','page_reset',51,'重置'),(24,'2013-12-16 06:27:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602275246','page_adduser',51,'添加用户'),(25,'2013-12-16 06:27:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602275248','page_eidtuser',51,'编辑用户'),(26,'2013-12-16 06:27:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602275250','page_add',51,'设置数据访问权限'),(27,'2013-12-16 06:27:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602275252','page_password',51,'修改密码'),(28,'2013-12-16 06:27:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602275254','page_enabuser',51,'启用用户'),(29,'2013-12-16 06:27:53',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602275356','page_disuse',51,'禁用用户'),(30,'2013-12-16 06:27:53',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602275358','page_deluser',51,'删除用户'),(31,'2013-12-16 06:28:01',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280160','page_add',52,'添加资源'),(32,'2013-12-16 06:28:01',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280162','page_edit',52,'编辑资源'),(33,'2013-12-16 06:28:01',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280164','page_delete',52,'删除资源'),(34,'2013-12-16 06:28:01',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280166','page_add',52,'生成资源文件'),(35,'2013-12-16 06:28:01',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280168','page_query',52,'查询'),(36,'2013-12-16 06:28:01',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280170','page_reset',52,'重置'),(37,'2013-12-16 06:28:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280272','page_add',52,'添加数据'),(38,'2013-12-16 06:28:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280274','page_edit',52,'编辑数据'),(39,'2013-12-16 06:28:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280276','page_enabled',52,'启用数据'),(40,'2013-12-16 06:28:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280278','page_disabled',52,'禁用数据'),(41,'2013-12-16 06:28:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280280','page_delete',52,'删除数据'),(42,'2013-12-16 06:28:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280682','page_preview',81,'预览模板'),(43,'2013-12-16 06:28:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280684','page_formdiyRefresh',81,'刷新模板清单项'),(44,'2013-12-16 06:28:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280686','page_add',81,'添加清单项'),(45,'2013-12-16 06:28:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280688','page_edit',81,'编辑清单项'),(46,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280690','page_enabled',81,'起用清单项'),(47,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280792','page_disabled',81,'禁用清单项'),(48,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280794','page_add',81,'添加模板'),(49,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280796','page_delete',81,'删除清单项'),(50,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M2013121602280798','page_edit',81,'编辑模板'),(51,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022807100','page_copy',81,'复制模板'),(52,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022807102','page_enabled',81,'起用模板'),(53,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022807104','page_disabled',81,'禁用模板'),(54,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022807106','page_delete',81,'删除模板'),(55,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022807108','page_formdiyRefresh',81,'刷新模板标题'),(56,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022807110','page_add',81,'添加标题'),(57,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022807112','page_edit',81,'编辑标题'),(58,'2013-12-16 06:28:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022807114','page_enabled',81,'起用标题'),(59,'2013-12-16 06:28:08',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022808116','page_disabled',81,'禁用标题'),(60,'2013-12-16 06:28:08',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022808118','page_delete',81,'删除标题'),(61,'2013-12-16 06:28:23',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022823120','page_reset',101,'重置'),(62,'2013-12-16 06:28:23',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022823122','page_add',101,'添加'),(63,'2013-12-16 06:28:23',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022823124','page_edit',101,'编辑'),(64,'2013-12-16 06:28:23',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022823126','page_enabled',101,'启用'),(65,'2013-12-16 06:28:23',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022823128','page_disabled',101,'禁用'),(66,'2013-12-16 06:28:23',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022823130','page_delete',101,'删除'),(67,'2013-12-16 06:28:28',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022828132','page_reset',112,'重置'),(68,'2013-12-16 06:28:28',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022828134','page_add',112,'添加'),(69,'2013-12-16 06:28:28',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022828136','page_edit',112,'编辑'),(70,'2013-12-16 06:28:29',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022828138','page_enabled',112,'启用'),(71,'2013-12-16 06:28:29',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022829140','page_disabled',112,'禁用'),(72,'2013-12-16 06:28:29',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022829142','page_delete',112,'删除'),(73,'2013-12-16 06:28:31',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022831144','page_add',116,'保存权限'),(74,'2013-12-16 06:28:31',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022831146','page_delete',116,'删除权限'),(75,'2013-12-16 06:28:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022835148','page_query',132,'查询'),(76,'2013-12-16 06:28:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022835150','page_reset',132,'重置'),(77,'2013-12-16 06:28:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022835152','page_add',132,'添加'),(78,'2013-12-16 06:28:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022835154','page_edit',132,'编辑'),(79,'2013-12-16 06:28:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022835156','page_edit',132,'账号映射'),(80,'2013-12-16 06:28:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022836158','page_enabled',132,'起用'),(81,'2013-12-16 06:28:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022836160','page_disabled',132,'禁用'),(82,'2013-12-16 06:28:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022836162','page_delete',132,'删除'),(83,'2013-12-16 06:28:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022837164','page_query',214,'查询'),(84,'2013-12-16 06:28:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022837166','page_reset',214,'重置'),(85,'2013-12-16 06:28:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022837168','page_add',214,'绑定凭证模板'),(86,'2013-12-16 06:28:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022837170','page_add',214,'重新生成凭证'),(87,'2013-12-16 06:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022842172','page_query',194,'查询'),(88,'2013-12-16 06:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022842174','page_reset',194,'重置'),(89,'2013-12-16 06:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022842176','page_add',194,'添加子流程'),(90,'2013-12-16 06:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022842178','page_edit',194,'编辑子流程'),(91,'2013-12-16 06:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022842180','page_detail',194,'查看流程图'),(92,'2013-12-16 06:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022842182','page_save',194,'配置流程'),(93,'2013-12-16 06:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022842184','page_enabled',194,'启用'),(94,'2013-12-16 06:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022842186','page_disabled',194,'禁用'),(95,'2013-12-16 06:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022842188','page_delete',194,'删除'),(96,'2013-12-16 06:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022842190','page_edit',194,'编辑条款'),(97,'2013-12-16 06:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022842192','page_delete',194,'删除条款'),(98,'2013-12-16 06:28:47',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022847194','page_query',60,'查询'),(99,'2013-12-16 06:28:47',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022847196','page_reset',60,'重置'),(100,'2013-12-16 06:28:47',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022847198','page_add',60,'添加品种'),(101,'2013-12-16 06:28:47',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022847200','page_edit',60,'复制品种'),(102,'2013-12-16 06:28:47',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022847202','page_detail',60,'查看那流程图'),(103,'2013-12-16 06:28:47',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022847204','page_save',60,'配置流程图'),(104,'2013-12-16 06:28:48',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022848206','page_enabled',60,'启用'),(105,'2013-12-16 06:28:48',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022848208','page_disabled',60,'禁用'),(106,'2013-12-16 06:28:48',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022848210','page_delete',60,'删除'),(107,'2013-12-16 06:29:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022902212','page_query',69,'查询'),(108,'2013-12-16 06:29:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022902214','page_reset',69,'重置'),(109,'2013-12-16 06:29:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022902216','page_add',69,'添加'),(110,'2013-12-16 06:29:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022903218','page_edit',69,'编辑'),(111,'2013-12-16 06:29:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022903220','page_detail',69,'详情'),(112,'2013-12-16 06:29:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022903222','page_detail',69,'同步'),(113,'2013-12-16 06:29:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022903224','page_enabled',69,'起用'),(114,'2013-12-16 06:29:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022903226','page_disabled',69,'禁用'),(115,'2013-12-16 06:29:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022903228','page_delete',69,'删除'),(116,'2013-12-16 06:29:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022903230','page_detail',69,'导入'),(117,'2013-12-16 06:29:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022903232','page_exportxls',69,'导出'),(118,'2013-12-16 06:29:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022903232','page_query',71,'查询'),(119,'2013-12-16 06:29:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022904236','page_reset',71,'重置'),(120,'2013-12-16 06:29:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022904238','page_add',71,'添加'),(121,'2013-12-16 06:29:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022904240','page_edit',71,'编辑'),(122,'2013-12-16 06:29:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022904242','page_detail',71,'详情'),(123,'2013-12-16 06:29:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022904244','page_detail',71,'同步'),(124,'2013-12-16 06:29:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022904246','page_enabled',71,'启用'),(125,'2013-12-16 06:29:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022904248','page_disabled',71,'禁用'),(126,'2013-12-16 06:29:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022904250','page_delete',71,'删除'),(127,'2013-12-16 06:29:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022904252','page_detail',71,'导入'),(128,'2013-12-16 06:29:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022904254','page_exportxls',71,'导出'),(129,'2013-12-16 06:29:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022906256','page_query',75,'查询'),(130,'2013-12-16 06:29:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022906258','page_reset',75,'重置'),(131,'2013-12-16 06:29:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022906260','page_add',75,'添加'),(132,'2013-12-16 06:29:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022906262','page_edit',75,'编辑'),(133,'2013-12-16 06:29:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022906264','page_detail',75,'详情'),(134,'2013-12-16 06:29:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022906266','page_confirm',75,'提交'),(135,'2013-12-16 06:29:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022906268','page_delete',75,'删除'),(136,'2013-12-16 06:29:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022906270','page_exportxls',75,'导出'),(137,'2013-12-16 06:29:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022906270','page_query',76,'查询'),(138,'2013-12-16 06:29:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022906274','page_reset',76,'重置'),(139,'2013-12-16 06:29:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022906276','page_add',76,'添加'),(140,'2013-12-16 06:29:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022907278','page_edit',76,'编辑'),(141,'2013-12-16 06:29:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022907280','page_detail',76,'详情'),(142,'2013-12-16 06:29:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022907282','page_confirm',76,'提交'),(143,'2013-12-16 06:29:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022907284','page_delete',76,'删除'),(144,'2013-12-16 06:29:07',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022907286','page_exportxls',76,'导出'),(145,'2013-12-16 06:29:11',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022911288','page_query',196,'查询'),(146,'2013-12-16 06:29:11',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022911290','page_reset',196,'重置'),(147,'2013-12-16 06:29:11',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022911292','page_add',196,'添加'),(148,'2013-12-16 06:29:12',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022912294','page_edit',196,'编辑'),(149,'2013-12-16 06:29:12',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022912296','page_confirm',196,'提交'),(150,'2013-12-16 06:29:12',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022912298','page_delete',196,'删除'),(151,'2013-12-16 06:29:13',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022913300','page_query',84,'查询'),(152,'2013-12-16 06:29:14',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022914302','page_reset',84,'重置'),(153,'2013-12-16 06:29:14',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022914304','page_audit',84,'审批'),(154,'2013-12-16 06:29:14',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022914306','page_query',85,'查询'),(155,'2013-12-16 06:29:14',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022914308','page_reset',85,'重置'),(156,'2013-12-16 06:29:14',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022914310','page_audit',85,'审批'),(157,'2013-12-16 06:29:14',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022914312','page_query',89,'查询'),(158,'2013-12-16 06:29:14',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022914314','page_reset',89,'重置'),(159,'2013-12-16 06:29:15',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022915316','page_detail',89,'查看贷款详情'),(160,'2013-12-16 06:29:15',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022915318','page_query',90,'查询'),(161,'2013-12-16 06:29:15',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022915320','page_reset',90,'重置'),(162,'2013-12-16 06:29:15',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022915322','page_detail',90,'查看贷款详情'),(163,'2013-12-16 06:29:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022916324','page_query',92,'查询'),(164,'2013-12-16 06:29:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022916326','page_reset',92,'重置'),(165,'2013-12-16 06:29:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022916328','page_detail',92,'查看贷款详情'),(166,'2013-12-16 06:29:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022916330','page_query',93,'查询'),(167,'2013-12-16 06:29:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022916332','page_reset',93,'重置'),(168,'2013-12-16 06:29:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022916334','page_detail',93,'查看贷款详情'),(169,'2013-12-16 06:29:17',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022917336','page_query',208,'查询'),(170,'2013-12-16 06:29:17',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022917338','page_reset',208,'重置'),(171,'2013-12-16 06:29:17',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022917340','page_audit',208,'审批'),(172,'2013-12-16 06:29:17',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022917342','page_query',217,'查询'),(173,'2013-12-16 06:29:17',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022917344','page_reset',217,'重置'),(174,'2013-12-16 06:29:17',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022917346','page_detail',217,'查看详情'),(175,'2013-12-16 06:29:18',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022918348','page_query',220,'查询'),(176,'2013-12-16 06:29:18',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022918350','page_reset',220,'重置'),(177,'2013-12-16 06:29:18',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022918352','page_detail',220,'查看详情'),(178,'2013-12-16 06:29:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022920354','page_query',99,'查询'),(179,'2013-12-16 06:29:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022920356','page_reset',99,'重置'),(180,'2013-12-16 06:29:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022920358','page_print',99,'打印放款单'),(181,'2013-12-16 06:29:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022920360','page_confirm',99,'放款'),(182,'2013-12-16 06:29:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022920362','page_detail',99,'导入文件放款'),(183,'2013-12-16 06:29:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022920364','page_exportxls',99,'导出'),(184,'2013-12-16 06:29:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022920366','page_query',109,'查询'),(185,'2013-12-16 06:29:21',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022921368','page_reset',109,'重置'),(186,'2013-12-16 06:29:21',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022921370','page_query',110,'查询'),(187,'2013-12-16 06:29:21',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022921370','page_save',109,'收款'),(188,'2013-12-16 06:29:21',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022921374','page_detail',109,'导入文件收款'),(189,'2013-12-16 06:29:21',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022921376','page_reset',110,'重置'),(190,'2013-12-16 06:29:21',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022921378','page_save',109,'转逾期'),(191,'2013-12-16 06:29:21',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022921380','page_query',113,'查询'),(192,'2013-12-16 06:29:21',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022921382','page_save',109,'全部转逾期'),(193,'2013-12-16 06:29:21',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022921380','page_confirm',110,'放款'),(194,'2013-12-16 06:29:21',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022921386','page_reset',113,'重置'),(195,'2013-12-16 06:29:21',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022921386','page_detail',110,'导入文件放款'),(196,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922390','page_confirm',113,'放款手续费收取'),(197,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922390','page_exportxls',110,'导出'),(198,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922394','page_detail',113,'导入放款手续费文件收款'),(199,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922396','page_exportxls',113,'导出放款手续费文件'),(200,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922398','page_exportxls',109,'导出'),(201,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922400','page_query',115,'查询'),(202,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922402','page_reset',115,'重置'),(203,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922404','page_save',115,'收款'),(204,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922406','page_detail',115,'导入文件收款'),(205,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922408','page_exportxls',115,'导出'),(206,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922410','page_query',215,'查询'),(207,'2013-12-16 06:29:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022922412','page_reset',215,'重置'),(208,'2013-12-16 06:29:23',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022923414','page_add',215,'绑定凭证模板'),(209,'2013-12-16 06:29:23',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216022923416','page_add',215,'重新生成凭证'),(210,'2013-12-16 06:30:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023036418','page_add',55,'新增菜单'),(211,'2013-12-16 06:30:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023037420','page_edit',55,'修改菜单'),(212,'2013-12-16 06:30:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023037422','page_detail',55,'菜单详情'),(213,'2013-12-16 06:30:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023037424','page_enabled',55,'起用菜单'),(214,'2013-12-16 06:30:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023037426','page_disabled',55,'禁用菜单'),(215,'2013-12-16 06:30:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023037428','page_delete',55,'删除菜单'),(216,'2013-12-16 06:30:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023037430','page_add',55,'新增按钮'),(217,'2013-12-16 06:30:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023037432','page_edit',55,'修改按钮'),(218,'2013-12-16 06:30:38',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023038434','page_delete',55,'删除按钮'),(219,'2013-12-16 06:34:34',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023434436','page_add',120,'添加'),(220,'2013-12-16 06:34:34',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023434438','page_edit',120,'编辑'),(221,'2013-12-16 06:34:34',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023434440','page_add',120,'连接测试'),(222,'2013-12-16 06:34:34',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023434442','page_enabled',120,'启用'),(223,'2013-12-16 06:34:34',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023434444','page_disabled',120,'禁用'),(224,'2013-12-16 06:34:34',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023434446','page_delete',120,'删除'),(225,'2013-12-16 06:35:39',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023539448','page_query',137,'查询'),(226,'2013-12-16 06:35:39',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023539450','page_reset',137,'重置'),(227,'2013-12-16 06:35:39',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023539452','page_detail',137,'详情'),(228,'2013-12-16 06:35:40',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023540454','page_exportxls',137,'导出'),(229,'2013-12-16 06:35:40',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023540456','page_query',138,'查询'),(230,'2013-12-16 06:35:40',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023540458','page_reset',138,'重置'),(231,'2013-12-16 06:35:40',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023540460','page_detail',138,'详情'),(232,'2013-12-16 06:35:40',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023540462','page_exportxls',138,'导出'),(233,'2013-12-16 06:35:40',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023540464','page_query',139,'查询'),(234,'2013-12-16 06:35:40',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023540466','page_reset',139,'重置'),(235,'2013-12-16 06:35:40',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023540468','page_detail',139,'查看贷款详情'),(236,'2013-12-16 06:35:40',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023540470','page_exportxls',139,'导出'),(237,'2013-12-16 06:35:41',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023541472','page_query',140,'查询'),(238,'2013-12-16 06:35:41',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023541474','page_reset',140,'重置'),(239,'2013-12-16 06:35:41',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023541476','page_detail',140,'查看贷款详情'),(240,'2013-12-16 06:35:41',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023541478','page_exportxls',140,'导出'),(241,'2013-12-16 06:35:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023542480','page_query',142,'查询'),(242,'2013-12-16 06:35:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023542482','page_reset',142,'重置'),(243,'2013-12-16 06:35:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023542484','page_exportxls',142,'导出'),(244,'2013-12-16 06:35:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023542486','page_query',143,'查询'),(245,'2013-12-16 06:35:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023542488','page_reset',143,'重置'),(246,'2013-12-16 06:35:42',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023542490','page_exportxls',143,'导出'),(247,'2013-12-16 06:35:43',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023543492','page_query',144,'查询'),(248,'2013-12-16 06:35:43',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023543494','page_reset',144,'重置'),(249,'2013-12-16 06:35:43',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023543496','page_exportxls',144,'导出'),(250,'2013-12-16 06:35:43',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023543498','page_query',145,'查询'),(251,'2013-12-16 06:35:43',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023543500','page_reset',145,'重置'),(252,'2013-12-16 06:35:43',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023543502','page_exportxls',145,'导出'),(253,'2013-12-16 06:35:44',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023544504','page_query',201,'查询'),(254,'2013-12-16 06:35:44',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023544506','page_reset',201,'重置'),(255,'2013-12-16 06:35:44',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023544508','page_detail',201,'放款单详情'),(256,'2013-12-16 06:35:44',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023544510','page_exportxls',201,'还款计划导出'),(257,'2013-12-16 06:35:44',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023544512','page_query',146,'查询'),(258,'2013-12-16 06:35:45',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023545514','page_reset',146,'重置'),(259,'2013-12-16 06:35:45',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023545516','page_exportxls',146,'导出'),(260,'2013-12-16 06:35:45',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023545518','page_query',150,'查询'),(261,'2013-12-16 06:35:45',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023545520','page_reset',150,'重置'),(262,'2013-12-16 06:35:45',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023545522','page_exportxls',150,'导出'),(263,'2013-12-16 06:35:45',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023545524','page_query',151,'查询'),(264,'2013-12-16 06:35:45',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023545526','page_reset',151,'重置'),(265,'2013-12-16 06:35:45',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023545528','page_exportxls',151,'导出'),(266,'2013-12-16 06:35:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023549530','page_query',152,'查询'),(267,'2013-12-16 06:35:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023549532','page_reset',152,'重置'),(268,'2013-12-16 06:35:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023549534','page_exportxls',152,'导出'),(269,'2013-12-16 06:35:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023552536','page_query',154,'查询'),(270,'2013-12-16 06:35:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023552538','page_reset',154,'重置'),(271,'2013-12-16 06:35:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023552540','page_exportxls',154,'导出'),(272,'2013-12-16 06:35:54',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023554542','page_query',210,'查询'),(273,'2013-12-16 06:35:54',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023554544','page_reset',210,'重置'),(274,'2013-12-16 06:35:55',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023555546','page_detail',210,'展期详情'),(275,'2013-12-16 06:35:55',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023555548','page_exportxls',210,'展期还款计划导出'),(276,'2013-12-16 06:35:56',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023556550','page_select',223,'选择客户'),(277,'2013-12-16 06:35:56',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023556552','page_exportxls',223,'导出'),(278,'2013-12-16 06:35:58',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023558554','page_query',225,'查询'),(279,'2013-12-16 06:35:58',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023558556','page_exportxls',225,'导出'),(280,'2013-12-16 06:35:59',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023559558','page_query',226,'查询'),(281,'2013-12-16 06:35:59',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023559560','page_reset',226,'重置'),(282,'2013-12-16 06:35:59',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023559562','page_exportxls',226,'导出'),(283,'2013-12-16 06:36:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023602564','page_query',65,'查询'),(284,'2013-12-16 06:36:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023602566','page_reset',65,'重置'),(285,'2013-12-16 06:36:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023602568','page_add',65,'添加'),(286,'2013-12-16 06:36:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023602570','page_edit',65,'编辑'),(287,'2013-12-16 06:36:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023602572','page_enabled',65,'起用'),(288,'2013-12-16 06:36:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023602574','page_disabled',65,'禁用'),(289,'2013-12-16 06:36:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023602576','page_delete',65,'删除'),(290,'2013-12-16 06:36:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023603578','page_query',77,'查询'),(291,'2013-12-16 06:36:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023603580','page_reset',77,'重置'),(292,'2013-12-16 06:36:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023603582','page_add',77,'添加'),(293,'2013-12-16 06:36:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023603584','page_edit',77,'编辑'),(294,'2013-12-16 06:36:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023603586','page_enabled',77,'起用'),(295,'2013-12-16 06:36:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023603588','page_disabled',77,'禁用'),(296,'2013-12-16 06:36:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023603590','page_delete',77,'删除'),(297,'2013-12-16 06:36:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023603592','page_query',79,'查询'),(298,'2013-12-16 06:36:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023603594','page_reset',79,'重置'),(299,'2013-12-16 06:36:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023603596','page_add',79,'添加'),(300,'2013-12-16 06:36:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023603598','page_edit',79,'编辑'),(301,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604600','page_enabled',79,'起用'),(302,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604602','page_disabled',79,'禁用'),(303,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604604','page_delete',79,'删除'),(304,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604606','page_query',103,'查询'),(305,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604608','page_reset',103,'重置'),(306,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604610','page_add',103,'添加'),(307,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604612','page_edit',103,'编辑'),(308,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604614','page_detail',103,'详情'),(309,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604616','page_enabled',103,'起用'),(310,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604618','page_disabled',103,'禁用'),(311,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604618','page_query',114,'查询'),(312,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604622','page_delete',103,'删除'),(313,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604622','page_reset',114,'重置'),(314,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604626','page_add',114,'添加'),(315,'2013-12-16 06:36:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023604628','page_edit',114,'编辑'),(316,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605630','page_confirm',114,'提交'),(317,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605632','page_audit',114,'审批通过'),(318,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605634','page_audit',114,'取消审批'),(319,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605636','page_enabled',114,'起用'),(320,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605638','page_query',193,'查询'),(321,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605638','page_disabled',114,'禁用'),(322,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605642','page_delete',114,'删除'),(323,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605642','page_reset',193,'重置'),(324,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605646','page_add',193,'设置账户金额'),(325,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605648','page_edit',193,'编辑账户金额'),(326,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605650','page_enabled',193,'起用'),(327,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605652','page_disabled',193,'禁用'),(328,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605654','page_delete',193,'删除'),(329,'2013-12-16 06:36:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023605656','page_detail',193,'资金流水明细'),(330,'2013-12-16 06:36:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023606658','page_query',228,'查询'),(331,'2013-12-16 06:36:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023606660','page_reset',228,'重置'),(332,'2013-12-16 06:36:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023606662','page_add',228,'添加'),(333,'2013-12-16 06:36:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023606664','page_edit',228,'编辑'),(334,'2013-12-16 06:36:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023606666','page_detail',228,'配置模板'),(335,'2013-12-16 06:36:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023606668','page_detail',228,'预览模板'),(336,'2013-12-16 06:36:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023606670','page_enabled',228,'起用'),(337,'2013-12-16 06:36:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023606672','page_disabled',228,'禁用'),(338,'2013-12-16 06:36:06',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023606674','page_delete',228,'删除'),(339,'2013-12-16 06:36:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023622676','page_query',54,'查询'),(340,'2013-12-16 06:36:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023622678','page_reset',54,'重置'),(341,'2013-12-16 06:36:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023622680','page_add',54,'添加'),(342,'2013-12-16 06:36:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023622682','page_edit',54,'编辑'),(343,'2013-12-16 06:36:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023622684','page_enabled',54,'启用'),(344,'2013-12-16 06:36:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023622686','page_disabled',54,'禁用'),(345,'2013-12-16 06:36:22',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216023622688','page_delete',54,'删除'),(346,'2013-12-16 06:52:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025236690','page_query',230,'查询'),(347,'2013-12-16 06:52:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025236692','page_reset',230,'重置'),(348,'2013-12-16 06:52:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025236694','page_detail',230,'详情'),(349,'2013-12-16 06:52:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025236696','page_enabled',230,'可逆'),(350,'2013-12-16 06:52:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025237698','page_exportxls',230,'导出'),(351,'2013-12-16 06:52:51',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025251700','page_query',231,'查询'),(352,'2013-12-16 06:52:51',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025251702','page_reset',231,'重置'),(353,'2013-12-16 06:52:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025252704','page_detail',231,'查看详情'),(354,'2013-12-16 06:52:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025252706','page_exportxls',231,'导出'),(355,'2013-12-16 06:52:56',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025256708','page_query',232,'查询'),(356,'2013-12-16 06:52:56',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025256710','page_reset',232,'重置'),(357,'2013-12-16 06:52:56',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025256712','page_detail',232,'详情'),(358,'2013-12-16 06:52:56',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025256714','page_exportxls',232,'导出'),(359,'2013-12-16 06:52:58',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025258716','page_query',233,'查询'),(360,'2013-12-16 06:52:58',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025258718','page_reset',233,'重置'),(361,'2013-12-16 06:52:58',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025258720','page_detail',233,'查看详情'),(362,'2013-12-16 06:52:58',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216025258722','page_exportxls',233,'导出'),(363,'2013-12-16 07:06:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030649724','page_formdiyRefresh',58,'刷新属性列表'),(364,'2013-12-16 07:06:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030649724','page_add',58,'添加表单'),(365,'2013-12-16 07:06:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030649728','page_add',58,'添加字段属性'),(366,'2013-12-16 07:06:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030649730','page_edit',58,'编辑表单'),(367,'2013-12-16 07:06:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030649732','page_edit',58,'编辑字段属性'),(368,'2013-12-16 07:06:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030649734','page_enabled',58,'起用表单'),(369,'2013-12-16 07:06:50',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030650736','page_enabled',58,'起用字段属性'),(370,'2013-12-16 07:06:50',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030650738','page_disabled',58,'禁用表单'),(371,'2013-12-16 07:06:50',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030650740','page_disabled',58,'禁用字段属性'),(372,'2013-12-16 07:06:50',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030650742','page_delete',58,'删除表单'),(373,'2013-12-16 07:06:50',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030650744','page_delete',58,'删除字段属性'),(374,'2013-12-16 07:06:50',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030650746','page_add',58,'生成文件'),(375,'2013-12-16 07:06:50',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030650748','page_formdiyRefresh',58,'刷新字段列表'),(376,'2013-12-16 07:08:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030805750','page_query',59,'查询'),(377,'2013-12-16 07:08:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030805752','page_reset',59,'重置'),(378,'2013-12-16 07:08:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030805754','page_add',59,'添加'),(379,'2013-12-16 07:08:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030805756','page_edit',59,'编辑'),(380,'2013-12-16 07:08:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030805758','page_enabled',59,'启用'),(381,'2013-12-16 07:08:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030805760','page_disabled',59,'禁用'),(382,'2013-12-16 07:08:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216030805762','page_delete',59,'删除'),(383,'2013-12-16 07:13:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216031320764','page_holidaysearch',56,'查询'),(384,'2013-12-16 07:13:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216031320766','page_reset',56,'重置'),(385,'2013-12-16 07:13:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216031320768','page_add',56,'节假日初始化'),(386,'2013-12-16 07:13:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216031320770','page_addholiday',56,'添加节假日'),(387,'2013-12-16 07:13:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216031320772','page_eidholiday',56,'编辑节假日'),(388,'2013-12-16 07:13:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216031320774','page_enabholiay',56,'启用'),(389,'2013-12-16 07:13:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216031320776','page_disholiday',56,'禁用'),(390,'2013-12-16 07:13:20',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216031320778','page_delholiday',56,'删除'),(391,'2013-12-16 07:24:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032416780','page_query',229,'查询'),(392,'2013-12-16 07:24:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032416782','page_reset',229,'重置'),(393,'2013-12-16 07:24:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032416784','page_exportxls',229,'收入'),(394,'2013-12-16 07:24:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032416786','page_download',229,'支出'),(395,'2013-12-16 07:24:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032416788','page_edit',229,'编辑'),(396,'2013-12-16 07:24:16',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032416790','page_delete',229,'删除'),(397,'2013-12-16 07:29:10',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032910792','page_add',73,'添加'),(398,'2013-12-16 07:29:10',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032910794','page_edit',73,'编辑'),(399,'2013-12-16 07:29:10',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032910796','page_edit',73,'设为默认'),(400,'2013-12-16 07:29:10',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032910798','page_enabled',73,'启用'),(401,'2013-12-16 07:29:10',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032910800','page_disabled',73,'禁用'),(402,'2013-12-16 07:29:10',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216032910802','page_delete',73,'删除'),(403,'2013-12-16 07:42:49',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216034249804','page_exportxls',193,'导出'),(404,'2013-12-16 08:28:58',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042858806','page_add',121,'财务系统映射'),(405,'2013-12-16 08:28:58',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042858808','page_delete',121,'取消映射'),(406,'2013-12-16 08:28:58',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042858810','page_enabled',121,'启用'),(407,'2013-12-16 08:28:58',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042858812','page_disabled',121,'禁用'),(408,'2013-12-16 08:29:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042902814','page_query',126,'查询'),(409,'2013-12-16 08:29:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042902816','page_reset',126,'重置'),(410,'2013-12-16 08:29:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042902818','page_enabled',126,'用户账号映射'),(411,'2013-12-16 08:29:02',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042902820','page_disabled',126,'取消用户账号映射'),(412,'2013-12-16 08:29:03',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042903822','page_edit',126,'同步数据'),(413,'2013-12-16 08:29:10',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042910824','page_query',131,'查询'),(414,'2013-12-16 08:29:10',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042910826','page_reset',131,'重置'),(415,'2013-12-16 08:29:10',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216042910828','page_edit',131,'同步数据'),(416,'2013-12-16 10:37:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216063735830','page_query',53,'查询'),(417,'2013-12-16 10:37:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216063735832','page_reset',53,'重置'),(418,'2013-12-16 10:37:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216063735834','page_add',53,'添加'),(419,'2013-12-16 10:37:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216063735836','page_edit',53,'编辑'),(420,'2013-12-16 10:37:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216063735838','page_enabled',53,'启用'),(421,'2013-12-16 10:37:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216063735840','page_disabled',53,'禁用'),(422,'2013-12-16 10:37:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216063736842','page_delete',53,'删除'),(423,'2013-12-16 11:07:57',11,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216070757844','page_add',97,'添加借款合同'),(424,'2013-12-16 11:07:57',11,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216070757846','page_edit',97,'编辑借款合同'),(425,'2013-12-16 11:07:57',11,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216070757848','page_delete',97,'删除借款合同'),(426,'2013-12-16 11:07:57',11,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216070757850','page_print',97,'生成借款合同'),(427,'2013-12-16 11:07:57',11,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216070757852','page_print',97,'打印借款合同'),(428,'2013-12-16 11:07:58',11,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216070758854','page_edit',97,'借款合同模板下载'),(429,'2013-12-16 11:41:57',11,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216074157856','page_add',100,'添加放款单'),(430,'2013-12-16 11:41:57',11,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216074157858','page_edit',100,'编辑放款单'),(431,'2013-12-16 11:41:58',11,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216074158860','page_delete',100,'删除放款单'),(432,'2013-12-16 11:41:58',11,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216074158862','page_add',100,'提交放款单'),(433,'2013-12-16 11:41:58',11,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216074158864','page_add',100,'打印放款单'),(434,'2013-12-16 11:43:32',23,2,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216074332866','page_add',104,'同意放款'),(435,'2013-12-16 11:43:32',23,2,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131216074332868','page_edit',104,'不同意放款'),(436,'2013-12-16 13:36:17',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216093617870','page_print',110,'打印放款单'),(437,'2013-12-16 13:36:52',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131216093652872','page_print',230,'打印放款单'),(438,'2013-12-17 02:05:31',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217100531874','page_add',86,'添加'),(439,'2013-12-17 02:05:31',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217100531876','page_edit',86,'编辑'),(440,'2013-12-17 02:05:32',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217100532878','page_delete',86,'删除'),(441,'2013-12-17 02:06:54',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217100654880','page_add',87,'添加'),(442,'2013-12-17 02:06:54',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217100654882','page_edit',87,'编辑'),(443,'2013-12-17 02:06:55',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217100655884','page_delete',87,'删除'),(444,'2013-12-17 02:07:01',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217100701886','page_edit',62,'编辑'),(445,'2013-12-17 02:07:01',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217100701888','page_save',62,'保存'),(446,'2013-12-17 03:51:34',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217115134890','page_query',130,'查询'),(447,'2013-12-17 03:51:34',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217115134892','page_reset',130,'重置'),(448,'2013-12-17 03:51:34',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217115134894','page_add',130,'添加模板'),(449,'2013-12-17 03:51:34',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217115134896','page_edit',130,'编辑模板'),(450,'2013-12-17 03:51:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217115135898','page_enabled',130,'起用模板'),(451,'2013-12-17 03:51:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217115135900','page_disabled',130,'禁用模板'),(452,'2013-12-17 03:51:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217115135902','page_delete',130,'删除模板'),(453,'2013-12-17 05:46:35',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217014635904','page_add',108,'添加保证合同'),(454,'2013-12-17 05:46:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217014636906','page_edit',108,'编辑保证合同'),(455,'2013-12-17 05:46:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217014636908','page_delete',108,'删除保证合同'),(456,'2013-12-17 05:46:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217014636910','page_print',108,'生成保证合同'),(457,'2013-12-17 05:46:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217014636912','page_print',108,'打印保证合同'),(458,'2013-12-17 05:46:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131217014636914','page_edit',108,'保证合同模板下载'),(459,'2013-12-17 07:41:07',9,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131217034107916','page_add',107,'添加抵押合同'),(460,'2013-12-17 07:41:07',9,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131217034107918','page_edit',107,'编辑抵押合同'),(461,'2013-12-17 07:41:07',9,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131217034107920','page_delete',107,'删除抵押合同'),(462,'2013-12-17 07:41:07',9,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131217034107922','page_query',107,'查询抵押合同'),(463,'2013-12-17 07:41:07',9,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131217034107924','page_add',107,'打印抵押合同'),(464,'2013-12-17 07:41:07',9,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131217034107926','page_edit',107,'抵押合同模板下载'),(465,'2013-12-17 07:41:10',9,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131217034110928','page_add',111,'添加质押合同'),(466,'2013-12-17 07:41:10',9,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131217034110930','page_edit',111,'编辑质押合同'),(467,'2013-12-17 07:41:10',9,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131217034110932','page_delete',111,'删除质押合同'),(468,'2013-12-17 07:41:11',9,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131217034111934','page_add',111,'打印质押合同'),(469,'2013-12-17 07:41:11',9,3,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20131217034111936','page_edit',111,'质押合同模板下载'),(470,'2013-12-18 03:30:36',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131218113036938','page_query',122,'查询'),(471,'2013-12-18 03:30:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131218113037940','page_reset',122,'重置'),(472,'2013-12-18 03:30:37',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131218113037942','page_edit',122,'同步数据'),(473,'2013-12-18 08:59:56',2,8,NULL,-1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131218045956944','page_query',234,'查询'),(474,'2013-12-18 08:59:56',2,8,NULL,-1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131218045956946','page_reset',234,'重置'),(475,'2013-12-18 08:59:57',2,8,NULL,-1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131218045957948','page_confirm',234,'放款'),(476,'2013-12-18 08:59:57',2,8,NULL,-1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131218045957950','page_detail',234,'导入文件放款'),(477,'2013-12-18 08:59:57',2,8,NULL,-1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131218045957952','page_exportxls',234,'导出'),(478,'2013-12-18 09:09:06',2,8,NULL,-1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131218050906954','page_print',234,'打印放款单'),(479,'2013-12-18 09:09:07',2,8,NULL,-1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20131218050907956','page_detail',234,'详情'),(480,'2014-03-15 06:27:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140315022704958','page_query',123,'查询'),(481,'2014-03-15 06:27:04',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140315022704960','page_reset',123,'重置'),(482,'2014-03-15 06:27:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140315022705962','page_add',123,'业务对象关联'),(483,'2014-03-15 06:27:05',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140315022705964','page_edit',123,'同步数据'),(484,'2014-01-14 03:27:59',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140114112759966','page_query',235,'查询'),(485,'2014-01-14 03:27:59',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140114112759968','page_reset',235,'重置'),(486,'2014-01-14 03:27:59',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140114112759970','page_save',235,'收款'),(487,'2014-01-17 02:08:19',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140117100819972','page_remover',235,'结清'),(488,'2014-01-16 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140117103818974','page_query',234,'查询'),(489,'2014-01-16 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140117103858976','page_reset',234,'重置'),(490,'2014-01-16 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140117103933978','page_print',234,'打印放款单'),(491,'2014-01-16 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140117104028980','page_detail',234,'详情'),(492,'2014-01-16 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140117104108982','page_exportxls',234,'导出'),(493,'2014-01-21 01:52:30',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140121095230984','page_query',236,'查询'),(494,'2014-01-21 01:52:30',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140121095230986','page_reset',236,'重置'),(495,'2014-01-21 01:52:30',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140121095230988','page_print',236,'打印放款单'),(496,'2014-01-21 01:52:31',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140121095231990','page_confirm',236,'放款'),(497,'2014-01-21 01:52:31',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140121095231992','page_detail',236,'导入文件放款'),(498,'2014-01-21 01:52:31',2,8,NULL,1,NULL,'2014-02-21 07:31:29',-1,NULL,'M20140121095231994','page_exportxls',236,'导出'),(499,'2014-01-22 08:39:40',13,4,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20140122043940996','page_save',236,'收款'),(500,'2014-01-27 10:16:51',13,4,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M20140127061651998','page_confirm',235,'息费返还'),(501,'2014-01-27 10:16:51',13,4,NULL,1,NULL,'2014-02-21 07:31:29',1,NULL,'M201401270616511000','page_remover',235,'息费豁免'),(502,'2014-02-25 02:22:59',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201402251022591002','page_query',69,'??'),(503,'2014-02-26 07:55:02',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201402260355021004','page_add',129,'添加业务对象'),(504,'2014-02-26 07:55:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201402260355031006','page_edit',129,'编辑业务对象'),(505,'2014-02-26 07:55:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201402260355031008','page_enabled',129,'起用业务对象'),(506,'2014-02-26 07:55:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201402260355031010','page_disabled',129,'禁用业务对象'),(507,'2014-02-26 07:55:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201402260355031012','page_delete',129,'删除业务对象'),(508,'2014-02-26 07:55:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201402260355031014','page_add',129,'添加字段'),(509,'2014-02-26 07:55:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201402260355031016','page_edit',129,'编辑字段'),(510,'2014-02-26 07:55:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201402260355031018','page_enabled',129,'起用字段'),(511,'2014-02-26 07:55:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201402260355031020','page_disabled',129,'禁用字段'),(512,'2014-02-26 07:55:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201402260355031022','page_delete',129,'删除字段'),(513,'2014-03-11 14:43:15',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'M201403111043151024','page_query',84,'??'),(514,'2014-03-11 14:43:29',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'M201403111043291026','page_query',85,'??'),(515,'2014-03-11 14:43:39',22,2,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'M201403111043391028','page_query',92,'??'),(516,'2014-03-14 09:17:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201403140517031030','page_query',238,'查询'),(517,'2014-03-14 09:17:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201403140517031032','page_reset',238,'重置'),(518,'2014-03-14 09:17:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201403140517031034','page_add',238,'添加'),(519,'2014-03-14 09:17:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201403140517031036','page_edit',238,'编辑'),(520,'2014-03-14 09:17:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201403140517031038','page_edit',238,'存入预收账款金额'),(521,'2014-03-14 09:17:03',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201403140517031040','page_delete',238,'删除'),(522,'2014-03-18 03:40:27',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,'M201403181140271042','page_detail',238,'详情'),(523,'2014-04-10 03:46:00',24,1,NULL,1,NULL,'0000-00-00 00:00:00',1,NULL,'M201404101146001044','page_reset',225,'重置');
/*!40000 ALTER TABLE `ts_module` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_moth`
--

DROP TABLE IF EXISTS `ts_moth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_moth` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `jypp` varchar(50) DEFAULT NULL,
  `moth` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `szdq` varchar(50) DEFAULT NULL,
  `tbdw` varchar(50) DEFAULT NULL,
  `tbr` varchar(50) DEFAULT NULL,
  `wxzcz` double DEFAULT NULL,
  `wxzts` int(11) DEFAULT NULL,
  `xcpfsl` int(11) DEFAULT NULL,
  `xcts` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_moth`
--

LOCK TABLES `ts_moth` WRITE;
/*!40000 ALTER TABLE `ts_moth` DISABLE KEYS */;
/*!40000 ALTER TABLE `ts_moth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_msgnotify`
--

DROP TABLE IF EXISTS `ts_msgnotify`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_msgnotify` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `autoSend` int(11) DEFAULT NULL,
  `bussType` int(11) DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `notifyDate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `notifyMan` double DEFAULT NULL,
  `notifySendMan` int(11) DEFAULT NULL,
  `notifysType` varchar(10) DEFAULT NULL,
  `phoneNum` varchar(20) DEFAULT NULL,
  `receiveMan` varchar(150) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `telNum` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_msgnotify`
--

LOCK TABLES `ts_msgnotify` WRITE;
/*!40000 ALTER TABLE `ts_msgnotify` DISABLE KEYS */;
/*!40000 ALTER TABLE `ts_msgnotify` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_nodecfg`
--

DROP TABLE IF EXISTS `ts_nodecfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_nodecfg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `btime` varchar(10) DEFAULT NULL,
  `counterrsign` int(11) DEFAULT NULL,
  `eventType` int(11) DEFAULT NULL,
  `isTimeOut` int(11) DEFAULT NULL,
  `nodeId` double DEFAULT NULL,
  `reminds` varchar(10) DEFAULT NULL,
  `sign` int(11) DEFAULT NULL,
  `timeOut` varchar(10) DEFAULT NULL,
  `transWay` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_nodecfg`
--

LOCK TABLES `ts_nodecfg` WRITE;
/*!40000 ALTER TABLE `ts_nodecfg` DISABLE KEYS */;
INSERT INTO `ts_nodecfg` VALUES (1,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 08:03:26',-1,NULL,NULL,0,1,0,1,'1',0,NULL,1),(2,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,2,'1',0,NULL,1),(3,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 08:04:29',-1,NULL,NULL,0,1,0,8,'1',0,NULL,1),(4,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 08:09:23',-1,NULL,NULL,0,1,0,3,'1',0,NULL,1),(5,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-18 06:29:45',-1,NULL,NULL,0,1,0,26,'1',0,NULL,1),(6,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-18 06:29:52',-1,NULL,NULL,0,1,0,27,'1',0,NULL,1),(7,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-18 06:29:59',-1,NULL,NULL,0,1,0,33,'1',0,NULL,1),(8,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,28,'1',0,NULL,1),(9,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,29,'1',0,NULL,1),(10,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,30,'1',0,NULL,1),(11,'2013-12-15 16:00:00',2,8,-1,1,2,'2020-12-18 04:01:18',-1,NULL,NULL,0,1,0,34,'1',0,NULL,1),(12,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-20 12:15:07',-1,NULL,NULL,0,1,0,37,'1',0,NULL,1),(13,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,31,'1',0,NULL,1),(14,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,36,'1',0,NULL,1),(15,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,32,'1',0,NULL,1),(16,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,35,'1',0,NULL,1),(17,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,39,'1',0,NULL,1),(18,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,40,'1',0,NULL,1),(19,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,45,'1',0,NULL,1),(20,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 08:36:29',-1,NULL,NULL,1,1,0,41,'1',0,NULL,1),(21,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 08:36:39',-1,NULL,NULL,0,1,0,42,'1',0,NULL,1),(22,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,46,'1',0,NULL,1),(23,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-20 12:16:20',-1,NULL,NULL,0,1,0,47,'1',0,NULL,1),(24,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,43,'1',0,NULL,1),(25,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,48,'1',0,NULL,1),(26,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,44,'1',0,NULL,1),(27,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,70,'1',0,NULL,1),(28,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 09:31:39',-1,NULL,NULL,0,2,0,71,'1',0,NULL,1),(29,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,73,'1',0,NULL,1),(30,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,82,'1',0,NULL,1),(31,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,75,'1',0,NULL,1),(32,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 10:10:00',-1,NULL,NULL,0,1,0,88,'1',0,NULL,1),(33,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 11:40:29',-1,NULL,NULL,0,2,0,89,'1',0,NULL,1),(34,'2013-12-15 16:00:00',2,8,-1,1,2,'2014-03-15 08:53:13',-1,NULL,NULL,0,1,0,91,'1',0,NULL,1),(35,'2013-12-15 16:00:00',2,8,-1,1,2,'2014-03-15 08:52:55',-1,NULL,NULL,0,1,0,90,'1',0,NULL,1),(36,'2013-12-15 16:00:00',2,8,-1,1,2,'2014-03-15 08:53:22',-1,NULL,NULL,0,1,0,100,'1',0,NULL,1),(37,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,101,'1',0,NULL,1),(38,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,99,'1',0,NULL,1),(39,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,95,'1',0,NULL,1),(40,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-20 10:32:38',-1,NULL,NULL,0,1,0,98,'1',0,NULL,1),(41,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-20 13:16:30',-1,NULL,NULL,0,1,0,102,'1',0,NULL,1),(42,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,97,'1',0,NULL,1),(43,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,103,'1',0,NULL,1),(44,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,96,'1',0,NULL,1),(45,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 12:00:34',-1,NULL,NULL,0,1,0,123,'1',0,NULL,1),(46,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,124,'1',0,NULL,1),(47,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,130,'1',0,NULL,1),(48,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,125,'1',0,NULL,1),(49,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,126,'1',0,NULL,1),(50,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,129,'1',0,NULL,1),(51,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,127,'1',0,NULL,1),(52,'2013-12-15 16:00:00',2,8,-1,1,2,'2013-12-16 12:07:03',-1,NULL,NULL,0,1,0,128,'1',0,NULL,1),(53,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,115,'1',0,NULL,1),(54,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,116,'1',0,NULL,1),(55,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,121,'1',0,NULL,1),(56,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,1,1,0,117,'1',0,NULL,1),(57,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,118,'1',0,NULL,1),(58,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,120,'1',0,NULL,1),(59,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,119,'1',0,NULL,1),(60,'2013-12-15 16:00:00',2,8,-1,1,2,'2014-03-15 08:53:55',-1,NULL,NULL,0,1,0,93,'1',1,NULL,1),(61,'2013-12-15 16:00:00',2,8,-1,1,2,'2014-03-15 08:54:05',-1,NULL,NULL,0,1,0,92,'1',0,NULL,1),(62,'2013-12-15 16:00:00',2,8,-1,1,2,'2014-03-15 08:54:14',-1,NULL,NULL,0,1,0,94,'1',1,NULL,1),(63,'2013-12-16 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,49,'1',0,NULL,1),(64,'2013-12-23 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,168,'1',0,NULL,1),(65,'2013-12-23 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,169,'1',0,NULL,1),(66,'2013-12-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,171,'1',0,NULL,1),(67,'2013-12-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,172,'1',0,NULL,1),(68,'2013-12-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,174,'1',0,NULL,1),(69,'2013-12-24 16:00:00',2,8,-1,1,2,'2013-12-25 04:10:57',-1,NULL,NULL,0,1,0,175,'1',0,NULL,1),(70,'2013-12-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,177,'1',0,NULL,1),(71,'2013-12-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,178,'1',0,NULL,1),(72,'2013-12-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,180,'1',0,NULL,1),(73,'2013-12-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,181,'1',0,NULL,1),(74,'2013-12-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,183,'1',0,NULL,1),(75,'2013-12-24 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,184,'1',0,NULL,1),(76,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,186,'1',0,NULL,1),(77,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,187,'1',0,NULL,1),(78,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,188,'1',0,NULL,1),(79,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,190,'1',0,NULL,1),(80,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,191,'1',0,NULL,1),(81,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,192,'1',0,NULL,1),(82,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,194,'1',0,NULL,1),(83,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,195,'1',0,NULL,1),(84,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,196,'1',0,NULL,1),(85,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,198,'1',0,NULL,1),(86,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,199,'1',0,NULL,1),(87,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,200,'1',0,NULL,1),(88,'2014-01-12 16:00:00',2,8,-1,1,2,'2014-01-13 02:07:12',-1,NULL,NULL,0,1,0,202,'1',0,NULL,1),(89,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,203,'1',0,NULL,1),(90,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,204,'1',0,NULL,1),(91,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,206,'1',0,NULL,1),(92,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,207,'1',0,NULL,1),(93,'2014-01-12 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,NULL,0,1,0,208,'1',0,NULL,1),(94,'2014-03-06 04:08:37',2,8,-1,1,2,'2014-03-06 04:08:37',-1,NULL,NULL,0,1,0,210,'1',0,NULL,1),(95,'2014-03-05 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,NULL,0,1,0,211,'1',0,NULL,1),(96,'2014-03-05 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,NULL,0,1,0,213,'1',0,NULL,1),(97,'2014-03-09 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,NULL,0,1,0,231,'1',0,NULL,1),(98,'2014-03-09 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,NULL,0,1,0,232,'1',0,NULL,1);
/*!40000 ALTER TABLE `ts_nodecfg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_post`
--

DROP TABLE IF EXISTS `ts_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_post` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `mnemonic` varchar(100) DEFAULT NULL,
  `mtask` varchar(200) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `isLoan` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_post`
--

LOCK TABLES `ts_post` WRITE;
/*!40000 ALTER TABLE `ts_post` DISABLE KEYS */;
INSERT INTO `ts_post` VALUES (1,'2013-11-27 16:00:00',2,8,NULL,1,2,'2013-11-27 16:00:00',-1,NULL,'P2013112804180500',NULL,NULL,'董事长',NULL),(2,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'P2013112804181302',NULL,NULL,'总经理',NULL),(3,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'P2013112804183304',NULL,NULL,'副总经理(风控)',NULL),(4,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'P2013112804190006',NULL,NULL,'总经理助理',NULL),(5,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'P2013112804191108',NULL,NULL,'客户经理',NULL),(6,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'P2013112804192510',NULL,NULL,'综合部经理',NULL),(7,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'P2013112804194112',NULL,NULL,'副总经理(财务)',NULL),(8,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'P2013112804203214',NULL,NULL,'综合文员',NULL),(9,'2013-11-27 16:00:00',2,8,NULL,1,2,'2014-01-12 16:00:00',-1,NULL,'P2013112804204916',NULL,NULL,'出纳',1),(10,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'P2013112804205318',NULL,NULL,'会计',NULL),(11,'2013-11-27 16:00:00',2,8,-1,0,NULL,'2014-02-21 07:31:30',-1,NULL,'P2013112804210920',NULL,NULL,'风控经理',NULL);
/*!40000 ALTER TABLE `ts_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_province`
--

DROP TABLE IF EXISTS `ts_province`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_province` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `countryd` double DEFAULT NULL,
  `ename` varchar(50) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `isdefault` int(11) DEFAULT NULL,
  `jname` varchar(50) DEFAULT NULL,
  `koname` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `twname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_province`
--

LOCK TABLES `ts_province` WRITE;
/*!40000 ALTER TABLE `ts_province` DISABLE KEYS */;
INSERT INTO `ts_province` VALUES (1,'2013-12-15 16:00:00',2,8,NULL,1,2,'2013-12-15 16:00:00',-1,NULL,'P2013121603455000',1,NULL,NULL,0,NULL,NULL,'广东省',NULL);
/*!40000 ALTER TABLE `ts_province` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_region`
--

DROP TABLE IF EXISTS `ts_region`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_region` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `cityId` double DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `ename` varchar(50) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `isdefault` int(11) DEFAULT NULL,
  `jname` varchar(50) DEFAULT NULL,
  `koname` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `twname` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_region`
--

LOCK TABLES `ts_region` WRITE;
/*!40000 ALTER TABLE `ts_region` DISABLE KEYS */;
INSERT INTO `ts_region` VALUES (1,'2014-04-09 03:20:14',2,8,NULL,1,24,'2014-04-08 16:00:00',-1,NULL,1,'R2013121603485000',NULL,NULL,0,NULL,NULL,'南山区',NULL),(2,'2014-04-09 03:20:22',2,8,NULL,1,24,'2014-04-08 16:00:00',-1,NULL,1,'R2013121603492502',NULL,NULL,0,NULL,NULL,'福田区',NULL),(3,'2014-04-09 03:20:30',2,8,NULL,1,24,'2014-04-08 16:00:00',-1,NULL,1,'R2013121603493404',NULL,NULL,0,NULL,NULL,'罗湖区',NULL),(4,'2014-04-09 03:20:39',2,8,NULL,1,24,'2014-04-08 16:00:00',-1,NULL,1,'R2013121603500106',NULL,NULL,0,NULL,NULL,'宝安区',NULL),(5,'2014-04-09 03:20:51',2,8,NULL,1,24,'2014-04-08 16:00:00',-1,NULL,1,'R2013121603501708',NULL,NULL,0,NULL,NULL,'盐田区',NULL),(6,'2013-12-15 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:30',-1,NULL,2,'R2013121603552710',NULL,NULL,0,NULL,NULL,'越秀区',NULL),(7,'2013-12-15 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:30',-1,NULL,2,'R2013121603585212',NULL,NULL,0,NULL,NULL,'荔湾区',NULL),(8,'2013-12-15 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:30',-1,NULL,2,'R2013121603590714',NULL,NULL,0,NULL,NULL,'海珠区',NULL),(9,'2013-12-15 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:30',-1,NULL,2,'R2013121603591816',NULL,NULL,0,NULL,NULL,'天河区',NULL),(10,'2013-12-15 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:30',-1,NULL,2,'R2013121603592618',NULL,NULL,0,NULL,NULL,'白云区',NULL),(11,'2013-12-15 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:30',-1,NULL,2,'R2013121603593420',NULL,NULL,0,NULL,NULL,'黄埔区',NULL),(12,'2014-04-08 16:00:00',24,1,-1,1,NULL,'0000-00-00 00:00:00',1,NULL,1,'R2014040911205722',NULL,NULL,0,NULL,NULL,'龙岗区',NULL);
/*!40000 ALTER TABLE `ts_region` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_reporttemp`
--

DROP TABLE IF EXISTS `ts_reporttemp`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_reporttemp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `funName` varchar(60) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `url` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_reporttemp`
--

LOCK TABLES `ts_reporttemp` WRITE;
/*!40000 ALTER TABLE `ts_reporttemp` DISABLE KEYS */;
INSERT INTO `ts_reporttemp` VALUES (1,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'资金头寸分析','资金头寸分析报表',3,'146',1,'attachments/xlsreports/资金头寸分析报表.xls'),(2,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'质押合同查询','质押合同查询报表',3,'145',1,'attachments/xlsreports/质押合同查询报表.xls'),(3,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'正常还款管理','正常还款导出报表',3,'109',1,'attachments/xlsreports/正常还款导出报表.xls'),(4,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'展期还款计划查询','展期还款计划报表',3,'210',1,'attachments/xlsreports/展期还款计划报表.xls'),(5,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'逾期还款管理','逾期还款导出报表',3,'115',1,'attachments/xlsreports/逾期还款导出报表.xls'),(6,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'企业客户暂存申请单','企业客户暂存申请申请报表',3,'76',1,'attachments/xlsreports/企业客户暂存申请申请报表.xls'),(7,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'企业客户资料管理','企业客户导出报表',3,'71',1,'attachments/xlsreports/企业客户导出报表.xls'),(8,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'企业贷款发放','企业贷款发放导出报表',3,'110',1,'attachments/xlsreports/企业贷款发放导出报表.xls'),(9,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'放款手续费收取','客户手续费收取导出报表',3,'113',1,'attachments/xlsreports/客户手续费收取导出报表.xls'),(10,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'借款合同查询','借款合同查询报表',3,'142',1,'attachments/xlsreports/借款合同查询报表.xls'),(11,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'还款计划查询','还款计划查询导出报表',3,'201',1,'attachments/xlsreports/还款计划查询导出报表.xls'),(12,'2013-10-28 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:30',-1,NULL,'个人客户暂存申请单','个人客户暂存申请单',3,'75',1,'attachments/xlsreports/个人客户暂存申请单.xls'),(13,'2013-10-28 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:30',-1,NULL,'个人客户资料管理','个人客户导出报表',3,'69',1,'attachments/xlsreports/个人客户导出报表.xls'),(14,'2013-10-28 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:30',-1,NULL,'个人贷款发放','个人贷款发放导出报表',3,'99',1,'attachments/xlsreports/个人贷款发放导出报表.xls'),(15,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'抵押合同查询','抵押合同查询报表',3,'144',1,'attachments/xlsreports/抵押合同查询报表.xls'),(16,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'贷款发放利率表','贷款发放利率表',3,'150',1,'attachments/xlsreports/贷款发放利率表.xls'),(17,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'保证合同查询','保证合同报表查询',3,'143',1,'attachments/xlsreports/保证合同报表查询.xls'),(18,'2013-10-28 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:30',-1,NULL,'个人客户资料查询','个人客户贷款查询报表',3,'137',1,'attachments/xlsreports/个人客户导出报表.xls'),(19,'2013-10-28 16:00:00',2,8,-1,-1,NULL,'2014-02-21 07:31:30',-1,NULL,'企业客户资料查询','企业客户贷款查询报表',3,'138',1,'attachments/xlsreports/企业客户导出报表.xls'),(20,'2013-10-28 16:00:00',2,8,NULL,1,2,'2013-10-28 16:00:00',-1,NULL,'个人客户贷款查询','个人客户贷款查询报表',3,'139',1,'attachments/xlsreports/个人客户暂存申请单.xls'),(21,'2013-10-28 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'企业客户贷款查询','企业客户贷款查询报表',3,'140',1,'attachments/xlsreports/企业客户暂存申请申请报表.xls'),(22,'2013-12-09 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'贷款业务统计','贷款业务统计报表',3,'226',1,'attachments/xlsreports/贷款业务统计表报.xls'),(23,'2013-12-13 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'民汇客户台账明细','客户台账明细',3,'225',1,'attachments/xlsreports/台账明细.xls'),(24,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,'自有资金管理','自有资金管理','自有资金管理导出报表',3,'193',1,'attachments/xlsreports/自有资金管理表.xls'),(25,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,'贷款发放流水','贷款发放流水','贷款发放流水导出报表',3,'230',1,'attachments/xlsreports/贷款发放流水导出报表.xls'),(26,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,'放款手续费收取流水导出报表','放款手续费收取流水','放款手续费收取流水导出报表',3,'231',1,'attachments/xlsreports/放款手续费收取流水导出报表.xls'),(27,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,'个人贷款发放导出报表','个人贷款发放','个人贷款发放导出报表',3,'99',1,'attachments/xlsreports/个人贷款发放导出报表.xls'),(28,'2013-12-15 16:00:00',2,8,NULL,1,2,'2014-01-09 16:00:00',-1,'客户手续费收取导出报表','放款手续费收取','客户手续费收取导出报表',3,'113',1,'attachments/xlsreports/放款手续费收取流水导出报表.xls'),(29,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,'逾期还款流水导出报表','逾期还款流水','逾期还款流水导出报表',3,'233',1,'attachments/xlsreports/逾期还款流水导出报表.xls'),(30,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,'正常还款流水导出报表','正常还款流水','正常还款流水导出报表',3,'232',1,'attachments/xlsreports/正常还款流水导出报表.xls'),(31,'2013-12-17 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,'企业贷款发放流水','企业贷款发放流水','企业贷款流水发放导出报表',3,'234',1,'attachments/xlsreports/企业贷款发放流水导出报表.xls'),(32,'2014-01-09 16:00:00',2,8,NULL,0,2,'2014-01-09 16:00:00',-1,'个人贷款发放流水导出(说明由于模版共有已经处理过了切记不要删除模版好吗?)','个人贷款发放流水','个人贷款发放流水',3,'230',1,'attachments/xlsreports/个人贷款发放导出报表.xls');
/*!40000 ALTER TABLE `ts_reporttemp` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_restype`
--

DROP TABLE IF EXISTS `ts_restype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_restype` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `recode` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_restype`
--

LOCK TABLES `ts_restype` WRITE;
/*!40000 ALTER TABLE `ts_restype` DISABLE KEYS */;
INSERT INTO `ts_restype` VALUES (1,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'资源名称','100001'),(2,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'证件类型','100002'),(3,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'婚姻状况','100003'),(4,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'籍贯','100004'),(5,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'民族','100005'),(6,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'学历','100006'),(7,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'住宅类别','100007'),(8,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'居住方式','100008'),(9,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'单位性质','100009'),(10,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'信用类型','100010'),(11,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'所属行业','100011'),(12,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'所有者类型','100012'),(13,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'出资方式','100013'),(14,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'注册资金币种','100014'),(15,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'所属区域','100015'),(16,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'抵押物','100016'),(17,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'质押物','100017'),(18,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'借款主体','200000'),(19,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'行业分类','200001'),(20,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'贷款方式','200002'),(21,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'期限种类','200003'),(22,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'业务来源方式','200004'),(23,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,NULL,'未来周期','200005'),(24,'2013-12-15 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:30',-1,'其他费用分类','其他费用分类','200006');
/*!40000 ALTER TABLE `ts_restype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_right`
--

DROP TABLE IF EXISTS `ts_right`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_right` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mmId` double DEFAULT NULL,
  `objtype` int(11) DEFAULT NULL,
  `roleId` double DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3500 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_right`
--

LOCK TABLES `ts_right` WRITE;
/*!40000 ALTER TABLE `ts_right` DISABLE KEYS */;
INSERT INTO `ts_right` VALUES (1,22,0,1,3,0),(2,24,0,1,3,0),(3,25,0,1,3,0),(4,29,0,1,3,0),(5,69,0,1,3,1),(6,71,0,1,3,1),(7,84,0,1,3,1),(8,85,0,1,3,1),(9,88,0,1,3,1),(10,89,0,1,3,1),(11,90,0,1,3,1),(12,91,0,1,3,1),(13,92,0,1,3,1),(14,93,0,1,3,1),(15,208,0,1,3,1),(16,217,0,1,3,1),(17,220,0,1,3,1),(18,109,0,1,3,1),(19,110,0,1,3,1),(20,113,0,1,3,1),(21,115,0,1,3,1),(22,215,0,1,3,1),(23,230,0,1,3,1),(24,231,0,1,3,1),(25,232,0,1,3,1),(26,233,0,1,3,1),(27,99,0,1,3,1),(28,133,0,1,3,1),(29,137,0,1,3,1),(30,134,0,1,3,1),(31,138,0,1,3,1),(32,136,0,1,3,1),(33,140,0,1,3,1),(34,135,0,1,3,1),(35,139,0,1,3,1),(36,141,0,1,3,1),(37,142,0,1,3,1),(38,143,0,1,3,1),(39,144,0,1,3,1),(40,145,0,1,3,1),(41,201,0,1,3,1),(42,150,0,1,3,1),(43,146,0,1,3,1),(44,152,0,1,3,1),(45,154,0,1,3,1),(46,209,0,1,3,1),(47,210,0,1,3,1),(48,225,0,1,3,1),(49,226,0,1,3,1),(50,111,0,1,3,2),(51,122,0,1,3,2),(52,151,0,1,3,2),(53,152,0,1,3,2),(54,153,0,1,3,2),(55,154,0,1,3,2),(56,155,0,1,3,2),(57,156,0,1,3,2),(58,157,0,1,3,2),(59,158,0,1,3,2),(60,159,0,1,3,2),(61,160,0,1,3,2),(62,161,0,1,3,2),(63,162,0,1,3,2),(64,163,0,1,3,2),(65,164,0,1,3,2),(66,165,0,1,3,2),(67,166,0,1,3,2),(68,167,0,1,3,2),(69,168,0,1,3,2),(70,169,0,1,3,2),(71,170,0,1,3,2),(72,171,0,1,3,2),(73,172,0,1,3,2),(74,173,0,1,3,2),(75,174,0,1,3,2),(76,175,0,1,3,2),(77,176,0,1,3,2),(78,177,0,1,3,2),(79,184,0,1,3,2),(80,186,0,1,3,2),(81,189,0,1,3,2),(82,193,0,1,3,2),(83,195,0,1,3,2),(84,197,0,1,3,2),(85,191,0,1,3,2),(86,194,0,1,3,2),(87,201,0,1,3,2),(88,202,0,1,3,2),(89,206,0,1,3,2),(90,207,0,1,3,2),(91,208,0,1,3,2),(92,209,0,1,3,2),(93,346,0,1,3,2),(94,347,0,1,3,2),(95,351,0,1,3,2),(96,352,0,1,3,2),(97,353,0,1,3,2),(98,355,0,1,3,2),(99,356,0,1,3,2),(100,357,0,1,3,2),(101,359,0,1,3,2),(102,360,0,1,3,2),(103,361,0,1,3,2),(104,178,0,1,3,2),(105,179,0,1,3,2),(106,185,0,1,3,2),(107,348,0,1,3,2),(108,225,0,1,3,2),(109,226,0,1,3,2),(110,227,0,1,3,2),(111,228,0,1,3,2),(112,229,0,1,3,2),(113,230,0,1,3,2),(114,231,0,1,3,2),(115,232,0,1,3,2),(116,237,0,1,3,2),(117,238,0,1,3,2),(118,239,0,1,3,2),(119,240,0,1,3,2),(120,233,0,1,3,2),(121,234,0,1,3,2),(122,235,0,1,3,2),(123,236,0,1,3,2),(124,241,0,1,3,2),(125,242,0,1,3,2),(126,243,0,1,3,2),(127,244,0,1,3,2),(128,245,0,1,3,2),(129,246,0,1,3,2),(130,247,0,1,3,2),(131,248,0,1,3,2),(132,249,0,1,3,2),(133,250,0,1,3,2),(134,251,0,1,3,2),(135,252,0,1,3,2),(136,253,0,1,3,2),(137,254,0,1,3,2),(138,255,0,1,3,2),(139,256,0,1,3,2),(140,260,0,1,3,2),(141,261,0,1,3,2),(142,262,0,1,3,2),(143,257,0,1,3,2),(144,258,0,1,3,2),(145,259,0,1,3,2),(146,266,0,1,3,2),(147,267,0,1,3,2),(148,268,0,1,3,2),(149,269,0,1,3,2),(150,270,0,1,3,2),(151,271,0,1,3,2),(152,272,0,1,3,2),(153,273,0,1,3,2),(154,274,0,1,3,2),(155,275,0,1,3,2),(156,278,0,1,3,2),(157,279,0,1,3,2),(158,280,0,1,3,2),(159,281,0,1,3,2),(160,282,0,1,3,2),(161,22,0,2,3,0),(162,24,0,2,3,0),(163,25,0,2,3,0),(164,29,0,2,3,0),(165,69,0,2,3,1),(166,71,0,2,3,1),(167,84,0,2,3,1),(168,85,0,2,3,1),(169,88,0,2,3,1),(170,89,0,2,3,1),(171,90,0,2,3,1),(172,91,0,2,3,1),(173,92,0,2,3,1),(174,93,0,2,3,1),(175,208,0,2,3,1),(176,217,0,2,3,1),(177,220,0,2,3,1),(178,99,0,2,3,1),(179,109,0,2,3,1),(180,110,0,2,3,1),(181,115,0,2,3,1),(182,215,0,2,3,1),(183,231,0,2,3,1),(184,232,0,2,3,1),(185,233,0,2,3,1),(186,133,0,2,3,1),(187,137,0,2,3,1),(188,138,0,2,3,1),(189,134,0,2,3,1),(190,139,0,2,3,1),(191,135,0,2,3,1),(192,140,0,2,3,1),(193,136,0,2,3,1),(194,210,0,2,3,1),(195,209,0,2,3,1),(196,154,0,2,3,1),(197,152,0,2,3,1),(198,151,0,2,3,1),(199,150,0,2,3,1),(200,146,0,2,3,1),(201,201,0,2,3,1),(202,141,0,2,3,1),(203,145,0,2,3,1),(204,144,0,2,3,1),(205,143,0,2,3,1),(206,142,0,2,3,1),(207,111,0,2,3,2),(208,122,0,2,3,2),(209,151,0,2,3,2),(210,152,0,2,3,2),(211,153,0,2,3,2),(212,154,0,2,3,2),(213,155,0,2,3,2),(214,156,0,2,3,2),(215,157,0,2,3,2),(216,158,0,2,3,2),(217,159,0,2,3,2),(218,160,0,2,3,2),(219,161,0,2,3,2),(220,162,0,2,3,2),(221,163,0,2,3,2),(222,164,0,2,3,2),(223,165,0,2,3,2),(224,166,0,2,3,2),(225,167,0,2,3,2),(226,168,0,2,3,2),(227,169,0,2,3,2),(228,170,0,2,3,2),(229,171,0,2,3,2),(230,172,0,2,3,2),(231,173,0,2,3,2),(232,174,0,2,3,2),(233,175,0,2,3,2),(234,176,0,2,3,2),(235,177,0,2,3,2),(236,178,0,2,3,2),(237,179,0,2,3,2),(238,184,0,2,3,2),(239,185,0,2,3,2),(240,186,0,2,3,2),(241,189,0,2,3,2),(242,201,0,2,3,2),(243,202,0,2,3,2),(244,206,0,2,3,2),(245,207,0,2,3,2),(246,351,0,2,3,2),(247,352,0,2,3,2),(248,353,0,2,3,2),(249,355,0,2,3,2),(250,356,0,2,3,2),(251,357,0,2,3,2),(252,358,0,2,3,2),(253,359,0,2,3,2),(254,360,0,2,3,2),(255,361,0,2,3,2),(256,225,0,2,3,2),(257,226,0,2,3,2),(258,227,0,2,3,2),(259,229,0,2,3,2),(260,230,0,2,3,2),(261,231,0,2,3,2),(262,233,0,2,3,2),(263,234,0,2,3,2),(264,235,0,2,3,2),(265,237,0,2,3,2),(266,238,0,2,3,2),(267,239,0,2,3,2),(268,272,0,2,3,2),(269,273,0,2,3,2),(270,269,0,2,3,2),(271,270,0,2,3,2),(272,266,0,2,3,2),(273,267,0,2,3,2),(274,263,0,2,3,2),(275,264,0,2,3,2),(276,260,0,2,3,2),(277,261,0,2,3,2),(278,257,0,2,3,2),(279,258,0,2,3,2),(280,253,0,2,3,2),(281,254,0,2,3,2),(282,250,0,2,3,2),(283,251,0,2,3,2),(284,247,0,2,3,2),(285,248,0,2,3,2),(286,245,0,2,3,2),(287,244,0,2,3,2),(288,242,0,2,3,2),(289,241,0,2,3,2),(290,22,0,3,3,0),(291,24,0,3,3,0),(292,29,0,3,3,0),(293,69,0,3,3,1),(294,71,0,3,3,1),(295,84,0,3,3,1),(296,85,0,3,3,1),(297,88,0,3,3,1),(298,89,0,3,3,1),(299,90,0,3,3,1),(300,91,0,3,3,1),(301,92,0,3,3,1),(302,93,0,3,3,1),(303,208,0,3,3,1),(304,217,0,3,3,1),(305,220,0,3,3,1),(306,133,0,3,3,1),(307,137,0,3,3,1),(308,134,0,3,3,1),(309,138,0,3,3,1),(310,135,0,3,3,1),(311,139,0,3,3,1),(312,136,0,3,3,1),(313,140,0,3,3,1),(314,150,0,3,3,1),(315,151,0,3,3,1),(316,154,0,3,3,1),(317,209,0,3,3,1),(318,210,0,3,3,1),(319,226,0,3,3,1),(320,225,0,3,3,1),(321,152,0,3,3,1),(322,146,0,3,3,1),(323,141,0,3,3,1),(324,201,0,3,3,1),(325,111,0,3,3,2),(326,122,0,3,3,2),(327,151,0,3,3,2),(328,152,0,3,3,2),(329,153,0,3,3,2),(330,154,0,3,3,2),(331,155,0,3,3,2),(332,156,0,3,3,2),(333,157,0,3,3,2),(334,158,0,3,3,2),(335,159,0,3,3,2),(336,160,0,3,3,2),(337,161,0,3,3,2),(338,162,0,3,3,2),(339,163,0,3,3,2),(340,164,0,3,3,2),(341,165,0,3,3,2),(342,166,0,3,3,2),(343,167,0,3,3,2),(344,168,0,3,3,2),(345,169,0,3,3,2),(346,170,0,3,3,2),(347,171,0,3,3,2),(348,172,0,3,3,2),(349,173,0,3,3,2),(350,174,0,3,3,2),(351,175,0,3,3,2),(352,176,0,3,3,2),(353,177,0,3,3,2),(354,225,0,3,3,2),(355,226,0,3,3,2),(356,227,0,3,3,2),(357,228,0,3,3,2),(358,229,0,3,3,2),(359,230,0,3,3,2),(360,231,0,3,3,2),(361,232,0,3,3,2),(362,233,0,3,3,2),(363,234,0,3,3,2),(364,235,0,3,3,2),(365,236,0,3,3,2),(366,237,0,3,3,2),(367,238,0,3,3,2),(368,239,0,3,3,2),(369,240,0,3,3,2),(370,260,0,3,3,2),(371,261,0,3,3,2),(372,262,0,3,3,2),(373,263,0,3,3,2),(374,264,0,3,3,2),(375,265,0,3,3,2),(376,269,0,3,3,2),(377,270,0,3,3,2),(378,271,0,3,3,2),(379,272,0,3,3,2),(380,273,0,3,3,2),(381,274,0,3,3,2),(382,275,0,3,3,2),(383,280,0,3,3,2),(384,281,0,3,3,2),(385,282,0,3,3,2),(386,278,0,3,3,2),(387,279,0,3,3,2),(388,266,0,3,3,2),(389,267,0,3,3,2),(390,268,0,3,3,2),(391,257,0,3,3,2),(392,258,0,3,3,2),(393,259,0,3,3,2),(394,253,0,3,3,2),(395,254,0,3,3,2),(396,255,0,3,3,2),(397,256,0,3,3,2),(398,22,0,4,3,0),(399,24,0,4,3,0),(400,69,0,4,3,1),(401,71,0,4,3,1),(402,84,0,4,3,1),(403,85,0,4,3,1),(404,88,0,4,3,1),(405,89,0,4,3,1),(406,90,0,4,3,1),(407,91,0,4,3,1),(408,92,0,4,3,1),(409,93,0,4,3,1),(410,208,0,4,3,1),(411,217,0,4,3,1),(412,220,0,4,3,1),(413,111,0,4,3,2),(414,122,0,4,3,2),(415,151,0,4,3,2),(416,152,0,4,3,2),(417,153,0,4,3,2),(418,154,0,4,3,2),(419,155,0,4,3,2),(420,156,0,4,3,2),(421,157,0,4,3,2),(422,158,0,4,3,2),(423,159,0,4,3,2),(424,160,0,4,3,2),(425,161,0,4,3,2),(426,162,0,4,3,2),(427,163,0,4,3,2),(428,164,0,4,3,2),(429,165,0,4,3,2),(430,166,0,4,3,2),(431,167,0,4,3,2),(432,168,0,4,3,2),(433,169,0,4,3,2),(434,170,0,4,3,2),(435,171,0,4,3,2),(436,172,0,4,3,2),(437,173,0,4,3,2),(438,174,0,4,3,2),(439,175,0,4,3,2),(440,176,0,4,3,2),(441,177,0,4,3,2),(624,22,0,6,3,0),(625,23,0,6,3,0),(626,24,0,6,3,0),(627,29,0,6,3,0),(628,68,0,6,3,1),(629,69,0,6,3,1),(630,70,0,6,3,1),(631,71,0,6,3,1),(632,72,0,6,3,1),(633,74,0,6,3,1),(634,75,0,6,3,1),(635,76,0,6,3,1),(636,195,0,6,3,1),(637,196,0,6,3,1),(638,84,0,6,3,1),(639,85,0,6,3,1),(640,88,0,6,3,1),(641,89,0,6,3,1),(642,90,0,6,3,1),(643,91,0,6,3,1),(644,92,0,6,3,1),(645,93,0,6,3,1),(646,208,0,6,3,1),(647,217,0,6,3,1),(648,220,0,6,3,1),(649,133,0,6,3,1),(650,137,0,6,3,1),(651,134,0,6,3,1),(652,138,0,6,3,1),(653,135,0,6,3,1),(654,139,0,6,3,1),(655,136,0,6,3,1),(656,140,0,6,3,1),(657,141,0,6,3,1),(658,142,0,6,3,1),(659,143,0,6,3,1),(660,144,0,6,3,1),(661,145,0,6,3,1),(662,201,0,6,3,1),(663,107,0,6,3,2),(664,108,0,6,3,2),(665,109,0,6,3,2),(666,110,0,6,3,2),(667,111,0,6,3,2),(668,112,0,6,3,2),(669,113,0,6,3,2),(670,114,0,6,3,2),(671,115,0,6,3,2),(672,116,0,6,3,2),(673,117,0,6,3,2),(674,118,0,6,3,2),(675,119,0,6,3,2),(676,120,0,6,3,2),(677,121,0,6,3,2),(678,122,0,6,3,2),(679,123,0,6,3,2),(680,124,0,6,3,2),(681,125,0,6,3,2),(682,126,0,6,3,2),(683,127,0,6,3,2),(684,128,0,6,3,2),(685,129,0,6,3,2),(686,130,0,6,3,2),(687,131,0,6,3,2),(688,132,0,6,3,2),(689,133,0,6,3,2),(690,134,0,6,3,2),(691,135,0,6,3,2),(692,136,0,6,3,2),(693,137,0,6,3,2),(694,138,0,6,3,2),(695,139,0,6,3,2),(696,140,0,6,3,2),(697,141,0,6,3,2),(698,142,0,6,3,2),(699,143,0,6,3,2),(700,144,0,6,3,2),(701,145,0,6,3,2),(702,146,0,6,3,2),(703,147,0,6,3,2),(704,148,0,6,3,2),(705,149,0,6,3,2),(706,150,0,6,3,2),(707,151,0,6,3,2),(708,152,0,6,3,2),(709,153,0,6,3,2),(710,154,0,6,3,2),(711,155,0,6,3,2),(712,156,0,6,3,2),(713,157,0,6,3,2),(714,158,0,6,3,2),(715,159,0,6,3,2),(716,160,0,6,3,2),(717,161,0,6,3,2),(718,162,0,6,3,2),(719,163,0,6,3,2),(720,164,0,6,3,2),(721,165,0,6,3,2),(722,166,0,6,3,2),(723,167,0,6,3,2),(724,168,0,6,3,2),(725,169,0,6,3,2),(726,170,0,6,3,2),(727,171,0,6,3,2),(728,172,0,6,3,2),(729,173,0,6,3,2),(730,174,0,6,3,2),(731,175,0,6,3,2),(732,176,0,6,3,2),(733,177,0,6,3,2),(734,225,0,6,3,2),(735,226,0,6,3,2),(736,227,0,6,3,2),(737,228,0,6,3,2),(738,229,0,6,3,2),(739,230,0,6,3,2),(740,231,0,6,3,2),(741,232,0,6,3,2),(742,233,0,6,3,2),(743,234,0,6,3,2),(744,235,0,6,3,2),(745,236,0,6,3,2),(746,237,0,6,3,2),(747,238,0,6,3,2),(748,239,0,6,3,2),(749,240,0,6,3,2),(750,241,0,6,3,2),(751,242,0,6,3,2),(752,243,0,6,3,2),(753,244,0,6,3,2),(754,245,0,6,3,2),(755,246,0,6,3,2),(756,247,0,6,3,2),(757,248,0,6,3,2),(758,249,0,6,3,2),(759,250,0,6,3,2),(760,251,0,6,3,2),(761,252,0,6,3,2),(762,253,0,6,3,2),(763,254,0,6,3,2),(764,255,0,6,3,2),(765,256,0,6,3,2),(1002,19,0,5,1,0),(1003,132,0,5,1,1),(1004,75,0,5,1,2),(1005,76,0,5,1,2),(1006,77,0,5,1,2),(1007,78,0,5,1,2),(1008,80,0,5,1,2),(1009,81,0,5,1,2),(1010,82,0,5,1,2),(1386,22,0,11,3,0),(1387,23,0,11,3,0),(1388,24,0,11,3,0),(1389,25,0,11,3,0),(1390,29,0,11,3,0),(1391,69,0,11,3,1),(1392,71,0,11,3,1),(1393,75,0,11,3,1),(1394,74,0,11,3,1),(1395,196,0,11,3,1),(1396,76,0,11,3,1),(1397,90,0,11,3,1),(1398,88,0,11,3,1),(1399,89,0,11,3,1),(1400,93,0,11,3,1),(1401,91,0,11,3,1),(1402,220,0,11,3,1),(1403,109,0,11,3,1),(1404,115,0,11,3,1),(1405,230,0,11,3,1),(1406,232,0,11,3,1),(1407,138,0,11,3,1),(1408,134,0,11,3,1),(1409,140,0,11,3,1),(1410,136,0,11,3,1),(1411,139,0,11,3,1),(1412,135,0,11,3,1),(1413,142,0,11,3,1),(1414,141,0,11,3,1),(1415,143,0,11,3,1),(1416,144,0,11,3,1),(1417,145,0,11,3,1),(1418,201,0,11,3,1),(1419,151,0,11,3,1),(1420,152,0,11,3,1),(1421,154,0,11,3,1),(1422,210,0,11,3,1),(1423,209,0,11,3,1),(1424,225,0,11,3,1),(1425,226,0,11,3,1),(1426,107,0,11,3,2),(1427,118,0,11,3,2),(1428,129,0,11,3,2),(1429,145,0,11,3,2),(1430,137,0,11,3,2),(1431,160,0,11,3,2),(1432,157,0,11,3,2),(1433,166,0,11,3,2),(1434,175,0,11,3,2),(1435,184,0,11,3,2),(1436,201,0,11,3,2),(1437,346,0,11,3,2),(1438,355,0,11,3,2),(1439,229,0,11,3,2),(1440,237,0,11,3,2),(1441,233,0,11,3,2),(1442,241,0,11,3,2),(1443,244,0,11,3,2),(1444,247,0,11,3,2),(1445,250,0,11,3,2),(1446,253,0,11,3,2),(1447,263,0,11,3,2),(1448,266,0,11,3,2),(1449,269,0,11,3,2),(1450,272,0,11,3,2),(1451,278,0,11,3,2),(1452,279,0,11,3,2),(1453,280,0,11,3,2),(1454,282,0,11,3,2),(2112,19,0,3,1,0),(2113,51,0,3,1,1),(2114,53,0,3,1,1),(2115,56,0,3,1,1),(2116,194,0,3,1,1),(2117,213,0,3,1,1),(2118,214,0,3,1,1),(2119,22,0,3,1,2),(2120,416,0,3,1,2),(2121,383,0,3,1,2),(2122,87,0,3,1,2),(2123,7,0,3,1,2),(2124,83,0,3,1,2),(2585,22,0,5,3,0),(2586,24,0,5,3,0),(2587,25,0,5,3,0),(2588,29,0,5,3,0),(2589,26,0,5,3,0),(2590,69,0,5,3,1),(2591,71,0,5,3,1),(2592,84,0,5,3,1),(2593,85,0,5,3,1),(2594,88,0,5,3,1),(2595,89,0,5,3,1),(2596,90,0,5,3,1),(2597,91,0,5,3,1),(2598,92,0,5,3,1),(2599,93,0,5,3,1),(2600,208,0,5,3,1),(2601,217,0,5,3,1),(2602,220,0,5,3,1),(2603,99,0,5,3,1),(2604,109,0,5,3,1),(2605,110,0,5,3,1),(2606,115,0,5,3,1),(2607,215,0,5,3,1),(2608,230,0,5,3,1),(2609,232,0,5,3,1),(2610,133,0,5,3,1),(2611,137,0,5,3,1),(2612,134,0,5,3,1),(2613,138,0,5,3,1),(2614,135,0,5,3,1),(2615,139,0,5,3,1),(2616,136,0,5,3,1),(2617,140,0,5,3,1),(2618,141,0,5,3,1),(2619,142,0,5,3,1),(2620,143,0,5,3,1),(2621,144,0,5,3,1),(2622,145,0,5,3,1),(2623,201,0,5,3,1),(2624,146,0,5,3,1),(2625,150,0,5,3,1),(2626,151,0,5,3,1),(2627,152,0,5,3,1),(2628,154,0,5,3,1),(2629,209,0,5,3,1),(2630,210,0,5,3,1),(2631,225,0,5,3,1),(2632,226,0,5,3,1),(2633,193,0,5,3,1),(2634,235,0,5,3,1),(2635,236,0,5,3,1),(2636,107,0,5,3,2),(2637,118,0,5,3,2),(2638,151,0,5,3,2),(2639,152,0,5,3,2),(2640,153,0,5,3,2),(2641,154,0,5,3,2),(2642,155,0,5,3,2),(2643,156,0,5,3,2),(2644,157,0,5,3,2),(2645,158,0,5,3,2),(2646,159,0,5,3,2),(2647,160,0,5,3,2),(2648,161,0,5,3,2),(2649,162,0,5,3,2),(2650,163,0,5,3,2),(2651,164,0,5,3,2),(2652,165,0,5,3,2),(2653,166,0,5,3,2),(2654,167,0,5,3,2),(2655,168,0,5,3,2),(2656,169,0,5,3,2),(2657,170,0,5,3,2),(2658,171,0,5,3,2),(2659,172,0,5,3,2),(2660,173,0,5,3,2),(2661,174,0,5,3,2),(2662,175,0,5,3,2),(2663,176,0,5,3,2),(2664,177,0,5,3,2),(2665,178,0,5,3,2),(2666,179,0,5,3,2),(2667,180,0,5,3,2),(2668,181,0,5,3,2),(2669,182,0,5,3,2),(2670,183,0,5,3,2),(2671,184,0,5,3,2),(2672,185,0,5,3,2),(2673,187,0,5,3,2),(2674,188,0,5,3,2),(2675,190,0,5,3,2),(2676,192,0,5,3,2),(2677,200,0,5,3,2),(2678,186,0,5,3,2),(2679,189,0,5,3,2),(2680,193,0,5,3,2),(2681,195,0,5,3,2),(2682,197,0,5,3,2),(2683,201,0,5,3,2),(2684,202,0,5,3,2),(2685,203,0,5,3,2),(2686,204,0,5,3,2),(2687,205,0,5,3,2),(2688,206,0,5,3,2),(2689,207,0,5,3,2),(2690,208,0,5,3,2),(2691,209,0,5,3,2),(2692,346,0,5,3,2),(2693,347,0,5,3,2),(2694,348,0,5,3,2),(2695,349,0,5,3,2),(2696,350,0,5,3,2),(2697,355,0,5,3,2),(2698,356,0,5,3,2),(2699,357,0,5,3,2),(2700,358,0,5,3,2),(2701,225,0,5,3,2),(2702,226,0,5,3,2),(2703,227,0,5,3,2),(2704,228,0,5,3,2),(2705,229,0,5,3,2),(2706,230,0,5,3,2),(2707,231,0,5,3,2),(2708,232,0,5,3,2),(2709,233,0,5,3,2),(2710,234,0,5,3,2),(2711,235,0,5,3,2),(2712,236,0,5,3,2),(2713,237,0,5,3,2),(2714,238,0,5,3,2),(2715,239,0,5,3,2),(2716,240,0,5,3,2),(2717,241,0,5,3,2),(2718,242,0,5,3,2),(2719,243,0,5,3,2),(2720,244,0,5,3,2),(2721,245,0,5,3,2),(2722,246,0,5,3,2),(2723,247,0,5,3,2),(2724,248,0,5,3,2),(2725,249,0,5,3,2),(2726,250,0,5,3,2),(2727,251,0,5,3,2),(2728,252,0,5,3,2),(2729,253,0,5,3,2),(2730,254,0,5,3,2),(2731,255,0,5,3,2),(2732,256,0,5,3,2),(2733,257,0,5,3,2),(2734,258,0,5,3,2),(2735,259,0,5,3,2),(2736,260,0,5,3,2),(2737,261,0,5,3,2),(2738,262,0,5,3,2),(2739,263,0,5,3,2),(2740,264,0,5,3,2),(2741,265,0,5,3,2),(2742,266,0,5,3,2),(2743,267,0,5,3,2),(2744,268,0,5,3,2),(2745,269,0,5,3,2),(2746,270,0,5,3,2),(2747,271,0,5,3,2),(2748,272,0,5,3,2),(2749,273,0,5,3,2),(2750,274,0,5,3,2),(2751,275,0,5,3,2),(2752,278,0,5,3,2),(2753,279,0,5,3,2),(2754,280,0,5,3,2),(2755,281,0,5,3,2),(2756,282,0,5,3,2),(2757,320,0,5,3,2),(2758,323,0,5,3,2),(2759,324,0,5,3,2),(2760,325,0,5,3,2),(2761,326,0,5,3,2),(2762,327,0,5,3,2),(2763,328,0,5,3,2),(2764,329,0,5,3,2),(2765,403,0,5,3,2),(2766,484,0,5,3,2),(2767,485,0,5,3,2),(2768,486,0,5,3,2),(2769,487,0,5,3,2),(2770,500,0,5,3,2),(2771,501,0,5,3,2),(2772,493,0,5,3,2),(2773,494,0,5,3,2),(2774,495,0,5,3,2),(2775,496,0,5,3,2),(2776,497,0,5,3,2),(2777,498,0,5,3,2),(2778,499,0,5,3,2),(2779,25,0,10,3,0),(2780,29,0,10,3,0),(2781,99,0,10,3,1),(2782,109,0,10,3,1),(2783,110,0,10,3,1),(2784,115,0,10,3,1),(2785,215,0,10,3,1),(2786,230,0,10,3,1),(2787,232,0,10,3,1),(2788,234,0,10,3,1),(2789,235,0,10,3,1),(2790,236,0,10,3,1),(2791,146,0,10,3,1),(2792,150,0,10,3,1),(2793,151,0,10,3,1),(2794,152,0,10,3,1),(2795,154,0,10,3,1),(2796,225,0,10,3,1),(2797,226,0,10,3,1),(2798,178,0,10,3,2),(2799,179,0,10,3,2),(2800,180,0,10,3,2),(2801,181,0,10,3,2),(2802,182,0,10,3,2),(2803,183,0,10,3,2),(2804,184,0,10,3,2),(2805,185,0,10,3,2),(2806,187,0,10,3,2),(2807,188,0,10,3,2),(2808,190,0,10,3,2),(2809,192,0,10,3,2),(2810,200,0,10,3,2),(2811,186,0,10,3,2),(2812,189,0,10,3,2),(2813,193,0,10,3,2),(2814,195,0,10,3,2),(2815,197,0,10,3,2),(2816,436,0,10,3,2),(2817,201,0,10,3,2),(2818,202,0,10,3,2),(2819,203,0,10,3,2),(2820,204,0,10,3,2),(2821,205,0,10,3,2),(2822,206,0,10,3,2),(2823,207,0,10,3,2),(2824,208,0,10,3,2),(2825,209,0,10,3,2),(2826,346,0,10,3,2),(2827,347,0,10,3,2),(2828,348,0,10,3,2),(2829,349,0,10,3,2),(2830,350,0,10,3,2),(2831,437,0,10,3,2),(2832,355,0,10,3,2),(2833,356,0,10,3,2),(2834,357,0,10,3,2),(2835,358,0,10,3,2),(2836,488,0,10,3,2),(2837,489,0,10,3,2),(2838,490,0,10,3,2),(2839,491,0,10,3,2),(2840,492,0,10,3,2),(2841,484,0,10,3,2),(2842,485,0,10,3,2),(2843,486,0,10,3,2),(2844,493,0,10,3,2),(2845,494,0,10,3,2),(2846,495,0,10,3,2),(2847,497,0,10,3,2),(2848,498,0,10,3,2),(2849,499,0,10,3,2),(2850,257,0,10,3,2),(2851,258,0,10,3,2),(2852,259,0,10,3,2),(2853,260,0,10,3,2),(2854,261,0,10,3,2),(2855,262,0,10,3,2),(2856,263,0,10,3,2),(2857,264,0,10,3,2),(2858,265,0,10,3,2),(2859,266,0,10,3,2),(2860,267,0,10,3,2),(2861,268,0,10,3,2),(2862,269,0,10,3,2),(2863,270,0,10,3,2),(2864,271,0,10,3,2),(2865,278,0,10,3,2),(2866,279,0,10,3,2),(2867,280,0,10,3,2),(2868,281,0,10,3,2),(2869,282,0,10,3,2),(2870,487,0,10,3,2),(3016,24,0,9,3,0),(3017,25,0,9,3,0),(3018,84,0,9,3,1),(3019,85,0,9,3,1),(3020,88,0,9,3,1),(3021,89,0,9,3,1),(3022,90,0,9,3,1),(3023,91,0,9,3,1),(3024,92,0,9,3,1),(3025,93,0,9,3,1),(3026,208,0,9,3,1),(3027,217,0,9,3,1),(3028,220,0,9,3,1),(3029,99,0,9,3,1),(3030,109,0,9,3,1),(3031,110,0,9,3,1),(3032,115,0,9,3,1),(3033,215,0,9,3,1),(3034,230,0,9,3,1),(3035,232,0,9,3,1),(3036,234,0,9,3,1),(3037,235,0,9,3,1),(3038,236,0,9,3,1),(3039,238,0,9,3,1),(3040,151,0,9,3,2),(3041,152,0,9,3,2),(3042,153,0,9,3,2),(3043,154,0,9,3,2),(3044,155,0,9,3,2),(3045,156,0,9,3,2),(3046,157,0,9,3,2),(3047,158,0,9,3,2),(3048,159,0,9,3,2),(3049,160,0,9,3,2),(3050,161,0,9,3,2),(3051,162,0,9,3,2),(3052,163,0,9,3,2),(3053,164,0,9,3,2),(3054,165,0,9,3,2),(3055,166,0,9,3,2),(3056,167,0,9,3,2),(3057,168,0,9,3,2),(3058,169,0,9,3,2),(3059,170,0,9,3,2),(3060,171,0,9,3,2),(3061,172,0,9,3,2),(3062,173,0,9,3,2),(3063,174,0,9,3,2),(3064,175,0,9,3,2),(3065,176,0,9,3,2),(3066,177,0,9,3,2),(3067,178,0,9,3,2),(3068,179,0,9,3,2),(3069,180,0,9,3,2),(3070,181,0,9,3,2),(3071,182,0,9,3,2),(3072,183,0,9,3,2),(3073,184,0,9,3,2),(3074,185,0,9,3,2),(3075,187,0,9,3,2),(3076,188,0,9,3,2),(3077,190,0,9,3,2),(3078,192,0,9,3,2),(3079,200,0,9,3,2),(3080,186,0,9,3,2),(3081,189,0,9,3,2),(3082,193,0,9,3,2),(3083,195,0,9,3,2),(3084,197,0,9,3,2),(3085,436,0,9,3,2),(3086,201,0,9,3,2),(3087,202,0,9,3,2),(3088,203,0,9,3,2),(3089,204,0,9,3,2),(3090,205,0,9,3,2),(3091,206,0,9,3,2),(3092,207,0,9,3,2),(3093,208,0,9,3,2),(3094,209,0,9,3,2),(3095,346,0,9,3,2),(3096,347,0,9,3,2),(3097,348,0,9,3,2),(3098,349,0,9,3,2),(3099,350,0,9,3,2),(3100,437,0,9,3,2),(3101,355,0,9,3,2),(3102,356,0,9,3,2),(3103,357,0,9,3,2),(3104,358,0,9,3,2),(3105,488,0,9,3,2),(3106,489,0,9,3,2),(3107,490,0,9,3,2),(3108,491,0,9,3,2),(3109,492,0,9,3,2),(3110,484,0,9,3,2),(3111,485,0,9,3,2),(3112,486,0,9,3,2),(3113,487,0,9,3,2),(3114,500,0,9,3,2),(3115,501,0,9,3,2),(3116,493,0,9,3,2),(3117,494,0,9,3,2),(3118,495,0,9,3,2),(3119,496,0,9,3,2),(3120,497,0,9,3,2),(3121,498,0,9,3,2),(3122,499,0,9,3,2),(3123,516,0,9,3,2),(3124,517,0,9,3,2),(3125,518,0,9,3,2),(3126,519,0,9,3,2),(3127,520,0,9,3,2),(3128,521,0,9,3,2),(3129,22,0,12,3,0),(3130,23,0,12,3,0),(3131,24,0,12,3,0),(3132,25,0,12,3,0),(3133,31,0,12,3,0),(3134,29,0,12,3,0),(3135,21,0,12,3,0),(3136,30,0,12,3,0),(3137,68,0,12,3,1),(3138,69,0,12,3,1),(3139,70,0,12,3,1),(3140,71,0,12,3,1),(3141,72,0,12,3,1),(3142,74,0,12,3,1),(3143,75,0,12,3,1),(3144,76,0,12,3,1),(3145,195,0,12,3,1),(3146,196,0,12,3,1),(3147,84,0,12,3,1),(3148,85,0,12,3,1),(3149,88,0,12,3,1),(3150,89,0,12,3,1),(3151,90,0,12,3,1),(3152,91,0,12,3,1),(3153,92,0,12,3,1),(3154,93,0,12,3,1),(3155,208,0,12,3,1),(3156,217,0,12,3,1),(3157,220,0,12,3,1),(3158,99,0,12,3,1),(3159,109,0,12,3,1),(3160,110,0,12,3,1),(3161,115,0,12,3,1),(3162,215,0,12,3,1),(3163,230,0,12,3,1),(3164,232,0,12,3,1),(3165,234,0,12,3,1),(3166,235,0,12,3,1),(3167,236,0,12,3,1),(3168,238,0,12,3,1),(3169,237,0,12,3,1),(3170,133,0,12,3,1),(3171,137,0,12,3,1),(3172,134,0,12,3,1),(3173,138,0,12,3,1),(3174,135,0,12,3,1),(3175,139,0,12,3,1),(3176,136,0,12,3,1),(3177,140,0,12,3,1),(3178,141,0,12,3,1),(3179,142,0,12,3,1),(3180,143,0,12,3,1),(3181,144,0,12,3,1),(3182,145,0,12,3,1),(3183,201,0,12,3,1),(3184,146,0,12,3,1),(3185,150,0,12,3,1),(3186,151,0,12,3,1),(3187,152,0,12,3,1),(3188,154,0,12,3,1),(3189,209,0,12,3,1),(3190,210,0,12,3,1),(3191,225,0,12,3,1),(3192,226,0,12,3,1),(3193,61,0,12,3,1),(3194,62,0,12,3,1),(3195,64,0,12,3,1),(3196,86,0,12,3,1),(3197,87,0,12,3,1),(3198,97,0,12,3,1),(3199,100,0,12,3,1),(3200,104,0,12,3,1),(3201,107,0,12,3,1),(3202,108,0,12,3,1),(3203,111,0,12,3,1),(3204,202,0,12,3,1),(3205,107,0,12,3,2),(3206,108,0,12,3,2),(3207,109,0,12,3,2),(3208,110,0,12,3,2),(3209,111,0,12,3,2),(3210,113,0,12,3,2),(3211,114,0,12,3,2),(3212,116,0,12,3,2),(3213,117,0,12,3,2),(3214,118,0,12,3,2),(3215,119,0,12,3,2),(3216,120,0,12,3,2),(3217,121,0,12,3,2),(3218,122,0,12,3,2),(3219,124,0,12,3,2),(3220,125,0,12,3,2),(3221,127,0,12,3,2),(3222,128,0,12,3,2),(3223,129,0,12,3,2),(3224,130,0,12,3,2),(3225,131,0,12,3,2),(3226,132,0,12,3,2),(3227,133,0,12,3,2),(3228,134,0,12,3,2),(3229,135,0,12,3,2),(3230,136,0,12,3,2),(3231,137,0,12,3,2),(3232,138,0,12,3,2),(3233,139,0,12,3,2),(3234,140,0,12,3,2),(3235,141,0,12,3,2),(3236,142,0,12,3,2),(3237,143,0,12,3,2),(3238,144,0,12,3,2),(3239,145,0,12,3,2),(3240,146,0,12,3,2),(3241,147,0,12,3,2),(3242,148,0,12,3,2),(3243,149,0,12,3,2),(3244,150,0,12,3,2),(3245,151,0,12,3,2),(3246,152,0,12,3,2),(3247,153,0,12,3,2),(3248,513,0,12,3,2),(3249,154,0,12,3,2),(3250,155,0,12,3,2),(3251,156,0,12,3,2),(3252,514,0,12,3,2),(3253,157,0,12,3,2),(3254,158,0,12,3,2),(3255,159,0,12,3,2),(3256,160,0,12,3,2),(3257,161,0,12,3,2),(3258,162,0,12,3,2),(3259,163,0,12,3,2),(3260,164,0,12,3,2),(3261,165,0,12,3,2),(3262,515,0,12,3,2),(3263,166,0,12,3,2),(3264,167,0,12,3,2),(3265,168,0,12,3,2),(3266,169,0,12,3,2),(3267,170,0,12,3,2),(3268,171,0,12,3,2),(3269,172,0,12,3,2),(3270,173,0,12,3,2),(3271,174,0,12,3,2),(3272,175,0,12,3,2),(3273,176,0,12,3,2),(3274,177,0,12,3,2),(3275,178,0,12,3,2),(3276,179,0,12,3,2),(3277,180,0,12,3,2),(3278,181,0,12,3,2),(3279,182,0,12,3,2),(3280,183,0,12,3,2),(3281,184,0,12,3,2),(3282,185,0,12,3,2),(3283,187,0,12,3,2),(3284,188,0,12,3,2),(3285,190,0,12,3,2),(3286,192,0,12,3,2),(3287,200,0,12,3,2),(3288,186,0,12,3,2),(3289,189,0,12,3,2),(3290,193,0,12,3,2),(3291,195,0,12,3,2),(3292,197,0,12,3,2),(3293,436,0,12,3,2),(3294,201,0,12,3,2),(3295,202,0,12,3,2),(3296,203,0,12,3,2),(3297,204,0,12,3,2),(3298,205,0,12,3,2),(3299,206,0,12,3,2),(3300,207,0,12,3,2),(3301,208,0,12,3,2),(3302,209,0,12,3,2),(3303,346,0,12,3,2),(3304,347,0,12,3,2),(3305,348,0,12,3,2),(3306,349,0,12,3,2),(3307,350,0,12,3,2),(3308,437,0,12,3,2),(3309,355,0,12,3,2),(3310,356,0,12,3,2),(3311,357,0,12,3,2),(3312,358,0,12,3,2),(3313,488,0,12,3,2),(3314,489,0,12,3,2),(3315,490,0,12,3,2),(3316,491,0,12,3,2),(3317,492,0,12,3,2),(3318,484,0,12,3,2),(3319,485,0,12,3,2),(3320,486,0,12,3,2),(3321,487,0,12,3,2),(3322,500,0,12,3,2),(3323,501,0,12,3,2),(3324,493,0,12,3,2),(3325,494,0,12,3,2),(3326,495,0,12,3,2),(3327,496,0,12,3,2),(3328,497,0,12,3,2),(3329,498,0,12,3,2),(3330,499,0,12,3,2),(3331,516,0,12,3,2),(3332,517,0,12,3,2),(3333,518,0,12,3,2),(3334,519,0,12,3,2),(3335,520,0,12,3,2),(3336,521,0,12,3,2),(3337,522,0,12,3,2),(3338,225,0,12,3,2),(3339,226,0,12,3,2),(3340,227,0,12,3,2),(3341,228,0,12,3,2),(3342,229,0,12,3,2),(3343,230,0,12,3,2),(3344,231,0,12,3,2),(3345,232,0,12,3,2),(3346,233,0,12,3,2),(3347,234,0,12,3,2),(3348,235,0,12,3,2),(3349,236,0,12,3,2),(3350,237,0,12,3,2),(3351,238,0,12,3,2),(3352,239,0,12,3,2),(3353,240,0,12,3,2),(3354,241,0,12,3,2),(3355,242,0,12,3,2),(3356,243,0,12,3,2),(3357,244,0,12,3,2),(3358,245,0,12,3,2),(3359,246,0,12,3,2),(3360,247,0,12,3,2),(3361,248,0,12,3,2),(3362,249,0,12,3,2),(3363,250,0,12,3,2),(3364,251,0,12,3,2),(3365,252,0,12,3,2),(3366,253,0,12,3,2),(3367,254,0,12,3,2),(3368,255,0,12,3,2),(3369,256,0,12,3,2),(3370,257,0,12,3,2),(3371,258,0,12,3,2),(3372,259,0,12,3,2),(3373,260,0,12,3,2),(3374,261,0,12,3,2),(3375,262,0,12,3,2),(3376,263,0,12,3,2),(3377,264,0,12,3,2),(3378,265,0,12,3,2),(3379,266,0,12,3,2),(3380,267,0,12,3,2),(3381,268,0,12,3,2),(3382,269,0,12,3,2),(3383,270,0,12,3,2),(3384,271,0,12,3,2),(3385,272,0,12,3,2),(3386,273,0,12,3,2),(3387,274,0,12,3,2),(3388,275,0,12,3,2),(3389,278,0,12,3,2),(3390,279,0,12,3,2),(3391,280,0,12,3,2),(3392,281,0,12,3,2),(3393,282,0,12,3,2),(3394,444,0,12,3,2),(3395,445,0,12,3,2),(3396,438,0,12,3,2),(3397,439,0,12,3,2),(3398,440,0,12,3,2),(3399,441,0,12,3,2),(3400,442,0,12,3,2),(3401,443,0,12,3,2),(3402,423,0,12,3,2),(3403,424,0,12,3,2),(3404,425,0,12,3,2),(3405,426,0,12,3,2),(3406,427,0,12,3,2),(3407,428,0,12,3,2),(3408,429,0,12,3,2),(3409,430,0,12,3,2),(3410,431,0,12,3,2),(3411,432,0,12,3,2),(3412,433,0,12,3,2),(3413,434,0,12,3,2),(3414,435,0,12,3,2),(3415,459,0,12,3,2),(3416,460,0,12,3,2),(3417,461,0,12,3,2),(3418,462,0,12,3,2),(3419,463,0,12,3,2),(3420,464,0,12,3,2),(3421,453,0,12,3,2),(3422,454,0,12,3,2),(3423,455,0,12,3,2),(3424,456,0,12,3,2),(3425,457,0,12,3,2),(3426,458,0,12,3,2),(3427,465,0,12,3,2),(3428,466,0,12,3,2),(3429,467,0,12,3,2),(3430,468,0,12,3,2),(3431,469,0,12,3,2),(3432,19,0,12,1,0),(3433,49,0,12,1,1),(3434,50,0,12,1,1),(3435,56,0,12,1,1),(3436,58,0,12,1,1),(3437,59,0,12,1,1),(3438,60,0,12,1,1),(3439,73,0,12,1,1),(3440,81,0,12,1,1),(3441,101,0,12,1,1),(3442,112,0,12,1,1),(3443,1,0,12,1,2),(3444,2,0,12,1,2),(3445,3,0,12,1,2),(3446,4,0,12,1,2),(3447,5,0,12,1,2),(3448,6,0,12,1,2),(3449,16,0,12,1,2),(3450,17,0,12,1,2),(3451,19,0,12,1,2),(3452,20,0,12,1,2),(3453,383,0,12,1,2),(3454,384,0,12,1,2),(3455,385,0,12,1,2),(3456,386,0,12,1,2),(3457,387,0,12,1,2),(3458,388,0,12,1,2),(3459,389,0,12,1,2),(3460,390,0,12,1,2),(3461,363,0,12,1,2),(3462,376,0,12,1,2),(3463,377,0,12,1,2),(3464,98,0,12,1,2),(3465,99,0,12,1,2),(3466,100,0,12,1,2),(3467,101,0,12,1,2),(3468,102,0,12,1,2),(3469,103,0,12,1,2),(3470,104,0,12,1,2),(3471,105,0,12,1,2),(3472,106,0,12,1,2),(3473,397,0,12,1,2),(3474,398,0,12,1,2),(3475,399,0,12,1,2),(3476,400,0,12,1,2),(3477,401,0,12,1,2),(3478,402,0,12,1,2),(3479,42,0,12,1,2),(3480,43,0,12,1,2),(3481,44,0,12,1,2),(3482,45,0,12,1,2),(3483,46,0,12,1,2),(3484,47,0,12,1,2),(3485,48,0,12,1,2),(3486,49,0,12,1,2),(3487,50,0,12,1,2),(3488,51,0,12,1,2),(3489,52,0,12,1,2),(3490,53,0,12,1,2),(3491,54,0,12,1,2),(3492,55,0,12,1,2),(3493,56,0,12,1,2),(3494,57,0,12,1,2),(3495,58,0,12,1,2),(3496,59,0,12,1,2),(3497,60,0,12,1,2),(3498,61,0,12,1,2),(3499,67,0,12,1,2);
/*!40000 ALTER TABLE `ts_right` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_role`
--

DROP TABLE IF EXISTS `ts_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_role`
--

LOCK TABLES `ts_role` WRITE;
/*!40000 ALTER TABLE `ts_role` DISABLE KEYS */;
INSERT INTO `ts_role` VALUES (1,'2013-11-27 16:00:00',2,8,NULL,1,2,'2013-11-27 16:00:00',-1,NULL,'R2013112804214300','董事长'),(2,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:31',-1,NULL,'R2013112804215102','总经理'),(3,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:31',-1,NULL,'R2013112804215704','副总经理(风控)'),(4,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:31',-1,NULL,'R2013112804223606','总经理助理'),(5,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:31',-1,NULL,'R2013112804225608','副总经理(财务)'),(6,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:31',-1,NULL,'R2013112804231510','客户经理'),(7,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:31',-1,NULL,'R2013112804233612','风控经理'),(8,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:31',-1,NULL,'R2013112804234314','综合部经理'),(9,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:31',-1,NULL,'R2013112804240716','会计'),(10,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:31',-1,NULL,'R2013112804241818','出纳'),(11,'2013-11-27 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:31',-1,NULL,'R2013112804242320','综合文员'),(12,'2014-04-01 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,NULL,'R2014040204382322','演示账户');
/*!40000 ALTER TABLE `ts_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_rolemod`
--

DROP TABLE IF EXISTS `ts_rolemod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_rolemod` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `modCode` varchar(255) DEFAULT NULL,
  `roleId` double DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_rolemod`
--

LOCK TABLES `ts_rolemod` WRITE;
/*!40000 ALTER TABLE `ts_rolemod` DISABLE KEYS */;
INSERT INTO `ts_rolemod` VALUES (26,'DESK_MOD_FIN_1',6,3),(27,'DESK_MOD_FIIN_2',6,3),(28,'DESK_MOD_FIN_12',6,3),(29,'DESK_MOD_FIN_9',6,3),(36,'DESK_MOD_FIN_1',10,3),(37,'DESK_MOD_FIIN_2',10,3),(38,'DESK_MOD_FIN_5',10,3),(39,'DESK_MOD_FIN_6',10,3),(40,'DESK_MOD_FIN_7',10,3),(41,'DESK_MOD_FIN_8',10,3),(49,'DESK_MOD_FIN_1',9,3),(50,'DESK_MOD_FIIN_2',9,3),(51,'DESK_MOD_FIN_5',9,3),(52,'DESK_MOD_FIN_6',9,3),(53,'DESK_MOD_FIN_1',5,3),(54,'DESK_MOD_FIIN_2',5,3),(55,'DESK_MOD_FIN_5',5,3),(56,'DESK_MOD_FIN_6',5,3),(57,'DESK_MOD_FIN_1',3,3),(58,'DESK_MOD_FIIN_2',3,3),(59,'DESK_MOD_FIN_12',3,3),(60,'DESK_MOD_FIN_6',3,3),(61,'DESK_MOD_FIN_9',3,3),(62,'DESK_MOD_FIN_1',2,3),(63,'DESK_MOD_FIIN_2',2,3),(64,'DESK_MOD_FIN_5',2,3),(65,'DESK_MOD_FIN_12',2,3),(66,'DESK_MOD_FIN_6',2,3),(67,'DESK_MOD_FIN_9',2,3),(68,'DESK_MOD_FIN_1',1,3),(69,'DESK_MOD_FIIN_2',1,3),(70,'DESK_MOD_FIN_5',1,3),(71,'DESK_MOD_FIN_12',1,3),(72,'DESK_MOD_FIN_6',1,3),(73,'DESK_MOD_FIN_7',1,3),(74,'DESK_MOD_FIN_8',1,3),(75,'DESK_MOD_FIN_9',1,3);
/*!40000 ALTER TABLE `ts_rolemod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_sysparams`
--

DROP TABLE IF EXISTS `ts_sysparams`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_sysparams` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `recode` varchar(50) DEFAULT NULL,
  `sysid` double DEFAULT NULL,
  `val` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_sysparams`
--

LOCK TABLES `ts_sysparams` WRITE;
/*!40000 ALTER TABLE `ts_sysparams` DISABLE KEYS */;
INSERT INTO `ts_sysparams` VALUES (3,'2013-10-28 16:00:00',2,8,NULL,0,2,'2013-11-19 16:00:00',-1,'当应还款日大于当期日期时,自动将还款计划转为逾期数据期日期时,自动将还款计划转为逾期数据。逾期.0至24：则在指定的小时自动将符合条件的客户转为期；例如：如设定为2(则表示在凌晨2点)系统自动执行转逾期业务功能','自动转逾期','OVER_TO_LATE_HOUR',3,'2'),(4,'2013-10-28 16:00:00',2,8,NULL,1,2,'2013-11-19 16:00:00',-1,'系统每天计算罚息和滞纳金的时间。\n取值范围：0-24小时','自动计算罚息滞纳金时间','CACLUTE_PEN_DEL_HOUR',3,'3'),(5,'2013-10-28 16:00:00',2,8,NULL,1,2,'2013-11-19 16:00:00',-1,'当罚息金额不足30元时，按30元收取','最低罚息金额','MIN_PEN_AMOUNT',3,'30'),(6,'2013-10-28 16:00:00',2,8,NULL,1,2,'2013-11-19 16:00:00',-1,'当滞纳金不足30元时，按30元收取','最低滞纳金','MIN_DEL_AMOUNT',3,'30'),(7,'2013-10-28 16:00:00',2,8,NULL,1,2,'2013-11-19 16:00:00',-1,'当系统自动计算罚息和滞纳金时，每次批量提取逾期数据的条数 （没用到）','批量提取逾期数据条数','BATCH_LATE_DATAS_COUNT',3,'1000'),(8,'2013-10-28 16:00:00',2,8,NULL,1,2,'2013-11-19 16:00:00',-1,'是否将一笔放款单作为一笔贷款投放数。取值如下：1:将一笔放款单作为一笔贷款投放笔数。0：将多张放款单作为一笔投放笔数（即：一个合同对应一笔投放数）。该参数主要用在以下功能：业务查询及统计[贷款业务结构表]','一笔放款单作为一笔投放笔数','LOAN_ONE_COUNT',3,'1'),(9,'2014-03-06 06:22:58',2,8,NULL,1,2,'2014-03-05 16:00:00',-1,'借款合同类型:\n 公司规定的结算日每月10号','公司规定的结算日','PAYDAY_SET',3,'10'),(10,'2014-02-05 16:00:00',2,8,NULL,1,2,'2014-01-11 16:00:00',-1,'1:算头又算尾,\n2:算头不算尾 ','随借随还利息头尾计算方式','RANDOM_SE_TYPE',3,'1'),(11,'2014-01-26 16:00:00',2,8,-1,1,NULL,'2014-02-21 07:31:32',-1,'1:按银行模式即全部按天算,\n2:从上一结息日到随借随还收款日期按天算,之前如果足月按月算\n3:以还款计划表为标准（即根据还款计划表中的应收利息和管理费作为参考值；如果使用该参数，系统不会重新生成还款计划）','随借随还利息算法','RANDOM_ALGORITHM',3,'3'),(12,'2014-02-27 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,'1:全部按天算,\n2:头尾如果小于30天就按天算；否则按月算','利息计算方式','INTEREST_CAL_TYPE',3,'1'),(13,'2014-03-07 09:22:08',2,8,NULL,1,2,'2014-03-06 16:00:00',-1,'1:单利，\n2:复利\n<br/>\n计算公式：\n[单利]罚息=(应收本金+应收利息)*罚息利率*逾期天数。<br/>\n[复利]罚息=应收本金或应收利息 * (1+罚息率/30)^n','罚息计算方式','PENAMOUNT_CAL_TYPE',3,'2'),(14,'2014-03-10 11:38:46',2,8,NULL,1,2,'2014-03-09 16:00:00',-1,'1:启用管理费,\n2:禁用管理费','启用管理费','ENABLE_MGRAMOUNT',3,'1'),(15,'2014-03-10 11:38:38',2,8,NULL,1,2,'2014-03-09 16:00:00',-1,'1:启用放款手续费,\n2:禁用放款手续费','启用放款手续费','ENABLE_PAY_FREEAMOUNT',3,'1'),(16,'2014-03-10 11:38:32',2,8,NULL,1,2,'2014-03-09 16:00:00',-1,'1:启用提前还款手续费,\n2:禁用提前还款手续费','启用提前还款手续费','ENABLE_BEFORE_AMOUNT',3,'1'),(17,'2014-03-10 11:37:57',2,8,NULL,1,2,'2014-03-09 16:00:00',-1,'1:百分比(%),2:千分比(‰)','系统利率默认单位','RATE_DEFAULT_UNIT',3,'1'),(18,'2014-03-16 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,'1:分开算罚息,\n2:合并算罚息\n<br/>\n1:分开算罚息（即本金算本金的罚息，利息算利息的罚息）\n2:合并算罚息(即：本金加上利息之和后，来算罚息)\n','罚息计算基数','PENAMOUNT_BASENUMS',3,'1'),(19,'2014-03-16 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,'1:计算逾期利息,\n2:不计算\n<br/>\n1:如果还款计划逾期，则根据逾期本金计算逾期利息。\n2:不计算逾期本金所占用的利息\n','是否计算逾期利息','ISCALCULATE_LATEINTEREST',3,'1'),(20,'2014-03-16 16:00:00',2,8,-1,1,NULL,'0000-00-00 00:00:00',-1,'每天定时进行融资管理利息的检查和更新','融资管理利息计算','FINANCE_MANAGEMENT_TYPE',3,'6');
/*!40000 ALTER TABLE `ts_sysparams` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_sysright`
--

DROP TABLE IF EXISTS `ts_sysright`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_sysright` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `objId` double DEFAULT NULL,
  `objtype` int(11) DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_sysright`
--

LOCK TABLES `ts_sysright` WRITE;
/*!40000 ALTER TABLE `ts_sysright` DISABLE KEYS */;
INSERT INTO `ts_sysright` VALUES (1,1,0,3),(2,2,0,3),(3,3,0,3),(4,4,0,3),(6,6,0,3),(29,5,0,1),(57,11,0,3),(78,3,0,1),(85,5,0,3),(86,10,0,3),(92,9,0,3),(109,22,1,1),(110,22,1,3),(111,7,1,3),(112,4,1,3),(113,23,1,1),(114,23,1,3),(115,3,1,3),(116,11,1,3),(117,10,1,3),(118,9,1,3),(119,8,1,3),(120,13,1,3),(121,12,1,3),(122,15,1,3),(124,12,0,3),(125,12,0,1),(126,24,1,1),(127,24,1,3);
/*!40000 ALTER TABLE `ts_sysright` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_system`
--

DROP TABLE IF EXISTS `ts_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_system` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `autotime` int(11) DEFAULT NULL,
  `code` varchar(30) DEFAULT NULL,
  `hasup` int(11) DEFAULT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `lastupdate` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `mnemonic` varchar(30) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `orderNo` int(11) DEFAULT NULL,
  `synopsis` varchar(255) DEFAULT NULL,
  `systype` int(11) DEFAULT NULL,
  `typeup` int(11) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_system`
--

LOCK TABLES `ts_system` WRITE;
/*!40000 ALTER TABLE `ts_system` DISABLE KEYS */;
INSERT INTO `ts_system` VALUES (1,'2012-01-27 16:10:00',1,1,NULL,1,2,'2013-09-04 16:00:00',9999,NULL,24,'S00001',0,'images/big_icons/48x48/actions/run-build-configure-txr.png','2014-02-21 07:31:32',NULL,'系统基础平台',NULL,NULL,0,0,'MyDesktop.SysPlatformWindow'),(2,'2012-01-27 16:10:00',-1,-1,NULL,0,NULL,'2014-02-21 07:31:32',0,NULL,NULL,'S2012102807535102',0,'images/big_icons/48x48/apps/accessories-calculator-3-txr.png','2014-02-21 07:31:32',NULL,'企业协同办公系统',NULL,NULL,0,0,'MyDesktop.AppWindow'),(3,'2012-01-27 16:10:00',-1,-1,NULL,1,1,'2013-10-16 16:00:00',0,NULL,NULL,'S00003',0,'images/big_icons/48x48/apps/money.png','2014-02-21 07:31:32',NULL,'小额贷款管理系统',NULL,'xxx',0,0,'MyDesktop.AppWindow'),(4,'2012-01-07 16:11:00',-1,-1,NULL,0,45,'2013-03-24 16:00:00',0,NULL,NULL,'S2012110808562506',0,'images/big_icons/48x48/others/animals-eagle-txr.png','2014-02-21 07:31:32',NULL,'同心日智能平台',NULL,NULL,3,1,'http://localhost:8082/smartplatform/'),(5,'2012-01-08 16:11:00',-1,-1,NULL,0,45,'2013-03-24 16:00:00',0,NULL,NULL,'S2012110909235908',0,'images/big_icons/48x48/others/animals-owl-txr.png','2014-02-21 07:31:32',NULL,'谷歌翻译',1,'xxxx',3,1,'http://fanyi.youdao.com/'),(6,'2013-07-12 16:00:00',1,-1,NULL,1,1,'2013-10-16 16:00:00',-1,NULL,NULL,'S00002',0,'images/big_icons/48x48/apps/finance_sys.png','2014-02-21 07:31:32',NULL,'财务接口',NULL,NULL,0,0,'MyDesktop.SysPlatformWindow');
/*!40000 ALTER TABLE `ts_system` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_tokenrecords`
--

DROP TABLE IF EXISTS `ts_tokenrecords`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_tokenrecords` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `bussNodeId` double DEFAULT NULL,
  `procId` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_tokenrecords`
--

LOCK TABLES `ts_tokenrecords` WRITE;
/*!40000 ALTER TABLE `ts_tokenrecords` DISABLE KEYS */;
INSERT INTO `ts_tokenrecords` VALUES (1,'2013-12-17 03:45:19',9,3,NULL,1,4,'2013-12-17 04:30:12',1,'[并行令牌完成]: {从路径[提交董事长] -->至[董事长审批]目标节点} ',89,'401',3,'MINGHUI_20131216'),(2,'2013-12-17 06:17:26',2,8,NULL,1,4,'2013-12-17 07:17:15',-1,'[并行令牌完成]: {从路径[提交董事长] -->至[董事长审批]目标节点} ',89,'501',3,'MINGHUI_20131216'),(3,'2013-12-17 07:24:18',10,3,NULL,1,23,'2013-12-18 03:29:46',1,'[并行令牌汇总]: {从路径[同意] -->至[总经理审批]目标节点} ',89,'727',2,'MINGHUI_20131216'),(4,'2013-12-17 07:35:26',2,8,NULL,1,4,'2013-12-17 07:51:41',-1,'[并行令牌完成]: {从路径[提交董事长] -->至[董事长审批]目标节点} ',89,'539',3,'MINGHUI_20131216'),(5,'2013-12-17 08:05:01',9,3,NULL,1,4,'2013-12-17 09:54:28',1,'[并行令牌完成]: {从路径[提交董事长] -->至[董事长审批]目标节点} ',89,'756',3,'MINGHUI_20131216'),(6,'2013-12-17 12:05:33',10,3,NULL,1,23,'2013-12-18 02:55:51',1,'[并行令牌汇总]: {从路径[同意] -->至[总经理审批]目标节点} ',89,'839',2,'MINGHUI_20131216'),(7,'2013-12-17 12:17:31',10,3,NULL,1,NULL,'2014-02-21 07:31:32',1,'[并行令牌开始]: {从路径[财务审批] -->至[财务部审批]目标节点} ',89,'848',1,'MINGHUI_20131216'),(8,'2013-12-17 12:37:04',10,3,NULL,1,4,'2013-12-17 12:52:19',1,'[并行令牌完成]: {从路径[提交董事长] -->至[董事长审批]目标节点} ',89,'1064',3,'MINGHUI_20131216'),(9,'2013-12-17 13:25:33',10,3,NULL,1,7,'2013-12-17 13:28:47',1,'[并行令牌汇总]: {从路径[同意] -->至[总经理审批]目标节点} ',89,'1214',2,'MINGHUI_20131216'),(10,'2013-12-18 01:59:04',8,3,NULL,1,NULL,'2014-02-21 07:31:32',1,'[并行令牌开始]: {从路径[财务审批] -->至[财务部审批]目标节点} ',89,'1301',1,'MINGHUI_20131216'),(11,'2013-12-19 12:13:28',10,3,NULL,1,4,'2013-12-19 14:01:56',1,'[并行令牌完成]: {从路径[提交董事长] -->至[董事长审批]目标节点} ',89,'2201',3,'MINGHUI_20131216'),(12,'2013-12-20 13:02:44',10,3,NULL,1,7,'2013-12-20 13:05:48',1,'[并行令牌汇总]: {从路径[同意] -->至[总经理审批]目标节点} ',89,'2922',3,'MINGHUI_20131216');
/*!40000 ALTER TABLE `ts_tokenrecords` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_transcfg`
--

DROP TABLE IF EXISTS `ts_transcfg`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_transcfg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `acType` int(11) DEFAULT NULL,
  `actorIds` varchar(150) DEFAULT NULL,
  `limitVals` varchar(150) DEFAULT NULL,
  `nodeId` double DEFAULT NULL,
  `stint` int(11) DEFAULT NULL,
  `tagWay` int(11) DEFAULT NULL,
  `token` varchar(50) DEFAULT NULL,
  `tokenType` int(11) DEFAULT NULL,
  `tpathType` int(11) DEFAULT NULL,
  `transId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_transcfg`
--

LOCK TABLES `ts_transcfg` WRITE;
/*!40000 ALTER TABLE `ts_transcfg` DISABLE KEYS */;
INSERT INTO `ts_transcfg` VALUES (2,'2013-12-16 08:03:26',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,1,1,0,'',1,0,1),(3,'2013-12-16 08:04:04',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,2,1,0,'',1,0,4),(4,'2013-12-16 08:04:04',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,2,1,0,'',1,0,3),(5,'2013-12-16 08:04:04',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'4',NULL,2,1,0,'',1,0,2),(8,'2013-12-16 08:04:29',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,3,1,0,'',1,0,17),(9,'2013-12-16 08:04:29',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,3,1,0,'',1,0,16),(12,'2013-12-16 08:09:23',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,4,1,0,'',1,0,6),(13,'2013-12-16 08:09:23',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'2',NULL,4,1,0,'',1,0,5),(21,'2013-12-16 08:24:59',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,8,1,0,'',1,0,48),(22,'2013-12-16 08:24:59',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'2',NULL,8,1,0,'',1,0,47),(23,'2013-12-16 08:26:11',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,9,1,0,'',1,0,52),(24,'2013-12-16 08:26:11',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,9,1,0,'',1,0,51),(25,'2013-12-16 08:26:11',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,9,1,0,'',1,0,50),(26,'2013-12-16 08:26:11',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'1',NULL,9,1,0,'',1,0,49),(27,'2013-12-16 08:26:59',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,10,1,0,'',1,0,55),(28,'2013-12-16 08:26:59',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,10,1,0,'',1,0,54),(29,'2013-12-16 08:26:59',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,10,1,0,'',1,0,53),(33,'2013-12-16 08:28:42',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'9',NULL,13,1,0,'',1,0,56),(34,'2013-12-16 08:28:59',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,14,1,0,'',1,0,62),(35,'2013-12-16 08:29:20',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,15,1,0,'',1,0,57),(36,'2013-12-16 08:29:33',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,16,1,0,'',1,0,61),(37,'2013-12-16 08:30:28',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,17,1,0,'',1,0,65),(38,'2013-12-16 08:31:37',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'4,3,2',NULL,18,1,0,'',1,0,68),(39,'2013-12-16 08:31:37',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,18,1,0,'',1,0,67),(40,'2013-12-16 08:31:37',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,18,1,0,'',1,0,66),(41,'2013-12-16 08:32:33',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,19,1,0,'',1,0,79),(42,'2013-12-16 08:32:33',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,19,1,0,'',1,0,78),(50,'2013-12-16 08:36:30',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,20,1,0,'',1,0,72),(51,'2013-12-16 08:36:30',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,20,1,0,'',1,0,71),(52,'2013-12-16 08:36:30',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,20,1,0,'',1,0,70),(53,'2013-12-16 08:36:30',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'1',NULL,20,1,0,'',1,0,69),(54,'2013-12-16 08:36:39',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,21,1,0,'',1,0,75),(55,'2013-12-16 08:36:39',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,21,1,0,'',1,0,74),(56,'2013-12-16 08:36:39',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,21,1,0,'',1,0,73),(57,'2013-12-16 08:37:24',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,22,1,0,'',1,0,80),(60,'2013-12-16 08:37:48',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'9',NULL,24,1,0,'',1,0,76),(61,'2013-12-16 08:37:58',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,25,1,0,'',1,0,83),(62,'2013-12-16 08:38:07',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,26,1,0,'',1,0,77),(63,'2013-12-16 09:30:55',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,27,1,0,'MINHUI_20131216',2,0,115),(66,'2013-12-16 09:31:39',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,28,1,0,'',1,0,117),(67,'2013-12-16 09:31:39',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,28,1,0,'',1,0,116),(68,'2013-12-16 09:54:21',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,29,1,1,'',1,0,121),(69,'2013-12-16 09:54:21',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,29,1,0,'',1,0,120),(70,'2013-12-16 09:55:54',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,30,1,1,'',1,0,139),(71,'2013-12-16 09:55:54',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,30,1,1,'',1,0,138),(72,'2013-12-16 09:55:54',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,30,1,0,'',1,0,137),(73,'2013-12-16 09:57:14',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'2',NULL,31,1,1,'MINHUI_20131216',3,0,125),(74,'2013-12-16 09:57:14',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,31,1,0,'',1,0,124),(76,'2013-12-16 10:10:00',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,32,1,0,'MINGHUI_20131216',2,0,144),(79,'2013-12-16 11:40:29',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,33,1,0,'',1,0,146),(80,'2013-12-16 11:40:29',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,33,1,0,'',1,0,145),(90,'2013-12-16 11:46:04',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,37,1,0,'',1,0,169),(91,'2013-12-16 11:49:06',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,38,1,0,'',1,0,165),(92,'2013-12-16 11:50:39',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,39,1,0,'',1,0,161),(93,'2013-12-16 11:50:39',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,39,1,1,'',1,2,160),(94,'2013-12-16 11:50:39',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,39,1,0,'',1,0,159),(99,'2013-12-16 11:51:47',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'9',NULL,42,1,0,'',1,0,163),(100,'2013-12-16 11:51:56',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,43,1,0,'',1,0,172),(101,'2013-12-16 11:52:02',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,44,1,0,'',1,0,162),(103,'2013-12-16 12:00:34',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,45,1,0,'',1,0,202),(104,'2013-12-16 12:01:03',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,46,1,0,'',1,0,205),(105,'2013-12-16 12:01:03',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,46,1,0,'',1,0,204),(106,'2013-12-16 12:01:03',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'4',NULL,46,1,0,'',1,0,203),(107,'2013-12-16 12:01:33',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,47,1,0,'',1,0,217),(108,'2013-12-16 12:04:48',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,48,1,0,'',1,0,208),(109,'2013-12-16 12:04:48',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,48,1,0,'',1,0,207),(110,'2013-12-16 12:04:48',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'2',NULL,48,1,0,'',1,0,206),(111,'2013-12-16 12:05:53',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,49,1,0,'',1,0,211),(112,'2013-12-16 12:05:53',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'1',NULL,49,1,0,'',1,0,209),(113,'2013-12-16 12:05:53',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,49,1,0,'',1,0,210),(114,'2013-12-16 12:06:00',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,50,1,0,'',1,0,216),(115,'2013-12-16 12:06:31',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,51,1,0,'',1,0,214),(116,'2013-12-16 12:06:31',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,51,1,0,'',1,0,213),(117,'2013-12-16 12:06:31',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,51,1,0,'',1,0,212),(119,'2013-12-16 12:07:04',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,52,1,0,'',1,0,215),(120,'2013-12-16 12:08:52',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,53,1,0,'',1,0,189),(121,'2013-12-16 12:14:53',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'2,3,4',NULL,54,1,0,'',1,0,192),(122,'2013-12-16 12:14:53',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,54,1,0,'',1,0,191),(123,'2013-12-16 12:14:53',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,54,1,0,'',1,0,190),(124,'2013-12-16 12:15:15',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,55,1,0,'',1,0,201),(125,'2013-12-16 12:16:19',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,56,1,0,'',1,0,195),(126,'2013-12-16 12:16:19',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,56,1,0,'',1,0,194),(127,'2013-12-16 12:16:19',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'1',NULL,56,1,0,'',1,0,193),(128,'2013-12-16 12:16:37',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,57,1,0,'',1,0,197),(129,'2013-12-16 12:16:37',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,57,1,0,'',1,0,196),(130,'2013-12-16 12:16:37',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,57,1,0,'',1,0,198),(131,'2013-12-16 12:16:43',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,58,1,0,'',1,0,200),(132,'2013-12-16 12:17:00',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,59,1,0,'',1,0,199),(144,'2013-12-17 03:11:23',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,63,1,0,'',1,0,84),(163,'2013-12-18 06:29:46',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'3',NULL,5,1,0,'',1,0,43),(164,'2013-12-18 06:29:52',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,6,1,0,'',1,0,46),(165,'2013-12-18 06:29:52',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,6,1,0,'',1,0,45),(166,'2013-12-18 06:29:52',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'4',NULL,6,1,0,'',1,0,44),(167,'2013-12-18 06:29:59',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,7,1,0,'',1,0,59),(168,'2013-12-18 06:29:59',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,7,1,0,'',1,0,58),(170,'2020-12-18 04:01:18',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,11,1,0,'',1,0,60),(171,'2014-03-15 08:52:56',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,35,1,1,'',1,0,148),(172,'2014-03-15 08:52:56',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'4',NULL,35,1,0,'',1,0,147),(173,'2014-03-15 08:53:13',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,34,1,1,'',1,0,150),(174,'2014-03-15 08:53:13',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,34,1,0,'',1,0,149),(175,'2014-03-15 08:53:22',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,36,1,1,'',1,0,168),(176,'2014-03-15 08:53:22',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,36,1,1,'',1,0,167),(177,'2014-03-15 08:53:22',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,36,1,0,'',1,0,166),(178,'2014-03-15 08:53:55',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'2',NULL,60,1,1,'MINGHUI_20131216',3,0,154),(179,'2014-03-15 08:53:55',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,60,1,0,'',1,0,153),(180,'2014-03-15 08:54:05',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'2',NULL,61,1,0,'MINGHUI_20131216',3,0,152),(181,'2014-03-15 08:54:05',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'',NULL,61,1,0,'',1,0,151),(182,'2014-03-15 08:54:14',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,62,1,0,'',1,0,158),(183,'2014-03-15 08:54:14',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'','',62,4,1,'',1,0,157),(184,'2014-03-15 08:54:14',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,3,'','',62,4,1,'',1,0,156),(185,'2014-03-15 08:54:14',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'1',NULL,62,1,0,'MINGHUI_20131216',4,0,155),(186,'2013-12-20 10:32:38',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,40,1,0,'',1,0,164),(189,'2013-12-20 12:15:07',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,12,1,0,'',1,0,64),(190,'2013-12-20 12:15:07',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'10',NULL,12,1,0,'',1,0,63),(191,'2013-12-20 12:16:21',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,23,1,0,'',1,0,82),(192,'2013-12-20 12:16:21',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'10',NULL,23,1,0,'',1,0,81),(197,'2013-12-20 13:16:30',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,5,'',NULL,41,1,0,'',1,0,171),(198,'2013-12-20 13:16:30',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'10',NULL,41,1,0,'',1,0,170),(199,'2013-12-24 05:06:10',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,64,1,0,'',1,0,276),(200,'2013-12-24 05:06:40',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,65,1,0,'',1,0,277),(201,'2013-12-25 04:06:42',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,66,1,0,'',1,0,278),(202,'2013-12-25 04:07:10',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,67,1,0,'',1,0,279),(203,'2013-12-25 04:08:42',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,68,1,0,'',1,0,280),(206,'2013-12-25 04:10:57',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,69,1,0,'',1,0,281),(207,'2013-12-25 04:12:13',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,70,1,0,'',1,0,282),(208,'2013-12-25 04:12:44',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,71,1,0,'',1,0,283),(209,'2013-12-25 04:14:11',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,72,1,0,'',1,0,284),(210,'2013-12-25 04:14:46',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,73,1,0,'',1,0,285),(211,'2013-12-25 04:16:20',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,74,1,0,'',1,0,286),(212,'2013-12-25 04:16:38',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,75,1,0,'',1,0,287),(213,'2014-01-13 02:00:03',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,76,1,0,'',1,0,288),(214,'2014-01-13 02:00:30',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'10',NULL,77,1,0,'',1,0,289),(215,'2014-01-13 02:00:37',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,78,1,0,'',1,0,290),(216,'2014-01-13 02:01:44',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,79,1,0,'',1,0,291),(217,'2014-01-13 02:02:14',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'10',NULL,80,1,0,'',1,0,292),(218,'2014-01-13 02:02:50',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,81,1,0,'',1,0,293),(219,'2014-01-13 02:04:02',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,82,1,0,'',1,0,294),(220,'2014-01-13 02:04:32',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'10',NULL,83,1,0,'',1,0,295),(221,'2014-01-13 02:04:40',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,84,1,0,'',1,0,296),(222,'2014-01-13 02:05:44',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,85,1,0,'',1,0,297),(223,'2014-01-13 02:06:02',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'10',NULL,86,1,0,'',1,0,298),(224,'2014-01-13 02:06:09',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,87,1,0,'',1,0,299),(226,'2014-01-13 02:07:12',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,88,1,0,'',1,0,300),(227,'2014-01-13 02:07:56',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'10',NULL,89,1,0,'',1,0,301),(228,'2014-01-13 02:08:02',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,90,1,0,'',1,0,302),(229,'2014-01-13 02:09:08',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'5',NULL,91,1,0,'',1,0,303),(230,'2014-01-13 02:09:30',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,1,'10',NULL,92,1,0,'',1,0,304),(231,'2014-01-13 02:09:44',2,8,NULL,1,NULL,'2014-02-21 07:31:32',-1,NULL,0,'',NULL,93,1,0,'',1,0,305),(233,'2014-03-06 03:42:17',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,0,'',NULL,95,1,0,'',1,0,307),(234,'2014-03-06 03:57:11',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,0,'',NULL,96,1,0,'TXR_BINXIN_20140306_01',2,0,308),(235,'2014-03-06 04:08:37',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,2,'23',NULL,94,1,0,'',1,0,306),(236,'2014-03-10 14:07:38',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,2,'13',NULL,97,1,0,'',1,0,337),(237,'2014-03-10 14:08:30',2,8,NULL,1,NULL,'0000-00-00 00:00:00',-1,NULL,0,'',NULL,98,1,0,'',1,0,338);
/*!40000 ALTER TABLE `ts_transcfg` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_urole`
--

DROP TABLE IF EXISTS `ts_urole`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_urole` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` double DEFAULT NULL,
  `userId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_urole`
--

LOCK TABLES `ts_urole` WRITE;
/*!40000 ALTER TABLE `ts_urole` DISABLE KEYS */;
INSERT INTO `ts_urole` VALUES (83,3,22),(84,4,7),(85,2,4),(86,5,23),(87,1,3),(88,6,11),(89,6,10),(90,6,9),(91,6,8),(92,10,13),(93,9,12),(94,11,15),(97,12,24);
/*!40000 ALTER TABLE `ts_urole` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_user`
--

DROP TABLE IF EXISTS `ts_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` bigint(20) DEFAULT NULL,
  `deptId` bigint(20) DEFAULT NULL,
  `empId` bigint(20) DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` bigint(20) DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` bigint(20) DEFAULT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `accessIds` varchar(255) DEFAULT NULL,
  `dataLevel` smallint(6) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `empName` varchar(30) DEFAULT NULL,
  `indeptId` bigint(20) DEFAULT NULL,
  `inempId` bigint(20) DEFAULT NULL,
  `iplimit` varchar(100) DEFAULT NULL,
  `isSystem` int(11) DEFAULT NULL,
  `leader` bigint(20) DEFAULT NULL,
  `loglimit` int(11) DEFAULT NULL,
  `mnemonic` varchar(100) DEFAULT NULL,
  `passWord` varchar(30) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `postId` bigint(20) DEFAULT NULL,
  `pwdcycle` varchar(50) DEFAULT NULL,
  `pwdfail` int(11) DEFAULT NULL,
  `pwdtip` int(11) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `userName` varchar(30) DEFAULT NULL,
  `accessNames` longtext,
  `incompId` bigint(20) DEFAULT NULL,
  `i18n` varchar(10) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  PRIMARY KEY (`userId`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_user`
--

LOCK TABLES `ts_user` WRITE;
/*!40000 ALTER TABLE `ts_user` DISABLE KEYS */;
INSERT INTO `ts_user` VALUES (2,'2014-04-02 02:48:11',1,-1,NULL,1,2,'2013-12-26 16:00:00',-1,NULL,'',7,'','超级管理员',8,NULL,NULL,1,NULL,0,NULL,'654321',NULL,8,'30',0,0,NULL,'admin',NULL,-1,NULL,NULL,0),(3,'2014-04-02 08:23:01',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用01',1,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,1,'30',1,0,NULL,'01',NULL,1,NULL,NULL,0),(4,'2014-04-02 08:22:45',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用02',2,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,2,'30',1,0,NULL,'02',NULL,1,NULL,NULL,1),(7,'2014-04-02 08:22:39',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用03',2,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,4,'30',1,0,NULL,'03',NULL,1,NULL,NULL,0),(8,'2014-04-02 08:23:37',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用04',3,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,5,'30',1,0,NULL,'04',NULL,1,NULL,NULL,0),(9,'2014-04-02 08:23:27',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用05',3,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,5,'30',1,0,NULL,'05',NULL,1,NULL,NULL,0),(10,'2014-04-02 08:23:21',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用06',3,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,5,'30',1,0,NULL,'06',NULL,1,NULL,NULL,0),(11,'2014-04-02 08:23:16',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用07',3,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,5,'30',1,0,NULL,'07',NULL,1,NULL,NULL,0),(12,'2014-04-02 08:23:50',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用08',4,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,10,'30',1,0,NULL,'08',NULL,1,NULL,NULL,1),(13,'2014-04-02 08:23:44',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用09',4,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,9,'30',1,0,NULL,'09',NULL,1,NULL,NULL,1),(14,'2014-04-02 08:16:46',2,8,NULL,0,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用10',5,NULL,NULL,0,NULL,0,NULL,'togxinri.com',NULL,6,'30',0,0,NULL,'10',NULL,1,NULL,NULL,1),(15,'2014-04-02 08:23:57',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用11',5,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,8,'30',1,0,NULL,'11',NULL,1,NULL,NULL,1),(16,'2014-04-02 08:16:34',2,8,NULL,0,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用12',5,NULL,NULL,0,NULL,0,NULL,'togxinri.com',NULL,8,'30',0,0,NULL,'12',NULL,1,NULL,NULL,1),(17,'2014-04-02 08:16:25',2,8,NULL,0,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用13',5,NULL,NULL,0,NULL,0,NULL,'togxinri.com',NULL,8,'30',0,0,NULL,'13',NULL,1,NULL,NULL,1),(22,'2014-04-02 08:22:28',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',5,NULL,'测试用14',2,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,3,'30',1,0,NULL,'14',NULL,1,NULL,NULL,0),(23,'2014-04-02 08:22:52',2,8,NULL,1,2,'2014-04-01 16:00:00',-1,NULL,'',7,NULL,'测试用15',2,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,7,'30',1,0,NULL,'15',NULL,1,NULL,NULL,1),(24,'2014-04-04 02:55:39',2,8,NULL,1,2,'2014-04-03 16:00:00',-1,NULL,'',7,NULL,'南江',1,NULL,NULL,0,NULL,0,NULL,'tongxinri.com',NULL,1,'30',0,0,NULL,'南江小贷',NULL,1,NULL,NULL,0);
/*!40000 ALTER TABLE `ts_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_usermod`
--

DROP TABLE IF EXISTS `ts_usermod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_usermod` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `modCode` varchar(255) DEFAULT NULL,
  `userId` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_usermod`
--

LOCK TABLES `ts_usermod` WRITE;
/*!40000 ALTER TABLE `ts_usermod` DISABLE KEYS */;
/*!40000 ALTER TABLE `ts_usermod` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ts_variety`
--

DROP TABLE IF EXISTS `ts_variety`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `ts_variety` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `creator` double DEFAULT NULL,
  `deptId` double DEFAULT NULL,
  `empId` double DEFAULT NULL,
  `isenabled` smallint(6) DEFAULT NULL,
  `modifier` double DEFAULT NULL,
  `modifytime` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `orgid` double DEFAULT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `amountRange` varchar(50) DEFAULT NULL,
  `bussType` int(11) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `companyIds` varchar(50) DEFAULT NULL,
  `companyName` varchar(50) DEFAULT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `isCredit` int(11) DEFAULT NULL,
  `isLimit` int(11) DEFAULT NULL,
  `isLoan` int(11) DEFAULT NULL,
  `limitRange` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `pdid` varchar(50) DEFAULT NULL,
  `sysId` double DEFAULT NULL,
  `useorg` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ts_variety`
--

LOCK TABLES `ts_variety` WRITE;
/*!40000 ALTER TABLE `ts_variety` DISABLE KEYS */;
INSERT INTO `ts_variety` VALUES (1,'2013-11-27 16:00:00',2,8,NULL,0,2,'2013-12-16 08:20:40',-1,NULL,'0.1,499',0,'V2013112804580800',NULL,NULL,'images/big_icons/48x48/apps/loan.png',0,0,1,NULL,'非上会流程','process1:3:12',3,0),(2,'2013-11-27 16:00:00',2,8,NULL,0,2,'2013-12-16 08:21:09',-1,NULL,'500,500',0,'V2013112805004602',NULL,NULL,'images/big_icons/48x48/apps/companyloans.png',0,0,1,NULL,'500万元上会流程','process1:4:16',3,0),(3,'2013-12-04 16:00:00',2,8,-1,0,2,'2013-12-16 10:07:22',-1,NULL,NULL,0,'V2013120510492604',NULL,NULL,'images/flat_icons/Windows MovieMaker.png',0,0,0,NULL,'并行流程','gaoming_binxing:3:104',3,0),(4,'2013-12-17 16:00:00',2,8,-1,0,2,'2013-12-18 03:13:34',-1,NULL,NULL,0,'V2013121811052706',NULL,NULL,'images/flat_icons/Y\'z Dock.png',0,0,0,NULL,'并行流程2','gaoming_binxing:5:1538',3,0),(5,'2014-03-06 03:58:07',2,8,NULL,0,2,'2014-01-13 02:08:15',-1,NULL,NULL,0,'V2013122401031108',NULL,NULL,'images/big_icons/48x48/places/crystal_clear-style/blockdevice.png',0,0,0,NULL,'过桥贷','process1:18:3324',3,0),(6,'2014-03-06 03:58:26',2,8,NULL,0,2,'2014-01-13 02:06:22',-1,NULL,'500,500',0,'V2013122511521610',NULL,NULL,'images/big_icons/48x48/places/crystal-style/globe.png',0,0,1,NULL,'500万过桥贷','process1:17:3320',3,0),(7,'2014-03-06 03:58:22',2,8,-1,0,2,'2014-01-13 02:04:56',-1,NULL,'0.1,499',0,'V2013122511533012',NULL,NULL,'images/big_icons/48x48/apps/loan.png',0,0,1,NULL,'个人经营性贷款','process1:16:3316',3,0),(8,'2014-03-06 03:58:19',2,8,NULL,0,2,'2014-01-13 02:03:05',-1,NULL,'500,500',0,'V2013122511552914',NULL,NULL,'images/big_icons/48x48/apps/moneys.png',0,0,1,NULL,'500万个人经营性贷款','process1:15:3312',3,0),(9,'2014-03-06 03:58:16',2,8,-1,0,2,'2014-01-13 02:00:52',-1,NULL,'0.1,499',0,'V2013122511584016',NULL,NULL,'images/big_icons/48x48/apps/companyloans.png',0,0,1,NULL,'企业流动性贷款','process1:14:3308',3,0),(10,'2014-03-06 03:44:37',2,8,-1,1,2,'2014-03-06 03:44:37',-1,NULL,'500,500',0,'V2013122511594318',NULL,NULL,'images/big_icons/48x48/apps/compiz-2.png',0,0,1,NULL,'500万企业流动性贷款','gaoming_binxing:1:8',3,0),(11,'2014-03-10 14:07:08',2,8,-1,1,2,'2014-03-10 14:07:08',-1,NULL,NULL,0,'V2014030611383720',NULL,NULL,'images/flat_icons/VMware.png',0,0,0,NULL,'演示流程','process1:1:4',3,0);
/*!40000 ALTER TABLE `ts_variety` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-04-26 21:43:26