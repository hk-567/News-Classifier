package uob.oop;

public class Vector {
    private double[] doubElements;

    public Vector(double[] _elements) {
        //TODO Task 3.1 - 0.5 marks
        doubElements = _elements;
    }

    public double getElementatIndex(int _index) {
        //TODO Task 3.2 - 2 marks
        if (_index >= 0 && _index < doubElements.length) {
            return doubElements[_index];
        } else {
            return -1; //you need to modify the return value
        }
    }

    public void setElementatIndex(double _value, int _index) {
        //TODO Task 3.3 - 2 marks
        if (_index >= 0 && _index < doubElements.length) {
            doubElements[_index] = _value;
        } else {
            doubElements[doubElements.length - 1] = _value;
        }
    }

    public double[] getAllElements() {
        //TODO Task 3.4 - 0.5 marks
        return doubElements; //you need to modify the return value
    }

    public int getVectorSize() {
        //TODO Task 3.5 - 0.5 marks
        return doubElements.length; //you need to modify the return value
    }

    public Vector reSize(int _size) {
        //TODO Task 3.6 - 6 marks
        int vectorLength = this.getVectorSize();
        if (_size <= 0 || _size == vectorLength) {
            return this;
        } else if (_size < vectorLength) {
            double[] newElements = new double[_size];
            for (int i = 0; i < _size; i++) {
                newElements[i] = this.getElementatIndex(i);
            }
            return new Vector(newElements);
        } else {
            double[] newElements = new double[_size];
            for (int i = 0; i < vectorLength; i++) {
                newElements[i] = this.getElementatIndex(i);
            }
            for (int i = vectorLength; i < _size; i++) {
                newElements[i] = -1.0;
            }
            return new Vector(newElements); //you need to modify the return value
        }

    }

    public Vector add(Vector _v) {
        //TODO Task 3.7 - 2 marks
        int maxLength = Math.max(this.getVectorSize(), _v.getVectorSize()); // vectors of different sizes can be added
        this.reSize(maxLength);
        _v.reSize(maxLength);

        // new vector for the addition of the two vectors
        Vector result = new Vector(new double[maxLength]);
        // vector add - add each corresponding element
        for (int i = 0; i < maxLength; i++) {
            result.setElementatIndex(this.getElementatIndex(i) + _v.getElementatIndex(i), i);
        }

        return result; //you need to modify the return value

        /*
        only works for addSameSize - fix later
        if(_v.getVectorSize() > this.getVectorSize()){
            this.reSize(_v.getVectorSize());
        }
        else if (this.getVectorSize()> _v.getVectorSize()){
            _v.reSize(this.getVectorSize());
        }*/

    }


    public Vector subtraction(Vector _v) {
        //TODO Task 3.8 - 2 marks
        int maxLength = Math.max(this.getVectorSize(), _v.getVectorSize());
        this.reSize(maxLength);
        _v.reSize(maxLength);

        Vector result = new Vector(new double[maxLength]);
        for (int i = 0; i < maxLength; i++) {
            result.setElementatIndex(this.getElementatIndex(i) - _v.getElementatIndex(i), i);
        }
        return result; //you need to modify the return value
    }

    public double dotProduct(Vector _v) {
        //TODO Task 3.9 - 2 marks
        int maxLength = Math.max(this.getVectorSize(), _v.getVectorSize());
        this.reSize(maxLength);
        _v.reSize(maxLength);
        // (1,2,3) dot (2,4,6) = 1*2+2*4+3*6 =
        double product = 0.0;
        for (int i = 0; i < maxLength; i++) {
            product += this.getElementatIndex(i) * _v.getElementatIndex(i);
        }
        return product; //you need to modify the return value
    }

    public double cosineSimilarity(Vector _v) {
        //TODO Task 3.10 - 6.5 marks
        /* equation dot product over the size of the vectors 1 and 2
         Plan - result = dotproduct/ maginitusdes fo two vectgors.. use dot p on itself to sqiare values? to add
         also make same length etc as before
*/
        int maxLength = Math.max(this.getVectorSize(), _v.getVectorSize());
        Vector result;
        result = this.reSize(maxLength);
        _v =  _v.reSize(maxLength);


        double dotProduct = result.dotProduct(_v);
        double mag1 = Math.sqrt(result.dotProduct(result));
        double mag2 = Math.sqrt(_v.dotProduct(_v));
        double cosDistance = dotProduct / (mag1 * mag2);

        return cosDistance;

       /* System.out.println("Vector 1: " + result);
        System.out.println("Vector 2: " + _v);
        System.out.println("Vector 1 dimension: " + this.getVectorSize());
        System.out.println("Vector 2 dimension: " + _v.getVectorSize());

          */
/*System.out.println("dot product " + dotProduct);
        System.out.println("magnitude 1 "+ mag1);
        System.out.println(" magnitudew 2 "+ mag2);
        System.out.println("cosDistance: " + cosDistance);*/
/*
        for (int i=0; i< result.getVectorSize();i++){
            System.out.println(result.getElementatIndex(i));
        }*/


    }
    @Override
    public boolean equals(Object _obj) {
        Vector v = (Vector) _obj;
        boolean boolEquals = true;

        if (this.getVectorSize() != v.getVectorSize())
            return false;

        for (int i = 0; i < this.getVectorSize(); i++) {
            if (this.getElementatIndex(i) != v.getElementatIndex(i)) {
                boolEquals = false;
                break;
            }
        }
        return boolEquals;
    }

    @Override
    public String toString() {
        StringBuilder mySB = new StringBuilder();
        for (int i = 0; i < this.getVectorSize(); i++) {
            mySB.append(String.format("%.5f", doubElements[i])).append(",");
        }
        mySB.delete(mySB.length() - 1, mySB.length());
        return mySB.toString();
    }
}
