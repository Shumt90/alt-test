package data;

import java.util.Random;

public class Garage {

  public static void main(String[] args) {

    int total = 0;
    int rightIfOpinionChanged = 0;
    int rightIfOpinionNotChanged = 0;

    Random random = new Random();

    for (int i = 0; i < 100000; ++i) {

      total++;

      int carLocation = random.nextInt(3);
      int suggestOne = random.nextInt(3);

      int withOutCar = carLocation;

      while (withOutCar == carLocation || withOutCar == suggestOne)//search for garage without car
      {
        withOutCar = random.nextInt(3);
      }

      int changedOpinion = suggestOne;

      while (changedOpinion == suggestOne || changedOpinion == withOutCar)//find one garage except without car and first opinion
      {
        changedOpinion = random.nextInt(3);
      }

      if (carLocation == changedOpinion) {
        rightIfOpinionChanged++;
      } else if (carLocation == suggestOne) {
        rightIfOpinionNotChanged++;
      } else {
        throw new RuntimeException();
      }
    }

    System.out.println(String.format("Total: %d, if change: %d, if not: %d", total, rightIfOpinionChanged, rightIfOpinionNotChanged));

  }


}

