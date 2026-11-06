#include "slice_connector.h"

/*
 * Safe starter abstraction.
 * Device-specific GPIO, serial, USB, or Bluetooth code must be added only after
 * choosing the target board and documenting its electrical limits.
 */
slice_status slice_hardware_initialize(void) {
    return SLICE_ERROR_NOT_IMPLEMENTED;
}

slice_status slice_hardware_get_status(int *connected) {
    if (connected == 0) return SLICE_ERROR_INVALID_ARGUMENT;
    *connected = 0;
    return SLICE_ERROR_NOT_IMPLEMENTED;
}
