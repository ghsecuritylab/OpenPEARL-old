#include "PearlIncludes.h"

pearlrt::Post myPost(1);
pearlrt::Lpc17xxClock myClock(0);
pearlrt::Lpc17xxInterrupt key1(2,11);
pearlrt::Lpc17xxInterrupt int0(2,10);

pearlrt::Lpc17xxDigitalOut led7(2,7,1);
pearlrt::Interrupt * _key1 = &key1;
pearlrt::Interrupt * _int0 = &int0;

pearlrt::Lpc17xxUart u2(2,9600,7,1,'E',0x03);

SPCTASK(ut);

/*
SPCTASK(tkey1);
SPCTASK(tint0);
SPCTASK(start);
SPCTASK(blink);
*/

DCLTASK(ut, pearlrt::Prio(110), pearlrt::BitString<1>(1)) {
   pearlrt::Log::info("uarttask is running");
   char buffer[10];

   u2.dationOpen(NULL,0);
   u2.dationWrite((void*)"Hallo Welt1\n", 12);
   u2.dationWrite((void*)"Hallo Welt2\n", 12);
   u2.dationWrite((void*)"Hallo", 5);
   u2.dationRead(buffer, 5);
   printf("read: ");
   for (int i=0; i<5; i++) {
      printf("%02x ", buffer[i]);
   }
   printf("\n");

   for (int i=0; i<10; i++) {
      u2.dationWrite((void*)"Hallo", 5);
      me->resume(pearlrt::Task::AFTER, pearlrt::Clock(), pearlrt::Duration(2.0));
      u2.dationWrite((void*)" Welt", 5);
      me->resume(pearlrt::Task::AFTER, pearlrt::Clock(), pearlrt::Duration(2.0));
   }
   u2.dationRead(buffer, 5);
   printf("read: ");
   for (int i=0; i<5; i++) {
      printf("%02x ", buffer[i]);
   }
   u2.dationClose(0);
} 
/*
DCLTASK(start, pearlrt::Prio(110), pearlrt::BitString<1>(1)) {
   pearlrt::Log::info("gpioInterrupt is running");

   tkey1.activate(me,pearlrt::Task::WHEN,
           pearlrt::Prio(), pearlrt::Clock(),
              pearlrt::Duration(0),   // after
              pearlrt::Duration(0),	    // all
	      pearlrt::Clock(),     // until
              pearlrt::Duration(0),	    // during
              _key1);  
    _key1->enable();
   tint0.activate(me,pearlrt::Task::WHEN,
           pearlrt::Prio(), pearlrt::Clock(),
              pearlrt::Duration(0),   // after
              pearlrt::Duration(0),	    // all
	      pearlrt::Clock(),     // until
              pearlrt::Duration(0),	    // during
              _int0);  
    _int0->enable();
}

DCLTASK(tkey1, pearlrt::Prio(110), pearlrt::BitString<1>(0)) {
      printf(" key1\n");
      me->resume(pearlrt::Task::AFTER, pearlrt::Clock(), pearlrt::Duration(0.5));
}
DCLTASK(tint0, pearlrt::Prio(110), pearlrt::BitString<1>(0)) {
      printf(" int0\n");
}

DCLTASK(blink, pearlrt::Prio(10), pearlrt::BitString<1>(1)) {
   pearlrt::BitString<1> b; 
   led7.dationOpen(NULL,0);
   while(1) {
      led7.dationWrite(&b, sizeof(b));
      b = b.bitNot();
      me->resume(pearlrt::Task::AFTER, pearlrt::Clock(), pearlrt::Duration(1)); 
   }
}

*/
