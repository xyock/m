#ifndef SLICE_CONNECTOR_H
#define SLICE_CONNECTOR_H

#ifdef __cplusplus
extern "C" {
#endif

typedef enum {
    SLICE_OK = 0,
    SLICE_ERROR_INVALID_ARGUMENT = 1,
    SLICE_ERROR_OUT_OF_RANGE = 2,
    SLICE_ERROR_NOT_IMPLEMENTED = 3
} slice_status;

/* Basic, bounded arithmetic API. Returns a status instead of executing expressions. */
slice_status slice_add(double a, double b, double *result);
slice_status slice_multiply(double a, double b, double *result);

/* Hardware abstraction placeholders. No device is accessed by this starter build. */
slice_status slice_hardware_initialize(void);
slice_status slice_hardware_get_status(int *connected);

#ifdef __cplusplus
}
#endif
#endif
