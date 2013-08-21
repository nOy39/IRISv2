package ru.phsystems.irisv2.speak;

/**
 * IRISv2 Project
 * Author: Nikolay A. Viguro
 * WWW: iris.ph-systems.ru
 * E-Mail: nv@ph-systems.ru
 * Date: 05.12.12
 * Time: 21:32
 * License: GPL v3
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.MapMessage;
import javax.jms.Message;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpeakService implements Runnable
{

    Thread t = null;
    private static Logger log = LoggerFactory.getLogger (SpeakService.class.getName ());

    public SpeakService()
    {
        t = new Thread (this);
        t.start ();
    }

    public Thread getThread()
    {
        return t;
    }

    @Override
    public synchronized void run()
    {

        log.info ("[speak] Service started");

        Message message = null;
        MapMessage m = null;
        ExecutorService exs = Executors.newFixedThreadPool (10);

        try
        {

            while ((message = Service.messageConsumer.receive (0)) != null)
            {
                m = (MapMessage) message;

                if(m.getString ("qpid.subject").equals ("event.speak"))
                {
                    log.info ("------------- Speak -------------");
                    log.info ("Confidence: " + m.getDouble ("confidence"));
                    log.info ("Text: " + m.getString ("text"));
                    log.info ("-------------------------------\n");

                    Synthesizer Voice = new Synthesizer (exs);
                    Voice.setAnswer (m.getString ("text"));
                    exs.submit (Voice).get ();
                }
            }

            Service.msg.close ();

        } catch (Exception e)
        {
            e.printStackTrace ();  //To change body of catch statement use File | Settings | File Templates.
            log.info ("Get error! " + m);
        }
    }
}
