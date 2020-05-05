package java.nio;

import elemental2.core.ArrayBufferView;

public interface HasArrayBufferView {

    ArrayBufferView getTypedArray();

    int getElementSize();
}
