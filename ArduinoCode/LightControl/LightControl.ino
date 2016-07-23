#include <SoftwareSerial.h>

SoftwareSerial BT1(10, 11); // RX | TX
byte bluetoothDataInLine;
String input;
int numeric;
unsigned long previousMillis=0;
const long interval = 100;
unsigned long currentMillis;
void setup() {

  pinMode(12, OUTPUT);
  pinMode(6, OUTPUT);
  delay(500);
  Serial.begin(9600);
  digitalWrite(12, HIGH);
  BT1.begin(9600);
}

void loop() {
  currentMillis = millis();
  if (BT1.available()>0) {
    input = "";
    while (BT1.available()>0) {
      bluetoothDataInLine = BT1.read();
        if (bluetoothDataInLine == ':') {
          break;
      }else{
        input += (char)bluetoothDataInLine;
      }
      delay(1);
    }
    Serial.println(input);
    if (input == "ON") {
      Serial.println("TURNING ON");
      Serial.println(input.toInt());
      digitalWrite(6, HIGH);
      delay(10);
    }else if (input == "OFF") {
      Serial.println("TURNING OFF");
      digitalWrite(6, LOW);
      delay(10);
    }else if((input.toInt()>=0)&&(input.toInt()<=255)) {
      Serial.print("TO INT: ");
      Serial.println(input.toInt());
      analogWrite(6, input.toInt());
      delay(1);
    }
  }
}

void delayNotDelay(long interval){
  if(currentMillis-previousMillis>=interval){
    previousMillis = currentMillis;
  }
}

