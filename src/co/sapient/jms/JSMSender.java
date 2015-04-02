package co.sapient.jms;

import java.util.Scanner;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JSMSender {
	public static void main(String[] args) {
		try {
			sendMessage("logicalTestQ");
		} catch (NamingException | JMSException e) {
			e.printStackTrace();
		}
		System.out.println("Sending of messages are done....");
	}
	
	public static void sendMessage(String logicalTestQ) throws NamingException, JMSException{
		QueueConnection con=null;
		QueueSession session=null;
		QueueSender sender=null;
		Scanner scanner=null;
		InitialContext context = new InitialContext();
		QueueConnectionFactory factory = (QueueConnectionFactory) context
				.lookup("QueueConnectionFactory");
		//Queue queue = (Queue) context.lookup("dynamicQueues/testQ");
		Queue queue = (Queue) context.lookup(logicalTestQ);

		con = factory.createQueueConnection();
		session = con.createQueueSession(false,
				Session.AUTO_ACKNOWLEDGE);
		sender = session.createSender(queue);
		TextMessage message = session.createTextMessage();
		scanner = new Scanner(System.in);
		String str=null;
		int i=0;
		while (!(str=scanner.nextLine()).equals("0")) {
			message.setIntProperty("count", i++);
			message.setText(str);
			sender.send(message);
		}
		scanner.close();
		sender.close();
		session.close();
		con.close();
	}
}
